package ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import com.example.shopping.R;
import data.UserDAO;
import model.User;

public class RegisterActivity extends AppCompatActivity {

    EditText edtFullName, edtEmail, edtPassword, edtConfirm;
    Button btnRegister;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirm = findViewById(R.id.edtConfirm);
        btnRegister = findViewById(R.id.btnRegister);

        userDAO = new UserDAO(this);

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String pass = edtPassword.getText().toString();
        String confirm = edtConfirm.getText().toString();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirm)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(0, name, email, pass, "user"); // default role = user
        long result = userDAO.insertUser(user);

        if (result > 0) {
            Toast.makeText(this, "Đăng ký thành công! Vui lòng đăng nhập.", Toast.LENGTH_SHORT).show();
            finish(); // quay về LoginActivity
        } else {
            Toast.makeText(this, "Email đã tồn tại!", Toast.LENGTH_SHORT).show();
        }
    }
}