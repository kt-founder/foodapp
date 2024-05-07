package com.example.myfoodapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;

import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.model.Food_Adapter;
import com.example.myfoodapp.model.Food_Type_Adapter;

import java.util.ArrayList;
import java.util.List;

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

    private List<Food> getFoodList() {
        List<Food> list = new ArrayList<>();
//        list.add(new Food(R.drawable.img, "ga tam", "30p"));
//        list.add(new Food(R.drawable.img, "ga chien", "30p"));
//        list.add(new Food(R.drawable.img, "ga xÃ o", "30p"));
//        list.add(new Food(R.drawable.img, "thit lon", "30p"));
//        list.add(new Food(R.drawable.img, "thit bo", "30p"));
//        list.add(new Food(R.drawable.img, "rau xao", "30p"));
//        list.add(new Food(R.drawable.img, "ga teo", "30p"));
        return list;
    }


}




