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

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.viewhoder> {
    ArrayList<DrinksCoffee> list;
    private ManagmentCart managmentCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartsAdapter(ArrayList<DrinksCoffee> list, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.list = list;
        managmentCart= new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartsAdapter.viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_viewhoder,parent,false);
        return new viewhoder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartsAdapter.viewhoder holder, int position) {
        holder.nameCart.setText(list.get(position).getName());
        holder.feeEach.setText("$"+list.get(position).getPrice());
        holder.totalFeeItem.setText(list.get(position).getNumberInCart()+"*$"+(
                list.get(position).getNumberInCart()*list.get(position).getPrice()));
        holder.numCart.setText(list.get(position).getNumberInCart()+"");

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImage_Url())
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.imgCart);

        holder.btnThemCart.setOnClickListener(view -> managmentCart.plusNumberItem(list, position, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            }
        }));

        holder.btnGiamCart.setOnClickListener(view -> managmentCart.minusNumberItem(list, position, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            }
        }));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewhoder extends RecyclerView.ViewHolder {
        TextView nameCart, feeEach, totalFeeItem, btnThemCart, btnGiamCart,numCart;

        ImageView imgCart;
        public viewhoder(@NonNull View itemView) {
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
