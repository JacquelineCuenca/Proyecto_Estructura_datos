package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Modelo.Aeropuerto;
import Modelo.DataRepository;

public class RouteCheckerActivity extends AppCompatActivity {

    private Spinner spinnerOrigen, spinnerDestino;
    private Button btnVerificar, btnRegresar;
    private List<Aeropuerto> aeropuertos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_checker);

        spinnerOrigen = findViewById(R.id.spinnerOrigen);
        spinnerDestino = findViewById(R.id.spinnerDestino);
        btnVerificar = findViewById(R.id.btnVerificar);

        btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> finish());

        aeropuertos = DataRepository.getAeropuertos();

        ArrayAdapter<Aeropuerto> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, aeropuertos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOrigen.setAdapter(adapter);
        spinnerDestino.setAdapter(adapter);

        btnVerificar.setOnClickListener(v -> verificarRuta());
    }

    private void verificarRuta() {
        Aeropuerto origen = (Aeropuerto) spinnerOrigen.getSelectedItem();
        Aeropuerto destino = (Aeropuerto) spinnerDestino.getSelectedItem();

        LinearLayout container = findViewById(R.id.layoutRutaContainer);
        container.removeAllViews(); // Limpiar ruta anterior

        if (origen == null || destino == null) {
            TextView tv = new TextView(this);
            tv.setText("Selecciona ambos aeropuertos");
            container.addView(tv);
            return;
        }

        List<Aeropuerto> camino = DataRepository.getGrafoVuelos().dijkstra(origen, destino);

        if (camino.isEmpty()) {
            TextView tv = new TextView(this);
            tv.setText("No existe un camino disponible entre estos aeropuertos.");
            container.addView(tv);
        } else {
            for (int i = 0; i < camino.size(); i++) {
                Aeropuerto a = camino.get(i);

                // Crear CardView para cada aeropuerto
                androidx.cardview.widget.CardView card = new androidx.cardview.widget.CardView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 16);
                card.setLayoutParams(params);
                card.setRadius(12);
                card.setCardElevation(8);

                // Contenido del CardView
                LinearLayout inner = new LinearLayout(this);
                inner.setOrientation(LinearLayout.VERTICAL);
                inner.setPadding(24, 24, 24, 24);

                TextView tvNombre = new TextView(this);
                tvNombre.setText(a.getNombre() + " (" + a.getCodigo() + ")");
                tvNombre.setTextSize(16);
                tvNombre.setTextColor(getResources().getColor(android.R.color.black));
                inner.addView(tvNombre);

                card.addView(inner);
                container.addView(card);

                // Flecha vertical entre aeropuertos
                if (i < camino.size() - 1) {
                    TextView arrow = new TextView(this);
                    arrow.setText("↓");
                    arrow.setTextSize(24);
                    arrow.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                    container.addView(arrow);
                }
            }
        }
    }


}
