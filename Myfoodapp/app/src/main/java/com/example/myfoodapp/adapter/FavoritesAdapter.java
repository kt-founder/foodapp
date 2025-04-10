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

    private List<Food> mRecipes;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<FavoritesDto> list = new ArrayList<>();
    // data is passed into the constructor
    public FavoritesAdapter(Context context, List<Food> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecipes = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Food food = mRecipes.get(position);
        holder.myTextView.setText(food.getName());

        // Handle image
        byte[] bytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            bytes = Base64.getDecoder().decode(food.getImage().getBytes());
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.myImageView.setImageBitmap(bitmap);

        // Handle the delete favorite logic
        holder.myImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitService retrofitService = new RetrofitService();
                FavoriteApi favoriteApi = retrofitService.getRetrofit().create(FavoriteApi.class);
                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int authID = sharedPreferences.getInt("UserId", -1);

                // Fetch the list of favorites to find the correct food to delete
                favoriteApi.getFavoritesByUserId(authID).enqueue(new Callback<List<FavoritesDto>>() {
                    @Override
                    public void onResponse(Call<List<FavoritesDto>> call, Response<List<FavoritesDto>> response) {
                        list = response.body();
                        for (FavoritesDto i : list) {
                            if (i.getFoodId() == food.getId()) {
                                // Create a Favorites object to delete it
                                Favorites favorites1 = new Favorites();
                                favorites1.setId(i.getId()); // Use the correct Favorites ID
                                favorites1.setIdUser(i.getIdUser());
                                favorites1.setFood(food);

                                // Delete the favorite using its ID
                                favoriteApi.deleteFavorite(favorites1.getId()).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Toast.makeText(v.getContext(), "Xóa thành công yêu thích", Toast.LENGTH_SHORT).show();
                                        // Remove the item from the list and notify adapter
                                        mRecipes.remove(position);
                                        notifyItemRemoved(position);
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                                        Toast.makeText(v.getContext(), "Xóa yêu thích thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FavoritesDto>> call, Throwable throwable) {
                        Log.e("FavoritesAdapter", "Error fetching favorites", throwable);
                    }
                });
            }
        });

        // Navigate to food details
        holder.myRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("foodId", food.getId());
                Navigation.findNavController(v).navigate(R.id.nav_gallery, bundle);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView myImageView;
        ImageButton myImageButton;
        RelativeLayout myRecyclerView;
        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvRecipeName);
            myImageView = itemView.findViewById(R.id.ivRecipeImage);
            myImageButton = itemView.findViewById(R.id.btnDelete);
            myRecyclerView = itemView.findViewById(R.id.relative_Yeuthich);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private void updateUI() {}
}
