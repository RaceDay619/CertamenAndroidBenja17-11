package com.contreras.certamenandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.contreras.certamenandroid.sqlite.DbHelper;

public class MainActivity extends AppCompatActivity {
    DbHelper midb;
    EditText editarNombre,editarApellido,editarNotas, editarID, editarAsignatura;
    Button btn_agregarDatos;
    Button btn_verTodo;
    Button btn_actualizaDatos;
    Button btn_borrarDatitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        midb = new DbHelper(this);

        editarNombre = findViewById(R.id.editText_Nombre);
        editarApellido = findViewById(R.id.editText_Apellido);
        editarNotas = findViewById(R.id.editText_Notas);
        editarID = findViewById(R.id.editText_ID);
        editarAsignatura = findViewById(R.id.editText_Asignatura);
        btn_agregarDatos = findViewById(R.id.btn_agregadatos);
        btn_verTodo = findViewById(R.id.btn_verDatos);
        btn_actualizaDatos = findViewById(R.id.btn_actualizarDatos);
        btn_borrarDatitos = findViewById(R.id.btn_eliminarDatos);


        AgregarDatos();
        obtenerDatos();
        ActualizarDatos();
        BorrarDatos();
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

    public void BorrarDatos(){
        btn_borrarDatitos.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer borrarColumnas = midb.borrarData(editarID.getText().toString());
                        if(borrarColumnas > 0)
                            Toast.makeText(MainActivity.this, "Datos eliminados correctamente.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Falló, datos no eliminados.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void ActualizarDatos(){
        btn_actualizaDatos.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = editarID.getText().toString();
                        String nombre = editarNombre.getText().toString();
                        String apellido = editarApellido.getText().toString();
                        String notas = editarNotas.getText().toString();
                        String asignatura = editarAsignatura.getText().toString();
                        if(id.equals("")||nombre.equals("")||apellido.equals("")||notas.equals("")||asignatura.equals("")){
                            Toast.makeText(MainActivity.this,
                                    "Credenciales no ingresadas"
                                    , Toast.LENGTH_LONG).show();
                            return;
                        }
                        boolean isUpdate = midb.actualizarDatos(id,
                                nombre,
                                apellido,
                                notas,
                                asignatura);
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this, "Datos actualizados correctamente.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Falló, datos no actualizados.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void AgregarDatos(){
        btn_agregarDatos.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nombre = editarNombre.getText().toString();
                        String apellido = editarApellido.getText().toString();
                        String notas = editarNotas.getText().toString();
                        String asignatura = editarAsignatura.getText().toString();
                        if(nombre.equals("")||apellido.equals("")||notas.equals("")||asignatura.equals("")){
                            Toast.makeText(MainActivity.this,
                                    "Credenciales no ingresadas"
                                    , Toast.LENGTH_LONG).show();
                            return;
                        }
                        boolean isInserted = midb.insertarDatos(nombre,
                                apellido,
                                notas,
                                asignatura);
                        if(isInserted = true)
                            Toast.makeText(MainActivity.this, "Datos insertados correctamente.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Falló, datos no insertados.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void obtenerDatos(){
        btn_verTodo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = midb.verTodo();
                        if(res.getCount() == 0) {
                            mostrarMensaje("Error","No hay alumnos en la base de datos.");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("ID :"+ res.getString(0)+"\n");
                            buffer.append("NOMBRE :"+ res.getString(1)+"\n");
                            buffer.append("APELLIDO :"+ res.getString(2)+"\n");
                            buffer.append("NOTAS :"+ res.getString(3)+"\n");
                            buffer.append("ASIGNATURA :"+ res.getString(4)+"\n");

                        }
                        mostrarMensaje("Datos de los alumnos",buffer.toString());
                    }
                }
        );
    }

    public void mostrarMensaje(String Titulo,String Mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Titulo);
        builder.setMessage(Mensaje);
        builder.show();
    }

}