package com.vanya.seccion_05_login.Splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.vanya.seccion_05_login.Activities.LoginActivity;
import com.vanya.seccion_05_login.Activities.MainActivity;
import com.vanya.seccion_05_login.Utils.Util;

import static android.content.Context.MODE_PRIVATE;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences prefs ;

    // Aqui vamos a consultar las preferencias y si tenemos el login
    // guardado pasamos directamente al main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("Preferences", MODE_PRIVATE) ;

        Intent intentLogin = new Intent(this, LoginActivity.class) ;
        Intent intentMain = new Intent(this, MainActivity.class) ;

        if(!TextUtils.isEmpty(Util.getUserMailPrefs(prefs)) &&
                !TextUtils.isEmpty(Util.getUserPassPrefs(prefs))){
            startActivity(intentMain);
        }else{
            startActivity(intentLogin);
        }

        finish();
    }
}
