package ui.adapter;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopping.R;
import model.Product;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private OnProductClickListener listener;
    private boolean isAdmin;

    public interface OnProductClickListener {
        void onEdit(Product product);
        void onDelete(Product product);
        void onAddToCart(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, boolean isAdmin, OnProductClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.isAdmin = isAdmin;
        this.listener = listener;
    }

    public void updateList(List<Product> list) {
        this.productList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = productList.get(position);
        holder.tvName.setText(p.getName());
        holder.tvPrice.setText(String.format("%.0f VNĐ", p.getPrice()));
        holder.tvDesc.setText(p.getDescription());

        // Admin sees edit/delete; user doesn't.
        holder.btnEdit.setVisibility(isAdmin ? View.VISIBLE : View.GONE);
        holder.btnDelete.setVisibility(isAdmin ? View.VISIBLE : View.GONE);

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(p);
        });
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(p);
        });

        // Add to cart visible for all users
        holder.btnAddToCart.setOnClickListener(v -> {
            if (listener != null) listener.onAddToCart(p);
        });
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvDesc;
        ImageButton btnEdit, btnDelete;
        Button btnAddToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}