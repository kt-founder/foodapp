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
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;
import com.example.myfoodapp.server.TypeFoodApi;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.model.Food_Adapter;
import com.example.myfoodapp.model.Food_Type_Adapter;
import com.example.myfoodapp.model.TypeFood;

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
    private List<Food> foodList,li;
    private boolean isSearchViewExpanded = false;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //binding.getRoot();
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = root.findViewById(R.id.rcv_food_type);
        recyclerSearch = root.findViewById(R.id.rcv_food);
        SearchView searchView = root.findViewById(R.id.searchView);
//        tv=root.findViewById(R.id.tv_search);
        recyclerSearch.setVisibility(View.GONE);
        setupRecyclerView();
        setupRecyclerSearch();
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
        RetrofitService retrofitService = new RetrofitService();
        TypeFoodApi typeFoodApi = retrofitService.getRetrofit().create(TypeFoodApi.class);

        typeFoodApi.getAllTypeFood().enqueue(new Callback<List<TypeFood>>() {
            @Override
            public void onResponse(Call<List<TypeFood>> call, Response<List<TypeFood>> response) {

                if (response.isSuccessful() && response.body() != null) {
//                    showTypeFoodList(response.body());
                    food_type_adapter = new Food_Type_Adapter(getContext(), response.body());
                    recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 5));
                    recyclerView.setAdapter(food_type_adapter);
                } else {
                    Toast.makeText(requireContext(), "Failed to retrieve food types", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TypeFood>> call, Throwable throwable) {
                Log.d("fa",throwable.toString());
            }
        });
    }

    private void setupRecyclerSearch() {
        RetrofitService retrofitService = new RetrofitService();
        FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);

        foodApi.getAllFood().enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {

                if (response.isSuccessful() && response.body() != null) {
//                    showTypeFoodList(response.body());
                    food_adapter = new Food_Adapter(getContext(), response.body());

                    recyclerSearch.setLayoutManager(new GridLayoutManager(requireContext(), 1));
                    recyclerSearch.setAdapter(food_adapter);

                } else {
                    Toast.makeText(requireContext(), "Failed to retrieve food types", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable throwable) {
                Log.d("fa",throwable.toString());
            }
        });
    }

}
