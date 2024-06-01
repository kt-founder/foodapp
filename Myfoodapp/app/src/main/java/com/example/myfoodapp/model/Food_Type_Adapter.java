package com.example.myfoodapp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;

import java.util.Base64;
import java.util.List;

public class Food_Type_Adapter extends RecyclerView.Adapter<Food_Type_Adapter.FoodTypeHolder>{
    private List<TypeFood> typeList;
    private Context context;

    public Food_Type_Adapter(Context context,List<TypeFood> typeList) {
        this.typeList = typeList;
        this.context = context;
    }

    public Food_Type_Adapter(List<TypeFood> body) {
        this.typeList= body;
    }

    @NonNull
    @Override
    public FoodTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type,parent,false);
        return new FoodTypeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodTypeHolder holder, int position) {
        TypeFood food_type= typeList.get(position);
        if(food_type==null) return;

        //Chuyển đổi mảng byte thành đối tượng Bitmap
        byte[] bytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            bytes = Base64.getDecoder().decode(food_type.getImg().getBytes());
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.img.setImageBitmap(bitmap);
        holder.tv.setText(food_type.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("TF",  food_type);

                // Sử dụng Navigation Component để chuyển đến Fragment mới và truyền dữ liệu
                Navigation.findNavController(v).navigate(R.id.nav_listfood,bundle);
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
