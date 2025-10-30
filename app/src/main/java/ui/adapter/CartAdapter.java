package ui.adapter;


import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shopping.R;
import data.CartDAO;
import data.ProductDAO;
import model.CartItem;
import model.Product;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;
    private ProductDAO productDAO;
    private CartDAO cartDAO;
    private OnTotalChangedListener listener;

    public interface OnTotalChangedListener {
        void onTotalChanged(double newTotal);
    }

    public CartAdapter(Context context, List<CartItem> cartItems, ProductDAO productDAO, OnTotalChangedListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.productDAO = productDAO;
        this.listener = listener;
        this.cartDAO = new CartDAO(context);
    }

    public double getTotalAmount() {
        double total = 0;
        for (CartItem item : cartItems) {
            Product p = productDAO.getById(item.getProductId());
            if (p != null)
                total += p.getPrice() * item.getQuantity();
        }
        return total;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        Product product = productDAO.getById(item.getProductId());

        if (product == null) return;

        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(String.format("%.0f VNĐ", product.getPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        holder.btnPlus.setOnClickListener(v -> {
            int qty = item.getQuantity() + 1;
            item.setQuantity(qty);
            cartDAO.updateQuantity(item.getId(), qty);
            notifyItemChanged(position);
            listener.onTotalChanged(getTotalAmount());
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                int qty = item.getQuantity() - 1;
                item.setQuantity(qty);
                cartDAO.updateQuantity(item.getId(), qty);
                notifyItemChanged(position);
                listener.onTotalChanged(getTotalAmount());
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            cartDAO.delete(item.getId());
            cartItems.remove(position);
            notifyItemRemoved(position);
            listener.onTotalChanged(getTotalAmount());
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity;
        ImageButton btnPlus, btnMinus, btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}