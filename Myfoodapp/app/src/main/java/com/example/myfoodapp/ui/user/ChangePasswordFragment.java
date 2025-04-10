package com.example.myfoodapp.ui.user;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.myfoodapp.model.ChangePasswordRequest;
import com.example.myfoodapp.server.RetrofitService;
import com.example.myfoodapp.server.UserApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment {

    private EditText oldPassword, newPassword, confirmPassword;
    private Button btnChangePassword;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chang_password, container, false);

        // Khởi tạo các view
        oldPassword = root.findViewById(R.id.old_password);
        newPassword = root.findViewById(R.id.new_password);
        confirmPassword = root.findViewById(R.id.confirm_password);
        btnChangePassword = root.findViewById(R.id.btn_change_password);

        // Xử lý sự kiện khi người dùng nhấn nút Đổi mật khẩu
        btnChangePassword.setOnClickListener(v -> {
            String oldPass = oldPassword.getText().toString().trim();
            String newPass = newPassword.getText().toString().trim();
            String confirmPass = confirmPassword.getText().toString().trim();

            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(getContext(), "Mật khẩu mới và xác nhận không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gọi API để thay đổi mật khẩu (chưa triển khai API)
            changePassword(oldPass, newPass,root);
        });

        return root;
    }

    private void changePassword(String oldPass, String newPass,View v) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int authID = sharedPreferences.getInt("UserId", -1);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(oldPass, newPass);
        userApi.updatePassword(authID,changePasswordRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Cập nhật mật khẩu thành công
                    Toast.makeText(getContext(), "Mật khẩu đã được thay đổi.", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(v).navigate(R.id.nav_user);
                    // Chuyển hướng đến trang khác hoặc làm gì đó tùy theo yêu cầu của bạn
                } else {
                    // Lỗi từ API
                    Toast.makeText(getContext(), "Mật khẩu cũ không đúng.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(getContext(), "Lỗi khi thay đổi mật khẩu.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
