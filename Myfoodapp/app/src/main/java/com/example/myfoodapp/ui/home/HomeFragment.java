package com.example.myfoodapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;

import com.example.myfoodapp.databinding.FragmentHomeBinding;
import com.example.myfoodapp.model.FoodDto;
import com.example.myfoodapp.model.TypeFoodResponseDto;
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;
import com.example.myfoodapp.server.TypeFoodApi;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.adapter.Food_Adapter;
import com.example.myfoodapp.adapter.Food_Type_Adapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment   {
    private TextView tv;
    private RecyclerView recyclerView;
    private RecyclerView recyclerSearch;
    private Food_Adapter food_adapter;
    private Food_Type_Adapter food_type_adapter;
    private List<FoodDto> foodList,li;
    private boolean isSearchViewExpanded = false;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //binding.getRoot();
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = root.findViewById(R.id.rcv_food_type);
        recyclerSearch = root.findViewById(R.id.rcv_food);
        SearchView searchView = root.findViewById(R.id.searchView);
        recyclerSearch.setVisibility(View.GONE);
        setupRecyclerView();
        setupRecyclerSearch();
        searchView.setOnSearchClickListener(v -> {
            isSearchViewExpanded = true;
            recyclerSearch.setVisibility(View.VISIBLE);

        });

        searchView.setOnCloseListener(() -> {
            isSearchViewExpanded = false;
            recyclerSearch.setVisibility(View.GONE);
            return false;
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (food_adapter != null && food_adapter.getFilter() != null) {
                    food_adapter.getFilter().filter(query); // Kiểm tra null trước khi gọi filter
                } else {
                    Log.e("SearchView", "food_adapter or filter is null.");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (food_adapter != null && food_adapter.getFilter() != null) {
                    food_adapter.getFilter().filter(newText); // Kiểm tra null trước khi gọi filter
                } else {
                    Log.e("SearchView", "food_adapter or filter is null.");
                }
                return true;
            }
        });

        return root;
    }

    private void setupRecyclerView() {
        RetrofitService retrofitService = new RetrofitService();
        TypeFoodApi typeFoodApi = retrofitService.getRetrofit().create(TypeFoodApi.class);

        typeFoodApi.getAllTypeFood1().enqueue(new Callback<List<TypeFoodResponseDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<TypeFoodResponseDto>> call, @NonNull Response<List<TypeFoodResponseDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TypeFoodResponseDto> foodTypes = response.body();

//                    // Kiểm tra danh sách trả về để chắc chắn rằng bạn nhận được dữ liệu đúng
//                    for (TypeFoodResponseDto foodType : foodTypes) {
//                        Log.d("FoodType", "Name: " + foodType.getName() + ", Image: " + foodType.getImageBase64());
//                    }

                    // Gắn dữ liệu vào adapter
                    food_type_adapter = new Food_Type_Adapter(getContext(), foodTypes);
                    recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
                    recyclerView.setAdapter(food_type_adapter);
                } else {
                    Toast.makeText(requireContext(), "Failed to retrieve food types", Toast.LENGTH_SHORT).show();
//                    assert response.body() != null;
                    Log.d("fa", response.toString());
                }
            }


            @Override
            public void onFailure(Call<List<TypeFoodResponseDto>> call, Throwable throwable) {
                Log.d("fa", throwable.toString());
            }
        });
    }

    private void setupRecyclerSearch() {
        RetrofitService retrofitService = new RetrofitService();
        FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);

        foodApi.getAllFood1().enqueue(new Callback<List<FoodDto>>() {
            @Override
            public void onResponse(Call<List<FoodDto>> call, Response<List<FoodDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FoodDto> foodList = response.body(); // Kiểm tra dữ liệu có hợp lệ không
                    if (foodList != null && !foodList.isEmpty()) {
                        food_adapter = new Food_Adapter(getContext(), foodList);
                        recyclerSearch.setLayoutManager(new GridLayoutManager(requireContext(), 1));
                        recyclerSearch.setAdapter(food_adapter);
                    } else {
                        Log.e("FoodList", "Food list is empty.");
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to retrieve food", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FoodDto>> call, Throwable throwable) {
                Log.d("FoodList", throwable.toString());
            }
        });
    }


}
