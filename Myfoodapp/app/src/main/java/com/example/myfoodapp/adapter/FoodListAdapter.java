package com.example.myfoodapp.adapter;

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
import com.example.myfoodapp.model.FoodItemDto;

import java.util.Base64;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodlistHolder> {
    private List<FoodItemDto> foodItemList; // Danh sách FoodItemDto

    // Constructor để khởi tạo dữ liệu
    public FoodListAdapter(Context context, List<FoodItemDto> foodItemList) {
        this.foodItemList = foodItemList;
    }

    @NonNull
    @Override
    public FoodlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng item trong RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foodlist, parent, false);
        return new FoodlistHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodlistHolder holder, int position) {
        // Lấy FoodItemDto tại vị trí tương ứng
        FoodItemDto foodItem = foodItemList.get(position);
        if (foodItem == null) return;

        // Hiển thị tên món ăn
        holder.tv.setText(foodItem.getName());

        // Chuyển đổi Base64 image thành Bitmap
        byte[] bytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            bytes = Base64.getDecoder().decode(foodItem.getImageBase64().getBytes()); // Chuyển đổi Base64 thành mảng byte
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length); // Chuyển đổi mảng byte thành Bitmap
        holder.img.setImageBitmap(bitmap); // Gán Bitmap vào ImageView

        // Xử lý sự kiện khi người dùng click vào món ăn
        holder.cardView.setOnClickListener(v -> {
            // Chuyển Bundle chứa đối tượng FoodItemDto sang fragment khác
            Bundle bundle = new Bundle();
            bundle.putInt("foodId", foodItem.getId()); // Chuyển FoodItemDto qua Fragment
            Navigation.findNavController(v).navigate(R.id.nav_gallery, bundle); // Điều hướng đến fragment mới
        });
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng món ăn trong danh sách
        if (foodItemList != null)
            return foodItemList.size();
        return 0;
    }

    // ViewHolder để lưu trữ các views trong item của RecyclerView
    public static class FoodlistHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tv;
        private CardView cardView;

        public FoodlistHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img); // Gán ImageView
            tv = itemView.findViewById(R.id.tv); // Gán TextView
            cardView = itemView.findViewById(R.id.layout_item_foodlist); // Gán CardView
        }
    }
}
