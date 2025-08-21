package com.example.myapplication;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import Modelo.GrafoView;
import Modelo.DataRepository;

public class Red_vuelos extends AppCompatActivity {
    EditText txtAirportName;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_vuelos);

        // Inicializar el grafo si no está listo
        DataRepository.inicializar(this);
        txtAirportName = findViewById(R.id.txtAirportName);
        btnSave = findViewById(R.id.btnSaveAirport);
        GrafoView grafoView = findViewById(R.id.grafoView);
        grafoView.setGrafo(DataRepository.getGrafoVuelos());

        // Botón regresar
        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> {
            String name = txtAirportName.getText().toString();
            if(name.isEmpty()){
                Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show();
            } else {
                // Aquí llamas al grafo para agregar aeropuerto
                // Graph.addAirport(new Airport(name));
                Toast.makeText(this, "Aeropuerto agregado: " + name, Toast.LENGTH_SHORT).show();
                finish(); // volver al menú
            }
        });
    }
}
