package ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // ⚠️ import bắt buộc
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shopping.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import ui.fragment.CartFragment;
import ui.fragment.ProductListFragment;
import ui.fragment.RevenueFragment;
import ui.fragment.StoreMapFragment;
import util.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    SharedPrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ⚙️ 1️⃣ Gắn Toolbar (thanh trên cùng)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Giờ bạn có thể hiển thị menu Logout ở góc phải trên cùng

        // ⚙️ 2️⃣ Khởi tạo SharedPrefManager & BottomNavigation
        bottomNav = findViewById(R.id.bottom_navigation);
        pref = new SharedPrefManager(this);

        // ⚙️ 3️⃣ Ẩn tab Revenue nếu là user
        if (!pref.getRole().equalsIgnoreCase("admin")) {
            bottomNav.getMenu().removeItem(R.id.nav_revenue);
        }

        // ⚙️ 4️⃣ Fragment mặc định khi mở app
        loadFragment(new ProductListFragment());

        // ⚙️ 5️⃣ Sự kiện chuyển tab
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selected = null;
            int id = item.getItemId();

            if (id == R.id.nav_products) {
                selected = new ProductListFragment();
            } else if (id == R.id.nav_cart) {
                selected = new CartFragment();
            } else if (id == R.id.nav_revenue) {
                selected = new RevenueFragment();
            } else if (id == R.id.nav_map) {
                selected = new StoreMapFragment();
            }

            if (selected != null) {
                loadFragment(selected);
                return true;
            }
            return false;
        });
    }

    // ⚙️ 6️⃣ Hàm load fragment
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    // ⚙️ 7️⃣ Menu Logout trên Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            pref.logout();

            Intent i = new Intent(this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
