package util;

import android.content.Context;
import android.content.SharedPreferences;
import model.User;

public class SharedPrefManager {
    private static final String PREF_NAME = "user_session";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_ROLE = "role";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveLogin(User user, boolean remember) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_ROLE, user.getRole());
        editor.putBoolean(KEY_REMEMBER, remember);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public boolean isRemembered() {
        return prefs.getBoolean(KEY_REMEMBER, false);
    }

    public String getEmail() { return prefs.getString(KEY_EMAIL, ""); }
    public String getPassword() { return prefs.getString(KEY_PASSWORD, ""); }
    public String getRole() { return prefs.getString(KEY_ROLE, "user"); }

    // Only mark logged out; keep remember fields intact (so email/password still filled if remember true)
    public void logout() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.apply();
    }

    public void clearRemember() {
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_REMEMBER);
        editor.apply();
    }

    public void clearAll() {
        editor.clear();
        editor.apply();
    }
}