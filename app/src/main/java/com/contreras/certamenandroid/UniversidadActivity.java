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

public class UniversidadActivity extends AppCompatActivity {

    Button btnMapa;
    EditText txtLatitud, txtLongitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universidad);

        btnMapa = findViewById(R.id.btnMapa);

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UniversidadActivity.this, MapsActivity.class);
                //Capturar coordenadas
                try{
                    float latitud = Float.parseFloat( txtLatitud.getText().toString() );
                    float longitud = Float.parseFloat( txtLongitud.getText().toString() );
                    intent.putExtra("latitud", latitud);
                    intent.putExtra("longitud", longitud);
                    }catch (Exception ex){

                    }
                    startActivity(intent);
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
