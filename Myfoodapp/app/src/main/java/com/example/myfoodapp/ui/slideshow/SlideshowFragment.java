package com.example.myfoodapp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentSlideShowBinding;
import com.example.myfoodapp.databinding.FragmentSlideshowBinding;
import com.example.myfoodapp.ui.home.Food;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private FragmentSlideShowBinding binding;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slide_show, container, false);
        recyclerView = root.findViewById(R.id.rcv_menufood);
        MenufoodAdapter adapter = new MenufoodAdapter(getList());
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        return root;
    }
    private List<Food> getList() {
        List<Food> list = new ArrayList<>();
        list.add(new Food(R.drawable.img, "ga tam", "30p"));
        list.add(new Food(R.drawable.img, "ga chien", "30p"));
        list.add(new Food(R.drawable.img, "ga xào", "30p"));
        list.add(new Food(R.drawable.img, "thit lon", "30p"));
        list.add(new Food(R.drawable.img, "thit bo", "30p"));
        list.add(new Food(R.drawable.img, "rau xao", "30p"));
        list.add(new Food(R.drawable.img, "ga teo", "30p"));
        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}