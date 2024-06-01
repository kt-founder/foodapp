package com.example.myfoodapp.ui.upload;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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


import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentUploadBinding;
import com.example.myfoodapp.server.TypeFoodApi;
import com.example.myfoodapp.model.FT_AddFood_Adapter;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;
import com.example.myfoodapp.model.TypeFood;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadFragment extends Fragment {

    private static final int REQUEST_IMAGE_IMPORT = 1;
    private FragmentUploadBinding binding;
    private static final int PICK_FILE_REQUEST = 1;
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

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        binding = FragmentUploadBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

//        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        String authID = sharedPreferences.getString("UserId",null);
        thongbao =binding.thongbao;
        iptenmon = binding.inputTenmon;
        ipmota = binding.inputMota;
        sptime = binding.spTime;

        recyclerView = binding.revFtAddfood;
        setupRcv();
        // Lay du lieu share
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int authID = sharedPreferences.getInt("UserId", -1);

        ipnguyenlieu = binding.inputNguyenlieu;
        ipcachlam = binding.inputCachlam;
        ipchatdam = binding.chatdam;
        ipchatduong = binding.chatduong;
        ipchatbeo = binding.chatbeo;
        ipchatxo = binding.chatxo;
        ipcalo = binding.calo;
        img = binding.img;
        btcam = binding.btCam;
        btfile = binding.btFile;
        ipvideo = binding.inputVideo;
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
            BitmapDrawable  bitmapDrawable = (BitmapDrawable) img.getDrawable();
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
            food.setIdAut(authID);
            // Re tro fit
            RetrofitService retrofit = new RetrofitService();
            FoodApi foodApi1 =retrofit.getRetrofit().create(FoodApi.class);
            //Set<Food> foodlink = new HashSet(typeFoodList);
            Set<TypeFood> typefoods1 = food.getTypefoods();
            typefoods1.addAll(typeFoodList);
            food.setTypefoods(typefoods1);
            foodApi1.addFoods(food).enqueue(new Callback<Food>() {
                @Override
                public void onResponse(Call<Food> call, Response<Food> response) {

                }

                @Override
                public void onFailure(Call<Food> call, Throwable throwable) {
                    Toast.makeText(getContext(),"Them thanh cong",Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.nav_upload_congThuc);
                }
            });
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
                    adapter = new FT_AddFood_Adapter(response.body(), getContext());
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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Tiếp tục với logic sử dụng authID
    }



}