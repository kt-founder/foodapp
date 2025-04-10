package com.example.myfoodapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentAdminQuanlyBinding;
import com.example.myfoodapp.model.FoodDto;
import com.example.myfoodapp.adapter.FoodQL_Adapter;
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class admin_quanlyFragment extends Fragment {
    private FragmentAdminQuanlyBinding binding;
    private FoodQL_Adapter adapter;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private boolean isSearchViewExpanded = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminQuanlyBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        searchView = binding.searchView;
        recyclerView = binding.suaXoa;
        setupRecyclerView();

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearchViewExpanded = true;
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isSearchViewExpanded = false;
                recyclerView.setVisibility(View.GONE);
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }

    private void setupRecyclerView() {
        RetrofitService retrofitService = new RetrofitService();
        FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);

        // Retrieve authId from SharedPreferences
        int authId = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                .getInt("UserId", -1);

        // Call the API to get food data by authId
        foodApi.getAllFoodByAuthId(authId).enqueue(new Callback<List<FoodDto>>() {
            @Override
            public void onResponse(Call<List<FoodDto>> call, Response<List<FoodDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FoodDto> foodList = response.body();
                    if (foodList != null && !foodList.isEmpty()) {
                        // Set the adapter with the data
                        adapter = new FoodQL_Adapter(requireContext(), foodList);
                        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.e("FoodList", "Food list is empty.");
                        Toast.makeText(requireContext(), "No food found for this user.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to retrieve food types", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FoodDto>> call, Throwable throwable) {
                Log.d("FoodList", throwable.toString());
                Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
