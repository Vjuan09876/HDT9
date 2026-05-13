import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class GrafoTest {
    private GrafoMatriz<String, Integer> grafo;
    private CalculadoraFloyd<String> calculadora;

    @BeforeEach
    void setUp() {
        List<String> ciudades = Arrays.asList("A", "B", "C");
        grafo = new GrafoMatriz<>(ciudades);
        calculadora = new CalculadoraFloyd<>();
    }

    @Test
    void testAddArco() {
        grafo.addArco("A", "B", 10);
        assertEquals(10, grafo.getPeso("A", "B"), "El peso del arco deberia ser 10");
    }

    @Test
    void testRemoveArco() {
        grafo.addArco("A", "B", 10);
        grafo.removeArco("A", "B");
        assertTrue(grafo.getPeso("A", "B") >= 1000000, "El arco deberia ser infinito tras eliminarlo");
    }

    @Test
    void testFloydAlgoritmo() {
        grafo.addArco("A", "B", 10);
        grafo.addArco("B", "C", 20);
        grafo.addArco("A", "C", 50);

        calculadora.ejecutarFloyd(grafo);
        String resultado = calculadora.obtenerRuta("A", "C", grafo);

        assertTrue(resultado.contains("30"), "La distancia calculada por Floyd debería ser 30");
        assertTrue(resultado.contains("A -> B -> C"), "La ruta deberia mostrar los nodos del medio");
    }

    @Test
    void testCentroGrafo() {
        grafo.addArco("A", "B", 5);
        grafo.addArco("B", "A", 5);
        grafo.addArco("B", "C", 5);
        grafo.addArco("C", "B", 5);
        calculadora.ejecutarFloyd(grafo);
        String centro = calculadora.calcularCentro(grafo);
        
        assertEquals("B", centro, "La ciudad B deberia ser el centro del grafo");
    }
}