package com.vanya.seccion_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn ;
    private final String GREETER="Hello from the other side" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Activar flecha de retroceso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Forzar y cargar icono de aplicacion en el actionbar
        //En el manifest tenemos que cambiar el parámetro del icono para poner
        //un icono de app en el escritorio
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_myicon_round);

        btn = findViewById(R.id.buttonMain) ;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Acceder al segundo activity y mandarle un string ;
                Intent intent= new Intent(MainActivity.this, SecondActivity.class) ;
                intent.putExtra("greeter", GREETER);
                startActivity(intent) ;
            }
        });
    }
}
