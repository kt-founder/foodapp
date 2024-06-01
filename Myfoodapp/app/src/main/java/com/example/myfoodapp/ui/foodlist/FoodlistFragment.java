package com.example.myfoodapp.ui.foodlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentListfoodBinding;
import com.example.myfoodapp.databinding.FragmentUploadBinding;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.model.FoodListAdapter;
import com.example.myfoodapp.model.Food_Adapter;
import com.example.myfoodapp.model.TypeFood;
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        binding = FragmentListfoodBinding.inflate(inflater,container,false);
        View view =binding.getRoot();
        Bundle bundle = getArguments();
        int tf_id = 0;
        if (bundle != null) {
            // Lấy dữ liệu từ Bundle
            TypeFood foodType = (TypeFood) bundle.getSerializable("TF");
            tv = binding.tv;
            // Kiểm tra dữ liệu
            if (foodType != null) {
                tv.setText("Danh sách các món: "+foodType.getName());
                tf_id=foodType.getId();
            }
        }
        recyclerView = binding.rcvListfood;
//        FoodListAdapter adapter = new FoodListAdapter(getList());
//        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(adapter);
        setFoodlistRcv(tf_id);

        return view;
    }
    private void setFoodlistRcv(int x) {
        RetrofitService retrofitService = new RetrofitService();
        FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);
        foodApi.getAllFood().enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Food> li = (List<Food>) response.body();
                    List<Food> list = new ArrayList<>();
                    for(Food food:li){
                        Set<TypeFood> typeFoodList = food.getTypefoods();
                        for(TypeFood typeFood: typeFoodList){
                            if(typeFood.getId()==x){
                                list.add(food);
                                break;
                            }
                        }
                    }
                    adapter = new FoodListAdapter(getContext(), list);
                    recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 4));
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


}