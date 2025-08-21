package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import Modelo.GrafoView;
import Modelo.DataRepository;

public class Red_vuelos extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_vuelos);

        // Inicializar el grafo si no está listo
        DataRepository.inicializar(this);

        GrafoView grafoView = findViewById(R.id.grafoView);
        grafoView.setGrafo(DataRepository.getGrafoVuelos());

        // Botón regresar
        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> finish());
    }
}