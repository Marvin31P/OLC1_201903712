package proyecto1;

import java.util.*;

public abstract class Automata {
    protected String nombre;
    protected String tipo;
    protected Set<String> estados;
    protected Set<String> alfabeto;
    protected String estadoInicial;
    protected Set<String> estadosAceptacion;

    public Automata(String nombre, String tipo, Set<String> estados, Set<String> alfabeto,
                    String estadoInicial, Set<String> estadosAceptacion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.estadoInicial = estadoInicial;
        this.estadosAceptacion = estadosAceptacion;
    }

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }

    public abstract boolean validarCadena(String cadena);

    public void mostrarDescripcion() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Tipo: " + tipo);
        System.out.println("Estados: " + estados);
        System.out.println("Alfabeto: " + alfabeto);
        System.out.println("Estado Inicial: " + estadoInicial);
        System.out.println("Estados de Aceptación: " + estadosAceptacion);
    }
}
