package com.example.sarithmetics;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences("appkey", 0);
        editor = sharedPreferences.edit();
        editor.commit();
    }
    public void setLogin(boolean login){
        editor.putBoolean("key_login", login);
        editor.commit();
    }
    public boolean getLogin(){
        return sharedPreferences.getBoolean("key_login", false);
    }
    public void setUsername(String username){
        editor.putString("key_username", username);
        editor.commit();
    }
    public String getUsername(){
        return sharedPreferences.getString("key_username", "");
    }
    public void setMainStatus(boolean status){
        editor.putBoolean("key_mainstatus", status);
        editor.commit();
    }
    public boolean getMainStatus(){
        return sharedPreferences.getBoolean("key_mainstatus", false);
    }

    public void addPowerups(String buttonId) {
        Set<String> selectedPowerups = getPowerups();
        selectedPowerups.add(buttonId);
        editor.putStringSet("powerups", selectedPowerups);
        editor.apply();
    }

    public Set<String> getPowerups() {
        return sharedPreferences.getStringSet("powerups", new HashSet<>());
    }
}
