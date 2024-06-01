package com.example.myfoodapp.ui.shopping;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myfoodapp.R;

public class ShoppingFragment extends Fragment {

    private ShoppingViewModel mViewModel;

    public static ShoppingFragment newInstance() {
        return new ShoppingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button shopee = view.findViewById(R.id.buttonShopee);
        shopee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://shopeefood.vn/";
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                // Kiểm tra nếu có ứng dụng có thể mở liên kết này
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // Hiển thị thông báo lỗi hoặc xử lý thay thế
                    webpage = Uri.parse("https://shopeefood.vn/" );
                    intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(intent);
                    System.out.println("Không có ứng dụng để mở URL này!");
                }

            }
        });
        Button winMart = view.findViewById(R.id.buttonWinmart);
        winMart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://winmart.vn/";
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                // Kiểm tra nếu có ứng dụng có thể mở liên kết này
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // Hiển thị thông báo lỗi hoặc xử lý thay thế
                    webpage = Uri.parse("https://winmart.vn/" );
                    intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(intent);
                    System.out.println("Không có ứng dụng để mở URL này!");
                }

            }
        });
        Button grab = view.findViewById(R.id.buttonGrab);
        grab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://food.grab.com/vn/vi/";
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                // Kiểm tra nếu có ứng dụng có thể mở liên kết này
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // Hiển thị thông báo lỗi hoặc xử lý thay thế
                    webpage = Uri.parse("https://food.grab.com/vn/vi/" );
                    intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(intent);
                    System.out.println("Không có ứng dụng để mở URL này!");
                }

            }
        });
        Button other = view.findViewById(R.id.buttonOther);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.google.com/search?q=%C4%90%E1%BA%B7t+%C4%91%E1%BB%93+%C4%83n&sca_esv=b99a51cc48654f1f&sca_upv=1&sxsrf=ADLYWILlnew0agZih1d0IClC8lwD859cZg%3A1715636232822&source=hp&ei=CIhCZo2mL7yivr0Pl6m3gAs&iflsig=AL9hbdgAAAAAZkKWGMtDdwq5aSJoTtwqXx1gnVXpJoD4&udm=&ved=0ahUKEwiNyt-By4uGAxU8ka8BHZfUDbAQ4dUDCBU&uact=5&oq=%C4%90%E1%BA%B7t+%C4%91%E1%BB%93+%C4%83n&gs_lp=Egdnd3Mtd2l6IhDEkOG6t3QgxJHhu5MgxINuMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABEiYElAAWN0QcAd4AJABApgBhwGgAcEKqgEEMTQuMrgBA8gBAPgBAZgCFaACpwmoAgrCAgoQIxiABBgnGIoFwgIEECMYJ8ICCxAAGIAEGLEDGIMBwgIHECMYJxjqAsICCBAAGIAEGLEDwgIOEC4YgAQYsQMYgwEYigXCAgUQLhiABMICCxAuGIAEGLEDGIMBwgIOEAAYgAQYsQMYgwEYigXCAggQLhiABBixA8ICAhAmwgIGEAAYFhgewgIIEC4YFhgeGA_CAggQABgWGB4YD5gDBJIHBDIwLjGgB954&sclient=gws-wiz";
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                // Kiểm tra nếu có ứng dụng có thể mở liên kết này
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // Hiển thị thông báo lỗi hoặc xử lý thay thế
                    webpage = Uri.parse("https://www.google.com/search?q=%C4%90%E1%BA%B7t+%C4%91%E1%BB%93+%C4%83n&sca_esv=b99a51cc48654f1f&sca_upv=1&sxsrf=ADLYWILlnew0agZih1d0IClC8lwD859cZg%3A1715636232822&source=hp&ei=CIhCZo2mL7yivr0Pl6m3gAs&iflsig=AL9hbdgAAAAAZkKWGMtDdwq5aSJoTtwqXx1gnVXpJoD4&udm=&ved=0ahUKEwiNyt-By4uGAxU8ka8BHZfUDbAQ4dUDCBU&uact=5&oq=%C4%90%E1%BA%B7t+%C4%91%E1%BB%93+%C4%83n&gs_lp=Egdnd3Mtd2l6IhDEkOG6t3QgxJHhu5MgxINuMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABDIFEAAYgAQyBRAAGIAEMgUQABiABEiYElAAWN0QcAd4AJABApgBhwGgAcEKqgEEMTQuMrgBA8gBAPgBAZgCFaACpwmoAgrCAgoQIxiABBgnGIoFwgIEECMYJ8ICCxAAGIAEGLEDGIMBwgIHECMYJxjqAsICCBAAGIAEGLEDwgIOEC4YgAQYsQMYgwEYigXCAgUQLhiABMICCxAuGIAEGLEDGIMBwgIOEAAYgAQYsQMYgwEYigXCAggQLhiABBixA8ICAhAmwgIGEAAYFhgewgIIEC4YFhgeGA_CAggQABgWGB4YD5gDBJIHBDIwLjGgB954&sclient=gws-wiz" );
                    intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(intent);
                    System.out.println("Không có ứng dụng để mở URL này!");
                }

            }
        });
    }
}