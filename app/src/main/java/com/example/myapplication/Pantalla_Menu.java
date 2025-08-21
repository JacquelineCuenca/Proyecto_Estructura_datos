package com.example.myapplication;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.myapplication.R;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pantalla_Menu extends AppCompatActivity {
    Button btnAddAirport, btnRemoveAirport, btnAddFlight, btnRemoveFlight, btnFindRoute, btnStats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_menu);

        Button btnRedVuelos = findViewById(R.id.btnRedVuelos);
        btnAddAirport = findViewById(R.id.btnAddAirport);
        btnRemoveAirport = findViewById(R.id.btnRemoveAirport);
        btnAddFlight = findViewById(R.id.btnAddFlight);
        btnRemoveFlight = findViewById(R.id.btnRemoveFlight);
        btnFindRoute = findViewById(R.id.btnFindRoute);
        btnStats = findViewById(R.id.btnStats);
        btnRedVuelos.setOnClickListener(view -> {
            Intent intent = new Intent(Pantalla_Menu.this, Red_vuelos.class);
            startActivity(intent);

            btnRedVuelos.setOnClickListener(v -> {
                startActivity(new Intent(Pantalla_Menu.this, Red_vuelos.class));
            });

            // Botones de funciones con Toast + apertura de actividad
            btnAddAirport.setOnClickListener(v -> {
                Toast.makeText(this, "Función: Añadir Aeropuerto", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Pantalla_Menu.this, Red_vuelos.class));
            });

            btnRemoveAirport.setOnClickListener(v -> {
                Toast.makeText(this, "Función: Eliminar Aeropuerto", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Pantalla_Menu.this, Red_vuelos.class));
            });

            btnAddFlight.setOnClickListener(v -> {
                Toast.makeText(this, "Función: Añadir Vuelo", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Pantalla_Menu.this, Red_vuelos.class));
            });

            btnRemoveFlight.setOnClickListener(v -> {
                Toast.makeText(this, "Función: Eliminar Vuelo", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Pantalla_Menu.this, Red_vuelos.class));
            });

            btnFindRoute.setOnClickListener(v -> {
                Toast.makeText(this, "Función: Buscar Ruta", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Pantalla_Menu.this, Red_vuelos.class));
            });

            btnStats.setOnClickListener(v -> {
                Toast.makeText(this, "Función: Ver Estadísticas", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Pantalla_Menu.this, Red_vuelos.class));

        });


    });
    ;}}