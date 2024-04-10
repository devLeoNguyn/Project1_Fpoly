package com.example.project1.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;  // Only needed if using Parcelable for a field
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.project1.Helper.ManagmentCart;
import com.example.project1.Models.DrinksCoffee;
import com.example.project1.R;
import com.example.project1.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private DrinksCoffee object;
    private int num = 1;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();  // Assuming you have a method to set UI elements based on object data
    }

    private void setVariable() {
        managmentCart = new ManagmentCart(this);

        if (object == null) {
            // Handle the case where object is null (e.g., show error message)
            return;
        }
        binding.backCartBtn.setOnClickListener(v -> finish());
        Glide.with(DetailActivity.this)
                .load(object.getImage_Url())
                .into(binding.imgDrinkDetail);
        binding.txtPriceDetail.setText("$"+object.getPrice());
        // Thiết lập các giá trị còn thiếu
        binding.txtDecDetail.setText(object.getDescription());
        binding.ratingDetail.setRating((float) object.getStar());
        binding.txtTotalDetail.setText((num*object.getPrice()+""));
        binding.txtStarDetail.setText(object.getStar()+"");
        binding.txtNameDetail.setText(object.getName());

        binding.btnTang.setOnClickListener(view -> {
            num = num + 1;
            binding.txtNum.setText(num + "");
            binding.txtTotalDetail.setText("$" + (num + object.getPrice()));
        });

        binding.btnGiam.setOnClickListener(view -> {
            if (num > 1) {
                num = num - 1;
                binding.txtNum.setText(num + "");
                binding.txtTotalDetail.setText("$" + (num + object.getPrice()));

            }
        });

        binding.btnAddCart.setOnClickListener(view -> {
            object.setNumberInCart(num);
            managmentCart.insertFood(object);
        });



    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("drinkInfo");  // Assuming the data is sent in a Bundle with key "drinkInfo"

        if (bundle == null) {
            // Handle the case where bundle is null (e.g., show error message)
            return;
        }

        // Lấy dữ liệu từ Bundle
        String name = bundle.getString("name");
        double price = bundle.getDouble("price");
//        String decDetail = bundle.getString("decDetail");
        double star = bundle.getDouble("star");
        String dec = bundle.getString("dec");
        String imageUrl = bundle.getString("imageUrl");
        double rating = bundle.getDouble("rating");

        // Kiểm tra xem tất cả các giá trị từ bundle có tồn tại không
        if (name == null || price < 0 || dec == null || imageUrl == null ) {
            // Nếu một trong các giá trị không hợp lệ, không thực hiện thêm bất kỳ xử lý nào
            return;
        }

        // Tạo đối tượng DrinksCoffee và thiết lập các giá trị từ bundle
        object = new DrinksCoffee();
        object.setName(name);
        object.setPrice(price);
        object.setDescription(dec);  // Assuming description field name is "dec"
//        object.setDescription_Detail(decDetail);
        object.setStar(star);
        object.setImage_Url(imageUrl);
        object.setStar(rating);
    }



}
