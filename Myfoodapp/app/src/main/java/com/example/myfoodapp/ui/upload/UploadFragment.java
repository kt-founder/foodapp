package com.example.myfoodapp.ui.upload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentUploadBinding;

public class UploadFragment extends Fragment {

    private FragmentUploadBinding binding;

    public View onCreateView(@NonNull LayoutInflater ìnlater, ViewGroup container, Bundle savedInstanceState){
        UploadViewModel uploadViewModel =
                new ViewModelProvider(this).get(UploadViewModel.class);
        binding = FragmentUploadBinding.inflate(ìnlater, container, false);
        View view = binding.getRoot();
        Button button = view.findViewById(R.id.bt_Upload);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng Navigation Component để chuyển đến Fragment mới
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}