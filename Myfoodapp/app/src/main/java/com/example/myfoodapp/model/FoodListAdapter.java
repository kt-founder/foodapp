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

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodlistHolder>{
    private List<Food> typeList;

    public FoodListAdapter(Context context, List<Food> typeList) {

        this.typeList = typeList;
    }

    @NonNull
    @Override
    public FoodlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foodlist,parent,false);
        return new FoodlistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodlistHolder holder, int position) {
        Food food= typeList.get(position);
        if(food==null) return;
        // holder.img.setImageResource(food.getImg());
        holder.tv.setText(food.getName());
        byte[] bytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            bytes = Base64.getDecoder().decode(food.getImage().getBytes());
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.img.setImageBitmap(bitmap);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("foodItem",  food);
                Navigation.findNavController(v).navigate(R.id.nav_gallery,bundle);
            }

        });


    }



    @Override
    public int getItemCount() {
        if(typeList!=null)
            return typeList.size();
        return 0;
    }

    public class FoodlistHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView tv;
        private CardView cardView;
        public FoodlistHolder(@NonNull View view) {
            super(view);
            img= view.findViewById(R.id.img);
            tv = view.findViewById(R.id.tv);
            cardView = view.findViewById(R.id.layout_item_foodlist);
        }

    }
}
