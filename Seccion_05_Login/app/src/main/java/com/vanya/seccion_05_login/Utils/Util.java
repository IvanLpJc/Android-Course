package com.vanya.seccion_05_login.Utils;

import android.content.SharedPreferences;

public class Util {

    public static String getUserMailPrefs(SharedPreferences prefs){
        // Pedimos el email si tenemos unas shared preferences y el segundo string
        // nos indica algo en caso de que no exista ningún elemento
        return prefs.getString("email", "") ;
    }

    public static String getUserPassPrefs(SharedPreferences prefs){
        // Pedimos el password si tenemos unas shared preferences y el segundo string
        // nos indica algo en caso de que no exista ningún elemento
        return prefs.getString("pass", "") ;
    }

    public static void deletePrefs(SharedPreferences prefs){

        SharedPreferences.Editor editor = prefs.edit() ;

        editor.remove("email") ;
        editor.remove("pass") ;

        editor.apply();

    }
}
