package com.example.myfoodapp.ui.foodlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.model.Food_Type;

import java.util.ArrayList;
import java.util.List;

public class FoodlistFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listfood, container, false);
        recyclerView = root.findViewById(R.id.rcv_listfood);
        tv = root.findViewById(R.id.tv);
        FoodListAdapter adapter = new FoodListAdapter(getList());
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        Bundle bundle = getArguments();

        // Kiểm tra xem Bundle có tồn tại không và có chứa key "food_type" không
        if (bundle != null && bundle.containsKey("food_type")) {
            // Lấy đối tượng Food_Type từ Bundle
            Food_Type food_type = bundle.getParcelable("food_type");
            tv.setText(food_type.getName());
            // Bây giờ bạn có thể sử dụng đối tượng Food_Type như mong muốn
        }
        return root;
    }
    private List<Food> getList() {
        List<Food> list = new ArrayList<>();
//        list.add(new Food(R.drawable.img, "ga tam", "30p"));
//        list.add(new Food(R.drawable.img, "ga chien", "30p"));
//        list.add(new Food(R.drawable.img, "ga xào", "30p"));
//        list.add(new Food(R.drawable.img, "thit lon", "30p"));
//        list.add(new Food(R.drawable.img, "thit bo", "30p"));
//        list.add(new Food(R.drawable.img, "rau xao", "30p"));
//        list.add(new Food(R.drawable.img, "ga teo", "30p"));
        return list;
    }


}