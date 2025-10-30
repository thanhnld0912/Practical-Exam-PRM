package data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "PhoneStore.db";
    public static final int DB_VERSION = 1; // tăng nếu đã thay đổi schema

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Users có thêm cột role
        db.execSQL("CREATE TABLE Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fullName TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT, " +
                "role TEXT DEFAULT 'user')");

        db.execSQL("CREATE TABLE Products (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "description TEXT, " +
                "price REAL, " +
                "image TEXT)");

        db.execSQL("CREATE TABLE Cart (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "productId INTEGER, " +
                "quantity INTEGER)");

        db.execSQL("CREATE TABLE Orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "total REAL, " +
                "date TEXT)");

        seedData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // đơn giản: drop & recreate (với project dev). Với production cần migrate.
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Products");
        db.execSQL("DROP TABLE IF EXISTS Cart");
        db.execSQL("DROP TABLE IF EXISTS Orders");
        onCreate(db);
    }

    private void seedData(SQLiteDatabase db) {
        // Seed users: admin + normal user
        db.execSQL("INSERT INTO Users (fullName, email, password, role) VALUES " +
                "('Admin User', 'admin@phonestore.com', 'admin123', 'admin')," +
                "('Nguyễn Văn A', 'user@phonestore.com', '123456', 'user')");

        // --- 20 sản phẩm mẫu ---
        db.execSQL("INSERT INTO Products (name, description, price, image) VALUES " +
                "('iPhone 15 Pro Max', 'Chip A17 Pro, RAM 8GB, 256GB', 35990000, '')," +
                "('Samsung Galaxy S24 Ultra', 'Snapdragon 8 Gen 3, RAM 12GB, 512GB', 32990000, '')," +
                "('Xiaomi 14', 'Snapdragon 8 Gen 3, RAM 12GB, 256GB', 19990000, '')," +
                "('OPPO Find X7', 'Dimensity 9300, 12GB, 256GB', 18990000, '')," +
                "('Vivo X100 Pro', 'Dimensity 9300, 16GB, 512GB', 24990000, '')," +
                "('Realme GT 5', 'Snapdragon 8 Gen 2, 16GB, 512GB', 15990000, '')," +
                "('Google Pixel 8 Pro', 'Tensor G3, RAM 12GB, 256GB', 24990000, '')," +
                "('ASUS ROG Phone 7', 'Snapdragon 8 Gen 2, 16GB, 512GB', 25990000, '')," +
                "('iPhone 14', 'Chip A15, RAM 6GB, 128GB', 18990000, '')," +
                "('iPhone 13', 'Chip A15, RAM 4GB, 128GB', 15990000, '')," +
                "('Samsung Galaxy Z Flip 5', 'Snapdragon 8 Gen 2, RAM 8GB, 256GB', 25990000, '')," +
                "('Samsung Galaxy Z Fold 5', 'Snapdragon 8 Gen 2, 12GB, 512GB', 36990000, '')," +
                "('POCO F5 Pro', 'Snapdragon 8+ Gen 1, 12GB, 256GB', 13990000, '')," +
                "('Xiaomi Redmi Note 13 Pro+', 'Dimensity 7200 Ultra, 8GB, 256GB', 8990000, '')," +
                "('OnePlus 12', 'Snapdragon 8 Gen 3, 16GB, 512GB', 23990000, '')," +
                "('Nokia G60 5G', 'Snapdragon 695, RAM 6GB, 128GB', 6990000, '')," +
                "('Infinix Zero 30', 'Helio G99, RAM 8GB, 256GB', 5990000, '')," +
                "('Vsmart Aris Pro', 'Snapdragon 730, 8GB, 128GB', 4990000, '')," +
                "('Sony Xperia 1 V', 'Snapdragon 8 Gen 2, RAM 12GB, 256GB', 28990000, '')," +
                "('Asus Zenfone 10', 'Snapdragon 8 Gen 2, 8GB, 256GB', 18990000, '')");
    }
}