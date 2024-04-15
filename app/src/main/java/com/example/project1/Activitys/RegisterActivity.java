package com.example.project1.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.project1.R;
import com.example.project1.databinding.ActivityMainBinding;
import com.example.project1.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends BaseActivity {
ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
    }
    private void setVariable() {
        binding.btnRegister.setOnClickListener(view -> {
            binding.btnRegister.setOnClickListener(v -> {
                String email = binding.edtEmail.getText().toString();
                String password = binding.edtPass.getText().toString();

                // Kiểm tra chiều dài mật khẩu
                if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Your password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Đăng ký người dùng mới
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, task -> {
                    if (task.isSuccessful()) {
                        Log.i(TAG, "onComplete: Registration successful");
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        // Đăng ký thành công, chuyển hướng đến màn hình chính hoặc màn hình đăng nhập
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish(); // Kết thúc activity hiện tại sau khi chuyển hướng
                    } else {
                        // Xử lý các trường hợp đặc biệt: email đã được sử dụng hoặc lỗi trong quá trình đăng ký
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(RegisterActivity.this, "This email is already registered. Please use another email.", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "onComplete: Registration failed", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });

        });
    }
}