package com.example.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import Modelo.Aeropuerto;
import Modelo.DataRepository;

public class AddAirportActivity extends AppCompatActivity {
    private Button btnRegresarMenu, btnAnadir;
    private TextView codigo, nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_airport);

        codigo = findViewById(R.id.editCodigo);
        nombre = findViewById(R.id.editAeropuerto);

        btnRegresarMenu = findViewById(R.id.btnRegresarAddAeropuerto);
        btnRegresarMenu.setOnClickListener(view -> {
            // Iniciar la actividad Pantalla_Menu
            Intent intent = new Intent(AddAirportActivity.this, Pantalla_Menu.class);
            startActivity(intent);
        });

        btnAnadir = findViewById(R.id.btnAnadirAeropuerto);

        btnAnadir.setOnClickListener(view -> {
            if(!codigo.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty()){
                addAirport(nombre.getText().toString(),codigo.getText().toString());
                nombre.setText("");
                codigo.setText("");
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void addAirport(String nombre, String codigo) {
        Aeropuerto aeropuerto = new Aeropuerto(codigo, nombre);
        DataRepository.agregarAeropuerto(aeropuerto, AddAirportActivity.this);
    }
}
