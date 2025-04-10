package com.example.myfoodapp.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.model.TypeFood;
import com.example.myfoodapp.model.TypeFoodResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FT_AddFood_Adapter extends RecyclerView.Adapter<FT_AddFood_Adapter.FTAddFoodHolder> {
    private List<TypeFoodResponseDto> typeFoodList;
    private SparseBooleanArray checkedItems;
    private Context context;
    private Set<TypeFood> selectedTypeFoods;  // Selected types (already selected in food)

    public FT_AddFood_Adapter(List<TypeFoodResponseDto> typeFoodList, Context context) {
        this.typeFoodList = typeFoodList;
        this.context = context;
        this.checkedItems = new SparseBooleanArray();
    }

    // Setter for selectedTypeFoods (for Edit mode)
    public void setSelectedTypeFoods(Set<TypeFood> selectedTypeFoods) {
        this.selectedTypeFoods = selectedTypeFoods;

        // Cập nhật trạng thái checkbox cho mỗi món
        for (int i = 0; i < typeFoodList.size(); i++) {
            TypeFoodResponseDto typeFood = typeFoodList.get(i);
            boolean isSelected = isTypeFoodSelected(typeFood);  // Kiểm tra nếu món này đã được chọn
            checkedItems.put(i, isSelected);  // Cập nhật trạng thái checkbox
        }
        notifyDataSetChanged();  // Cập nhật lại UI
    }


    @NonNull
    @Override
    public FTAddFoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tf_addfood, parent, false);
        return new FTAddFoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FTAddFoodHolder holder, int position) {
        TypeFoodResponseDto typeFood = typeFoodList.get(position);
        if (typeFood == null) return;

        holder.cb.setText(typeFood.getName());

        // Kiểm tra xem loại món này có được chọn trong selectedTypeFoods không
        boolean isChecked = isTypeFoodSelected(typeFood);
        holder.cb.setChecked(isChecked);

        // Thêm listener cho checkbox để lưu lại trạng thái khi thay đổi
        holder.cb.setOnCheckedChangeListener((buttonView, isChecked1) -> {
            checkedItems.put(position, isChecked1);  // Lưu trạng thái checkbox
        });
    }


    @Override
    public int getItemCount() {
        return typeFoodList != null ? typeFoodList.size() : 0;
    }

    public class FTAddFoodHolder extends RecyclerView.ViewHolder {
        private CheckBox cb;
        private CardView cv;

        public FTAddFoodHolder(@NonNull View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.item_tf_addfood);
            cv = itemView.findViewById(R.id.cv_tfafood);
        }
    }

    // Function to check if this typeFood is selected
    private boolean isTypeFoodSelected(TypeFoodResponseDto typeFood) {
        if (selectedTypeFoods != null) {
            for (TypeFood selectedType : selectedTypeFoods) {
                if (selectedType.getId() == typeFood.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    // Get selected type foods from checkbox
    public List<TypeFoodResponseDto> getTypeFoods() {
        List<TypeFoodResponseDto> checkedTypeFoods = new ArrayList<>();
        for (int i = 0; i < typeFoodList.size(); i++) {
            if (checkedItems.get(i)) {
                checkedTypeFoods.add(typeFoodList.get(i));
            }
        }
        return checkedTypeFoods;
    }
}

