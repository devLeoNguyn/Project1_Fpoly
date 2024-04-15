package com.example.project1.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.project1.R;
import com.example.project1.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#0C0F14"));
    }

    private void setVariable() {
        binding.txtLoginIntro.setOnClickListener(v -> {
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));// su li nut login
            }else{
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
        }
        });

        binding.txtRegisterIntro.setOnClickListener(view -> startActivity(new Intent(IntroActivity.this, RegisterActivity.class)));
    }
}