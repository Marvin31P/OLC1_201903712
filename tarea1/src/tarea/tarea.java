package tarea;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

// ---------------- CLASE USUARIO ----------------
class Usuario {
    int id;
    String nombre;
    String usuario;
    String password;
    String email;
    String telefono;

    ListaCorreos correos;

    public Usuario(int id, String nombre, String usuario, String password, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
        this.email = email;
        this.telefono = telefono;
        this.correos = new ListaCorreos();
    }
}

// ---------------- NODO USUARIO ----------------
class NodoUsuario {
    Usuario usuario;
    NodoUsuario siguiente;

    public NodoUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.siguiente = null;
    }
}

// ---------------- LISTA SIMPLE USUARIOS ----------------
class ListaUsuarios {
    NodoUsuario cabeza;

    public void insertar(Usuario usuario) {
        NodoUsuario nuevo = new NodoUsuario(usuario);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoUsuario temp = cabeza;
            while (temp.siguiente != null) temp = temp.siguiente;
            temp.siguiente = nuevo;
        }
    }

    public void cargarUsuarios(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line;
            int id = 0;
            String nombre = "", usuario = "", password = "", email = "", telefono = "";
            boolean dentroUsuario = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.equals("{")) dentroUsuario = true;
                else if (line.equals("},") || line.equals("}")) {
                    if (dentroUsuario) {
                        insertar(new Usuario(id, nombre, usuario, password, email, telefono));
                        dentroUsuario = false;
                    }
                } else if (line.startsWith("\"id\"")) {
                    id = Integer.parseInt(line.split(":")[1].trim().replace(",", ""));
                } else if (line.startsWith("\"nombre\"")) {
                    nombre = line.split(":")[1].trim().replace("\"", "").replace(",", "");
                } else if (line.startsWith("\"usuario\"")) {
                    usuario = line.split(":")[1].trim().replace("\"", "").replace(",", "");
                } else if (line.startsWith("\"password\"")) {
                    password = line.split(":")[1].trim().replace("\"", "").replace(",", "");
                } else if (line.startsWith("\"email\"")) {
                    email = line.split(":")[1].trim().replace("\"", "").replace(",", "");
                } else if (line.startsWith("\"telefono\"")) {
                    telefono = line.split(":")[1].trim().replace("\"", "").replace(",", "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String graficar() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph ListaUsuarios {\nrankdir=LR;\n");
        NodoUsuario temp = cabeza;
        while (temp != null) {
            sb.append("U" + temp.usuario.id + "[label=\"" + temp.usuario.nombre + "\\n" + temp.usuario.email + "\"];\n");
            if (temp.siguiente != null)
                sb.append("U" + temp.usuario.id + " -> U" + temp.siguiente.usuario.id + ";\n");
            temp = temp.siguiente;
        }
        sb.append("}\n");
        return sb.toString();
    }
}

// ---------------- CLASE CORREO ----------------
class Correo {
    int id;
    String remitente;
    String estado;
    String programado;
    String asunto;
    String fecha;
    String mensaje;

    public Correo(int id, String remitente, String estado, String programado, String asunto, String fecha, String mensaje) {
        this.id = id;
        this.remitente = remitente;
        this.estado = estado;
        this.programado = programado;
        this.asunto = asunto;
        this.fecha = fecha;
        this.mensaje = mensaje;
    }
}

// ---------------- NODO CORREO ----------------
class NodoCorreo {
    Correo correo;
    NodoCorreo anterior, siguiente;

    public NodoCorreo(Correo correo) {
        this.correo = correo;
    }
}

// ---------------- LISTA DOBLE CORREOS ----------------
class ListaCorreos {
    NodoCorreo cabeza, cola;

    public void insertar(Correo correo) {
        NodoCorreo nuevo = new NodoCorreo(correo);
        if (cabeza == null) cabeza = cola = nuevo;
        else {
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            cola = nuevo;
        }
    }

    public String graficar(int userId) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph ListaCorreos" + userId + " {\nrankdir=LR;\n");
        NodoCorreo temp = cabeza;
        while (temp != null) {
            sb.append("C" + userId + "_" + temp.correo.id + "[label=\"" + temp.correo.asunto + "\\n" + temp.correo.fecha + "\"];\n");
            if (temp.siguiente != null) {
                sb.append("C" + userId + "_" + temp.correo.id + " -> C" + userId + "_" + temp.siguiente.correo.id + ";\n");
                sb.append("C" + userId + "_" + temp.siguiente.correo.id + " -> C" + userId + "_" + temp.correo.id + ";\n");
            }
            temp = temp.siguiente;
        }
        sb.append("}\n");
        return sb.toString();
    }
}

// ---------------- CLASE GRAFICADOR ----------------
class Graficador {

    public static void guardarDot(String nombreArchivo, String contenido) {
        try (FileWriter fw = new FileWriter(nombreArchivo)) {
            fw.write(contenido);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generarImagen(String dotFile, String salidaImagen) {
        try {
            String cmd = "dot -Tpng " + dotFile + " -o " + salidaImagen;
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();

            File img = new File(salidaImagen);
            if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(img);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mostrarDotEnConsola(String contenido) {
        System.out.println(contenido);
    }
}

// ---------------- MAIN ----------------
public class tarea {

    public static void main(String args[]) {
        ListaUsuarios lista = new ListaUsuarios();
        lista.cargarUsuarios("usuarios.json");

        // Insertar correos manualmente
        if (lista.cabeza != null) {
            lista.cabeza.usuario.correos.insertar(new Correo(1, "remitente1@mail.com", "Leído", "No", "Asunto1", "2023-08-01", "Hola usuario1"));
            lista.cabeza.usuario.correos.insertar(new Correo(2, "remitente2@mail.com", "No leído", "Sí", "Asunto2", "2023-08-02", "Mensaje programado"));

            if (lista.cabeza.siguiente != null) {
                lista.cabeza.siguiente.usuario.correos.insertar(new Correo(1, "otro@mail.com", "No leído", "No", "AsuntoX", "2023-08-05", "Mensaje para usuario2"));
            }
        }

        // Graficar lista de usuarios
        String dotUsuarios = lista.graficar();
        Graficador.mostrarDotEnConsola(dotUsuarios);
        Graficador.guardarDot("Usuarios.dot", dotUsuarios);
        Graficador.generarImagen("Usuarios.dot", "Usuarios.png");

        // Graficar correos del primer usuario
        if (lista.cabeza != null) {
            String dotCorreos = lista.cabeza.usuario.correos.graficar(lista.cabeza.usuario.id);
            Graficador.mostrarDotEnConsola(dotCorreos);
            Graficador.guardarDot("Correos1.dot", dotCorreos);
            Graficador.generarImagen("Correos1.dot", "Correos1.png");
        }
    }
}
