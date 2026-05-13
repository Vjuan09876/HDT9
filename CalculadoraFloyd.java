public class CalculadoraFloyd<V> {
    private long[][] distancias;
    private int[][] recorridos;
    private final int INF = 1000000;

    public void ejecutarFloyd(GrafoMatriz<V, Integer> grafo) {
        int n = grafo.getSize();
        distancias = new long[n][n];
        recorridos = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distancias[i][j] = grafo.getPeso(grafo.getNombresCiudades().get(i), grafo.getNombresCiudades().get(j));
                if (distancias[i][j] != INF && i != j) {
                    recorridos[i][j] = j;
                } else {
                    recorridos[i][j] = -1;
                }
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        recorridos[i][j] = recorridos[i][k];
                    }
                }
            }
        }
    }

    public String obtenerRuta(V origen, V destino, GrafoMatriz<V, Integer> grafo) {
        int i = grafo.getIndice(origen);
        int j = grafo.getIndice(destino);
        if (distancias[i][j] >= INF) return "no hay ruta";

        StringBuilder ruta = new StringBuilder(origen.toString());
        int actual = i;
        while (actual != j) {
            actual = recorridos[actual][j];
            ruta.append(" -> ").append(grafo.getNombresCiudades().get(actual));
        }
        return "Distancia: " + distancias[i][j] + " KM. Ruta: " + ruta.toString();
    }

    public V calcularCentro(GrafoMatriz<V, Integer> grafo) {
        int n = grafo.getSize();
        long[] excentricidades = new long[n];

        for (int j = 0; j < n; j++) {
            long max = 0;
            for (int i = 0; i < n; i++) {
                max = Math.max(max, distancias[i][j]);
            }
            excentricidades[j] = max;
        }

        int indiceCentro = 0;
        for (int i = 1; i < n; i++) {
            if (excentricidades[i] < excentricidades[indiceCentro]) {
                indiceCentro = i;
            }
        }
        return grafo.getNombresCiudades().get(indiceCentro);
    }
}