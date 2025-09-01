package com.example.myapplication;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AirportDialogFragment extends DialogFragment {
    private static final String ARG_CODIGO = "codigo";
    private static final String ARG_NOMBRE = "nombre";

    private OnAirportEditedListener listener;

    private String codigoOriginal;


    public static AirportDialogFragment newInstance(String codigo, String nombre){
        AirportDialogFragment fragment = new AirportDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CODIGO, codigo);
        args.putString(ARG_NOMBRE, nombre);
        fragment.setArguments(args);
        return fragment;
    }

    public interface OnAirportEditedListener {
        void onAirportEdited(String codigoOriginal, String codigoNuevo, String nombreNuevo);
    }


    public void setOnAirportEditedListener(OnAirportEditedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_airport, container, false);


        LinearLayout layoutVista = view.findViewById(R.id.layout_vista);
        LinearLayout layoutEdicion = view.findViewById(R.id.layout_edicion);
        TextView tvCodigo = view.findViewById(R.id.tv_codigo_aeropuerto);
        TextView tvNombre = view.findViewById(R.id.tv_nombre_aeropuerto);
        EditText etCodigo = view.findViewById(R.id.et_codigo_aeropuerto);
        EditText etNombre = view.findViewById(R.id.et_nombre_aeropuerto);
        Button btnEditar = view.findViewById(R.id.btn_editar);
        Button btnGuardar = view.findViewById(R.id.btn_guardar);
        Button btnCerrar = view.findViewById(R.id.btn_cerrar);



        Bundle args = getArguments();
        if (args != null) {
            String codigo = args.getString(ARG_CODIGO, "");
            String nombre = args.getString(ARG_NOMBRE, "");

            tvCodigo.setText("Código: " + codigo);
            tvNombre.setText("Nombre: " + nombre);

            codigoOriginal = args.getString(ARG_CODIGO, "");

            etCodigo.setText(codigo);
            etNombre.setText(nombre);
        }

        btnEditar.setOnClickListener(v -> {
            layoutVista.setVisibility(View.GONE);
            layoutEdicion.setVisibility(View.VISIBLE);
            btnEditar.setVisibility(View.GONE);
            btnGuardar.setVisibility(View.VISIBLE);
        });

        //Guardar la nueva informacion
        btnGuardar.setOnClickListener(v -> {
            String nuevoNombre = etNombre.getText().toString();
            String nuevoCodigo = etCodigo.getText().toString();

            if (listener != null) {
                listener.onAirportEdited(codigoOriginal, nuevoCodigo, nuevoNombre);
            }


            dismiss(); // Cierra el popup
        });

        btnCerrar.setOnClickListener(v -> dismiss());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Ajustar el tamaño del popup al contenido y centrarlo
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
        }
    }
}