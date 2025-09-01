package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Modelo.Aeropuerto;
import Modelo.DataRepository;
import Modelo.Vuelo;

public class RemoveFlightActivity extends AppCompatActivity implements  FlightDialogFragment.OnFlightEditedListener{
    private List<Vuelo> vuelos;
    private List<Aeropuerto> aeropuertos;

    private TableLayout tablaVuelos;
    private AppCompatButton btnRegresarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remove_flight);

        btnRegresarMenu = findViewById(R.id.btnRegresar);
        btnRegresarMenu.setOnClickListener(v -> finish());

        tablaVuelos = findViewById(R.id.tableFlight);

        llenarTabla();
    }

    public void llenarTabla(){
        aeropuertos = DataRepository.getAeropuertos();
        vuelos = DataRepository.getVuelos();
        cleanTableP(tablaVuelos);
        if (vuelos == null || vuelos.isEmpty()) {
            Toast.makeText(this, "No hay vuelos registrados", Toast.LENGTH_SHORT).show();
            TextView tvFlight = new TextView(this);
            tvFlight.setText("No hay vuelos registrados");
            tvFlight.setGravity(Gravity.CENTER);
            tvFlight.setTextSize(18);

            tablaVuelos.addView(tvFlight);
            return;
        }

        for (Vuelo vuelo : vuelos) {
            //CardView principal
            CardView card = new CardView(this);
            card.setCardElevation(8);
            card.setRadius(24);
            card.setUseCompatPadding(true);
            card.setContentPadding(32, 32, 32, 32);

            LinearLayout.LayoutParams paramsCard = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsCard.setMargins(0, 0, 0, 20);
            card.setLayoutParams(paramsCard);

            //Layout horizontal dentro de la tarjeta
            LinearLayout filaVuelo = new LinearLayout(this);
            filaVuelo.setOrientation(LinearLayout.HORIZONTAL);
            filaVuelo.setGravity(Gravity.CENTER_VERTICAL);

            //Layout vertical para los textos
            LinearLayout llText = new LinearLayout(this);
            llText.setOrientation(LinearLayout.VERTICAL);
            llText.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
            ));

            // TextView Salida
            TextView tvSalida = new TextView(this);
            tvSalida.setText("Salida: " + vuelo.getPartida().getNombre());
            tvSalida.setTextSize(16);
            tvSalida.setTextColor(Color.BLACK);
            tvSalida.setTypeface(Typeface.DEFAULT_BOLD);

            // TextView Destino
            TextView tvDestino = new TextView(this);
            tvDestino.setText("Destino: " + vuelo.getDestino().getNombre());
            tvDestino.setTextSize(16);
            tvDestino.setTextColor(Color.BLACK);
            tvDestino.setTypeface(Typeface.DEFAULT_BOLD);

            llText.addView(tvSalida);
            llText.addView(tvDestino);

            // Layout para botones
            LinearLayout botonesLayout = new LinearLayout(this);
            botonesLayout.setOrientation(LinearLayout.HORIZONTAL);
            botonesLayout.setGravity(Gravity.END);

            LinearLayout.LayoutParams paramsBtn = new LinearLayout.LayoutParams(120, 120);
            paramsBtn.setMargins(20, 0, 0, 0);

            // Botón eliminar
            AppCompatImageButton btnEliminar = new AppCompatImageButton(this);
            btnEliminar.setBackgroundResource(R.drawable.delete_button);
            btnEliminar.setImageResource(R.drawable.baseline_delete_24);
            btnEliminar.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            btnEliminar.setPadding(30, 30, 30, 30);
            btnEliminar.setLayoutParams(paramsBtn);
            btnEliminar.setOnClickListener(v -> {
                DataRepository.eliminarVuelo(vuelo, getApplicationContext());
                tablaVuelos.removeView(card);
                Toast.makeText(this, "Vuelo eliminado", Toast.LENGTH_SHORT).show();
            });

            // Botón info
            AppCompatImageButton btnInfo = new AppCompatImageButton(this);
            btnInfo.setBackgroundResource(R.drawable.information_button);
            btnInfo.setImageResource(R.drawable.baseline_info_24);
            btnInfo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            btnInfo.setPadding(30, 30, 30, 30);
            btnInfo.setLayoutParams(paramsBtn);
            btnInfo.setOnClickListener(v -> {
                int index = vuelos.indexOf(vuelo);
                FlightDialogFragment fragment = FlightDialogFragment.newInstance(vuelo, index);
                fragment.setOnFlightEditedListener(this);
                fragment.show(getSupportFragmentManager(), "flight_dialog");
            });

            botonesLayout.addView(btnEliminar);
            botonesLayout.addView(btnInfo);

            filaVuelo.addView(llText);
            filaVuelo.addView(botonesLayout);

            card.addView(filaVuelo);

            tablaVuelos.addView(card);
        }


    }

    @Override
    public void onFlightEdited(Vuelo vueloOriginal, Aeropuerto salidaNueva, Aeropuerto destinoNueva, Date fechaSalidaNueva, Date fechaLlegadaNueva) {
        if (vueloOriginal == null) return;

        Vuelo vueloActualizado = new Vuelo(salidaNueva, destinoNueva, fechaSalidaNueva, fechaLlegadaNueva);
        DataRepository.actualizarVuelo(vueloOriginal, vueloActualizado, this);
        Toast.makeText(this, "Vuelo actualizado", Toast.LENGTH_SHORT).show();
        llenarTabla();
    }

    public void cleanTableP(TableLayout table){
        int childCount = table.getChildCount();
        if(childCount > 1){
            table.removeViews(1, childCount-1);
        }
    }
}