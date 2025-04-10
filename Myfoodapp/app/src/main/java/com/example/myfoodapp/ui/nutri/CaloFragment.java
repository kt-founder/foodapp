package com.example.myfoodapp.ui.nutri;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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
import com.example.myfoodapp.model.FavoritesDto;
import com.example.myfoodapp.model.FoodDto;
import com.example.myfoodapp.server.FavoriteApi;
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaloFragment extends Fragment {
    private RetrofitService retrofit = new RetrofitService();
    private FavoriteApi favoriteApi = retrofit.getRetrofit().create(FavoriteApi.class);
    private List<FavoritesDto> favorites = new ArrayList<>();
    private FoodDto food; // Use FoodDto instead of Food
    private ImageButton imageButton;
    private FoodApi foodApi = retrofit.getRetrofit().create(FoodApi.class);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calo, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int authID = sharedPreferences.getInt("UserId", -1);
        Bundle bundle = getArguments();

        if (bundle != null) {
            // Set Image
            imageButton = view.findViewById(R.id.im_yeuthich);
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


        }

        Button btDetail = view.findViewById(R.id.bt_Detail);
        Button btNutri = view.findViewById(R.id.bt_nutri);
        Button btVideo = view.findViewById(R.id.bt_video);

        // Set button colors
        btDetail.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Xanhblue)));
        btNutri.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
        btVideo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Xanhblue)));

        btNutri.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_nutri, bundle));
        btVideo.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_video, bundle));
        btDetail.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_gallery, bundle));
    }
    private void updateUI(View view) {
        if (food != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            int authID = sharedPreferences.getInt("UserId", -1);
            ImageView ivImage = view.findViewById(R.id.imageViewMonAN);
            TextView tv1 = view.findViewById(R.id.protid);
            TextView tv2 = view.findViewById(R.id.glucid);
            TextView tv3 = view.findViewById(R.id.lipit);
            TextView tv4 = view.findViewById(R.id.chatxo);
            TextView tv5 = view.findViewById(R.id.calo);
            // Set image from Base64 string
            byte[] bytes = Base64.decode(food.getImageBase64(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ivImage.setImageBitmap(bitmap);

            // Split and set nutritional info
            String[] arr = food.getNutrition().split(",");
            tv1.setText(arr[0]);
            tv2.setText(arr[1]);
            tv3.setText(arr[2]);
            tv4.setText(arr[3]);
            tv5.setText(arr[4]);
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

    private void updateFavoriteButton(boolean isFavorite, ImageButton imageButton, int authID) {
        if (isFavorite) {
            imageButton.setImageResource(R.drawable.ic_heart_red);
            imageButton.setOnClickListener(v -> removeFavorite(imageButton));
        } else {
            imageButton.setImageResource(R.drawable.ic_heart_black);
            imageButton.setOnClickListener(v -> addFavorite(imageButton, authID));
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
