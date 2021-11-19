package com.contreras.certamenandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.contreras.certamenandroid.models.Usuario;
import com.contreras.certamenandroid.sqlite.DbUsuarios;

public class LoginActivity2 extends AppCompatActivity {
    public static Usuario usuario_logeado = null;
    Button btnLogin, btnRegister;
    EditText txtEmailLogin, txtClaveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        txtEmailLogin = findViewById(R.id.txtEmailLogin);
        txtClaveLogin = findViewById(R.id.txtClaveLogin);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento =
                        new Intent(LoginActivity2.this,activity_registro.class );
                startActivity(intento);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbUsuarios bd = new DbUsuarios(LoginActivity2.this);
                // Capturar los datos para pasarlos al método login->DbUsuarios
                String e = txtEmailLogin.getText().toString();
                String c = txtClaveLogin.getText().toString();

                if(e.equals("")||c.equals("")){
                    Toast.makeText(LoginActivity2.this,
                            "Credenciales no ingresadas"
                            , Toast.LENGTH_LONG).show();
                    return;
                }

                Usuario userLog = bd.login(e,c);

                // puede ser que userLog sea null o no
                if( userLog == null ){ // significa error de credenciales
                    Toast.makeText(LoginActivity2.this,
                            "Error en las credenciales ingresadas"
                            , Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity2.this,
                            "Bienvenid@ "+userLog.getNombres()
                            , Toast.LENGTH_LONG).show();
                    usuario_logeado = userLog;
                    Intent intent = new Intent(LoginActivity2.this,
                            MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}