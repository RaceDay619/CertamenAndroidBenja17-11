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
                        new Intent(LoginActivity2.this,MainActivity.class );
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // condicional - switch

        switch (item.getItemId()) { // tomamos el id del item seleccionado
            case R.id.menu_inicio:
                Intent intent1 = new Intent(this, LoginActivity2.class);
                Toast.makeText(this, "Inicio", Toast.LENGTH_LONG).show();
                startActivity(intent1);
                return true;

            case R.id.menu_mapa:
                Intent intent2 = new Intent(this, UniversidadActivity.class);
                Toast.makeText(this, "Ubicación", Toast.LENGTH_LONG).show();
                startActivity(intent2);
                return true;

            case R.id.menu_notas:
                Intent intent3 = new Intent(this, MainActivity.class);
                Toast.makeText(this, "Notas", Toast.LENGTH_LONG).show();
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}