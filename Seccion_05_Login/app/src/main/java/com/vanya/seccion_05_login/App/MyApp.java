package com.vanya.seccion_05_login.App;

import android.app.Application;
import android.os.SystemClock;

public class MyApp extends Application {

    // Esto es para simular una carga en internet
    // para el splashScreen
    @Override
    public void onCreate() {
        super.onCreate();


        SystemClock.sleep(3000);
    }
}
