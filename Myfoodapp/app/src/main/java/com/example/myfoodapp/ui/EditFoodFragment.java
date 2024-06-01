package com.example.myfoodapp.ui;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentEditFoodBinding;
import com.example.myfoodapp.model.FT_AddFood_Adapter;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.model.TypeFood;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFoodFragment extends Fragment {
    private FragmentEditFoodBinding binding;
    private int request_cam =123,request_file =124;
    private FT_AddFood_Adapter adapter;
    private Food food = new Food();
    private TextView thongbao;
    private EditText iptenmon,ipmota, ipnguyenlieu,ipcachlam,ipchatdam,ipchatduong,
            ipchatbeo,ipchatxo,ipcalo,ipvideo;
    private Spinner sptime;
    private ImageView img;
    private ImageButton btcam,btfile;
    private Button btthem;
    private RecyclerView recyclerView;
    private List<TypeFood> typeFoodList = new ArrayList<>();


    public EditFoodFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditFoodBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Lấy dữ liệu từ Bundle
            food =(Food) bundle.getSerializable("Fo");
        }
        iptenmon = binding.inputTenmon;
        iptenmon.setText(food.getName());

        ipmota = binding.inputMota;
        ipmota.setText(food.getDetail());
        sptime = binding.spTime;

        recyclerView = binding.revFtAddfood;
        setupRcv();

        ipnguyenlieu = binding.inputNguyenlieu;
        ipnguyenlieu.setText(food.getIngredient());

        ipcachlam = binding.inputCachlam;
        ipcachlam.setText(food.getGuide());

        String[] dd = food.getNutrition().split(",");
        ipchatdam = binding.chatdam;
        ipchatdam.setText(dd[0]);

        ipchatduong = binding.chatduong;
        ipchatduong.setText(dd[1]);

        ipchatbeo = binding.chatbeo;
        ipchatbeo.setText(dd[2]);

        ipchatxo = binding.chatxo;
        ipchatxo.setText(dd[3]);

        ipcalo = binding.calo;
        ipcalo.setText(dd[4]);

        img = binding.img;
        byte[] bytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            bytes = Base64.getDecoder().decode(food.getImage().getBytes());
        }
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        img.setImageBitmap(bitmap1);

        btcam = binding.btCam;
        btfile = binding.btFile;
        ipvideo = binding.inputVideo;
        ipvideo.setText(food.getVideo());
        btthem = binding.btUpload;


        btcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent,request_cam);

            }



        });
        btfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,request_file);
            }
        });

        // Nut Save
        btthem.setOnClickListener(view1->{
            String ten = iptenmon.getText().toString().trim();
            String mota = ipmota.getText().toString().trim();
            String time =sptime.getSelectedItem().toString().trim();

            String nguyenlieu =ipnguyenlieu.getText().toString().trim();
            String cachlam = ipcachlam.getText().toString().trim();
            String dinhduong = ipchatdam.getText().toString().trim() +","+
                    ipchatduong.getText().toString().trim()+","+
                    ipchatbeo.getText().toString().trim()+","+
                    ipchatxo.getText().toString().trim()+","+
                    ipcalo.getText().toString().trim();
            // Xu ly anh
            String imag="";
            BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
            byte[] image = byteArray.toByteArray();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                imag = Base64.getEncoder().encodeToString(image);
            }

            String video = ipvideo.getText().toString().trim();

            typeFoodList = adapter.getTypeFoods();



            food.setName(ten);
            food.setTime(time);
            food.setDetail(mota);
            food.setIngredient(nguyenlieu);
            food.setGuide(cachlam);
            food.setNutrition(dinhduong);
            food.setImage(imag);
            food.setVideo(video);
            food.setIdAut(1);


            RetrofitService retrofit = new RetrofitService();
            FoodApi foodApi1 =retrofit.getRetrofit().create(FoodApi.class);
            Set<TypeFood> foodlink = new HashSet(typeFoodList);
            food.setTypefoods(foodlink);
            //food.setState(true);
            foodApi1.UpdateFood(food.getId(),food).enqueue(new Callback<String>() {

                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(getContext(), "a", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    Toast.makeText(getContext(), "aa", Toast.LENGTH_SHORT).show();
                }
            });
            Navigation.findNavController(view1).navigate(R.id.nav_admin_quanly);
        });

        return view;
    }
    public void setupRcv(){
        RetrofitService retrofitService = new RetrofitService();
        TypeFoodApi typeFoodApi = retrofitService.getRetrofit().create(TypeFoodApi.class);

        typeFoodApi.getAllTypeFood().enqueue(new Callback<List<TypeFood>>() {
            @Override
            public void onResponse(Call<List<TypeFood>> call, Response<List<TypeFood>> response) {
                Toast.makeText(getContext(), "aa", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful() && response.body() != null) {
//                    showTypeFoodList(response.body());
                    adapter = new FT_AddFood_Adapter(response.body(),getContext());
                    recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 5));
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(requireContext(), "Failed to retrieve food types", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TypeFood>> call, Throwable throwable) {
                Log.d("fa",throwable.toString());
            }
        });
    }


    @Override
    public void onActivityResult(int request_code, int result_code, Intent data){
        if(request_code == request_cam && result_code == RESULT_OK && data != null){

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if(bitmap != null && img != null){
                img.setImageBitmap(bitmap);
            }
        }
        if(request_code == request_file && result_code == RESULT_OK && data != null){
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
