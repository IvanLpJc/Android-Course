package com.vanya.seccion_05_login.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.vanya.seccion_05_login.R;
import com.vanya.seccion_05_login.Utils.Util;

public class LoginActivity extends AppCompatActivity {

    //Primero las declaramos
    private SharedPreferences prefs ;

    private EditText editTextEmail ;
    private EditText editTextPassword ;
    private Switch switchRemember ;
    private Button btnLogin ;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();

        // Las inicializamos dandole un nombre y un modo, privado en este caso
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE) ;

        if(setCredentialIfExists()){
            switchRemember.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString() ;
                String password = editTextPassword.getText().toString() ;

                if (login(email,password))
                        goToMain() ;
                        saveOnPreferences(email,password);
            }
        });
    }

    private void bindUI(){
        editTextEmail = findViewById(R.id.editTextEmail) ;
        editTextPassword = findViewById(R.id.editTextPassword) ;
        switchRemember = findViewById(R.id.switchRemember) ;
        btnLogin = findViewById(R.id.buttonLogin) ;
    }

    private boolean setCredentialIfExists(){
        //En este método comprobamos si teniamos el remember activado
        String email = Util.getUserMailPrefs(prefs) ;
        String pass = Util.getUserPassPrefs(prefs) ;

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            editTextEmail.setText(email);
            editTextPassword.setText(pass);
            return true ;
        }
        return false ;
    }
    /*

        Lo he movido a utils para conseguir mas reutilizacion

    private String getUserMailPrefs(){
        // Pedimos el email si tenemos unas shared preferences y el segundo string
        // nos indica algo en caso de que no exista ningún elemento
        return prefs.getString("email", "") ;
    }

    private String getUserPassPrefs(){
        // Pedimos el password si tenemos unas shared preferences y el segundo string
        // nos indica algo en caso de que no exista ningún elemento
        return prefs.getString("pass", "") ;
    }*/
    private boolean login(String email, String password){
        if (!isValidEmail(email)){
            Toast.makeText(this, "Email is not valid, please try again", Toast.LENGTH_LONG).show();
            return false ;
        }else if (!isValidPassword(password)) {
            Toast.makeText(this, "Password is not valid, please try again", Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true ;
        }
    }

    private void saveOnPreferences(String email, String password){
        if (switchRemember.isChecked()){
            // El preference que hemos creado es reader, usando este método
            // nos devuelve un editor con el que podemos añadir información
            // a preferences
            SharedPreferences.Editor editor = prefs.edit() ;
            editor.putString("email", email) ;
            editor.putString("pass", password);

            // Con commit el código no continua hasta que todos los datos han sido
            // guardados
            // editor.commit() ;

            // Con el apply, se guardan los datos en segundo plano, y el código sigo
            editor.apply();
        }
    }

    private boolean isValidEmail(String email){
        // Comprobamos si el string está vacío y con Patterns.EMAIL_ADDRESS comprobamos si cumple con
        // el patron de un correo: si tiene el @, despues letras, despues un .com o .es, etc...
        return !(TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email).matches() ;
    }

    private boolean isValidPassword(String password){
        return password.length() > 4 ;
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class) ;
        // Con esto evitamos que despues de logearnos, al darle atrás, vuelva a la pantalla del login
        // directamente sale de la app
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
        startActivity(intent);
    }
}
