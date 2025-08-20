package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pantalla_Menu extends AppCompatActivity {

    private PantallaRegistrarVuelo registrarVuelo;
    private PantallaListarVuelos listarVuelos;
    private PantallaBuscarVuelos buscarVuelos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_menu);

        // Instanciar lógica de negocio
        ControlVuelos control = new ControlVuelos();

        // Instanciar pantallas
        registrarVuelo = new PantallaRegistrarVuelo(control);
        listarVuelos = new PantallaListarVuelos(control);
        buscarVuelos = new PantallaBuscarVuelos(control);

        // Botones del layout
        Button btnRegistrar = findViewById(R.id.btnRegistrarVuelo);
        Button btnListar = findViewById(R.id.btnListarVuelos);
        Button btnBuscar = findViewById(R.id.btnBuscarVuelo);

        // Eventos
        btnRegistrar.setOnClickListener(v -> registrarVuelo.mostrarFormulario(this));
        btnListar.setOnClickListener(v -> listarVuelos.mostrarVuelos(this));
        btnBuscar.setOnClickListener(v -> buscarVuelos.buscarVuelo(this));
    }
}