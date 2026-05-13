public interface IGrafo<V, E> {
    void addArco(V v1, V v2, E peso);
    void removeArco(V v1, V v2);
    int getIndice(V valor);
}