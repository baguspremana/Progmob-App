package com.example.user_pc.semnas_ti.bantuan;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.user_pc.semnas_ti.model.User;

public class PreferencesHelper {
    private SharedPreferences sharedPreferences;
    private final String PREFERENCE_NAME = "shared_preferences";
    private final String LOGIN="login";
    private final String TOKEN="token";
    private final String NAME="name";
    private final String EMAIL="email";
    private final String CONTACT="contact";
    private final String ROLE="role";

    public PreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public void setLogin(boolean login){
        sharedPreferences.edit().putBoolean(LOGIN,login).apply();
    }

    public boolean getLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void setToken(String token){
        sharedPreferences.edit().putString(TOKEN,token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN, "");
    }

    public void setName(String name){
        sharedPreferences.edit().putString(NAME,name).apply();
    }

    public String getName() {
        return sharedPreferences.getString(NAME, "");
    }

    public void setEmail(String email){
        sharedPreferences.edit().putString(EMAIL,email).apply();
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL, "");
    }

    public void setContact(String contact){
        sharedPreferences.edit().putString(CONTACT,contact).apply();
    }

    public String getContact() {
        return sharedPreferences.getString(CONTACT, "");
    }

    public void setRole(int role){
        sharedPreferences.edit().putInt(ROLE,role).apply();
    }

    public int getRole() {
        return sharedPreferences.getInt(ROLE, 0);
    }

    public void setUserLogin(User user){
        setLogin(true);
        setToken(user.getToken());
        setName(user.getName());
        setEmail(user.getEmail());
        setContact(user.getContact());
        setRole(user.getRole());
    }

    public void logout(){
        sharedPreferences.edit()
                .clear()
                .apply();
    }
}
