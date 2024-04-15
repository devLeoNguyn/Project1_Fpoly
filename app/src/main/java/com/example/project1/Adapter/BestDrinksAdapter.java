package com.example.project1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.project1.Activitys.DetailActivity;
import com.example.project1.Models.DrinksCoffee;
import com.example.project1.R;

import java.util.ArrayList;

public class BestDrinksAdapter extends RecyclerView.Adapter<BestDrinksAdapter.viewhoder> {
    ArrayList<DrinksCoffee> items;
    Context context;

    public BestDrinksAdapter(ArrayList<DrinksCoffee> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BestDrinksAdapter.viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produce_viewhoder, parent, false);
        return new viewhoder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull BestDrinksAdapter.viewhoder holder, @SuppressLint("RecyclerView") int position) {
        int currentPosition = holder.getAdapterPosition();

        holder.txtName.setText(items.get(position).getName());
        holder.txtDec.setText(items.get(position).getDescription());
        holder.txtPrice.setText("" + items.get(position).getPrice());
        holder.txtStar.setText("" + items.get(position).getStar());

        Glide.with(context)
                .load(items.get(position).getImage_Url())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.imgDrink);

        ///chuyen man hinh sang chi tiet
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDrink = new Intent(context, DetailActivity.class);

                // Tạo Bundle để lưu trữ dữ liệu DrinksCoffee
                Bundle drinkInfoBundle = new Bundle();
                drinkInfoBundle.putString("name", items.get(position).getName());
                drinkInfoBundle.putDouble("price", items.get(position).getPrice());
                drinkInfoBundle.putString("decDetail", items.get(position).getDescription());
                drinkInfoBundle.putDouble("star", items.get(position).getStar());
                drinkInfoBundle.putString("dec", items.get(position).getDescription());
                drinkInfoBundle.putString("imageUrl", items.get(position).getImage_Url());
                drinkInfoBundle.putDouble("rating", items.get(position).getStar());

                // Thêm Bundle vào Intent
                intentDrink.putExtra("drinkInfo", drinkInfoBundle);

                context.startActivity(intentDrink);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewhoder extends RecyclerView.ViewHolder {
        TextView txtName, txtDec, txtPrice, txtStar;
        ImageView imgDrink;

        public viewhoder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtDec = itemView.findViewById(R.id.txtDec);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtStar = itemView.findViewById(R.id.txtStar);
            imgDrink = itemView.findViewById(R.id.imgDrink);
        }
    }
}
