package com.example.myfoodapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentAdminQuanlyBinding;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.model.FoodQL_Adapter;
import com.example.myfoodapp.model.Food_Adapter;
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class admin_quanlyFragment extends Fragment {
    private FragmentAdminQuanlyBinding binding;
    private FoodQL_Adapter adapter;
    private Button addct,qlct;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private boolean isSearchViewExpanded = false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminQuanlyBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        addct=binding.addct;
        qlct = binding.qlct;
        searchView= binding.searchView;
        recyclerView=binding.suaXoa;
        setupRecyclerView();

        addct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_upload_congThuc);
            }
        });
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

        foodApi.getAllFood().enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                Toast.makeText(getContext(), "aa", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful() && response.body() != null) {
//                    showTypeFoodList(response.body());
                    adapter = new FoodQL_Adapter(requireContext(), response.body());
                    recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
                    recyclerView.setAdapter(adapter);
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
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
