package proyecto1;

import java.util.*;

// Clase principal que administra los autómatas
public class Proyecto {

    // Diccionario de autómatas (nombre -> objeto)
    private static Map<String, Automata> automatas = new HashMap<>();

    

    // 4.1 Listar autómatas
    public static void verAutomatas() {
        for (Automata a : automatas.values()) {
            System.out.println(a.getNombre() + " " + a.getTipo());
        }
    }

    // 4.2 Descripción de autómata
    public static void desc(String nombre) {
        Automata a = automatas.get(nombre);
        if (a == null) {
            System.out.println("No existe el autómata: " + nombre);
            return;
        }
        a.mostrarDescripcion();
    }

   

    
    public static void main(String[] args) {
       

        

        
        System.out.println("=== LISTA DE AUTÓMATAS ===");
        verAutomatas();

        System.out.println("\n=== DESCRIPCIÓN AFD_1 ===");
        desc("AFD_1");

        
    }
}
