package ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import data.CartDAO;
import data.OrderDAO;
import data.ProductDAO;
import model.CartItem;
import model.Order;
import model.Product;
import ui.adapter.CartAdapter;

import java.text.SimpleDateFormat;
import java.util.*;

public class CartFragment extends Fragment {

    private RecyclerView rvCart;
    private TextView tvTotal;
    private Button btnCheckout;
    private CartAdapter adapter;
    private CartDAO cartDAO;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);

        rvCart = view.findViewById(R.id.rvCart);
        tvTotal = view.findViewById(R.id.tvTotal);
        btnCheckout = view.findViewById(R.id.btnCheckout);

        cartDAO = new CartDAO(getContext());
        productDAO = new ProductDAO(getContext());
        orderDAO = new OrderDAO(getContext());

        rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        loadCart();

        btnCheckout.setOnClickListener(v -> checkout());

        return view;
    }

    private void loadCart() {
        List<CartItem> cartItems = cartDAO.getAll();
        adapter = new CartAdapter(getContext(), cartItems, productDAO, newTotal -> tvTotal.setText("Tổng: " + newTotal + " VNĐ"));
        rvCart.setAdapter(adapter);
        tvTotal.setText("Tổng: " + adapter.getTotalAmount() + " VNĐ");
    }

    private void checkout() {
        double total = adapter.getTotalAmount();
        if (total == 0) {
            Toast.makeText(getContext(), "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận thanh toán")
                .setMessage("Bạn có muốn đặt hàng với tổng tiền " + total + " VNĐ?")
                .setPositiveButton("Đồng ý", (dialog, which) -> {
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    Order order = new Order(0, total, date);
                    orderDAO.insert(order);

                    cartDAO.clear();
                    loadCart();
                    Toast.makeText(getContext(), "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Huỷ", null)
                .show();
    }
}