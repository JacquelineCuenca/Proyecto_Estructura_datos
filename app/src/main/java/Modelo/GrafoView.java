package Modelo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class GrafoView extends View {
    private GraphAL<Aeropuerto, Vuelo> grafo;
    private Paint paintNodo, paintArista, paintTexto;
    private Map<Aeropuerto, Float[]> posiciones;

    public GrafoView(Context context) {
        super(context);
        init();
    }

    public GrafoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintNodo = new Paint();
        paintNodo.setColor(Color.BLUE);
        paintNodo.setStyle(Paint.Style.FILL);

        paintArista = new Paint();
        paintArista.setColor(Color.GRAY);
        paintArista.setStrokeWidth(5);

        paintTexto = new Paint();
        paintTexto.setColor(Color.WHITE);
        paintTexto.setTextSize(32);
        paintTexto.setTextAlign(Paint.Align.CENTER);

        posiciones = new HashMap<>();
    }

    public void setGrafo(GraphAL<Aeropuerto, Vuelo> grafo) {
        this.grafo = grafo;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (grafo == null) return;

        List<Aeropuerto> nodos = grafo.getVerticesContents();
        int n = nodos.size();
        int radio = 60;
        int radioCirculo = Math.min(getWidth(), getHeight()) / 2 - 2 * radio;
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        posiciones.clear();

        // Distribuir nodos en círculo
        for (int i = 0; i < n; i++) {
            double ang = 2 * Math.PI * i / n;
            float x = (float) (cx + radioCirculo * Math.cos(ang));
            float y = (float) (cy + radioCirculo * Math.sin(ang));
            posiciones.put(nodos.get(i), new Float[]{x, y});
        }

        // Dibujar aristas
        for (Aeropuerto origen : nodos) {
            Float[] posOrigen = posiciones.get(origen);
            for (Object obj : grafo.findVertex(origen).getEdges()) {
                @SuppressWarnings("unchecked")
                Edge<Vuelo, Aeropuerto> arista = (Edge<Vuelo, Aeropuerto>) obj;
                Aeropuerto destino = arista.getTarget().getContent();
                Float[] posDestino = posiciones.get(destino);
                if (posDestino != null) {
                    canvas.drawLine(posOrigen[0], posOrigen[1], posDestino[0], posDestino[1], paintArista);
                }
            }
        }

        // Dibujar nodos
        for (Aeropuerto nodo : nodos) {
            Float[] pos = posiciones.get(nodo);
            canvas.drawCircle(pos[0], pos[1], radio, paintNodo);
            canvas.drawText(nodo.getCodigo(), pos[0], pos[1] + 10, paintTexto);
        }
    }
}
