package com.example.myfoodapp.ui.saved;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.databinding.FragmentDaluuBinding;
import com.example.myfoodapp.model.Favorites;
import com.example.myfoodapp.model.FavoritesAdapter;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.server.FavoriteApi;
import com.example.myfoodapp.server.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedFragment extends Fragment {
    private RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_daluu, container, false);
         recyclerView = root.findViewById(R.id.recyclerViewFavorites);


        return root;

    }

    private void setUpRecycleView() {
        RetrofitService retrofitService = new RetrofitService();
        FavoriteApi favoriteApi = retrofitService.getRetrofit().create(FavoriteApi.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int authID = sharedPreferences.getInt("UserId", -1);
        favoriteApi.getAllRecipes(authID).enqueue(new Callback<List<Favorites>>() {
            @Override
            public void onResponse(Call<List<Favorites>> call, Response<List<Favorites>> response) {
                List<Favorites> fv = new ArrayList<>();
                List<Food> f1 = new ArrayList<>();
                fv = response.body();
                for (Favorites f : fv){
                    f1.add(f.getFood());
                }

                FavoritesAdapter favoritesAdapter = new FavoritesAdapter(getContext(),f1);
                recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),1));
                recyclerView.setAdapter(favoritesAdapter);
            }

            @Override
            public void onFailure(Call<List<Favorites>> call, Throwable throwable) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecycleView();
//        ImageView button = view.findViewById(R.id.Mon1);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Sử dụng Navigation Component để chuyển đến Fragment mới
//                Navigation.findNavController(v).navigate(R.id.nav_gallery);
//            }
//        });

    }

}
