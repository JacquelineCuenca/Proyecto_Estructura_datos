package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Modelo.Aeropuerto;
import Modelo.DataRepository;

public class RemoveAirportActivity extends AppCompatActivity implements AirportDialogFragment.OnAirportEditedListener {
    private AppCompatButton btnRegresarMenu;
    private TableLayout tablaAeropuertos;
    private List<Aeropuerto> aeropuertos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_airport);

        btnRegresarMenu = findViewById(R.id.btnRegresar);
        btnRegresarMenu.setOnClickListener(v -> finish());
        
        tablaAeropuertos = findViewById(R.id.tableAirport);
        llenarTabla();
    }

    private void llenarTabla() {
        aeropuertos = DataRepository.getAeropuertos();

        cleanTableP(tablaAeropuertos);

        if (aeropuertos == null || aeropuertos.isEmpty()) {
            Toast.makeText(this, "No hay aeropuertos registrados", Toast.LENGTH_SHORT).show();
            TextView tvAeropuerto = new TextView(this);
            tvAeropuerto.setText("No hay aeropuertos registrados");
            tvAeropuerto.setGravity(Gravity.CENTER);
            tvAeropuerto.setTextSize(18);

            tablaAeropuertos.addView(tvAeropuerto);

            return;
        }

        for (Aeropuerto aeropuerto : aeropuertos) {
            LinearLayout filaAeropuerto = new LinearLayout(this);
            filaAeropuerto.setOrientation(LinearLayout.HORIZONTAL);
            filaAeropuerto.setPadding(20, 20, 20, 20);
            filaAeropuerto.setBackgroundResource(R.drawable.fila_background);


            LinearLayout.LayoutParams paramsFila = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsFila.setMargins(0, 0, 0, 5);
            filaAeropuerto.setLayoutParams(paramsFila);

            TextView tvAeropuerto = new TextView(this);
            tvAeropuerto.setText(aeropuerto.getNombre());
            tvAeropuerto.setTextSize(16);
            tvAeropuerto.setTextColor(Color.BLACK);
            tvAeropuerto.setSingleLine(true);
            tvAeropuerto.setEllipsize(TextUtils.TruncateAt.END);
            tvAeropuerto.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams paramsTexto = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
            );
            tvAeropuerto.setLayoutParams(paramsTexto);

            LinearLayout botonesLayout = new LinearLayout(this);
            botonesLayout.setOrientation(LinearLayout.HORIZONTAL);
            botonesLayout.setGravity(Gravity.CENTER);


            AppCompatImageButton btnEliminar = new AppCompatImageButton(this);
            btnEliminar.setBackgroundResource(R.drawable.delete_button);
            btnEliminar.setImageResource(R.drawable.baseline_delete_24);
            btnEliminar.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            btnEliminar.setPadding(30, 30, 30, 30);

            LinearLayout.LayoutParams paramsBtn = new LinearLayout.LayoutParams(
                    120, 120
            );
            paramsBtn.setMargins(20, 0, 0, 0);
            btnEliminar.setLayoutParams(paramsBtn);

            btnEliminar.setOnClickListener(v -> {
                DataRepository.eliminarAeropuerto(aeropuerto, RemoveAirportActivity.this);
                Toast.makeText(RemoveAirportActivity.this, "Aeropuerto eliminado", Toast.LENGTH_SHORT).show();
                llenarTabla();
            });


            // Botón info
            AppCompatImageButton btnInfo = new AppCompatImageButton(this);
            btnInfo.setBackgroundResource(R.drawable.information_button);
            btnInfo.setImageResource(R.drawable.baseline_info_24);
            btnInfo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            btnInfo.setPadding(30, 30, 30, 30);
            btnInfo.setLayoutParams(paramsBtn);

            btnInfo.setOnClickListener(v -> {
                Toast.makeText(RemoveAirportActivity.this, "Información del aeropuerto", Toast.LENGTH_SHORT).show();
                AirportDialogFragment fragment = AirportDialogFragment.newInstance(aeropuerto.getCodigo(), aeropuerto.getNombre());
                fragment.setOnAirportEditedListener(this);
                fragment.show(getSupportFragmentManager(), "airport_dialog");
            });

            // Agregar botones
            botonesLayout.addView(btnEliminar);
            botonesLayout.addView(btnInfo);

            // Agregar a la fila
            filaAeropuerto.addView(tvAeropuerto);
            filaAeropuerto.addView(botonesLayout);

            // Agregar fila a la tabla
            tablaAeropuertos.addView(filaAeropuerto);
        }
    }


    public void cleanTableP(TableLayout table){
        int childCount = table.getChildCount();
        if(childCount > 1){
            table.removeViews(1, childCount-1);
        }
    }

    @Override
    public void onAirportEdited(String codigoOriginal, String codigoNuevo, String nombreNuevo) {
        if(codigoOriginal == null) return; // Necesitamos el código original

        Aeropuerto aeropuertoActualizado = new Aeropuerto(codigoNuevo, nombreNuevo);
        DataRepository.actualizarAeropuerto(codigoOriginal, aeropuertoActualizado, this);
        Toast.makeText(this, "Aeropuerto actualizado", Toast.LENGTH_SHORT).show();
        llenarTabla();
    }
}
