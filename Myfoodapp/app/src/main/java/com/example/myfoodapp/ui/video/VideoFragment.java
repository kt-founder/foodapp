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
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.server.FavoriteApi;
import com.example.myfoodapp.server.RetrofitService;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoFragment extends Fragment {
    private RetrofitService retrofit = new RetrofitService();
    private FavoriteApi favoriteApi = retrofit.getRetrofit().create(FavoriteApi.class);
    private List<Favorites> favorites = new ArrayList<>();
    private FragmentVideoBinding binding;
    private Food food;
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
        ImageButton imageButton = view.findViewById(R.id.im_yeuthich);
        Button btDetail = view.findViewById(R.id.bt_Detail);
        Button btNutri = view.findViewById(R.id.bt_nutri);
        Button btVideo = view.findViewById(R.id.bt_video);

        WebView webView = view.findViewById(R.id.vv_video);
        TextView openYoutube = view.findViewById(R.id.moYoutue);
        // Doi mau
        btDetail.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Xanhblue)));
        btNutri.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Xanhblue)));
        btVideo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
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
        }
        favoriteApi.getAllRecipes(authID).enqueue(new Callback<List<Favorites>>() {
            @Override
            public void onResponse(Call<List<Favorites>> call, Response<List<Favorites>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    favorites = response.body();
                    checkFavoriteStatus(favorites, food.getId(), imageButton,authID);
                    Log.d("API Success", "Number of favorites retrieved: " + favorites.size());
                } else {
                    Log.e("API Error", "Response not successful or is empty: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Favorites>> call, Throwable throwable) {

            }
        });


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
    private void checkFavoriteStatus(List<Favorites> favoritesList, long foodId, ImageButton imageButton, int authID) {
        boolean isFavorite = false;
        for (Favorites fav : favoritesList) {
            if (fav.getFood().getId() == foodId) {
                isFavorite = true;
                break;
            }
        }

        updateFavoriteButton(isFavorite, imageButton,authID);
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
    private void addFavorite(ImageButton imageButton,int authID) {
        Favorites newFavorite = new Favorites();
        newFavorite.setFood(food);  // 'food' should be accessible in this context
        newFavorite.setIdUser(authID);

        favoriteApi.addFavorite(newFavorite).enqueue(new Callback<Favorites>() {
            @Override
            public void onResponse(Call<Favorites> call, Response<Favorites> response) {

            }

            @Override
            public void onFailure(Call<Favorites> call, Throwable t) {
                Toast.makeText(getContext(), "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                updateFavoriteButton(true, imageButton,authID);
            }
        });
    }

    private void removeFavorite(ImageButton imageButton) {
        Favorites toBeRemoved = null;
        for (Favorites favorite : favorites) {
            if (favorite.getFood().getId()==(food.getId())) {
                toBeRemoved = favorite;
                break;
            }
        }

        if (toBeRemoved != null) {
            final Favorites finalFavorite = toBeRemoved;
            favoriteApi.deleteFavorite(finalFavorite).enqueue(new Callback<Favorites>() {
                @Override
                public void onResponse(Call<Favorites> call, Response<Favorites> response) {

                }

                @Override
                public void onFailure(Call<Favorites> call, Throwable t) {
                    Toast.makeText(getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    favorites.remove(finalFavorite);
                    updateFavoriteButton(false,imageButton, food.getIdAut());
                }
            });
        } else {
            Toast.makeText(getContext(), "Món này chưa được yêu thích!", Toast.LENGTH_SHORT).show();
        }
    }
}