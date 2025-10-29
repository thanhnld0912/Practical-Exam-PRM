package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private SQLiteDatabase db;

    public CartDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    // Thêm sản phẩm vào giỏ
    public long addToCart(int productId, int quantity) {
        Cursor cursor = db.rawQuery("SELECT * FROM Cart WHERE productId=?", new String[]{String.valueOf(productId)});
        if (cursor.moveToFirst()) {
            // Đã có => cập nhật số lượng
            int id = cursor.getInt(0);
            int oldQty = cursor.getInt(2);
            int newQty = oldQty + quantity;
            ContentValues values = new ContentValues();
            values.put("quantity", newQty);
            db.update("Cart", values, "id=?", new String[]{String.valueOf(id)});
            cursor.close();
            return id;
        }
        cursor.close();

        // Nếu chưa có => thêm mới
        ContentValues values = new ContentValues();
        values.put("productId", productId);
        values.put("quantity", quantity);
        return db.insert("Cart", null, values);
    }

    // Lấy danh sách giỏ hàng
    public List<CartItem> getAll() {
        List<CartItem> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Cart", null);
        while (cursor.moveToNext()) {
            CartItem c = new CartItem(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2)
            );
            list.add(c);
        }
        cursor.close();
        return list;
    }

    // Cập nhật số lượng
    public int updateQuantity(int id, int quantity) {
        ContentValues values = new ContentValues();
        values.put("quantity", quantity);
        return db.update("Cart", values, "id=?", new String[]{String.valueOf(id)});
    }

    // Xoá sản phẩm khỏi giỏ
    public int delete(int id) {
        return db.delete("Cart", "id=?", new String[]{String.valueOf(id)});
    }

    // Xoá toàn bộ giỏ (sau khi checkout)
    public void clear() {
        db.execSQL("DELETE FROM Cart");
    }
}