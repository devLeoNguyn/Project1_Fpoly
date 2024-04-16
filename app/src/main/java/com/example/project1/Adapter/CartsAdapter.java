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
import com.example.project1.Helper.ChangeNumberItemsListener;
import com.example.project1.Helper.ManagmentCart;
import com.example.project1.Models.DrinksCoffee;
import com.example.project1.R;

import java.util.ArrayList;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.ViewHolder> {
    private ArrayList<DrinksCoffee> itemList;
    private Context context;
    private ManagmentCart managmentCart;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public CartsAdapter(ArrayList<DrinksCoffee> itemList, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.itemList = itemList;
        this.context = context;
        this.managmentCart = new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_viewhoder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DrinksCoffee item = itemList.get(position);

        holder.nameCart.setText(item.getName());
        holder.feeEach.setText("$" + item.getPrice());
        holder.numCart.setText(String.valueOf(item.getNumberInCart()));
        holder.totalFeeItem.setText("$" + (item.getNumberInCart() * item.getPrice()));

        Glide.with(context)
                .load(item.getImage_Url())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.imgCart);

        holder.btnThemCart.setOnClickListener(view -> {
            managmentCart.plusNumberItem(itemList, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            });
        });

        holder.btnGiamCart.setOnClickListener(view -> {
            managmentCart.minusNumberItem(itemList, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            });
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameCart, feeEach, totalFeeItem, btnThemCart, btnGiamCart, numCart;
        ImageView imgCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCart = itemView.findViewById(R.id.txtNameItem_Cart);
            feeEach = itemView.findViewById(R.id.txtFeeEach);
            totalFeeItem = itemView.findViewById(R.id.txtTotalFeeItem);
            btnGiamCart = itemView.findViewById(R.id.btnGiam_Cart);
            btnThemCart = itemView.findViewById(R.id.btnThem_Cart);
            numCart = itemView.findViewById(R.id.txtNumCart);
            imgCart = itemView.findViewById(R.id.imgItem_Cart);
        }
    }
}
