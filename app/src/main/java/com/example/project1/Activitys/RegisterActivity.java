package com.example.project1.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project1.Models.User;
import com.example.project1.R;
import com.example.project1.databinding.ActivityMainBinding;
import com.example.project1.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button bt_dk;
    TextInputEditText edt_name, edt_pass,edt_email,edt_pass2;
    ImageView img_back;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhxa();
        nutBack();
        nutDK();
        nutTVDN();




    }

    private void nutBack() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }

    private void nutTVDN() {
    }

    private void nutDK() {
        bt_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = edt_name.getText().toString().trim();
                final String pass = edt_pass.getText().toString().trim();
                final String pass2 = edt_pass2.getText().toString().trim();
                final String email = edt_email.getText().toString().trim();

                // Kiểm tra xem các trường đã được điền đầy đủ chưa
                if (name.isEmpty() || pass.isEmpty() || pass2.isEmpty() || email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra định dạng email
                if (!isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Email không hợp lệ, vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem mật khẩu nhập lại có trùng với mật khẩu ban đầu không
                if (!pass.equals(pass2)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Truy vấn Firebase Firestore để kiểm tra xem đã có tài khoản nào có tên trùng lặp hay chưa
                db.collection("tk")
                        .document(name)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    // Tài khoản đã tồn tại
                                    Toast.makeText(RegisterActivity.this, "Tên người dùng đã tồn tại, vui lòng chọn tên khác", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Tài khoản chưa tồn tại, tiến hành đăng ký
                                    User tk = new User(name, pass, email);

                                    Map<String, Object> dt = new HashMap<>();
                                    dt.put("name", name);
                                    dt.put("email", email);
                                    dt.put("pass", pass);

                                    db.collection("tk")
                                            .document(name)
                                            .set(dt)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText( RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, "Lỗi khi kiểm tra tên người dùng", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }


    private void anhxa() {
        //ánh xạ
        img_back = findViewById(R.id.imgback);
        bt_dk = findViewById(R.id.bttDangKy);
        edt_name = findViewById(R.id.dk_name);
        edt_pass = findViewById(R.id.dk_pass);
        edt_email = findViewById(R.id.dk_email);
        edt_pass2 = findViewById(R.id.dk_pass_2);
    }


    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}