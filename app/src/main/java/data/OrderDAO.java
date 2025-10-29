package data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private SQLiteDatabase db;

    public OrderDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    // Tạo đơn hàng
    public long insert(Order order) {
        ContentValues values = new ContentValues();
        values.put("total", order.getTotal());
        values.put("date", order.getDate());
        return db.insert("Orders", null, values);
    }

    // Lấy toàn bộ đơn hàng
    public List<Order> getAll() {
        List<Order> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Orders", null);
        while (cursor.moveToNext()) {
            Order o = new Order(
                    cursor.getInt(0),
                    cursor.getDouble(1),
                    cursor.getString(2)
            );
            list.add(o);
        }
        cursor.close();
        return list;
    }

    // Tính tổng doanh thu
    public double getTotalRevenue() {
        Cursor cursor = db.rawQuery("SELECT SUM(total) FROM Orders", null);
        double total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    // Doanh thu theo ngày
    public double getRevenueByDay(String date) {
        Cursor cursor = db.rawQuery("SELECT SUM(total) FROM Orders WHERE date=?", new String[]{date});
        double total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    // Doanh thu theo tháng
    public double getRevenueByMonth(String month) {
        Cursor cursor = db.rawQuery("SELECT SUM(total) FROM Orders WHERE substr(date, 6, 2)=?", new String[]{month});
        double total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    // Doanh thu theo năm
    public double getRevenueByYear(String year) {
        Cursor cursor = db.rawQuery("SELECT SUM(total) FROM Orders WHERE substr(date, 1, 4)=?", new String[]{year});
        double total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }
}
