package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Modelo.Aeropuerto;
import Modelo.DataRepository;
import Modelo.Vuelo;

public class FlightDialogFragment extends DialogFragment {
    private static final String ARG_DESTINO_CODIGO = "destino_codigo";
    private static final String ARG_SALIDA_CODIGO = "salida_codigo";
    private static final String ARG_FECHA_SALIDA = "fecha_salida";
    private static final String ARG_FECHA_LLEGADA = "fecha_llegada";
    private static final String ARG_VUELO_INDEX = "vuelo_index";

    private OnFlightEditedListener listener;

    private Vuelo vueloOriginal;

    public static FlightDialogFragment newInstance(Vuelo vuelo, int index) {
        FlightDialogFragment fragment = new FlightDialogFragment();
        fragment.vueloOriginal = vuelo;
        Bundle args = new Bundle();
        args.putInt(ARG_VUELO_INDEX, index);
        args.putString(ARG_SALIDA_CODIGO, vuelo.getPartida().getCodigo());
        args.putString(ARG_DESTINO_CODIGO, vuelo.getDestino().getCodigo());
        args.putString(ARG_FECHA_SALIDA, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(vuelo.getHoraInicio()));
        args.putString(ARG_FECHA_LLEGADA, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(vuelo.getHoraFin()));
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnFlightEditedListener {
        void onFlightEdited(Vuelo vueloOriginal, Aeropuerto salida, Aeropuerto destino, Date fechaSalida, Date fechaLlegada);
    }

    public void setOnFlightEditedListener(OnFlightEditedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_flight, container, false);

        LinearLayout layoutVista = view.findViewById(R.id.layout_vista);
        LinearLayout layoutEdicion = view.findViewById(R.id.layout_edicion);

        TextView tvAiportSalida = view.findViewById(R.id.tv_salida_aeropuerto);
        TextView tvAirportDestino = view.findViewById(R.id.tv_destino_aeropuerto);
        TextView tvFechaSalida = view.findViewById(R.id.tv_fecha_salida);
        TextView tvFechaLlegada = view.findViewById(R.id.tv_fecha_llegada);

        Spinner spAirportSalida = view.findViewById(R.id.spinnerAirportSalida);
        Spinner spAirportDestino = view.findViewById(R.id.spinnerAirportLlegada);
        EditText etFechaSalida = view.findViewById(R.id.etFechaSalida);
        EditText etFechaLlegada = view.findViewById(R.id.etFechaLlegada);

        Button btnEditar = view.findViewById(R.id.btn_editar);
        Button btnGuardar = view.findViewById(R.id.btn_guardar);
        Button btnCerrar = view.findViewById(R.id.btn_cerrar);
        Button btnFechaSalida = view.findViewById(R.id.btnFechaSalida);
        Button btnFechaLlegada = view.findViewById(R.id.btnFechaLlegada);

        // Obtener aeropuertos
        List<Aeropuerto> aeropuertos = DataRepository.getAeropuertos();
        ArrayAdapter<Aeropuerto> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, aeropuertos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAirportSalida.setAdapter(adapter);
        spAirportDestino.setAdapter(adapter);

        Bundle args = getArguments();
        Aeropuerto salida = null;
        Aeropuerto destino = null;
        String fechaSalidaStr = "";
        String fechaLlegadaStr = "";

        if (args != null) {
            String codigoSalida = args.getString(ARG_SALIDA_CODIGO);
            String codigoDestino = args.getString(ARG_DESTINO_CODIGO);
            fechaSalidaStr = args.getString(ARG_FECHA_SALIDA, "");
            fechaLlegadaStr = args.getString(ARG_FECHA_LLEGADA, "");

            salida = DataRepository.getAeropuertoPorCodigo(codigoSalida);
            destino = DataRepository.getAeropuertoPorCodigo(codigoDestino);
        }

        tvAiportSalida.setText("Salida: " + (salida != null ? salida.getNombre() : "Desconocido"));
        tvAirportDestino.setText("Destino: " + (destino != null ? destino.getNombre() : "Desconocido"));
        tvFechaSalida.setText("Fecha Salida: " + fechaSalidaStr);
        tvFechaLlegada.setText("Fecha Llegada: " + fechaLlegadaStr);

        // Selección en spinners
        if (salida != null) spAirportSalida.setSelection(aeropuertos.indexOf(salida));
        if (destino != null) spAirportDestino.setSelection(aeropuertos.indexOf(destino));

        etFechaSalida.setText(fechaSalidaStr);
        etFechaLlegada.setText(fechaLlegadaStr);

        // Botón editar
        btnEditar.setOnClickListener(v -> {
            layoutVista.setVisibility(View.GONE);
            layoutEdicion.setVisibility(View.VISIBLE);
            btnEditar.setVisibility(View.GONE);
            btnGuardar.setVisibility(View.VISIBLE);
        });

        // Botones para elegir fechas
        btnFechaSalida.setOnClickListener(v -> mostrarDateTimePicker(etFechaSalida));
        btnFechaLlegada.setOnClickListener(v -> mostrarDateTimePicker(etFechaLlegada));

        // Guardar cambios
        btnGuardar.setOnClickListener(v -> {
            Aeropuerto nuevoAirpotSalida = (Aeropuerto) spAirportSalida.getSelectedItem();
            Aeropuerto nuevoAirpotDestino = (Aeropuerto) spAirportDestino.getSelectedItem();
            String nuevoFechaSalida = etFechaSalida.getText().toString();
            String nuevoFechaLlegada = etFechaLlegada.getText().toString();

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                Date fechaSalidaDate = sdf.parse(nuevoFechaSalida);
                Date fechaLlegadaDate = sdf.parse(nuevoFechaLlegada);

                if (listener != null) {
                    listener.onFlightEdited(vueloOriginal, nuevoAirpotSalida, nuevoAirpotDestino, fechaSalidaDate, fechaLlegadaDate);
                }

                dismiss();

            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Formato de fecha inválido", Toast.LENGTH_SHORT).show();
            }
        });


        btnCerrar.setOnClickListener(v -> dismiss());

        return view;
    }

    private void mostrarDateTimePicker(EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    TimePickerDialog timePicker = new TimePickerDialog(getContext(),
                            (timeView, hourOfDay, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                                editText.setText(sdf.format(calendar.getTime()));
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true);
                    timePicker.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }
}
