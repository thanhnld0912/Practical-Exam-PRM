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
        values.put("role", user.getRole());
        return db.insert("Users", null, values);
    }

    public boolean checkLogin(String email, String password) {
        Cursor c = db.rawQuery("SELECT * FROM Users WHERE email=? AND password=?", new String[]{email, password});
        boolean result = c.getCount() > 0;
        c.close();
        return result;
    }

    public User getUserByEmail(String email) {
        Cursor c = db.rawQuery("SELECT * FROM Users WHERE email=?", new String[]{email});
        if (c.moveToFirst()) {
            User u = new User();
            u.setId(c.getInt(c.getColumnIndexOrThrow("id")));
            u.setFullName(c.getString(c.getColumnIndexOrThrow("fullName")));
            u.setEmail(c.getString(c.getColumnIndexOrThrow("email")));
            u.setPassword(c.getString(c.getColumnIndexOrThrow("password")));
            // role may be null if older data — default to 'user'
            String role = "user";
            try {
                role = c.getString(c.getColumnIndexOrThrow("role"));
                if (role == null) role = "user";
            } catch (Exception ex) {
                role = "user";
            }
            u.setRole(role);
            c.close();
            return u;
        }
        c.close();
        return null;
    }
}