package ui.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import data.CartDAO;
import data.ProductDAO;
import data.UserDAO;
import model.Product;
import model.User;
import ui.adapter.ProductAdapter;
import util.SharedPrefManager;

import java.util.List;

public class ProductListFragment extends Fragment implements ProductAdapter.OnProductClickListener {

    private RecyclerView rvProducts;
    private ProductAdapter adapter;
    private ProductDAO productDAO;
    private EditText edtSearch;
    private Button btnSort, btnAdd;
    private boolean ascending = true;
    private boolean isAdmin = false;
    private SharedPrefManager pref;
    private CartDAO cartDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        rvProducts = view.findViewById(R.id.rvProducts);
        edtSearch = view.findViewById(R.id.edtSearch);
        btnSort = view.findViewById(R.id.btnSort);
        btnAdd = view.findViewById(R.id.btnAdd);

        productDAO = new ProductDAO(getContext());
        cartDAO = new CartDAO(getContext());
        pref = new SharedPrefManager(getContext());

        // xác định role từ SharedPref
        isAdmin = pref.getRole().equalsIgnoreCase("admin");

        // nếu không phải admin thì ẩn nút Add
        btnAdd.setVisibility(isAdmin ? View.VISIBLE : View.GONE);

        rvProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductAdapter(getContext(), productDAO.getAll(), isAdmin, this);
        rvProducts.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> showAddDialog(null));
        btnSort.setOnClickListener(v -> toggleSort());
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.updateList(productDAO.search(s.toString()));
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void toggleSort() {
        ascending = !ascending;
        List<Product> sorted = productDAO.sortByPrice(ascending);
        adapter.updateList(sorted);
    }

    private void showAddDialog(Product existing) {
        // only admin can call; additional guard
        if (!isAdmin) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(existing == null ? "Thêm sản phẩm" : "Cập nhật sản phẩm");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_product, null);
        builder.setView(dialogView);

        EditText edtName = dialogView.findViewById(R.id.edtName);
        EditText edtDesc = dialogView.findViewById(R.id.edtDesc);
        EditText edtPrice = dialogView.findViewById(R.id.edtPrice);
        EditText edtImage = dialogView.findViewById(R.id.edtImage);

        if (existing != null) {
            edtName.setText(existing.getName());
            edtDesc.setText(existing.getDescription());
            edtPrice.setText(String.valueOf(existing.getPrice()));
            edtImage.setText(existing.getImage());
        }

        builder.setPositiveButton(existing == null ? "Thêm" : "Lưu", (dialog, which) -> {
            String name = edtName.getText().toString();
            String desc = edtDesc.getText().toString();
            String priceStr = edtPrice.getText().toString();

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(getContext(), "Tên và giá không được trống", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            String image = edtImage.getText().toString();

            if (existing == null) {
                productDAO.insert(new Product(0, name, desc, price, image));
            } else {
                existing.setName(name);
                existing.setDescription(desc);
                existing.setPrice(price);
                existing.setImage(image);
                productDAO.update(existing);
            }

            adapter.updateList(productDAO.getAll());
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    // Adapter callbacks
    @Override
    public void onEdit(Product product) {
        if (!isAdmin) return;
        showAddDialog(product);
    }

    @Override
    public void onDelete(Product product) {
        if (!isAdmin) return;
        new AlertDialog.Builder(getContext())
                .setTitle("Xoá sản phẩm")
                .setMessage("Bạn có chắc muốn xoá " + product.getName() + "?")
                .setPositiveButton("Xoá", (dialog, which) -> {
                    productDAO.delete(product.getId());
                    adapter.updateList(productDAO.getAll());
                })
                .setNegativeButton("Huỷ", null)
                .show();
    }

    @Override
    public void onAddToCart(Product product) {
        // tất cả user đều có thể thêm vào giỏ
        cartDAO.addToCart(product.getId(), 1);
        Toast.makeText(getContext(), "Đã thêm vào giỏ: " + product.getName(), Toast.LENGTH_SHORT).show();
    }
}