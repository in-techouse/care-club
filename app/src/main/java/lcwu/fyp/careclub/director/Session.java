package lcwu.fyp.careclub.director;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import lcwu.fyp.careclub.model.NGOs;
import lcwu.fyp.careclub.model.User;

public class Session {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public Session(Context c) {
        context = c;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public void setNGO(NGOs ngo) {
        Gson gson = new Gson();
        String value = gson.toJson(ngo);
        editor.putString("ngo", value);
        editor.apply();
    }

    public void setSession(User user) {
        Gson gson = new Gson();
        String value = gson.toJson(user);
        editor.putString("user", value);
        editor.apply();
    }

    public User getSession() {
        String str = preferences.getString("user", "*");
        if (str.equals("*"))
            return null;
        Gson gson = new Gson();
        return gson.fromJson(str, User.class);
    }


    public NGOs getNgo() {
        String str = preferences.getString("ngo", "*");
        if (str.equals("*"))
            return null;
        Gson gson = new Gson();
        return gson.fromJson(str, NGOs.class);
    }

    public void destroySession() {
        editor.remove("user");
        editor.remove("ngo");
        editor.apply();
    }
}
