package com.contreras.certamenandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.contreras.certamenandroid.models.Usuario;
import com.contreras.certamenandroid.sqlite.DbUsuarios;

public class activity_registro extends AppCompatActivity {

    EditText txtNombres, txtApellidos,txtEmail,txtClave;
    Button btnRegistrar;
    Spinner spUsuarios;

    public void cargarSpinner(){
        DbUsuarios db3 = new DbUsuarios(this);
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item,
                db3.selectAll() );
        spUsuarios.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        // Referencias
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtEmail = findViewById(R.id.txtEmail);
        txtClave = findViewById(R.id.txtClave);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        spUsuarios = findViewById(R.id.spUsuarios);

        cargarSpinner();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // OBTENER TEXTOS INGRESADOS EN LA UI
                String nombres = txtNombres.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String email = txtEmail.getText().toString();
                String clave = txtClave.getText().toString();

                if(nombres.equals("")||apellidos.equals("")||email.equals("")||clave.equals("")){
                    Toast.makeText(activity_registro.this,
                            "Credenciales no ingresadas"
                            , Toast.LENGTH_LONG).show();
                    return;
                }


                Usuario user =
                        new Usuario(nombres,apellidos,email,clave);

                DbUsuarios dbUsuarios = new DbUsuarios(activity_registro.this);
                long id =  dbUsuarios.insertar(user);
                Toast.makeText(activity_registro.this,
                        "id:"+id, Toast.LENGTH_SHORT).show();
                cargarSpinner();            }
        });

    }
}