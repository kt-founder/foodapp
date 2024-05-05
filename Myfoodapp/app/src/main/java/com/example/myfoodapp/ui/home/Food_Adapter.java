package com.example.myfoodapp.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;

import java.util.ArrayList;
import java.util.List;

public class Food_Adapter extends RecyclerView.Adapter<Food_Adapter.FoodHolder> implements Filterable {
    private List<Food> foodList;
    private List<Food> mfoodList;
    private Context context;

    public Food_Adapter(Context context, List<Food> foodList) {
        this.foodList = foodList;
        this.mfoodList = new ArrayList<>(foodList); // Sao chép danh sách ban đầu
        this.context = context;
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        Food food = foodList.get(position);
        if (food == null) return;
        //holder.img.setImageBitmap(food.getImage());
        holder.tv_name.setText(food.getName());
        holder.tv_time.setText(food.getTime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_gallery);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tv_name, tv_time;
        private RelativeLayout cardView;

        public FoodHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img);
            tv_name = view.findViewById(R.id.tv_name);
            tv_time = view.findViewById(R.id.tv_time);
            cardView = view.findViewById(R.id.layout_food);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString().toLowerCase().trim();
                if (strSearch.isEmpty()) {
                    foodList.clear();
                    foodList.addAll(mfoodList); // Khôi phục danh sách ban đầu
                } else {
                    List<Food> filteredList = new ArrayList<>();
                    for (Food food : mfoodList) {
                        if (food.getName().toLowerCase().contains(strSearch)) {
                            filteredList.add(food);
                        }
                    }
                    foodList.clear();
                    foodList.addAll(filteredList);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = foodList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }
}
