package com.example.studentmanager4;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilUsername, tilPassword;
    private TextInputEditText edtUsername, edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các TextInputLayout để hiển thị lỗi
        tilUsername = findViewById(R.id.edtUsername).getParent().getParent() instanceof TextInputLayout ? 
                (TextInputLayout) findViewById(R.id.edtUsername).getParent().getParent() : null;
        tilPassword = findViewById(R.id.edtPassword).getParent().getParent() instanceof TextInputLayout ? 
                (TextInputLayout) findViewById(R.id.edtPassword).getParent().getParent() : null;
        
        // Vì findViewById trả về View, ta cần ép kiểu về đúng TextInputEditText
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLogin()) {
                    performLogin();
                }
            }
        });
    }

    private boolean validateLogin() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        boolean isValid = true;

        // Reset lỗi
        if (tilUsername != null) tilUsername.setError(null);
        if (tilPassword != null) tilPassword.setError(null);

        // Kiểm tra username
        if (TextUtils.isEmpty(username)) {
            if (tilUsername != null) tilUsername.setError("Tên đăng nhập không được để trống");
            isValid = false;
        } else if (username.length() < 3) {
            if (tilUsername != null) tilUsername.setError("Tên đăng nhập phải ít nhất 3 ký tự");
            isValid = false;
        }

        // Kiểm tra password
        if (TextUtils.isEmpty(password)) {
            if (tilPassword != null) tilPassword.setError("Mật khẩu không được để trống");
            isValid = false;
        } else if (password.length() < 6) {
            if (tilPassword != null) tilPassword.setError("Mật khẩu phải ít nhất 6 ký tự");
            isValid = false;
        }

        return isValid;
    }

    private void performLogin() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Kiểm tra tài khoản mẫu
        if (username.equals("admin") && password.equals("123456")) {
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }
}
