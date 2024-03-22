package com.example.myfoodapp.ui.upload;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfoodapp.databinding.FragmentUploadBinding;

public class UploadFragment extends Fragment {

    private FragmentUploadBinding binding;

    public View onCreateView(@NonNull LayoutInflater ìnlater, ViewGroup container, Bundle savedInstanceState){
        UploadViewModel uploadViewModel =
                new ViewModelProvider(this).get(UploadViewModel.class);
        binding = FragmentUploadBinding.inflate(ìnlater, container, false);
        View root = binding.getRoot();
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}