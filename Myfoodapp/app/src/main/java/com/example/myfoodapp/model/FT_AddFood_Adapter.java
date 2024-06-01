package com.example.myfoodapp.model;

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

import java.util.ArrayList;
import java.util.List;

public class FT_AddFood_Adapter extends RecyclerView.Adapter<FT_AddFood_Adapter.FTAddFoodHolder>{
    private List<TypeFood> typeFoodList ;
    private SparseBooleanArray checkedItems;
    private Context context;

    public FT_AddFood_Adapter(List<TypeFood> typeFoodList, Context context) {
        this.typeFoodList = typeFoodList;
        this.context = context;
        this.checkedItems= new SparseBooleanArray();
    }

    @NonNull
    @Override
    public FTAddFoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tf_addfood,parent,false);
        return new FTAddFoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FTAddFoodHolder holder, int position) {
        TypeFood typeFood = typeFoodList.get(position);
        if (typeFood==null) return;;
        holder.cb.setText((CharSequence) typeFood.getName());
        holder.cb.setChecked(checkedItems.get(position, false));
        holder.cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkedItems.put(position, isChecked);
        });
    }

    @Override
    public int getItemCount() {
        if(typeFoodList!=null) return typeFoodList.size();
        return 0;
    }

    public class FTAddFoodHolder extends RecyclerView.ViewHolder{
        private CheckBox cb;
        private CardView cv;
        public FTAddFoodHolder(@NonNull View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.item_tf_addfood);
            cv= itemView.findViewById(R.id.cv_tfafood);
        }
    }
    public List<TypeFood> getTypeFoods() {
        List<TypeFood> checkedTypeFoods = new ArrayList<>();
        for (int i = 0; i < typeFoodList.size(); i++) {
            if (checkedItems.get(i)) {
                checkedTypeFoods.add(typeFoodList.get(i));
            }
        }
        return checkedTypeFoods;
    }
}
