package Modelo;

import android.content.Context;
import android.content.res.AssetManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DataRepository es una clase que maneja la inicialización y acceso al grafo de vuelos.
 * Carga los datos de aeropuertos y vuelos desde archivos JSON en los assets de la aplicación.
 */
public class DataRepository {
    private static GraphAL<Aeropuerto, Vuelo> grafoVuelos;
    private static Map<String, Aeropuerto> aeropuertosMap;
    private static List<Aeropuerto> listaAeropuertos;

    private static List<Vuelo> listaVuelos;

    public static void inicializar(Context context) {
        if (grafoVuelos != null) return;

        if (listaAeropuertos == null) {
            leerAeropuertos(context);
        }
        if (listaVuelos == null) {
            leerVuelos(context);
        }

        grafoVuelos = new GraphAL<>(true, Comparator.comparing(Aeropuerto::getCodigo));
        aeropuertosMap = new HashMap<>();
        listaAeropuertos = new ArrayList<>();
        listaVuelos = new ArrayList<>();
        try {
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
                Vuelo vuelo = new Vuelo(partida, destino, null, null);
                listaVuelos.add(vuelo);
                grafoVuelos.connect(partida, destino, 1, vuelo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actualizarAeropuerto(String codigoOriginal, Aeropuerto aeropuertoNuevo, Context context){
        List<Aeropuerto> lista = leerAeropuertos(context);

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equalsIgnoreCase(codigoOriginal)) {
                lista.set(i, aeropuertoNuevo);
                break;
            }
        }

        escribirArchivoAeropuerto(convertirAeropuertoJson(lista).toString(), context);

        listaAeropuertos = lista;
        aeropuertosMap.put(aeropuertoNuevo.getCodigo(), aeropuertoNuevo);
    }

    public static void actualizarVuelo(Vuelo vueloOriginal, Vuelo vueloNuevo, Context context) {
        if (listaVuelos == null) return;

        for (int i = 0; i < listaVuelos.size(); i++) {
            Vuelo v = listaVuelos.get(i);
            if (v.equals(vueloOriginal)) { // compara referencias o puedes implementar equals() en Vuelo
                listaVuelos.set(i, vueloNuevo);
                break;
            }
        }

        try {
            JSONArray arrVuelos = new JSONArray();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

            for (Vuelo v : listaVuelos) {
                JSONObject obj = new JSONObject();
                obj.put("partida", v.getPartida().getCodigo());
                obj.put("destino", v.getDestino().getCodigo());
                obj.put("horaInicio", sdf.format(v.getHoraInicio()));
                obj.put("horaFin", sdf.format(v.getHoraFin()));
                arrVuelos.put(obj);
            }

            String json = arrVuelos.toString(4);
            FileOutputStream fos = context.openFileOutput("vuelos.json", Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void guardarAeropuerto(Aeropuerto aeropuerto, Context context){
        List<Aeropuerto> lista;
        File file = new File(context.getFilesDir(), "aeropuertos.json");
        if(!file.exists()){
            lista = getAeropuertos();
        } else {
            lista = leerAeropuertos(context);
        }

        lista.add(aeropuerto);

        escribirArchivoAeropuerto(convertirAeropuertoJson(lista).toString(), context);

        listaAeropuertos = lista;
        aeropuertosMap.put(aeropuerto.getCodigo(), aeropuerto);
    }


    public static void guardarVuelo(Vuelo vuelo, Context context){
        try{
            if (listaVuelos == null) {
                listaVuelos = new ArrayList<>();
            }

            listaVuelos.add(vuelo);

            if(grafoVuelos != null){
                grafoVuelos.connect(vuelo.getPartida(), vuelo.getDestino(), 1, vuelo);
            }

            JSONArray arrVuelos = new JSONArray();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

            for (Vuelo v : listaVuelos) {
                JSONObject obj = new JSONObject();
                obj.put("partida", v.getPartida().getCodigo());
                obj.put("destino", v.getDestino().getCodigo());
                obj.put("fechaInicio", sdf.format(v.getHoraInicio()));
                obj.put("fechaFin", sdf.format(v.getHoraFin()));
                arrVuelos.put(obj);
            }

            String json = arrVuelos.toString(4);
            FileOutputStream fos = context.openFileOutput("vuelos.json", Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void eliminarAeropuerto(Aeropuerto aeropuerto, Context context){
        List<Aeropuerto> lista = leerAeropuertos(context);

        lista.removeIf(a -> a.getCodigo().equalsIgnoreCase(aeropuerto.getCodigo()));
        escribirArchivoAeropuerto(convertirAeropuertoJson(lista).toString(), context);
        listaAeropuertos = lista;
        aeropuertosMap.clear();
        for (Aeropuerto a : lista) {
            aeropuertosMap.put(a.getCodigo(), a);
        }
    }

    public static void eliminarVuelo(Vuelo vuelo, Context context) {
        if (listaVuelos == null) return;

        listaVuelos.remove(vuelo);

        try {
            JSONArray arrVuelos = new JSONArray();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

            for (Vuelo v : listaVuelos) {
                JSONObject obj = new JSONObject();
                obj.put("partida", v.getPartida().getCodigo());
                obj.put("destino", v.getDestino().getCodigo());
                obj.put("horaInicio", sdf.format(v.getHoraInicio()));
                obj.put("horaFin", sdf.format(v.getHoraFin()));
                arrVuelos.put(obj);
            }

            String json = arrVuelos.toString(4);
            FileOutputStream fos = context.openFileOutput("vuelos.json", Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static List<Aeropuerto> leerAeropuertos(Context context){
        List<Aeropuerto> lista = new ArrayList<>();
        File file = new File(context.getFilesDir(), "aeropuertos.json");
        StringBuilder contenido = new StringBuilder();
        try{
            InputStream is;
            if(file.exists()){
                is = new FileInputStream(file);
            } else {
                // Copiar desde assets
                is = context.getAssets().open("aeropuertos.json");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String linea;
            while((linea = br.readLine()) != null){
                contenido.append(linea);
            }
            br.close();
            is.close();

            JSONArray array = new JSONArray(contenido.toString());
            for(int i = 0; i<array.length(); i++){
                JSONObject aeropuerto = array.getJSONObject(i);
                String nombre = aeropuerto.getString("nombre");
                String codigo = aeropuerto.getString("codigo");
                lista.add(new Aeropuerto(codigo, nombre));
            }

        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
        return lista;
    }


    public static void leerVuelos(Context context){
        try{
            File file = new File(context.getFilesDir(), "vuelos.json");
            if(!file.exists()) return;

            FileInputStream fis = context.openFileInput("vuelos.json");
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            String jsonVuelos = new String(buffer, StandardCharsets.UTF_8);
            JSONArray arrVuelos = new JSONArray(jsonVuelos);
            listaVuelos = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

            for(int i = 0; i<arrVuelos.length(); i++){
                JSONObject obj = arrVuelos.getJSONObject(i);
                Aeropuerto partida = aeropuertosMap.get(obj.getString("partida"));
                Aeropuerto destino = aeropuertosMap.get(obj.getString("destino"));
                Date fechaInicio = sdf.parse(obj.getString("horaInicio"));
                Date fechaFin = sdf.parse(obj.getString("horaFin"));

                Vuelo vuelo = new Vuelo(partida, destino, fechaInicio, fechaFin);
                listaVuelos.add(vuelo);

                grafoVuelos.connect(partida, destino, 1, vuelo);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static JSONArray convertirAeropuertoJson(List<Aeropuerto> lista){
        JSONArray array = new JSONArray();
        for(Aeropuerto a: lista){
            try{
                JSONObject obj = new JSONObject();
                obj.put("codigo", a.getCodigo());
                obj.put("nombre", a.getNombre());
                array.put(obj);
            }catch (JSONException e){
                    e.printStackTrace();
            }
        }
        return  array;
    }

    public static void escribirArchivoAeropuerto(String data, Context context){
        try{
            FileOutputStream fos = context.openFileOutput("aeropuertos.json", Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void resetAirportsData(Context context) {
        try {
            InputStream is = context.getAssets().open("aeropuertos.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            FileOutputStream fos = context.openFileOutput("aeropuertos.json", Context.MODE_PRIVATE);
            fos.write(buffer);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetVuelosData(Context context) {
        try {
            InputStream is = context.getAssets().open("vuelos.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            FileOutputStream fos = context.openFileOutput("vuelos.json", Context.MODE_PRIVATE);
            fos.write(buffer);
            fos.close();

            if (listaVuelos != null) listaVuelos.clear();
            leerVuelos(context);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static List<Vuelo> getVuelos(){
        return listaVuelos;
    }

}
