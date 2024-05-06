package com.example.myfoodapp.ui.upload;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myfoodapp.R;
import com.example.myfoodapp.dao.FoodDAO;
import com.example.myfoodapp.databinding.FragmentUploadBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UploadFragment extends Fragment {
    private FoodDAO foodDAO;
    private TextView thongbao;
    private EditText input_monan, input_mota,nguyenlieu,cachlam,video;
    private Spinner sp_time;
    private CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,cb10,cb11,cb12,cb13,cb14;
    private EditText chatdam,chatduong,chatbeo,chatxo,calo;
    private ImageView img;
    private ImageButton file,cam;
    private Button bt_them;
    private int request_cam =123,request_file =124;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_upload, container, false);
        thongbao = view.findViewById(R.id.thongbao);
        input_monan = view.findViewById(R.id.input_tenmon);
        input_mota = view.findViewById(R.id.input_mota);
        nguyenlieu = view.findViewById(R.id.nguyenlieu);
        cachlam = view.findViewById(R.id.cachlam);
        video = view.findViewById(R.id.video);
        sp_time= view.findViewById(R.id.sp_time);
        cb1 = view.findViewById(R.id.bunpho);
        cb2 = view.findViewById(R.id.haisan);
        cb3= view.findViewById(R.id.thibo);
        cb4 = view.findViewById(R.id.thitga);
        cb5 = view.findViewById(R.id.thitheo);
        cb6 = view.findViewById(R.id.goinom);
        cb7 = view.findViewById(R.id.chao);
        cb8 = view.findViewById(R.id.monchay);
        cb9 = view.findViewById(R.id.banhmi_xoi);
        cb10 = view.findViewById(R.id.monxao);
        cb11 = view.findViewById(R.id.monkho);
        cb12 = view.findViewById(R.id.monchien);
        cb13 = view.findViewById(R.id.anvat);
        cb14 = view.findViewById(R.id.montet);
        chatdam= view.findViewById(R.id.chatdam);
        chatduong = view.findViewById(R.id.chatduong);
        chatbeo = view.findViewById(R.id.chatbeo);
        chatxo = view.findViewById(R.id.chatxo);
        calo = view.findViewById(R.id.calo);
        img = view.findViewById(R.id.img);
        file = view.findViewById(R.id.buttonfile);
        cam = view.findViewById(R.id.buttoncam);
        bt_them = view.findViewById(R.id.bt_Them);

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
        bt_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodDAO = new FoodDAO(getContext());
                BitmapDrawable  bitmapDrawable = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                byte[] hanh = byteArray.toByteArray();
                String ten = input_monan.getText().toString().trim();
                String mota = input_mota.getText().toString().trim();
                String nlieu = nguyenlieu.getText().toString().trim();
                String clam = cachlam.getText().toString().trim();
                String vdeo = video.getText().toString().trim();
                String time = sp_time.getSelectedItem().toString().trim();
                String loai ="";

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
                String dduong = chatdam.getText().toString().trim()+" "+chatduong.getText().toString().trim()+" "+
                        chatbeo.getText().toString().trim()+" "+chatxo.getText().toString().trim()+" "+calo.getText().toString().trim();


                foodDAO.insertFood(ten,mota,hanh,time,loai,nlieu,dduong,clam,vdeo);




            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int request_code, int result_code, Intent data){
        // cam
        if(request_code == request_cam && result_code == RESULT_OK && data != null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if(bitmap != null && img != null){
                img.setImageBitmap(bitmap);
            }
        }
        // file
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