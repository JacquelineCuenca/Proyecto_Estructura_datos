package com.example.myapplication;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class AddFlightActivity extends AppCompatActivity{
    private Button btnAddFlight, btnRegresar;
    private TextView fechaSalida, fechaLlegada;
    private Spinner partida, destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        partida = findViewById(R.id.spinnerAeropuertoSalida);
        destino = findViewById(R.id.spinnerAeropuertoLlegada);
        fechaSalida = findViewById(R.id.etFechaSalida);
        fechaLlegada = findViewById(R.id.etFechaLlegada);

        btnRegresar = findViewById(R.id.btnRegresarAddAeropuerto);
        btnRegresar.setOnClickListener(v -> {
            finish();
        });

        btnAddFlight = findViewById(R.id.btnAnadirVuelo);



        //if(partida != null && destino != null && fechaSalida != null && fechaLlegada != null){}

    }
}
