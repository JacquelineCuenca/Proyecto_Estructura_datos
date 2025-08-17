package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private PantallaRegistrarVuelo registrarVuelo;
    private PantallaListarVuelos listarVuelos;
    private PantallaBuscarVuelos buscarVuelos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
