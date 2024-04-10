package com.example.project1.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.project1.Adapter.BeansAdapter;
import com.example.project1.Adapter.BestDrinksAdapter;
import com.example.project1.Models.BeansCoffee;
import com.example.project1.Models.DrinksCoffee;
import com.example.project1.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //khai bao firebase
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    // khai bao ActivityMainBinding
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//khai bao ham lay danh sach tren firebase
//        ----------------
        initBestFood();
        initBean();
//        setVariable();

    }

//    private void setVariable(){
//        binding.
//    }

// Hàm khởi tạo để lấy dữ liệu thức uống ngon nhất


    private void setVariable() {
        //signup
        //search

        binding.btnCart.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CartActivity.class)));
    }
    private void initBestFood() {

        // Khởi tạo Firebase
        database = FirebaseDatabase.getInstance();

        // Lấy tham chiếu đến root của Firebase Realtime Database
        myRef = database.getReference("CoffeeDrink");

        // Thiết lập hiển thị thanh tiến trình trong khi lấy dữ liệu
        binding.progressBarBestFood.setVisibility(View.VISIBLE);

        // Khởi tạo danh sách rỗng để lưu trữ dữ liệu thức uống ngon nhất
        ArrayList<DrinksCoffee> list = new ArrayList<>();

        // Tạo truy vấn để tìm kiếm các node con có child "BestDrink" với giá trị là true
        // (Lưu ý: tên child có thể là "BestDrinks" theo đoạn code trước đó)
        Query query = myRef.orderByChild("bestdrink").equalTo(true);  // Sửa "BestDrink" thành "BestDrinks"

        // Lắng nghe sự kiện thay đổi dữ liệu từ Firebase Realtime Database
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            // Được gọi khi có dữ liệu mới được thêm, cập nhật hoặc xóa
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    Log.d("Firebase", "Dữ liệu snapshot tồn tại, đang xử lý...");
                {
                    // Lặp qua từng node con (mỗi phần tử trong kết quả)
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        // Xử lý dữ liệu của từng node con (phần tử)
                        list.add(childSnapshot.getValue(DrinksCoffee.class));
                        DrinksCoffee drink = childSnapshot.getValue(DrinksCoffee.class);
                        Log.d("Firebase", "Thức uống: " + drink.getName() + ", Mô tả: " + drink.getDescription());

                    }
                    Log.d("Firebase", "Nhận dữ liệu thành công: " + list.size() + " mục");
                    if (list.size() > 0) {
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter adapter = new BestDrinksAdapter(list);
                        binding.recyclerView.setAdapter(adapter);

                    }
                    // Ẩn thanh tiến trình khi đã lấy xong dữ liệu
                    binding.progressBarBestFood.setVisibility(View.GONE);
                }
            }

            // Được gọi khi có lỗi xảy ra trong quá trình truy vấn dữ liệu
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error fetching data", error.toException());
                // Xử lý lỗi nếu cần
            }
        });
    }

    private void initBean() {
        // Khởi tạo Firebase
        database = FirebaseDatabase.getInstance();

        // Lấy tham chiếu đến root của Firebase Realtime Database
        myRef = database.getReference("CoffeeBean");

        // Thiết lập hiển thị thanh tiến trình trong khi lấy dữ liệu
        binding.progressBarBean.setVisibility(View.VISIBLE);

        ArrayList<BeansCoffee> listBean = new ArrayList<>();
        // Thực hiện lắng nghe sự kiện khi dữ liệu thay đổi trên Firebase
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa danh sách cũ trước khi thêm dữ liệu mới
                    listBean.clear();

                // Duyệt qua từng nút con trong nút gốc "CoffeeBean"
                if (dataSnapshot.exists())
                    Log.d("Firebase", "Dữ liệu snapshot tồn tại, đang xử lý...");
                {
                    // Lặp qua từng node con (mỗi phần tử trong kết quả)
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        // Xử lý dữ liệu của từng node con (phần tử)
                        listBean.add(childSnapshot.getValue(BeansCoffee.class));
                        BeansCoffee bean = childSnapshot.getValue(BeansCoffee.class);
                        Log.d("Firebase", "Hat: " + bean.getName_Bean()+ ", Mô tả: " + bean.getDescription_Bean());

                    }
                    Log.d("Firebase", "Nhận dữ liệu Bean thành công: " + listBean.size() + " mục");
                    if (listBean.size() > 0) {
                        binding.recyclerViewBean.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter adapterBean = new BeansAdapter(listBean);
                        binding.recyclerViewBean.setAdapter(adapterBean);

                    }
                    // Ẩn thanh tiến trình khi đã lấy xong dữ liệu
                    binding.progressBarBean.setVisibility(View.GONE);
                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            Log.e("Firebase", "Error fetching data BEAN", databaseError.toException());
            // Xử lý lỗi nếu cần
            }
        });
    }
}


