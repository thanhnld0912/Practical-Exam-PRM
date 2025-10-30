package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.shopping.R;
import com.example.phonestore.ui.fragment.CartFragment;
import com.example.phonestore.ui.fragment.ProductListFragment;
import com.example.phonestore.ui.fragment.RevenueFragment;
import com.example.phonestore.ui.fragment.StoreMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);

        // Mặc định hiển thị danh sách sản phẩm
        loadFragment(new ProductListFragment());

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

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}