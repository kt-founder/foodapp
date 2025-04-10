package com.example.myfoodapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.model.FoodDto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Food_Adapter extends RecyclerView.Adapter<Food_Adapter.FoodHolder> implements Filterable {
    private List<FoodDto> foodList;  // Danh sách hiển thị trong RecyclerView
    private List<FoodDto> mfoodList; // Danh sách gốc không thay đổi
    private Context context;

    public Food_Adapter(Context context, List<FoodDto> foodList) {
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
        FoodDto food = foodList.get(position);
        if (food == null) return;

        // Hiển thị tên món ăn và thời gian
        holder.tv_name.setText(food.getName());
        holder.tv_tgian.setText(food.getTime());

        // Chuyển đổi ảnh từ Base64 thành Bitmap
        if (food.getImageBase64() != null && !food.getImageBase64().isEmpty()) {
            byte[] bytes = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(food.getImageBase64());
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.img.setImageBitmap(bitmap);
        }

        // Xử lý khi người dùng click vào một món ăn
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("foodId", food.getId());
                Navigation.findNavController(v).navigate(R.id.nav_gallery, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class FoodHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tv_name;
        private TextView tv_tgian;
        private RelativeLayout cardView;

        public FoodHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img);
            tv_name = view.findViewById(R.id.tv_tenMon);
            tv_tgian = view.findViewById(R.id.tv_time);
            cardView = view.findViewById(R.id.layout_food);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString().toLowerCase().trim();

                // Nếu không có tìm kiếm, phục hồi danh sách gốc
                if (strSearch.isEmpty()) {
                    foodList.clear();
                    foodList.addAll(mfoodList); // Khôi phục danh sách ban đầu
                } else {
                    List<FoodDto> filteredList = new ArrayList<>();
                    for (FoodDto food : mfoodList) {
                        // Kiểm tra tên món ăn có chứa từ khóa tìm kiếm
                        if (food.getName() != null && food.getName().toLowerCase().contains(strSearch)) {
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
                // Cập nhật UI sau khi lọc
                if (filterResults.values != null) {
                    foodList = (List<FoodDto>) filterResults.values;
                    notifyDataSetChanged();
                }
            }
        };
    }
}

