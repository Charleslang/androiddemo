package com.qinjie.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.dysy.carttest.R;


/**
  * token持久化工具，用户token
  *
  * @author:
 **/
public class PersistenceToken {

    public static void saveToken(Context context,String token){
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.string_persistent), Context.MODE_PRIVATE);
        preferences.edit().putString("token", token).commit();
    }
    public static String getToken(Context context){
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.string_persistent), Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        return token;
    }
    public static void removeToken(Context context){
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.string_persistent), Context.MODE_PRIVATE);
        preferences.edit().remove("token").commit();
    }

    public static void savePosition(Context context,String token){
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.string_persistent), Context.MODE_PRIVATE);
        preferences.edit().putString("positio", token).commit();
    }
    public static String getPosition(Context context){
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.string_persistent), Context.MODE_PRIVATE);
        String token = preferences.getString("positio", "");
        return token;
    }
    public static void removePosition(Context context){
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.string_persistent), Context.MODE_PRIVATE);
        preferences.edit().remove("positio").commit();
    }
}
