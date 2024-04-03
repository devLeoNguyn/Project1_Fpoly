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
import com.example.project1.Models.BeansCoffee;
import com.example.project1.Models.DrinksCoffee;
import com.example.project1.R;

import java.util.ArrayList;

public class BeansAdapter extends RecyclerView.Adapter<BeansAdapter.viewhoderBean> {
    ArrayList<BeansCoffee> itemsBean;
    Context contextBean;

    public BeansAdapter(ArrayList<BeansCoffee> itemsBean){
        this.itemsBean = itemsBean;
    }
    @NonNull
    @Override
    public viewhoderBean onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        contextBean =  parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beanproduct_viewhoder, parent, false);
        return new BeansAdapter.viewhoderBean(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewhoderBean holder, int position) {
        holder.txtName_Bean.setText(itemsBean.get(position).getName_Bean());
        holder.txtDec_Bean.setText(itemsBean.get(position).getDescription_Bean());
        holder.txtPrice_Bean.setText("" + itemsBean.get(position).getPrice_Bean());
        holder.txtStar_Bean.setText(""+ itemsBean.get(position).getStar_Bean());

        Glide.with(contextBean)
                .load(itemsBean.get(position).getImageUrl_Bean())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.img_Bean);
    }

    @Override
    public int getItemCount() {
        return itemsBean.size();
    }

    public class viewhoderBean extends RecyclerView.ViewHolder{
        TextView txtName_Bean,txtDec_Bean,txtPrice_Bean,txtStar_Bean;
        ImageView img_Bean;

        public viewhoderBean(@NonNull View itemView) {
            super(itemView);
            txtName_Bean = itemView.findViewById(R.id.txtName_Bean);
            txtDec_Bean = itemView.findViewById(R.id.txtDec_Bean);
            txtPrice_Bean = itemView.findViewById(R.id.txtPrice_Bean);
            txtStar_Bean = itemView.findViewById(R.id.txtStar_Bean);
            img_Bean = itemView.findViewById(R.id.img_Bean);
        }

    }
}

