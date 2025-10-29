package ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.example.phonestore.R;
import com.example.phonestore.data.UserDAO;
import com.example.phonestore.util.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    CheckBox chkRemember;
    Button btnLogin, btnRegister;
    UserDAO userDAO;
    SharedPrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        chkRemember = findViewById(R.id.chkRemember);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        userDAO = new UserDAO(this);
        pref = new SharedPrefManager(this);

        // Nếu user đã chọn Remember Me
        if (pref.isRemembered()) {
            edtEmail.setText(pref.getEmail());
            edtPassword.setText(pref.getPassword());
            chkRemember.setChecked(true);
        }

        btnLogin.setOnClickListener(v -> loginUser());
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String pass = edtPassword.getText().toString();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isValid = userDAO.checkLogin(email, pass);

        if (isValid) {
            pref.saveLogin(email, pass, chkRemember.isChecked());
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }
}