package com.example.myfoodapp.ui.change;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.myfoodapp.R;
import com.example.myfoodapp.model.Food;
import com.example.myfoodapp.model.FoodDto;
import com.example.myfoodapp.server.FoodApi;
import com.example.myfoodapp.server.RetrofitService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeFragment extends Fragment {

    private Food food;

    public static ChangeFragment newInstance() {
        return new ChangeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change, container, false);
        EditText ingredientEditText = view.findViewById(R.id.textViewNguyenLieu);
        ImageView imageViewDish = view.findViewById(R.id.imageViewDish);
        Button saveButton = view.findViewById(R.id.bt_Save);

        Bundle bundle = getArguments();
        if (bundle != null) {
            // Get the food ID passed through the bundle
            int foodId = bundle.getInt("foodId");
            fetchFoodById(foodId, ingredientEditText, imageViewDish);

            saveButton.setOnClickListener(v -> {
                // When the save button is clicked, we get the updated ingredient and send a PUT request
                String updatedIngredient = ingredientEditText.getText().toString();
                food.setIngredient(updatedIngredient);
                updateFood(food.getId(), food);  // Call the API to update the food
            });
        }

        return view;
    }

    private void fetchFoodById(int foodId, EditText ingredientEditText, ImageView imageViewDish) {
        RetrofitService retrofitService = new RetrofitService();
        FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);
        foodApi.getFoodById(foodId).enqueue(new Callback<FoodDto>() {
            @Override
            public void onResponse(Call<FoodDto> call, Response<FoodDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    food = response.body().toFood();
                    // Display food information in the UI
                    ingredientEditText.setText(food.getIngredient());

                    byte[] bytes = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        bytes = android.util.Base64.decode(food.getImage(), Base64.DEFAULT);
                    }
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageViewDish.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(getContext(), "Failed to fetch food details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodDto> call, Throwable t) {
                Toast.makeText(getContext(), "Error fetching food details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFood(int foodId, Food updatedFood) {
        RetrofitService retrofitService = new RetrofitService();
        FoodApi foodApi = retrofitService.getRetrofit().create(FoodApi.class);
        foodApi.updateFood(foodId, updatedFood).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Food updated successfully", Toast.LENGTH_SHORT).show();
                    // Navigate back to the previous fragment after successful update
                    Navigation.findNavController(getView()).popBackStack();
                } else {
                    Toast.makeText(getContext(), "Failed to update food", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Error updating food", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
