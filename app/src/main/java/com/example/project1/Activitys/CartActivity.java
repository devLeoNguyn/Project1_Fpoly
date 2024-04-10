package com.example.project1.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.project1.Adapter.CartsAdapter;
import com.example.project1.Helper.ChangeNumberItemsListener;
import com.example.project1.Helper.ManagmentCart;
import com.example.project1.R;
import com.example.project1.databinding.ActivityCartBinding;
import com.example.project1.databinding.ActivityDetailBinding;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private  double tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        setVariable();
        calculateCart();
        initListCart();
    }

    private void initListCart() {
        if (managmentCart.getListCart().isEmpty()) {
            binding.txtEmpty.setVisibility(View.VISIBLE);
            binding.scrollCart.setVisibility(View.GONE);
        } else {
            binding.txtEmpty.setVisibility(View.GONE);
            binding.scrollCart.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.cartRecycle.setLayoutManager(linearLayoutManager);
        adapter = new CartsAdapter(managmentCart.getListCart(), this, () -> calculateCart());
        binding.cartRecycle.setAdapter(adapter);
    }
    private void calculateCart() {
        double percentTax = 0.02;
        double deliver = 10;

        tax = Math.round(managmentCart.getTotalFee() * percentTax *100.0) / 100;

        double total = Math.round((managmentCart.getTotalFee() + tax + deliver) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalFee() * 100) / 100;

        binding.txtTotalFee.setText("$" + itemTotal);
        binding.txtTax.setText("$" + tax);
        binding.txtDelivery.setText("$" + deliver);
        binding.txtTotal.setText("$" +  total);
    }

    private void setVariable() {
        binding.backCartBtn.setOnClickListener(view -> finish());
    }
}