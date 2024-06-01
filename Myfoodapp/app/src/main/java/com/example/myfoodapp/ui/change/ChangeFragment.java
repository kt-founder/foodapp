package com.example.myfoodapp.ui.change;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.model.Food;

import java.util.Base64;

public class ChangeFragment extends Fragment {



    public static ChangeFragment newInstance() {
        return new ChangeFragment();
    }
    private Food food;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        View view = inflater.inflate(R.layout.fragment_change, container, false);
        EditText id = view.findViewById(R.id.textViewNguyenLieu);
        if(bundle != null){
            food = (Food) bundle.getSerializable("foodItem");
            ImageView im = view.findViewById(R.id.imageViewDish);

            if (food != null) {
                byte[] bytes = new byte[0];
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    bytes = Base64.getDecoder().decode(food.getImage().getBytes());
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                im.setImageBitmap(bitmap);
                id.setText(food.getIngredient());
                Button button1 = view.findViewById(R.id.bt_Save);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Sử dụng Navigation Component để chuyển đến Fragment mới
                        // Lay du lieu moi
                        String newNL = id.getText().toString();
                        food.setIngredient(newNL);
                        bundle.putSerializable("foodItem",food);
                        Navigation.findNavController(v).navigate(R.id.nav_gallery, bundle);
                    }
                });
            }


        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}