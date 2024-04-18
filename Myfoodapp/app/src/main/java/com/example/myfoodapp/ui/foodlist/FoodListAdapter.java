package com.example.myfoodapp.ui.foodlist;

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
import com.example.myfoodapp.ui.home.Food;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodlistHolder>{
    private List<Food> typeList;

    public FoodListAdapter(List<Food> typeList) {

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
        holder.img.setImageResource(food.getImg());
        holder.tv.setText(food.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_gallery);
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
