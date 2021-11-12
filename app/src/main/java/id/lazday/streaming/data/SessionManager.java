package id.lazday.streaming.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import id.lazday.streaming.activity.MainActivity;

public class SessionManager {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LazdayVidStreamingPref";

    private static final String IS_EXIST = "IsExist";

    public static final String ANDROID_ID = "android_id";

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createSession(String android_id){
        editor.putBoolean(IS_EXIST, true);
        editor.putString(ANDROID_ID, android_id);
        editor.commit();
    }

    public void checkExist(){
        if(this.isExist()){
            Intent i = new Intent(_context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    public HashMap<String, String> getData(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(ANDROID_ID, pref.getString(ANDROID_ID, null));
        return user;
    }

    public boolean isExist(){
        return pref.getBoolean(IS_EXIST, false);
    }
}
