package com.example.myfoodapp.ui.foodlist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentListfoodBinding;
import com.example.myfoodapp.model.FoodItemDto;
import com.example.myfoodapp.adapter.FoodListAdapter;
import com.example.myfoodapp.model.TypeFood;
import com.example.myfoodapp.model.TypeFoodResponseDto;
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;
import com.example.myfoodapp.server.TypeFoodApi;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodlistFragment extends Fragment {

    private FragmentListfoodBinding binding;
    private RecyclerView recyclerView;
    private TextView tv;
    private FoodListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListfoodBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Lấy dữ liệu từ Bundle
        Bundle bundle = getArguments();
        int typeFoodId = 0;
        if (bundle != null) {
            // Lấy ID từ bundle
            typeFoodId = bundle.getInt("type_food_id", 0); // default value is 0
        }

        tv = binding.tv;
        recyclerView = binding.rcvListfood;

        // Gọi API lấy thông tin loại món ăn theo id
        getTypeFoodById(typeFoodId);

        return view;
    }

    // Lấy thông tin loại món ăn theo ID
    private void getTypeFoodById(int typeFoodId) {
        RetrofitService retrofitService = new RetrofitService();
        TypeFoodApi typeFoodApi = retrofitService.getRetrofit().create(TypeFoodApi.class);

        // Sửa đúng kiểu trả về Callback
        typeFoodApi.getTypeFoodById(typeFoodId).enqueue(new Callback<TypeFoodResponseDto>() {
            @Override
            public void onResponse(Call<TypeFoodResponseDto> call, Response<TypeFoodResponseDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TypeFoodResponseDto typeFood = response.body();
                    if (typeFood != null) {
                        // Cập nhật tên loại món ăn
                        tv.setText("Danh sách các món thuộc loại: " + typeFood.getName());
                    }

                    Log.d("FoodlistFragment", "Type Food Name: " + typeFood.getName());
                    // Sau khi lấy được thông tin loại món ăn, gọi API để lấy các món ăn thuộc loại này
                    getFoodListByType(typeFoodId);
                }
            }

            @Override
            public void onFailure(Call<TypeFoodResponseDto> call, Throwable t) {
                Log.e("FoodlistFragment", "Error fetching food type: " + t.getMessage());
                Toast.makeText(getContext(), "Failed to retrieve food type", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Lấy danh sách món ăn theo loại món ăn
    private void getFoodListByType(int typeFoodId) {
        RetrofitService retrofitService = new RetrofitService();
        FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);

        foodApi.getFoodByType(typeFoodId).enqueue(new Callback<List<FoodItemDto>>() {
            @Override
            public void onResponse(Call<List<FoodItemDto>> call, Response<List<FoodItemDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FoodItemDto> foodItems = response.body();

                    // Cập nhật adapter với danh sách món ăn
                    adapter = new FoodListAdapter(getContext(), foodItems);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns in grid
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Failed to retrieve food list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FoodItemDto>> call, Throwable t) {
                Log.e("FoodlistFragment", "Error fetching food list: " + t.getMessage());
                Toast.makeText(getContext(), "Error fetching food list", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
