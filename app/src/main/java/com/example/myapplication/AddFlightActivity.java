package com.example.myapplication;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Modelo.Aeropuerto;
import Modelo.DataRepository;
import Modelo.Vuelo;

public class AddFlightActivity extends AppCompatActivity{
    private Button btnAddFlight, btnRegresar, btnSalida, btnDestino;
    private EditText fechaSalida, fechaLlegada;
    private Spinner partida, destino;

    private List<Aeropuerto> aeropuertos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        partida = findViewById(R.id.spinnerAeropuertoSalida);
        destino = findViewById(R.id.spinnerAeropuertoLlegada);
        fechaSalida = findViewById(R.id.etFechaSalida);
        fechaLlegada = findViewById(R.id.etFechaLlegada);

        aeropuertos = DataRepository.getAeropuertos();

        btnRegresar = findViewById(R.id.btnRegresarAddVuelo);
        btnRegresar.setOnClickListener(v -> {
            finish();
        });

        ArrayAdapter<Aeropuerto> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                aeropuertos
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        partida.setAdapter(adapter);
        destino.setAdapter(adapter);

        btnSalida = findViewById(R.id.btnFechaSalida);
        btnSalida.setOnClickListener(v -> {
            mostrarDatePicker(fechaSalida);
        });

        btnDestino = findViewById(R.id.btnFechaLlegada);
        btnDestino.setOnClickListener(v -> {
            mostrarDatePicker(fechaLlegada);
        });

        btnAddFlight = findViewById(R.id.btnAnadirVuelo);
        btnAddFlight.setOnClickListener(v -> {
            boolean existPartida = partida.getSelectedItem() != null;
            boolean existDestino = destino.getSelectedItem() != null;
            boolean existFechaSalida = !fechaSalida.getText().toString().isEmpty();
            boolean existFechaLlegada = !fechaLlegada.getText().toString().isEmpty();

            if(existPartida && existDestino && existFechaSalida && existFechaLlegada){
                try{
                    Aeropuerto aeropuertoSalida = (Aeropuerto) partida.getSelectedItem();
                    Aeropuerto aeropuertoDestino = (Aeropuerto) destino.getSelectedItem();

                    SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());
                    Date fechaInicio = sdf.parse(fechaSalida.getText().toString());
                    Date fechaFin = sdf.parse(fechaLlegada.getText().toString());

                    Vuelo nuevoVuelo = new Vuelo(aeropuertoSalida, aeropuertoDestino, fechaInicio, fechaFin);
                    DataRepository.getVuelos().add(nuevoVuelo);
                    DataRepository.guardarVuelo(nuevoVuelo, this);

                    Toast.makeText(this, "Vuelo agregado correctamente", Toast.LENGTH_SHORT).show();
                    finish();

                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(this, "Error al guardar el vuelo", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mostrarDatePicker(EditText targetEditText) {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, yearSelected, monthSelected, daySelected) -> {
                    String fecha =  String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month + 1, year);
                    targetEditText.setText(fecha);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}
