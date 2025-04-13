package com.example.myfoodapp.ui.detail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
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

public class FoodDetailFragment extends Fragment {
    private List<FavoritesDto> favorites = new ArrayList<>();
    private FoodDto food = null;
    private Bundle bundle;

    // Khai báo Retrofit service và API
    private final RetrofitService retrofit = new RetrofitService();
    private final FavoriteApi favoriteApi = retrofit.getRetrofit().create(FavoriteApi.class);
    private final FoodApi foodApi = retrofit.getRetrofit().create(FoodApi.class);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate view layout
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (bundle != null) {
            getListFavorite(getView()); // lấy danh sách yêu thích trước → rồi update UI trong đó
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Lấy foodId từ bundle
        bundle = getArguments();
        Button btDetail = view.findViewById(R.id.bt_Detail);
        Button btNutri = view.findViewById(R.id.bt_nutri);
        Button btVideo = view.findViewById(R.id.bt_video);
//        Button rate = view.findViewById(R.id.bt_rating);

        if (bundle != null) {
            // Lấy foodId từ bundle và gọi API getFoodById
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
//                        Toast.makeText(getContext(), "Failed to retrieve food details aaa", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<FoodDto> call, Throwable throwable) {
                    Log.e("FoodDetailFragment", "Error fetching food details", throwable);
                    Toast.makeText(getContext(), "Error fetching food details", Toast.LENGTH_SHORT).show();
                }
            });
            getListFavorite(view);

        }


        // Các button click listener
        btNutri.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_nutri, bundle));
        btDetail.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_gallery, bundle));
        btVideo.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_video, bundle));

        btDetail.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
        btNutri.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Xanhblue)));
        btVideo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.Xanhblue)));
        Button button2 = view.findViewById(R.id.bt_Edit);
        button2.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_change, bundle));

        Button button3 = view.findViewById(R.id.bt_share);
        button3.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_share));
        // Set danh gia
        Button rate = view.findViewById(R.id.bt_rating);

        rate.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Đánh giá");

            // Tạo RatingBar giả định
            RatingBar ratingBar = new RatingBar(getContext());
            ratingBar.setNumStars(5);
            ratingBar.setStepSize(1f);

            // Đặt layout cho RatingBar
            FrameLayout container = new FrameLayout(getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = Gravity.CENTER;
            container.setPadding(50, 20, 50, 20);
            container.addView(ratingBar, params);

            builder.setView(container);

            builder.setPositiveButton("Gửi", (dialog, which) -> {
                int rating = ratingBar.getProgress();
                Toast.makeText(getContext(), "Đánh giá thành công: " + rating + " sao", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("Hủy", null);

            builder.show();
        });

    }

    private void getListFavorite(View v) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int authID = sharedPreferences.getInt("UserId", -1);
        ImageButton imageButton = v.findViewById(R.id.bt_yeuthich);
        favoriteApi.getFavoritesByUserId(authID).enqueue(new Callback<List<FavoritesDto>>() {
            @Override
            public void onResponse(Call<List<FavoritesDto>> call, Response<List<FavoritesDto>> response) {
                favorites =response.body();
                if(favorites != null){
                    for(FavoritesDto f : favorites){
                        Log.e("foooood", String.valueOf(f.getId()));
                    }
                }
                updateUI(v);
            }

            @Override
            public void onFailure(Call<List<FavoritesDto>> call, Throwable throwable) {

                Log.e("FoodDetailFragment", "Error fetching favorites");
            }
        });


    }

    // Hàm cập nhật giao diện sau khi nhận được dữ liệu từ API
    private void updateUI(View view) {
        if (food != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            int authID = sharedPreferences.getInt("UserId", -1);

            ImageButton imageButton = view.findViewById(R.id.bt_yeuthich);
            TextView tvTenMonAn = view.findViewById(R.id.txtTenMonAn);
            TextView tvNguyenluyen = view.findViewById(R.id.txtNguyenLieu);
            TextView tvCachLam = view.findViewById(R.id.Cachlam);
            ImageView ivImage = view.findViewById(R.id.imageViewMonAN);

            // Set tên món ăn
            tvTenMonAn.setText("Tên món: " + food.getName());
            tvNguyenluyen.setText(food.getIngredient());
            tvCachLam.setText(food.getGuide());


            // Set ảnh món ăn từ Base64
            byte[] bytes = Base64.decode(food.getImageBase64(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ivImage.setImageBitmap(bitmap);
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
        Log.e("FoodDetailFragment", "check Done");
        updateFavoriteButton(isFavorite, imageButton, authID);
    }

    private void updateFavoriteButton(boolean isFavorite, ImageButton imageButton, int authID) {
        if (isFavorite) {
            imageButton.setImageResource(R.drawable.ic_heart_red);
            imageButton.setOnClickListener(v -> removeFavorite(imageButton));
        } else {
            imageButton.setImageResource(R.drawable.ic_heart_black);
            imageButton.setOnClickListener(v -> addFavorite(imageButton, authID));
            Log.e("FoodDetailFragment", "set Done");
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
