package ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import com.example.shopping.R;
import data.UserDAO;
import model.User;
import util.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    EditText edEmail, edPassword;
    CheckBox chkRemember;
    Button btnLogin, btnRegister;
    UserDAO userDAO;
    SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        chkRemember = findViewById(R.id.chkRemember);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        userDAO = new UserDAO(this);
        prefManager = new SharedPrefManager(this);

        if (prefManager.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        if (prefManager.isRemembered()) {
            edEmail.setText(prefManager.getEmail());
            edPassword.setText(prefManager.getPassword());
            chkRemember.setChecked(true);
        }

        btnLogin.setOnClickListener(v -> {
            String email = edEmail.getText().toString().trim();
            String password = edPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = userDAO.getUserByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                // Save session (incl. role)
                prefManager.saveLogin(user, chkRemember.isChecked());
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }
}