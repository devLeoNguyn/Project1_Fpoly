package com.example.project1.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.project1.Models.CategoriesCoffee;
import com.example.project1.Models.DrinksCoffee;
import com.example.project1.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {
    ArrayList<CategoriesCoffee> items;


    Context context;

    public CategoryAdapter(ArrayList<CategoriesCoffee> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_viewhoder, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewholder holder, int position) {
        holder.txtNameCategory.setText(items.get(position).getName());

        int drawableResourceId;
        if (position == 0) {
            drawableResourceId = R.drawable.background_category;
        } else {
            drawableResourceId = context.getResources().getIdentifier(items.get(position).getImage_Url(), "drawable", holder.itemView.getContext().getPackageName());
        }

        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.imgCategory);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtNameCategory;
        ImageView imgCategory;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            txtNameCategory = itemView.findViewById(R.id.txtName_Category);
            imgCategory = itemView.findViewById(R.id.img_Category);

        }
    }
}
