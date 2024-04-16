package com.example.project1.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1.R;
import com.example.project1.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private List<String> danhSachTaiKhoan = new ArrayList<>();
    private Button bt_dn,bt_gg;
    private TextView tv_dang_ki,tv_forgot;
    private TextInputEditText edt_name, edt_pass;
    private TextInputLayout textInputLayout2;
    private TextInputLayout textInputLayout1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhxa();
        nutDN();
        nutTVDK();
        loginGG();
        nut_forgotpass();

    }

    private void nut_forgotpass() {
        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
    }


    // gg
    private void loginGG() {
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> Log.d("Google Sign In", "Connection failed"))
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Bắt sự kiện khi click vào nút đăng nhập bằng Google
        findViewById(R.id.DangNhapGoogle).setOnClickListener(view -> signInWithGoogle());

    }
    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        }
    }
    private void handleGoogleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        } else {
            // Google Sign In failed
            Log.w("Google Sign In", "signInResult:failed code=" + result.getStatus());
            Toast.makeText(this, "Google Sign In failed", Toast.LENGTH_SHORT).show();
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        Log.d("Firebase Auth", "signInWithCredential:success");

                        // Lưu thông tin tài khoản Google vào Firestore
                        saveGoogleAccountToFirestore(acct);

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // Sign in fails
                        Log.w("Firebase Auth", "signInWithCredential:failure", task.getException());
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveGoogleAccountToFirestore(GoogleSignInAccount acct) {
        // Lấy thông tin tài khoản Google
        String googleUserId = acct.getId();
        String googleDisplayName = acct.getDisplayName();
        String googleEmail = acct.getEmail();
        String googlePhotoUrl = acct.getPhotoUrl() != null ? acct.getPhotoUrl().toString() : null;

        // Tạo một đối tượng Map chứa thông tin tài khoản Google
        Map<String, Object> googleAccount = new HashMap<>();
        googleAccount.put("userId", googleUserId);
        googleAccount.put("displayName", googleDisplayName);
        googleAccount.put("email", googleEmail);
        googleAccount.put("photoUrl", googlePhotoUrl);

        // Lưu thông tin vào Firestore
        db.collection("googleAccounts").document(googleUserId)
                .set(googleAccount)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Google account successfully written!"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error writing document", e));
    }

    //
    private void check_tk() {
        final String input = edt_name.getText().toString().trim();
        final String pass = edt_pass.getText().toString().trim();

        // Kiểm tra xem chuỗi nhập vào có định dạng email hay không
        if (isValidEmail(input)) {
            // Nếu có định dạng email, xử lý như đăng nhập bằng email
            db.collection("tk")
                    .whereEqualTo("email", input)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                // Tài khoản tồn tại, kiểm tra mật khẩu
                                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                String storedPassword = documentSnapshot.getString("pass");
                                if (storedPassword.equals(pass)) {
                                    // Đăng nhập thành công
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    // Sai mật khẩu
                                    Toast.makeText(LoginActivity.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Tài khoản không tồn tại
                                Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                                textInputLayout1.setBoxStrokeColor(getResources().getColor(R.color.red));
                                // Đổi hint thành "err"
                                textInputLayout2.setHint("error");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Đọc dữ liệu không thành công
                            Toast.makeText(LoginActivity.this, "Lỗi khi đọc dữ liệu", Toast.LENGTH_SHORT).show();

                            textInputLayout1.setBoxStrokeColor(getResources().getColor(R.color.red));
                            // Đổi hint thành "err"
                            textInputLayout2.setHint("error");
                        }
                    });
        } else {
            // Nếu không có định dạng email, xử lý như đăng nhập bằng tên người dùng
            db.collection("tk")
                    .document(input)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                // Tài khoản tồn tại, kiểm tra mật khẩu
                                String storedPassword = documentSnapshot.getString("pass");
                                if (storedPassword.equals(pass)) {
                                    // Đăng nhập thành công
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    // Sai mật khẩu
                                    Toast.makeText(LoginActivity.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                                    // Thiết lập màu đỏ cho viền TextInputLayout chứa phần nhập mật khẩu
                                    textInputLayout2.setBoxStrokeColor(getResources().getColor(R.color.red));
                                    // Đổi hint thành "err"
                                    textInputLayout2.setHint("error");
                                }
                            } else {
                                // Tài khoản không tồn tại
                                Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                                textInputLayout1.setBoxStrokeColor(getResources().getColor(R.color.red));
                                // Đổi hint thành "err"
                                textInputLayout2.setHint("error");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Đọc dữ liệu không thành công
                            Toast.makeText(LoginActivity.this, "Lỗi khi đọc dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }



    //Thêm phương thức xử lý sự kiện khi nhấn vào nút "Quên mật khẩu"
    private void forgotPassword() {
        // Hiển thị một hộp thoại để người dùng nhập địa chỉ email
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quên mật khẩu");
        final View viewInflated = LayoutInflater.from(this).inflate(R.layout.forgot_password_dialog, null);
        final TextInputEditText input = viewInflated.findViewById(R.id.editTextEmail);
        builder.setView(viewInflated);

        // Thiết lập nút "Gửi"
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = input.getText().toString().trim();
                if (isValidEmail(email)) {
                    // Gửi email đặt lại mật khẩu
                    sendPasswordResetEmail(email);
                } else {
                    // Hiển thị thông báo lỗi nếu địa chỉ email không hợp lệ
                    Toast.makeText(LoginActivity.this, "Địa chỉ email không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Thiết lập nút "Hủy"
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    // Phương thức gửi email đặt lại mật khẩu
    private void sendPasswordResetEmail(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Email đặt lại mật khẩu đã được gửi", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Không thể gửi email đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




    // Phương thức kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void nutTVDK() {
        tv_dang_ki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void nutDN() {
        bt_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_tk();
            }
        });
    }

    private void anhxa() {
        tv_forgot = findViewById(R.id.tv_forgotpass);
        bt_dn = findViewById(R.id.btt_DangNhap);
        bt_gg = findViewById(R.id.DangNhapGoogle);
        tv_dang_ki = findViewById(R.id.tv_dknow);
        edt_name = findViewById(R.id.dn_name);
        edt_pass = findViewById(R.id.dn_pass);
        textInputLayout2 = findViewById(R.id.textInputLayout2);
        textInputLayout1 = findViewById(R.id.textInputLayout1);
    }
}

