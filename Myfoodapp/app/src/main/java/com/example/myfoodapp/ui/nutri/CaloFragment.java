package com.example.myfoodapp.ui.nutri;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfoodapp.R;

import com.example.myfoodapp.model.Favorites;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.server.FavoriteApi;
import com.example.myfoodapp.server.RetrofitService;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaloFragment extends Fragment {
    private RetrofitService retrofit = new RetrofitService();
    private FavoriteApi favoriteApi = retrofit.getRetrofit().create(FavoriteApi.class);
    private List<Favorites> favorites = new ArrayList<>();
    private Food food;
    private ImageButton imageButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calo, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Button button1 = view.findViewById(R.id.bt_nutri);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int authID = sharedPreferences.getInt("UserId", -1);
        Bundle bundle = getArguments();
        //Food food = null;
        if (bundle != null) {
           // Toast.makeText(getContext(), "O day", Toast.LENGTH_SHORT).show();
            food = (Food) bundle.getSerializable("foodItem");

            // Doi mau
//
            // Set Image
            imageButton= view.findViewById(R.id.im_yeuthich);
            ImageView ivImage = view.findViewById(R.id.imageViewMonAN);
            TextView tv1 = view.findViewById(R.id.protid);
            TextView tv2 = view.findViewById(R.id.glucid);
            TextView tv3 = view.findViewById(R.id.lipit);
            TextView tv4 = view.findViewById(R.id.chatxo);
            TextView tv5 = view.findViewById(R.id.calo);
            if (food != null) {
                byte[] bytes = new byte[0];
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    bytes = Base64.getDecoder().decode(food.getImage().getBytes());
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                ivImage.setImageBitmap(bitmap);
                String dinhduong = food.getNutrition();
                String [] arr = dinhduong.split(",");
                tv1.setText(arr[0]);
                tv2.setText(arr[1]);
                tv3.setText(arr[2]);
                tv4.setText(arr[3]);
                tv5.setText(arr[4]);
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

        }
        Button btDetail = view.findViewById(R.id.bt_Detail);
        Button btNutri = view.findViewById(R.id.bt_nutri);
        Button btVideo = view.findViewById(R.id.bt_video);
        // Doi mau
        btDetail.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Xanhblue)));
        btNutri.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
        btVideo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Xanhblue)));
        btNutri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng Navigation Component để chuyển đến Fragment mới
                Navigation.findNavController(v).navigate(R.id.nav_nutri, bundle);
            }
        });

        btVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng Navigation Component để chuyển đến Fragment mới
                Navigation.findNavController(v).navigate(R.id.nav_video, bundle);
            }
        });

        btDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sử dụng Navigation Component để chuyển đến Fragment mới
                Navigation.findNavController(v).navigate(R.id.nav_gallery, bundle);
            }
        });
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