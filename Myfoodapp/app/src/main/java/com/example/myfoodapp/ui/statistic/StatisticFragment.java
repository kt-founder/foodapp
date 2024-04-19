package com.example.myfoodapp.ui.statistic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentStatisticBinding;
import com.example.myfoodapp.ui.foodlist.FoodListAdapter;
import com.example.myfoodapp.model.Food;

import java.util.ArrayList;
import java.util.List;

public class StatisticFragment extends Fragment {
    private FragmentStatisticBinding binding;
    private TextView textView;
    private Button bt1;
    private Button bt2;
    private RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_statistic, container, false);
        textView=root.findViewById(R.id.tv);
        bt1 = root.findViewById(R.id.bt1);
        bt2=root.findViewById(R.id.bt2);
        recyclerView = root.findViewById(R.id.rcv_l);
        FoodListAdapter adapter = new FoodListAdapter(getList());
        GridLayoutManager manager = new GridLayoutManager(requireContext(), 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return root;
    }
    private List<Food> getList() {
        List<Food> list = new ArrayList<>();
        list.add(new Food(R.drawable.img, "ga tam", "30p"));
        list.add(new Food(R.drawable.img, "ga chien", "30p"));
        list.add(new Food(R.drawable.img, "ga xao", "30p"));
        list.add(new Food(R.drawable.img, "thit lon", "30p"));
        list.add(new Food(R.drawable.img, "thit bo", "30p"));
        list.add(new Food(R.drawable.img, "rau xao", "30p"));
        list.add(new Food(R.drawable.img, "ga teo", "30p"));
        return list;
    }
}
