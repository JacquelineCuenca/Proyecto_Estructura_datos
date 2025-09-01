package com.example.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
            finish();
        });

        btnAnadir = findViewById(R.id.btnAnadirAeropuerto);

        btnAnadir.setOnClickListener(view -> {
            if(!codigo.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty()){
                addAirport(nombre.getText().toString(),codigo.getText().toString());
                nombre.setText("");
                codigo.setText("");
                setResult(Activity.RESULT_OK);
                Toast.makeText(this, "Aeropuerto agregado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addAirport(String nombre, String codigo) {
        Aeropuerto aeropuerto = new Aeropuerto(codigo, nombre);
        DataRepository.guardarAeropuerto(aeropuerto, this);
    }
}
