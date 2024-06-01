package com.example.myfoodapp.ui.xemct;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentXemctBinding;
import com.example.myfoodapp.model.Food;

public class XemCTFragment extends Fragment {

    private FragmentXemctBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_xemct, container, false);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        Food food = null;
        WebView webView = view.findViewById(R.id.vv_video);
        TextView cachLam = view.findViewById(R.id.Cachlam);
        if(bundle != null){
            food = (Food) bundle.getSerializable("foodItem");
            cachLam.setText(food.getGuide());
            if (bundle != null) {
                // Toast.makeText(getContext(), "O day", Toast.LENGTH_SHORT).show();
                food = (Food) bundle.getSerializable("foodItem");
                //Toast.makeText(getContext(), food.getVideo(), Toast.LENGTH_SHORT).show();
                webView.getSettings().setJavaScriptEnabled(true);  // Enable JavaScript
                webView.getSettings().setDomStorageEnabled(true);  // Enable DOM storage

                // Set up a web client to handle rendering
                webView.setWebChromeClient(new WebChromeClient());
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(food.getVideo());
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}