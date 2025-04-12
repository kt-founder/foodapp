package com.example.myfoodapp.ui.register;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfoodapp.R;
import com.example.myfoodapp.model.User;
import com.example.myfoodapp.server.RetrofitService;
import com.example.myfoodapp.server.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etPassword1;
    private Button btnRegister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        User user = new User();
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        etPassword1 = view.findViewById(R.id.etConfirmPassword);
        btnRegister = view.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String confirm = etPassword1.getText().toString();

            if(password.equals(confirm)){
                user.setRole("user");
                user.setUsername(username);
                user.setPassword(password);
                RetrofitService retrofitService = new RetrofitService();
                UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
                userApi.addUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast.makeText(getContext(),"Dang ki thanh cong",Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.loginFragment);
                        //Toast.makeText(getContext(),throwable.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable throwable) {
                        Log.d("TAG",throwable.toString());
                    }
                });
            }
            else {
                Toast.makeText(getContext(), "password do not match", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
