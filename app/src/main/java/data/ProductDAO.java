package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private SQLiteDatabase db;

    public ProductDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    // Thêm sản phẩm
    public long insert(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("image", product.getImage());
        return db.insert("Products", null, values);
    }

    // Cập nhật sản phẩm
    public int update(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("image", product.getImage());
        return db.update("Products", values, "id=?", new String[]{String.valueOf(product.getId())});
    }

    // Xoá sản phẩm
    public int delete(int id) {
        return db.delete("Products", "id=?", new String[]{String.valueOf(id)});
    }

    // Lấy toàn bộ sản phẩm
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Products", null);
        while (cursor.moveToNext()) {
            Product p = new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4)
            );
            list.add(p);
        }
        cursor.close();
        return list;
    }

    // Tìm kiếm theo tên
    public List<Product> search(String keyword) {
        List<Product> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Products WHERE name LIKE ?", new String[]{"%" + keyword + "%"});
        while (cursor.moveToNext()) {
            Product p = new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4)
            );
            list.add(p);
        }
        cursor.close();
        return list;
    }

    // Sắp xếp theo giá
    public List<Product> sortByPrice(boolean ascending) {
        String order = ascending ? "ASC" : "DESC";
        List<Product> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Products ORDER BY price " + order, null);
        while (cursor.moveToNext()) {
            Product p = new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4)
            );
            list.add(p);
        }
        cursor.close();
        return list;
    }

    // Lấy sản phẩm theo ID
    public Product getById(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM Products WHERE id=?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            Product p = new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4)
            );
            cursor.close();
            return p;
        }
        cursor.close();
        return null;
    }
}
