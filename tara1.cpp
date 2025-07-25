#include <iostream>

using namespace std;

// Estructura del nodo de la lista enlazada
struct Nodo {
    int dato;
    Nodo* siguiente;

    // Constructor que reserva memoria para un nuevo nodo
    Nodo(int valor) {
        dato = valor;
        siguiente = nullptr;
        // Aquí se asigna memoria dinámica al nodo con `new`
    }
};

class ListaEnlazada {
private:
    Nodo* cabeza;

public:
    // Constructor: inicializa la lista vacía
    ListaEnlazada() {
        cabeza = nullptr;
    }

    // Inserta un nodo al inicio de la lista
    void insertar(int valor) {
        // Se crea un nuevo nodo en memoria dinámica
        Nodo* nuevo = new Nodo(valor);

        // Se enlaza el nuevo nodo con la lista actual
        nuevo->siguiente = cabeza;
        cabeza = nuevo;

        cout << "Nodo con valor " << valor << " insertado.\n";

        // Aquí la memoria dinámica queda reservada hasta que se elimine el nodo
    }

    // Elimina el primer nodo que contiene el valor dado
    void eliminar(int valor) {
        Nodo* actual = cabeza;
        Nodo* anterior = nullptr;

        while (actual != nullptr) {
            if (actual->dato == valor) {
                if (anterior == nullptr) {
                    // Se elimina el nodo cabeza
                    cabeza = actual->siguiente;
                } else {
                    // Se salta el nodo actual en la lista
                    anterior->siguiente = actual->siguiente;
                }

                // Se libera la memoria dinámica del nodo eliminado
                delete actual;
                cout << "Nodo con valor " << valor << " eliminado.\n";
                return;
            }

            anterior = actual;
            actual = actual->siguiente;
        }

        cout << "Nodo con valor " << valor << " no encontrado.\n";
    }

    // Muestra el contenido de la lista
    void mostrar() {
        Nodo* actual = cabeza;
        cout << "Lista: ";
        while (actual != nullptr) {
            cout << actual->dato << " -> ";
            actual = actual->siguiente;
        }
        cout << "NULL\n";
    }

    // Destructor: libera toda la memoria dinámica usada por la lista
    ~ListaEnlazada() {
        Nodo* actual = cabeza;

        // Se recorre toda la lista y se libera cada nodo
        while (actual != nullptr) {
            Nodo* temp = actual;
            actual = actual->siguiente;

            // Se libera la memoria de cada nodo con `delete`
            delete temp;
        }

        cout << "Memoria liberada correctamente.\n";
    }
};

// Función principal
int main() {
    ListaEnlazada lista;

    // Se crean tres nodos (memoria dinámica)
    lista.insertar(10);
    lista.insertar(20);
    lista.insertar(30);

    lista.mostrar();

    // Se elimina un nodo (libera memoria de un nodo)
    lista.eliminar(20);
    lista.mostrar();

    // Intento de eliminar un nodo inexistente (no se libera nada)
    lista.eliminar(40);
    lista.mostrar();

    // Al finalizar el programa, el destructor libera el resto de la memoria
    return 0;
}
