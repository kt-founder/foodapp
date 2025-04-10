package com.example.myfoodapp.ui;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentEditFoodBinding;
import com.example.myfoodapp.adapter.FT_AddFood_Adapter;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.model.FoodDto;
import com.example.myfoodapp.model.TypeFood;
import com.example.myfoodapp.model.TypeFoodResponseDto;
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;
import com.example.myfoodapp.server.TypeFoodApi;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFoodFragment extends Fragment {
    private FragmentEditFoodBinding binding;
    private int request_cam = 123, request_file = 124;
    private FT_AddFood_Adapter adapter;
    private Food food = new Food();
    private TextView thongbao;
    private EditText iptenmon, ipmota, ipnguyenlieu, ipcachlam, ipchatdam, ipchatduong,
            ipchatbeo, ipchatxo, ipcalo, ipvideo;
    private Spinner sptime;
    private ImageView img;
    private ImageButton btcam, btfile;
    private Button btthem;
    private RecyclerView recyclerView;
    private List<TypeFoodResponseDto> typeFoodList = new ArrayList<>();

    public EditFoodFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditFoodBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Get the foodId from the Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            int foodId = bundle.getInt("foodId");

            // Call API to get the existing food data by foodId
            RetrofitService retrofitService = new RetrofitService();
            FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);

            foodApi.getFoodById(foodId).enqueue(new Callback<FoodDto>() {
                @Override
                public void onResponse(Call<FoodDto> call, Response<FoodDto> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        food = response.body().toFood(); // Convert FoodDto to Food model
                        updateUI(food);
                        setupRcv();
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch food data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<FoodDto> call, Throwable throwable) {
                    Toast.makeText(getContext(), "Error fetching food details", Toast.LENGTH_SHORT).show();
                }
            });
            btcam = binding.btCam;
            btfile = binding.btFile;
            btthem = binding.btUpload;
            thongbao = binding.tvNamePage;
            iptenmon = binding.inputTenmon;
            ipmota = binding.inputMota;
            sptime = binding.spTime;
            ipnguyenlieu = binding.inputNguyenlieu;
            ipcachlam = binding.inputCachlam;
            ipchatdam = binding.chatdam;
            ipchatduong = binding.chatduong;
            ipchatbeo = binding.chatbeo;
            ipchatxo = binding.chatxo;
            ipcalo = binding.calo;
            ipvideo = binding.inputVideo;
            img = binding.img;
            recyclerView = binding.revFtAddfood;
            thongbao.setText("Chỉnh sửa công thức của bạn");
        }

        // Set up the recycler view for selecting food types


        btcam.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, request_cam);
        });

        btfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, request_file);
        });

        btthem.setOnClickListener(view1 -> {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            int authID = sharedPreferences.getInt("UserId", -1);
            // Collect data from the input fields
            String ten = iptenmon.getText().toString().trim();
            String mota = ipmota.getText().toString().trim();
            String time = sptime.getSelectedItem().toString().trim();
            String nguyenlieu = ipnguyenlieu.getText().toString().trim();
            String cachlam = ipcachlam.getText().toString().trim();
            String dinhduong = ipchatdam.getText().toString().trim() + "," +
                    ipchatduong.getText().toString().trim() + "," +
                    ipchatbeo.getText().toString().trim() + "," +
                    ipchatxo.getText().toString().trim() + "," +
                    ipcalo.getText().toString().trim();

            // Convert image to base64
            String imag = "";
            BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
            byte[] image = byteArray.toByteArray();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imag = Base64.getEncoder().encodeToString(image);
            }

            String video = ipvideo.getText().toString().trim();

            typeFoodList = adapter.getTypeFoods();

            // Update food object with new values
            food.setName(ten);
            food.setTime(time);
            food.setDetail(mota);
            food.setIngredient(nguyenlieu);
            food.setGuide(cachlam);
            food.setNutrition(dinhduong);
            food.setImage(imag);
            food.setVideo(video);
            food.setIdAut(authID); // Set a default or existing value for idAut

            // Prepare the food data for update
            Set<TypeFood> foodlink = new HashSet<>();
            for(TypeFoodResponseDto tf : typeFoodList){
                TypeFood typeFood = new TypeFood();
                typeFood.setId(tf.getId());
                typeFood.setName(tf.getName());
                typeFood.setImg(tf.getImageBase64());
                foodlink.add(typeFood);
//                Log.e("food", tf.getName());
            }
            food.setTypefoods(foodlink);

            RetrofitService retrofitService = new RetrofitService();
            FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);

            // Call the API to update food
            foodApi.updateFood(food.getId(), food).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(getContext(), "Food updated successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view1).navigate(R.id.nav_admin_quanly);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    Toast.makeText(getContext(), "Failed to update food", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }

    private void updateUI(Food food) {
        iptenmon.setText(food.getName());
        ipmota.setText(food.getDetail());
        ipnguyenlieu.setText(food.getIngredient());
        ipcachlam.setText(food.getGuide());

        String[] dd = food.getNutrition().split(",");
        ipchatdam.setText(dd[0]);
        ipchatduong.setText(dd[1]);
        ipchatbeo.setText(dd[2]);
        ipchatxo.setText(dd[3]);
        ipcalo.setText(dd[4]);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sptime.setAdapter(adapter);
        String foodTime = food.getTime();
        int spinnerPosition = adapter.getPosition(foodTime);
        sptime.setSelection(spinnerPosition);


        byte[] bytes = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            bytes = Base64.getDecoder().decode(food.getImage());
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        img.setImageBitmap(bitmap);
        ipvideo.setText(food.getVideo());
    }

    public void setupRcv() {
        RetrofitService retrofitService = new RetrofitService();
        TypeFoodApi typeFoodApi = retrofitService.getRetrofit().create(TypeFoodApi.class);

        typeFoodApi.getAllTypeFood1().enqueue(new Callback<List<TypeFoodResponseDto>>() {
            @Override
            public void onResponse(Call<List<TypeFoodResponseDto>> call, Response<List<TypeFoodResponseDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new FT_AddFood_Adapter(response.body(), getContext());

                    Set<TypeFood> selectedTypeFoods = food.getTypefoods();
//                    selectedTypeFoods.add(new TypeFood(1,"Món xào",""));
//                    Log.d("fa", food.getTypefoods().toString());
                    adapter.setSelectedTypeFoods(selectedTypeFoods);
                    recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 5));
                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(requireContext(), "Failed to retrieve food types", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TypeFoodResponseDto>> call, Throwable throwable) {
                Log.d("fa", throwable.toString());
            }
        });
    }

    @Override
    public void onActivityResult(int request_code, int result_code, Intent data) {
        if (request_code == request_cam && result_code == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if (bitmap != null && img != null) {
                img.setImageBitmap(bitmap);
            }
        }
        if (request_code == request_file && result_code == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(request_code, result_code, data);
    }
}

