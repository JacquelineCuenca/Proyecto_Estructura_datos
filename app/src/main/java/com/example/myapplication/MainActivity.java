package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el botón
        btn_ingresar = findViewById(R.id.btnIngresar);
        // Configurar el evento de clic del botón
        btn_ingresar.setOnClickListener(view -> {
            // Iniciar la actividad Pantalla_Menu
            Intent intent = new Intent(MainActivity.this, Pantalla_Menu.class);
            startActivity(intent);
        });



    }
}
