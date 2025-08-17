package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Instancias de las clases Valeska
    private PantallaRegistrarVuelo registrarVuelo;
    private PantallaListarVuelos listarVuelos;
    private PantallaBuscarVuelos buscarVuelos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar clases Valeska
        registrarVuelo = new PantallaRegistrarVuelo();
        listarVuelos = new PantallaListarVuelos();
        buscarVuelos = new PantallaBuscarVuelos();

        // Encontrar los botones en el layout
        Button btnRegistrar = findViewById(R.id.btnRegistrarVuelo);
        Button btnListar = findViewById(R.id.btnListarVuelos);
        Button btnBuscar = findViewById(R.id.btnBuscarVuelo);

        // Asignar eventos a los botones
        btnRegistrar.setOnClickListener(v -> registrarVuelo.mostrarFormulario(this));
        btnListar.setOnClickListener(v -> listarVuelos.mostrarVuelos(this));
        btnBuscar.setOnClickListener(v -> buscarVuelos.buscarVuelo(this));
    }
}
