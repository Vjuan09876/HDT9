import java.util.*;
import java.io.*;

public class SistemaLogistica {
    private static GrafoMatriz<String, Integer> grafo;
    private static CalculadoraFloyd<String> calculadora;
    private static final String ARCHIVO = "guategrafo.txt";

    public static void main(String[] args) {
        Scanner sn = new Scanner(System.in);
        calculadora = new CalculadoraFloyd<>();
        
        inicializarGrafo();
        
        if (grafo == null) {
            System.out.println("no se pudo cargar" + ARCHIVO + "fin");
            return;
        }

        calculadora.ejecutarFloyd(grafo);

        grafo.mostrarMatriz();

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            try {
                int opcion = Integer.parseInt(sn.nextLine());
                switch (opcion) {
                    case 1:
                        buscarRuta(sn);
                    case 2:
                        System.out.println("\nel centro del grafo es" + calculadora.calcularCentro(grafo));
                    case 3:
                        modificarGrafo(sn);
                    case 4:
                        System.out.println("saliendo");
                        salir = true;
                    default:
                        System.out.println("invalido");
                }
            } catch (NumberFormatException e) {
                System.out.println("ingrese un número valido");
            }
        }
    }

    private static void inicializarGrafo() {
        Set<String> ciudadesUnicas = new LinkedHashSet<>();
        List<String[]> conexiones = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(" ");
                if (partes.length == 3) {
                    ciudadesUnicas.add(partes[0]);
                    ciudadesUnicas.add(partes[1]);
                    conexiones.add(partes);
                }
            }
            
            grafo = new GrafoMatriz<>(new ArrayList<>(ciudadesUnicas));
            
            for (String[] con : conexiones) {
                grafo.addArco(con[0], con[1], Integer.parseInt(con[2]));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarMenu() {
        System.out.println("\ncentro de respuestas");
        System.out.println("1. Consultar ruta entre ciudades");
        System.out.println("2. Consultar centro del grafo");
        System.out.println("3. Modificar grafo");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void buscarRuta(Scanner sn) {
        System.out.print("ciudad de origen");
        String origen = sn.nextLine();
        System.out.print("ciudad de destino");
        String destino = sn.nextLine();
        
        try {
            System.out.println("\n" + calculadora.obtenerRuta(origen, destino, grafo));
        } catch (Exception e) {
            System.out.println("la ciudad no existe");
        }
    }

    private static void modificarGrafo(Scanner sn) {
        System.out.println("\nModificar Grafo");
        System.out.println("a. Hay interrupción de trafico entre dos ciudades");
        System.out.println("b. Establecer nueva conexión");
        String subOp = sn.nextLine().toLowerCase();

        System.out.print("Ciudad origen: ");
        String c1 = sn.nextLine();
        System.out.print("Ciudad destino: ");
        String c2 = sn.nextLine();

        if (subOp.equals("a")) {
            grafo.removeArco(c1, c2);
            System.out.println("Interrupcion registrada.");
        } else if (subOp.equals("b")) {
            System.out.print("Ingrese nueva distancia en km: ");
            int km = Integer.parseInt(sn.nextLine());
            grafo.addArco(c1, c2, km);
            System.out.println("conexion actualizada.");
        }
        calculadora.ejecutarFloyd(grafo);
        System.out.println("Rutas y centro recalculados exitosamente");
        grafo.mostrarMatriz();
    }
}