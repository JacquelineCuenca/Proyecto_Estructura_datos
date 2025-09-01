package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import Modelo.GrafoView;
import Modelo.DataRepository;

public class Red_vuelos extends AppCompatActivity {

    private GrafoView grafoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_vuelos);

        DataRepository.inicializar(this);

        grafoView = findViewById(R.id.grafoView);

        grafoView.setGrafo(DataRepository.getGrafoVuelos());
        grafoView.invalidate();

        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        grafoView.setGrafo(DataRepository.getGrafoVuelos());
        grafoView.invalidate();
    }
}
