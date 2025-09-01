package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import Modelo.DataRepository;

public class Pantalla_Menu extends AppCompatActivity {

    Button btnAddAirport, btnRemoveAirport, btnAddFlight, btnRemoveFlight, btnFindRoute, btnStats, btnRedVuelos;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    DataRepository.leerAeropuertos(this);
                    DataRepository.leerVuelos(this);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_menu);

        // Inicializar datos
        DataRepository.inicializar(this);
        DataRepository.leerAeropuertos(this);
        DataRepository.leerVuelos(this);
        DataRepository.resetAirportsData(this);
        DataRepository.resetVuelosData(this);
        Toast.makeText(this, "Datos cargados correctamente", Toast.LENGTH_SHORT).show();

        // Botón: Red de vuelos
        btnRedVuelos = findViewById(R.id.btnRedVuelos);
        btnRedVuelos.setOnClickListener(view -> {
            Intent intent = new Intent(Pantalla_Menu.this, Red_vuelos.class);
            launcher.launch(intent);
        });

        // Botón: Añadir aeropuerto
        btnAddAirport = findViewById(R.id.btnAddAirport);
        btnAddAirport.setOnClickListener(v -> {
            Toast.makeText(this, "Función: Añadir Aeropuerto", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Pantalla_Menu.this, AddAirportActivity.class);
            launcher.launch(intent);
        });

        // Botón: Eliminar aeropuerto
        btnRemoveAirport = findViewById(R.id.btnEditarAeropuertos);
        btnRemoveAirport.setOnClickListener(v -> {
            Toast.makeText(this, "Función: Eliminar Aeropuerto", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Pantalla_Menu.this, RemoveAirportActivity.class);
            launcher.launch(intent);
        });

        // Botón: Añadir vuelo
        btnAddFlight = findViewById(R.id.btnAddFlight);
        btnAddFlight.setOnClickListener(v -> {
            Toast.makeText(this, "Función: Añadir Vuelo", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Pantalla_Menu.this, AddFlightActivity.class);
            launcher.launch(intent);
        });

        // Botón: Eliminar vuelo
        btnRemoveFlight = findViewById(R.id.btnEditarVuelos);
        btnRemoveFlight.setOnClickListener(v -> {
            Toast.makeText(this, "Función: Eliminar Vuelo", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Pantalla_Menu.this, RemoveFlightActivity.class);
            launcher.launch(intent);
        });

        // Botón: Buscar ruta
        btnFindRoute = findViewById(R.id.btnFindRoute);
        btnFindRoute.setOnClickListener(v -> {
            Toast.makeText(this, "Función: Buscar Ruta", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Pantalla_Menu.this, RouteCheckerActivity.class));
        });

        // Botón: Ver estadísticas
        //btnStats = findViewById(R.id.btnStats);
        //btnStats.setOnClickListener(v -> {
            //Toast.makeText(this, "Función: Ver Estadísticas", Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(Pantalla_Menu.this, Red_vuelos.class));
        //});
    }
}
