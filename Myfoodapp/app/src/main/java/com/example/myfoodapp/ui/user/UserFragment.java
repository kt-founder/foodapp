package com.example.myfoodapp.ui.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.myfoodapp.R;

public class UserFragment extends Fragment {
    private Button user_tk,user_congthuc,user_quanly,user_thich,user_thucdon;
    private Button admin_tk,admin_congthuc,admin_quanly,admin_thongke;
    private LinearLayout layout_user,layout_admin;
    private TextView tv;
    private String role = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        tv = view.findViewById(R.id.tv_tienIch);
        layout_user = view.findViewById(R.id.user);
        user_tk = view.findViewById(R.id.user_tk);
        user_congthuc = view.findViewById(R.id.user_congthuc);
        user_thich = view.findViewById(R.id.user_thich);
        user_thucdon = view.findViewById(R.id.user_thucdon);
        admin_quanly = view.findViewById(R.id.admin_quanly);
        user_congthuc.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_upload_congThuc));
        user_thucdon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Chức năng đang phát triển",Toast.LENGTH_LONG).show();
            }
        });
        user_tk.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.nav_change_password));
        user_thich.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_DSYeuThich));
        admin_quanly.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.nav_admin_quanly));
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        role = sharedPreferences.getString("UserRole",null);
//        if(role == null){
//            Navigation.findNavController(view).navigate(R.id.loginFragment);
//        }else if(role.equals("user")){
//            layout_admin.setVisibility(View.INVISIBLE);
//            tv.setText("Tiện ích của app");
//        }else{
//            tv.setText("Bạn đang sử dụng quyền admin");
//        }
    }

}
