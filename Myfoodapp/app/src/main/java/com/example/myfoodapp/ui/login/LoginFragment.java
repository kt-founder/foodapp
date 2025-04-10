package com.example.myfoodapp.ui.login;

import static androidx.core.app.ActivityCompat.invalidateOptionsMenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfoodapp.MainActivity;
import com.example.myfoodapp.R;
import com.example.myfoodapp.model.User;
import com.example.myfoodapp.server.RetrofitService;
import com.example.myfoodapp.server.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private LoginListener loginListener;

    private EditText etUsername;
    private EditText etPassword;
     // Request code for Google SignIn

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        //SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        Button btnLogin = view.findViewById(R.id.btnLogin);
        Button btnRegister = view.findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            performLogin(v);
        });

        btnRegister.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.registerFragment);
        });
        com.google.android.gms.common.SignInButton btnGoogleSignIn = view.findViewById(R.id.btnGoogleSignIn);
        btnGoogleSignIn.setOnClickListener(v -> {
            String url = "https://accounts.google.com/";
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

            // Kiểm tra nếu có ứng dụng có thể mở liên kết này
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                // Hiển thị thông báo lỗi hoặc xử lý thay thế
                webpage = Uri.parse("https://accounts.google.com/" );
                intent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(intent);
                System.out.println("Không có ứng dụng để mở URL này!");
            }
        });
        //

        return view;
    }

    private void performLogin(View v) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        User tmp = new User();
        tmp.setUsername(username);
        tmp.setPassword(password);
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        userApi.login(tmp).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() ){
                    User user = response.body();
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("UserId", user.getId());
                    editor.putString("Username", user.getUsername());
                    editor.putString("UserRole", user.getRole());
                    editor.apply();
                    Toast.makeText(getContext(), "Login successful: " + tmp.getRole(), Toast.LENGTH_SHORT).show();
                    if (loginListener != null) {
                        loginListener.onLoginSuccess();
                    }
                    Navigation.findNavController(v).navigate(R.id.nav_home);
                } else {
                    Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
                }
            @Override
            public void onFailure(Call<User> call, Throwable throwable) {

            }
        });

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginListener) {
            loginListener = (LoginListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LoginListener");
        }
    }

    public interface LoginListener {
        void onLoginSuccess();
    }

}
