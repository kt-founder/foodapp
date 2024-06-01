package com.example.myfoodapp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FoodQL_Adapter extends RecyclerView.Adapter<FoodQL_Adapter.FoodQLholder> implements Filterable {
    private List<Food> foodList;
    private List<Food> mfoodList;
    private Context  context;

    public FoodQL_Adapter(Context context, List<Food> foodList) {
        this.foodList = foodList;
        this.mfoodList = new ArrayList<>(foodList);
        this.context = context;
    }

    @NonNull
    @Override
    public FoodQLholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_ql, parent, false);
        return new FoodQLholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodQLholder holder, int position) {
        Food food = foodList.get(position);
        if (food == null) return;
        //holder.img.setImageBitmap(food.getImage());
        holder.tv.setText(food.getName());
        byte[] bytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            bytes = Base64.getDecoder().decode(food.getImage().getBytes());
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.img.setImageBitmap(bitmap);
        holder.sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Fo",food);
                Navigation.findNavController(v).navigate(R.id.nav_editfood,bundle);
            }
        });
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitService retrofitService = new RetrofitService();
                FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);
                foodApi.deleteFood(food.getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        // Xử lý khi xóa món ăn thành công, ví dụ: chuyển hướng đến trang quản lý
                        Navigation.findNavController(v).navigate(R.id.nav_admin_quanly);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        // Xử lý khi xảy ra lỗi
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class FoodQLholder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView tv;
        private Button sua,xoa;


        public FoodQLholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tv = itemView.findViewById(R.id.tv);
            sua = itemView.findViewById(R.id.sua);
            xoa = itemView.findViewById(R.id.xoa);

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
