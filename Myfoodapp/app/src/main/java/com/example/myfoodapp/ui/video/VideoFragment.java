package com.example.myfoodapp.ui.video;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentVideoBinding;
import com.example.myfoodapp.model.Favorites;
import com.example.myfoodapp.model.FavoritesDto;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.model.FoodDto;
import com.example.myfoodapp.server.FavoriteApi;
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoFragment extends Fragment {
    private RetrofitService retrofit = new RetrofitService();
    private FavoriteApi favoriteApi = retrofit.getRetrofit().create(FavoriteApi.class);
    private List<FavoritesDto> favorites = new ArrayList<>();
    private FragmentVideoBinding binding;
    private FoodDto food;
    private FoodApi foodApi = retrofit.getRetrofit().create(FoodApi.class);
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_video, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int authID = sharedPreferences.getInt("UserId", -1);
        //Food food = null;

        Button btDetail = view.findViewById(R.id.bt_Detail);
        Button btNutri = view.findViewById(R.id.bt_nutri);
        Button btVideo = view.findViewById(R.id.bt_video);


        // Doi mau
        btDetail.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Xanhblue)));
        btNutri.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Xanhblue)));
        btVideo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
        if (bundle != null) {
           // Toast.makeText(getContext(), "O day", Toast.LENGTH_SHORT).show();
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
            favoriteApi.getFavoritesByUserId(authID).enqueue(new Callback<List<FavoritesDto>>() {
                @Override
                public void onResponse(Call<List<FavoritesDto>> call, Response<List<FavoritesDto>> response) {
                    favorites =response.body();
                }

                @Override
                public void onFailure(Call<List<FavoritesDto>> call, Throwable throwable) {

                    Log.e("FoodDetailFragment", "Error fetching favorites");
                }
            });

            //Toast.makeText(getContext(), food.getVideo(), Toast.LENGTH_SHORT).show();

        }

        btVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng Navigation Component để chuyển đến Fragment mới
                Navigation.findNavController(v).navigate(R.id.nav_nutri,bundle);
            }
        });
        btNutri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_nutri,bundle);
            }
        });
        btVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng Navigation Component để chuyển đến Fragment mới
                Navigation.findNavController(v).navigate(R.id.nav_video,bundle);
            }
        });
        btDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng Navigation Component để chuyển đến Fragment mới
                Navigation.findNavController(v).navigate(R.id.nav_gallery,bundle);
            }
        });
        Button button3 = view.findViewById(R.id.bt_XemCT);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng Navigation Component để chuyển đến Fragment mới
                Navigation.findNavController(v).navigate(R.id.nav_xemct,bundle);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void updateUI(View view) {
        if (food != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            int authID = sharedPreferences.getInt("UserId", -1);
            WebView webView = view.findViewById(R.id.vv_video);
            TextView openYoutube = view.findViewById(R.id.moYoutue);
            ImageButton imageButton = view.findViewById(R.id.im_yeuthich);
            webView.getSettings().setJavaScriptEnabled(true);  // Enable JavaScript
            webView.getSettings().setDomStorageEnabled(true);  // Enable DOM storage

            // Set up a web client to handle rendering
            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(food.getVideo());
            int index = food.getVideo().indexOf("v=")+2;
            String VideoID = food.getVideo().substring(index);
            openYoutube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uri uri = Uri.parse("vnd.youtube:" + VideoID);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    // Check if the YouTube app is installed
                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        // YouTube app is not installed, redirect to a web browser
                        uri = Uri.parse("http://www.youtube.com/watch?v=" + VIDEO_ID);
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }
            });
            checkFavoriteStatus(favorites, food.getId(), imageButton,authID);

        }
    }
    private void checkFavoriteStatus(List<FavoritesDto> favoritesList, long foodId, ImageButton imageButton, int authID) {
        boolean isFavorite = false;
        if(favoritesList != null){
            for (FavoritesDto fav : favoritesList) {
                if (fav.getFoodId() == foodId) {
                    isFavorite = true;
                    break;
                }
            }
        }

        updateFavoriteButton(isFavorite, imageButton, authID);
    }

    private void updateFavoriteButton(boolean isFavorite, ImageButton imageButton,int authID) {
        if (isFavorite) {
            imageButton.setImageResource(R.drawable.ic_heart_red);
            imageButton.setOnClickListener(v -> removeFavorite(imageButton));
        } else {
            imageButton.setImageResource(R.drawable.ic_heart_black);
            imageButton.setOnClickListener(v -> addFavorite(imageButton,authID));
        }
    }
    private void addFavorite(ImageButton imageButton, int authID) {
        Favorites newFavorite = new Favorites();
        newFavorite.setFood(food.toFood());  // 'food' should be accessible in this context
        newFavorite.setIdUser(authID);
        Log.e("FoodDetailFragment", "Click");

        favoriteApi.addFavorite(newFavorite).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                FavoritesDto f = new FavoritesDto();
                f.setIdUser(authID);
                f.setId(newFavorite.getId());
                f.setFoodId(newFavorite.getId());
                favorites.add(f);
                checkFavoriteStatus(favorites, food.getId(), imageButton, authID);
                updateFavoriteButton(true, imageButton, authID);
                Toast.makeText(getContext(), "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.e("FoodDetailFragment", "Error adding favorite", throwable);
            }
        });
    }

    private void removeFavorite(ImageButton imageButton) {
        Favorites toBeRemoved = new Favorites();
        for (FavoritesDto favorite : favorites) {
            if (favorite.getFoodId() == food.getId()) {
                toBeRemoved.setId(favorite.getId());
                toBeRemoved.setIdUser(favorite.getIdUser());
                toBeRemoved.setFood(food.toFood());
                break;
            }
        }

        if (toBeRemoved.getId() != 0) {
            FavoritesDto favoritesDto = new FavoritesDto();
            favoritesDto.setId(toBeRemoved.getId());
            favoritesDto.setIdUser(toBeRemoved.getIdUser());
            favoritesDto.setFoodId(toBeRemoved.getFood().getId());
            favoriteApi.deleteFavorite(toBeRemoved.getId()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    favorites.remove(favoritesDto);
                    Toast.makeText(getContext(), "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                    updateFavoriteButton(false, imageButton, food.getIdAut());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    Log.e("FoodDetailFragment", "Error deleting favorite", throwable);
                }
            });
        } else {
            Toast.makeText(getContext(), "Món này chưa được yêu thích!", Toast.LENGTH_SHORT).show();
        }
    }
}