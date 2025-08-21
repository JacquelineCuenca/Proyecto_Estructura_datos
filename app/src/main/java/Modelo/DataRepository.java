package Modelo;

import android.content.Context;
import android.content.res.AssetManager;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.*;

/**
 * DataRepository es una clase que maneja la inicialización y acceso al grafo de vuelos.
 * Carga los datos de aeropuertos y vuelos desde archivos JSON en los assets de la aplicación.
 */
public class DataRepository {
    private static GraphAL<Aeropuerto, Vuelo> grafoVuelos;

    public static void inicializar(Context context) {
        if (grafoVuelos != null) return; // Ya inicializado

        grafoVuelos = new GraphAL<>(true, Comparator.comparing(Aeropuerto::getCodigo));
        Map<String, Aeropuerto> mapaAeropuertos = new HashMap<>();

        try {
            // Leer aeropuertos
            AssetManager am = context.getAssets();
            InputStream is = am.open("aeropuertos.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String jsonAeropuertos = new String(buffer, "UTF-8");
            JSONArray arrAeropuertos = new JSONArray(jsonAeropuertos);
            for (int i = 0; i < arrAeropuertos.length(); i++) {
                JSONObject obj = arrAeropuertos.getJSONObject(i);
                Aeropuerto a = new Aeropuerto(obj.getString("codigo"), obj.getString("nombre"));
                grafoVuelos.addVertex(a);
                mapaAeropuertos.put(a.getCodigo(), a);
            }

            // Leer vuelos
            is = am.open("vuelos.json");
            buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String jsonVuelos = new String(buffer, "UTF-8");
            JSONArray arrVuelos = new JSONArray(jsonVuelos);
            for (int i = 0; i < arrVuelos.length(); i++) {
                JSONObject obj = arrVuelos.getJSONObject(i);
                Aeropuerto partida = mapaAeropuertos.get(obj.getString("partida"));
                Aeropuerto destino = mapaAeropuertos.get(obj.getString("destino"));
                // Aquí puedes parsear las fechas si lo necesitas
                Vuelo vuelo = new Vuelo(partida, destino, null, null);
                grafoVuelos.connect(partida, destino, 1, vuelo); // Peso 1 por defecto
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GraphAL<Aeropuerto, Vuelo> getGrafoVuelos() {
        return grafoVuelos;
    }
}
