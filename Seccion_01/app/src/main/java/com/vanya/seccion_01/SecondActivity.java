package com.vanya.seccion_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView textView ;
    private Button btnNext ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Activar flecha de retroceso
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Forzar y cargar icono de aplicacion en el actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_myicon);

        textView = findViewById(R.id.textView);
        btnNext = findViewById(R.id.buttonGoSharing) ;
        // Tomar los datos del intent
        Bundle bundle = getIntent().getExtras() ;
        if(bundle != null && bundle.getString("greeter") != null){
            String greeter = bundle.getString("greeter") ;
            Toast.makeText(SecondActivity.this, greeter, Toast.LENGTH_LONG).show() ;
            textView.setText(greeter);
        }else{
            Toast.makeText(SecondActivity.this, "It is empty!", Toast.LENGTH_SHORT).show();
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class) ;
                startActivity(intent) ;
            }
        });
    }
}
