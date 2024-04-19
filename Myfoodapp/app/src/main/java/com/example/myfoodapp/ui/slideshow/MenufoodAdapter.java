package com.example.myfoodapp.ui.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.model.Food;

import java.util.List;

public class MenufoodAdapter extends RecyclerView.Adapter<MenufoodAdapter.MenufoodHolder>{
    private List<Food> typeList;

    public MenufoodAdapter(List<Food> typeList) {
        this.typeList = typeList;
    }

    @NonNull
    @Override
    public MenufoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menufood,parent,false);
        return new MenufoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenufoodHolder holder, int position) {
        Food food= typeList.get(position);
        if(food==null) return;
        holder.img.setImageResource(food.getImg());
        holder.tv.setText(food.getName());
    }



    @Override
    public int getItemCount() {
        if(typeList!=null)
            return typeList.size();
        return 0;
    }

    public class MenufoodHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView tv;
        public MenufoodHolder(@NonNull View view) {
            super(view);
            img= view.findViewById(R.id.img);
            tv = view.findViewById(R.id.tv);
        }

    }
}
