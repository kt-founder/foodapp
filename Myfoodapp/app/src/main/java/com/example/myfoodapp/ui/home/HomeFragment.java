package com.example.myfoodapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;

import com.example.myfoodapp.entity.RetrofitService;
import com.example.myfoodapp.entity.TypeFoodApi;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.model.Food_Adapter;
import com.example.myfoodapp.model.Food_Type_Adapter;
import com.example.myfoodapp.model.TypeFood;

import java.util.ArrayList;
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
    private SearchView searchView;
    private List<Food> foodList,li;
    private boolean isSearchViewExpanded = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = root.findViewById(R.id.rcv_food_type);
        recyclerSearch = root.findViewById(R.id.rcv_food);
        searchView =root.findViewById(R.id.searchView);
        tv=root.findViewById(R.id.tv_search);
        recyclerSearch.setVisibility(View.GONE);
        setupRecyclerView();
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearchViewExpanded = true;
                recyclerSearch.setVisibility(View.VISIBLE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isSearchViewExpanded = false;
                recyclerSearch.setVisibility(View.GONE);
                return false;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                food_adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                food_adapter.getFilter().filter(newText);

                return true;
            }
        });



        return root;
    }

    private void setupRecyclerView() {
        RetrofitService retro = new RetrofitService();

        TypeFoodApi typeFoodApi = retro.getRetrofit().create(TypeFoodApi.class);
        typeFoodApi.getAllTypeFood().enqueue(new Callback<List<TypeFood>>() {
            @Override
            public void onResponse(Call<List<TypeFood>> call, Response<List<TypeFood>> response) {
                if (response.isSuccessful()) {
                    List<TypeFood> typeFoods = response.body();
                    // Hiển thị danh sách loại thực phẩm lên RecyclerView
                    showTypeFoodList(typeFoods);
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TypeFood>> call, Throwable throwable) {
                Toast.makeText(requireContext(),"FAIL",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void popListView(List<TypeFood> body) {
        food_type_adapter = new Food_Type_Adapter(getContext(),body);
        recyclerView.setAdapter(food_type_adapter);

//        //dbHelper = new SQLiteHelper(getContext());
//        TypefoodDAO typefoodDAO = new TypefoodDAO(getContext()); // Tạo một thể hiện của TypefoodDAO
//        food_type_adapter = new Food_Type_Adapter(getContext(), typefoodDAO.getAllFoodTypes()); // Gọi phương thức không tĩnh từ thể hiện
//        GridLayoutManager manager = new GridLayoutManager(requireContext(), 5);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(food_type_adapter);

        foodList = getFoodList();
        food_adapter = new Food_Adapter(getContext(),foodList);
        LinearLayoutManager manager1 = new LinearLayoutManager(requireContext());
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerSearch.setLayoutManager(manager1);
        recyclerSearch.setAdapter(food_adapter);
    }
    private void showTypeFoodList(List<TypeFood> typeFoods) {
        // Tạo adapter với danh sách loại thực phẩm
        Food_Type_Adapter foodTypeAdapter = new Food_Type_Adapter(getContext(), typeFoods);
        // Cấu hình layout cho RecyclerView
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        // Đặt adapter vào RecyclerView
        recyclerView.setAdapter(foodTypeAdapter);
    }
        //food_type_adapter = new Food_Type_Adapter(getContext(), TypeFoodApi.getAllTypeFood()); // Gọi phương thức không tĩnh từ thể hiện
//        GridLayoutManager manager = new GridLayoutManager(requireContext(), 5);
//        recyclerView.setLayoutManager(manager);
        //recyclerView.setAdapter(food_type_adapter);

//        foodList = getFoodList();
//        food_adapter = new Food_Adapter(getContext(),foodList);
//        LinearLayoutManager manager1 = new LinearLayoutManager(requireContext());
//        manager1.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerSearch.setLayoutManager(manager1);
//        recyclerSearch.setAdapter(food_adapter);

}




