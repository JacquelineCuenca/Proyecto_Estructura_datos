package com.example.myapplication;
import android.app.Activity;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.myapplication.R;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Modelo.DataRepository;

public class Pantalla_Menu extends AppCompatActivity {
    Button btnAddAirport, btnRemoveAirport, btnAddFlight, btnRemoveFlight, btnFindRoute, btnStats, btnRedVuelos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_menu);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        DataRepository.inicializar(this);
                    }
                }
        );

        btnRedVuelos  = findViewById(R.id.btnRedVuelos);
        btnRedVuelos.setOnClickListener(view -> {
            Intent intent = new Intent(Pantalla_Menu.this, Red_vuelos.class);
            startActivity(intent);
        });

        btnAddAirport = findViewById(R.id.btnAddAirport);
        btnAddAirport.setOnClickListener(v -> {
            Toast.makeText(this, "Función: Añadir Aeropuerto", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Pantalla_Menu.this, AddAirportActivity.class);
            launcher.launch(intent);
        });

        btnRemoveAirport = findViewById(R.id.btnRemoveAirport);
        btnRemoveAirport.setOnClickListener(v -> {
            Toast.makeText(this, "Función: Eliminar Aeropuerto", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Pantalla_Menu.this, RemoveAirportActivity.class);
            launcher.launch(intent);
        });


        btnAddFlight = findViewById(R.id.btnAddFlight);
        btnAddFlight.setOnClickListener(v -> {
            Toast.makeText(this, "Función: Añadir Vuelo", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Pantalla_Menu.this, AddFlightActivity.class);
            launcher.launch(intent);
        });


        btnRemoveFlight = findViewById(R.id.btnRemoveFlight);
        btnRemoveFlight.setOnClickListener(v -> {
            Toast.makeText(this, "Función: Eliminar Vuelo", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Pantalla_Menu.this, Red_vuelos.class);
            launcher.launch(intent);
        });

        btnFindRoute = findViewById(R.id.btnFindRoute);
        btnFindRoute.setOnClickListener(v -> {
            Toast.makeText(this, "Función: Buscar Ruta", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Pantalla_Menu.this, Red_vuelos.class));
        });

        btnStats = findViewById(R.id.btnStats);
        btnStats.setOnClickListener(v -> {
            Toast.makeText(this, "Función: Ver Estadísticas", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Pantalla_Menu.this, Red_vuelos.class));
        });



    ;}}