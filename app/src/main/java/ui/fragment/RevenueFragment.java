package ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.shopping.R;
import data.OrderDAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RevenueFragment extends Fragment {
    private TextView tvTotal, tvToday, tvMonth, tvYear;
    private OrderDAO orderDAO;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.revenue_fragment, container, false);

        tvTotal = view.findViewById(R.id.tvTotal);
        tvToday = view.findViewById(R.id.tvToday);
        tvMonth = view.findViewById(R.id.tvMonth);
        tvYear = view.findViewById(R.id.tvYear);
        orderDAO = new OrderDAO(getContext());

        updateRevenue();
        return view;
    }

    private void updateRevenue() {
        double total = orderDAO.getTotalRevenue();

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());

        double byDay = orderDAO.getRevenueByDay(today);
        double byMonth = orderDAO.getRevenueByMonth(month);
        double byYear = orderDAO.getRevenueByYear(year);

        tvTotal.setText("Tổng doanh thu: " + total + " VNĐ");
        tvToday.setText("Hôm nay: " + byDay + " VNĐ");
        tvMonth.setText("Tháng này: " + byMonth + " VNĐ");
        tvYear.setText("Năm nay: " + byYear + " VNĐ");
    }
}