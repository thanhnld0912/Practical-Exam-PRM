package ui.fragment;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.shopping.R;

public class CartFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);
        TextView tv = view.findViewById(R.id.tvMessage);
        tv.setText("Giỏ hàng của bạn");
        return view;
    }
}