package Modelo;

import android.content.Context;
import android.content.res.AssetManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * DataRepository es una clase que maneja la inicialización y acceso al grafo de vuelos.
 * Carga los datos de aeropuertos y vuelos desde archivos JSON en los assets de la aplicación.
 */
public class DataRepository {
    private static GraphAL<Aeropuerto, Vuelo> grafoVuelos;
    private static Map<String, Aeropuerto> aeropuertosMap;
    private static List<Aeropuerto> listaAeropuertos;

    public static void inicializar(Context context) {
        if (grafoVuelos != null) return; // Ya inicializado

        grafoVuelos = new GraphAL<>(true, Comparator.comparing(Aeropuerto::getCodigo));
        aeropuertosMap = new HashMap<>();
        listaAeropuertos = new ArrayList<>();
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
                listaAeropuertos.add(a);
                aeropuertosMap.put(a.getCodigo(), a);
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
                Aeropuerto partida = aeropuertosMap.get(obj.getString("partida"));
                Aeropuerto destino = aeropuertosMap.get(obj.getString("destino"));
                // Aquí puedes parsear las fechas si lo necesitas
                Vuelo vuelo = new Vuelo(partida, destino, null, null);
                grafoVuelos.connect(partida, destino, 1, vuelo); // Peso 1 por defecto
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void agregarAeropuerto(Aeropuerto aeropuerto, Context context) {
        try {
            if (grafoVuelos == null) {
                System.err.println("Error: Grafo de vuelos no inicializado. Llama a inicializar() primero.");
                return;
            }
            if (!aeropuertosMap.containsKey(aeropuerto.getCodigo())) {
                grafoVuelos.addVertex(aeropuerto);
                aeropuertosMap.put(aeropuerto.getCodigo(), aeropuerto);
                listaAeropuertos.add(aeropuerto);
                agregarAeropuertoAJson(aeropuerto, context); // Persistir en JSON
            } else {
                System.out.println("El aeropuerto con código " + aeropuerto.getCodigo() + " ya existe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void agregarAeropuertoAJson(Aeropuerto aeropuerto, Context context) {
        try {
            File aeropuertosFile = new File(context.getFilesDir(), "aeropuertos.json");
            JSONArray arrAeropuertos;

            if (aeropuertosFile.exists()) {
                InputStream is = context.openFileInput("aeropuertos.json");
                String jsonString = convertStreamToString(is);
                is.close();
                arrAeropuertos = new JSONArray(jsonString);
            } else {
                arrAeropuertos = new JSONArray();
            }

            JSONObject newAeropuertoJson = new JSONObject();
            newAeropuertoJson.put("codigo", aeropuerto.getCodigo());
            newAeropuertoJson.put("nombre", aeropuerto.getNombre());

            arrAeropuertos.put(newAeropuertoJson);

            FileOutputStream fos = context.openFileOutput("aeropuertos.json", Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(arrAeropuertos.toString(4)); // toString(4) para formato legible
            writer.close();
            fos.close();

            System.out.println("Aeropuerto agregado al JSON: " + newAeropuertoJson.toString());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static void agregarVuelo(Vuelo vuelo, Context context) {
        try{
            if (grafoVuelos == null) {
                System.err.println("Error: Grafo de vuelos no inicializado. Llama a inicializar() primero.");
                return;
            }
            grafoVuelos.connect(vuelo.getPartida(), vuelo.getDestino(), 1, vuelo);
            agregarVueloAJson(vuelo, context);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void agregarVueloAJson(Vuelo vuelo, Context context) throws JSONException {
        try {
            File vuelosFile = new File(context.getFilesDir(), "vuelos.json");
            JSONArray arrVuelos;

            if (vuelosFile.exists()) {
                InputStream is = context.openFileInput("vuelos.json");
                String jsonString = convertStreamToString(is);
                is.close();
                arrVuelos = new JSONArray(jsonString);
            } else {
                arrVuelos = new JSONArray();
            }

            JSONObject newVueloJson = new JSONObject();
            newVueloJson.put("partida", vuelo.getPartida().getCodigo());
            newVueloJson.put("destino", vuelo.getDestino().getCodigo());

            arrVuelos.put(newVueloJson);

            FileOutputStream fos = context.openFileOutput("vuelos.json", Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(arrVuelos.toString(4));
            writer.close();
            fos.close();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
    private static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }
        reader.close();
        return sb.toString();
    }

    public static GraphAL<Aeropuerto, Vuelo> getGrafoVuelos() {
        return grafoVuelos;
    }

    public static Aeropuerto getAeropuertoPorCodigo(String codigo) {
        return aeropuertosMap.get(codigo);
    }

    public static List<Aeropuerto> getAeropuertos() {
        return new ArrayList<>(listaAeropuertos);
    }
}
