package com.example.myfoodapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myfoodapp.R;
import com.example.myfoodapp.model.Favorites;
import com.example.myfoodapp.model.FavoritesDto;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.server.FavoriteApi;
import com.example.myfoodapp.server.RetrofitService;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private final List<FavoritesDto> favoritesList;
    private final LayoutInflater inflater;

    public FavoritesAdapter(Context context, List<FavoritesDto> favoritesList) {
        this.inflater = LayoutInflater.from(context);
        this.favoritesList = favoritesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FavoritesDto favorite = favoritesList.get(position);

        holder.myTextView.setText(favorite.getFoodName());

        byte[] bytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            bytes = Base64.getDecoder().decode(favorite.getFoodImageBase64().getBytes());
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.myImageView.setImageBitmap(bitmap);

        // Xoá yêu thích
        holder.myImageButton.setOnClickListener(v -> {
            int currentPos = holder.getBindingAdapterPosition();
            if (currentPos != RecyclerView.NO_POSITION) {
                RetrofitService retrofitService = new RetrofitService();
                FavoriteApi favoriteApi = retrofitService.getRetrofit().create(FavoriteApi.class);
                favoriteApi.deleteFavorite(favorite.getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(v.getContext(), "Xoá thành công", Toast.LENGTH_SHORT).show();
                        favoritesList.remove(currentPos);
                        notifyItemRemoved(currentPos);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        Toast.makeText(v.getContext(), "Xoá thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Mở chi tiết món ăn
        holder.myRecyclerView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("foodId", favorite.getFoodId());
            Navigation.findNavController(v).navigate(R.id.nav_gallery, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        ImageView myImageView;
        ImageButton myImageButton;
        RelativeLayout myRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvRecipeName);
            myImageView = itemView.findViewById(R.id.ivRecipeImage);
            myImageButton = itemView.findViewById(R.id.btnDelete);
            myRecyclerView = itemView.findViewById(R.id.relative_Yeuthich);
        }
    }
}

