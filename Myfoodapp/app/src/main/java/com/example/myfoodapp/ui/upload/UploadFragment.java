package com.example.myfoodapp.ui.upload;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewDatabase;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentUploadBinding;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.entity.FoodApi;
import com.example.myfoodapp.entity.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serial;
import java.sql.Blob;
import java.sql.SQLException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadFragment extends Fragment {

    private static final int REQUEST_IMAGE_IMPORT = 1;
    private FragmentUploadBinding binding;
    private static final int PICK_FILE_REQUEST = 1;
    private int request_cam =123,request_file =124;
    private ImageView img;
    private Food food = new Food();
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        UploadViewModel uploadViewModel =
                new ViewModelProvider(this).get(UploadViewModel.class);
        binding = FragmentUploadBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Tạo food


        // input image
//        binding.inputImage.setOnClickListener(v->{
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            //intent.setAction(Intent.ACTION_GET_CONTENT);
//            Uri uri = intent.getData();
//            String path = uri.getPath();
//            File file = new File(path);
//            byte[] bytesArray = new byte[(int) file.length()];
//
//            FileInputStream fis = null;
//            try {
//                fis = new FileInputStream(file);
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                fis.read(bytesArray); // Đọc dữ liệu từ file vào mảng bytesArray
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                fis.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            Blob blob = null;
//            try {
//                blob.setBytes(1,bytesArray);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            food.setImage(blob);
//
//        });


        Button buttonSave = view.findViewById(R.id.bt_Upload);

        RetrofitService retrofitService = new RetrofitService();
        img = binding.img;
        ImageButton file = binding.btFile;
        ImageButton cam = binding.btCam;

        FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent,request_cam);

            }



        });
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,request_file);
            }
        });

        // Nut Save
        buttonSave.setOnClickListener(view1->{
            EditText inputProtid= binding.chatdam;
            EditText inputlipid= binding.chatbeo;
            EditText inputglucid= binding.chatduong;
            EditText inputXo= binding.chatxo;
            EditText inputCalo= binding.calo;



            String value = inputProtid.getText().toString().trim();
            String value2 = inputlipid.getText().toString().trim();
            String value3 = inputglucid.getText().toString().trim();
            String value4 = inputXo.getText().toString().trim();
            String value5 = inputCalo.getText().toString().trim();
            String nutrition = "protid: " + value + " ,lipit: "+value2 + " ,glucid: "+ value3 + " ,chat xo: " + value4 +" ,calo: "+ value5;
            food.setNutrition(nutrition.trim());
            // Add text

            //Check Box input
            CheckBox cb1= binding.bunpho;
            CheckBox cb2= binding.haisan;
            CheckBox cb3= binding.thibo;
            CheckBox cb4= binding.thitga;
            CheckBox cb5= binding.thitheo;
            CheckBox cb6= binding.goinom;
            CheckBox cb7= binding.chao;
            CheckBox cb8= binding.monchay;
            CheckBox cb9= binding.banhmiXoi;
            CheckBox cb10= binding.monxao;
            CheckBox cb11= binding.monkho;
            CheckBox cb12= binding.monchien;
            CheckBox cb13= binding.anvat;
            CheckBox cb14= binding.montet;



            //Time
            Spinner inputTime = binding.spTime;

            // EditText Input
            EditText inputEditName= binding.inputTenmon;
            EditText inputEditNguyenLieu = binding.inputNguyenlieu;
            EditText inputEditCachLam = binding.inputCachlam;
            EditText inputEditMota = binding.inputMota;
            EditText inputEditVideo = binding.inputVideo;
            // Xu ly du liẹu String
            String name = String.valueOf(inputEditName.getText());
            String guide = String.valueOf(inputEditCachLam.getText());
            String nguyenLieu = String.valueOf(inputEditNguyenLieu.getText());
            String mota = String.valueOf(inputEditMota.getText());
            String time = inputTime.getSelectedItem().toString().trim();
            String video = String.valueOf(inputEditVideo.getText());
            String loai = "";


            // Xu ly anh
            BitmapDrawable  bitmapDrawable = (BitmapDrawable) img.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
            byte[] image = byteArray.toByteArray();
            System.out.println(image.toString());
            //Blob blob = Hibernate.createBlob(image);

            food.setImage(image);

            // Xu ly loai mon an
            if(cb1.isChecked()) loai+="bunpho ";
            if(cb2.isChecked()) loai+="haisan ";
            if(cb3.isChecked()) loai+="thitbo ";
            if(cb4.isChecked()) loai+="thitga ";
            if(cb5.isChecked()) loai+="thitheo ";
            if(cb6.isChecked()) loai+="goinom";
            if(cb7.isChecked()) loai+="chao ";
            if(cb8.isChecked()) loai+="monchay ";
            if(cb9.isChecked()) loai+="banhmi_xoi ";
            if(cb10.isChecked()) loai+="monkho ";
            if(cb11.isChecked()) loai+="monxao ";
            if(cb12.isChecked()) loai+="monchien ";
            if(cb13.isChecked()) loai+="anvat ";
            if(cb14.isChecked()) loai+="montet ";
            loai = loai.trim();
            //
            food.setIdAut(1);
            food.setTime(time);
            food.setName(name);
            food.setGuide(guide);
            food.setIngredient(nguyenLieu);
            food.setDetail(mota);
            food.setVideo(video);

            foodApi.save(food)
                    .enqueue(new Callback<List<Food>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<Food>> call, @NonNull Response<List<Food>> response) {
                            Toast.makeText(requireContext(), "Save failed!",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<Food>> call, @NonNull Throwable throwable) {
                            Toast.makeText(requireContext(),"Save sucessed!!", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(UploadFragment.class.getName()).log(Level.SEVERE, "Erorr occurred");
                        }
                    });
        });

        return view;
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