package com.example.myfoodapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myfoodapp.ui.home.HomeFragment;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.ui.foodlist.FoodlistFragment;

import java.util.List;

public class Food_Type_Adapter extends RecyclerView.Adapter<Food_Type_Adapter.FoodTypeHolder>{
    private List<Food_Type> typeList;
    private Context context;

    public Food_Type_Adapter(Context context,List<Food_Type> typeList) {
        this.typeList = typeList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type,parent,false);
        return new FoodTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodTypeHolder holder, int position) {
        Food_Type food_type= typeList.get(position);
        if(food_type==null) return;
        // Chuyển đổi mảng byte thành đối tượng Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(food_type.getImg(), 0, food_type.getImg().length);
        holder.img.setImageBitmap(bitmap);
        holder.tv.setText(food_type.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("food_type",  food_type);

                // Sử dụng Navigation Component để chuyển đến Fragment mới và truyền dữ liệu
                Navigation.findNavController(v).navigate(R.id.nav_listfood, bundle);
            }
        });


    }


    @Override
    public int getItemCount() {
        if(typeList!=null)
            return typeList.size();
        return 0;
    }

    public static class FoodTypeHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView tv;
        private CardView cardView;
        public FoodTypeHolder(@NonNull View view) {
            super(view);
            img= view.findViewById(R.id.img_type);
            tv = view.findViewById(R.id.tv_type);
            cardView=view.findViewById(R.id.layout_itemtype);
        }

    }

}
