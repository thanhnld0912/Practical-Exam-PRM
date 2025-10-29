package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import model.User;

public class UserDAO {
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put("fullName", user.getFullName());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        return db.insert("Users", null, values);
    }

    public boolean checkLogin(String email, String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE email=? AND password=?",
                new String[]{email, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }
}
