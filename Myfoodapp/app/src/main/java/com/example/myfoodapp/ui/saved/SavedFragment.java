package com.example.myfoodapp.ui.saved;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfoodapp.databinding.FragmentDaluuBinding;
public class SavedFragment extends Fragment {
    private FragmentDaluuBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        SavedViewModel savedViewModel =
                    new ViewModelProvider(this).get(SavedViewModel.class);
        binding = FragmentDaluuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }


}
