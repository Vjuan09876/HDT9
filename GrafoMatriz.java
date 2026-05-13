import java.util.*;

public class GrafoMatriz<V, E> implements IGrafo<V, E> {
    private final E[][] matriz;
    private final Map<V, Integer> diccIndices;
    private final List<V> nombresCiudades;
    private final int INF = 1000000;

    public GrafoMatriz(List<V> ciudades) {
        int n = ciudades.size();
        this.nombresCiudades = ciudades;
        this.diccIndices = new HashMap<>();
        this.matriz = (E[]) new Object[n][n];

        for (int i = 0; i < n; i++) {
            diccIndices.put(ciudades.get(i), i);
            Arrays.fill(matriz[i], (E) Integer.valueOf(INF));
            matriz[i][i] = (E) Integer.valueOf(0);
        }
    }

    @Override
    public void addArco(V v1, V v2, E peso) {
        if (diccIndices.containsKey(v1) && diccIndices.containsKey(v2)) {
            int i = diccIndices.get(v1);
            int j = diccIndices.get(v2);
            matriz[i][j] = peso;
        }
    }

    @Override
    public void removeArco(V v1, V v2) {
        if (diccIndices.containsKey(v1) && diccIndices.containsKey(v2)) {
            int i = diccIndices.get(v1);
            int j = diccIndices.get(v2);
            matriz[i][j] = (E) Integer.valueOf(INF); 
    }
}
    @Override
    public E getPeso(V v1, V v2) {
        return matriz[diccIndices.get(v1)][diccIndices.get(v2)];
    }

    @Override
    public int getIndice(V valor) {
        return diccIndices.get(valor);
    }

    public List<V> getNombresCiudades() {
        return nombresCiudades;
    }

    public int getSize() {
        return nombresCiudades.size();
    }

    public void mostrarMatriz() {
        System.out.println("\nmatriz de adyacencia");
        System.out.print("\t");
        for (V ciudad : nombresCiudades) {
            System.out.print(ciudad + "\t");
        }
        System.out.println();

        for (int i = 0; i < getSize(); i++) {
            System.out.print(nombresCiudades.get(i) + "\t");
            for (int j = 0; j < getSize(); j++) {
                Object peso = matriz[i][j];
                System.out.print((peso.equals(INF) ? "inf" : peso) + "\t");
            }
            System.out.println();
        }
    }
}