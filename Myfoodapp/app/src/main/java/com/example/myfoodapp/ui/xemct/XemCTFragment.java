package com.example.myfoodapp.ui.xemct;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.myfoodapp.model.FoodDto;
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XemCTFragment extends Fragment {

    private FragmentXemctBinding binding;
    private RetrofitService retrofit = new RetrofitService();
    private FoodApi foodApi = retrofit.getRetrofit().create(FoodApi.class);
    private FoodDto food = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_xemct, container, false);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        if(bundle != null){
            int foodId = bundle.getInt("foodId");
            foodApi.getFoodById(foodId).enqueue(new Callback<FoodDto>() {
                @Override
                public void onResponse(Call<FoodDto> call, Response<FoodDto> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        food = response.body();
                        Log.d("food", food.getName());
                        // Cập nhật giao diện sau khi nhận được thông tin món ăn từ API
                        updateUI(view);
                    } else {
                        Toast.makeText(getContext(), "Failed to retrieve food details", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<FoodDto> call, Throwable throwable) {
                    Log.e("FoodDetailFragment", "Error fetching food details", throwable);
                    Toast.makeText(getContext(), "Error fetching food details", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void updateUI(View view) {
        WebView webView = view.findViewById(R.id.vv_video);
        TextView cachLam = view.findViewById(R.id.Cachlam);

        if (food != null) {
            cachLam.setText(food.getGuide());
            // Toast.makeText(getContext(), "O day", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), food.getVideo(), Toast.LENGTH_SHORT).show();
            webView.getSettings().setJavaScriptEnabled(true);  // Enable JavaScript
            webView.getSettings().setDomStorageEnabled(true);  // Enable DOM storage

            // Set up a web client to handle rendering
            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(food.getVideo());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}