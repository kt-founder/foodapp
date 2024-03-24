package com.example.myfoodapp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myfoodapp.R;
import com.example.myfoodapp.ui.clorine.CaloFragment.*;
import com.example.myfoodapp.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.txtBannerCachLam;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.bt_nutri);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng Navigation Component để chuyển đến Fragment mới
                Navigation.findNavController(v).navigate(R.id.nav_nutri);
            }
        });
        Button button1 = view.findViewById(R.id.bt_video);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng Navigation Component để chuyển đến Fragment mới
                Navigation.findNavController(v).navigate(R.id.nav_video);
            }
        });
        Button button2 = view.findViewById(R.id.bt_Edit);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng Navigation Component để chuyển đến Fragment mới
                Navigation.findNavController(v).navigate(R.id.nav_change);
            }
        });
        Button button3 = view.findViewById(R.id.bt_share);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng Navigation Component để chuyển đến Fragment mới
                Navigation.findNavController(v).navigate(R.id.nav_share);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}