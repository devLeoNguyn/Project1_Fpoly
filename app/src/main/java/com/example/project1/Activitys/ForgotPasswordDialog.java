package com.example.project1.Activitys;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.project1.R;

public class ForgotPasswordDialog extends DialogFragment {
    private EditText editTextEmail;
    private Button buttonSendResetEmail;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.forgot_password_dialog, null);
        builder.setView(dialogView);

        editTextEmail = dialogView.findViewById(R.id.editTextEmail);
        buttonSendResetEmail = dialogView.findViewById(R.id.buttonSendResetEmail);

        buttonSendResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi người dùng nhấn nút Gửi email đặt lại mật khẩu
                String email = editTextEmail.getText().toString().trim();
                // Gửi yêu cầu đặt lại mật khẩu ở đây (có thể gọi phương thức gửi email đặt lại mật khẩu từ Firebase)
                dismiss(); // Đóng dialog sau khi xử lý
            }
        });

        return builder.create();
    }
}
