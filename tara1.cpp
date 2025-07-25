#include <iostream>

using namespace std;

struct Nodo {
    int dato;
    Nodo* siguiente;

    Nodo(int valor) {
        dato = valor;
        siguiente = nullptr;
    }
};

class ListaEnlazada {
private:
    Nodo* cabeza;

public:
    ListaEnlazada() {
        cabeza = nullptr;
    }

    void insertar(int valor) {
        Nodo* nuevo = new Nodo(valor);
        nuevo->siguiente = cabeza;
        cabeza = nuevo;
        cout << "Nodo con valor " << valor << " insertado.\n";
    }

    void eliminar(int valor) {
        Nodo* actual = cabeza;
        Nodo* anterior = nullptr;

        while (actual != nullptr) {
            if (actual->dato == valor) {
                if (anterior == nullptr) {
                    cabeza = actual->siguiente;
                } else {
                    anterior->siguiente = actual->siguiente;
                }

                delete actual;
                cout << "Nodo con valor " << valor << " eliminado.\n";
                return;
            }

            anterior = actual;
            actual = actual->siguiente;
        }

        cout << "Nodo con valor " << valor << " no encontrado.\n";
    }

    void mostrar() {
        Nodo* actual = cabeza;
        cout << "Lista: ";
        while (actual != nullptr) {
            cout << actual->dato << " -> ";
            actual = actual->siguiente;
        }
        cout << "NULL\n";
    }

    ~ListaEnlazada() {
        Nodo* actual = cabeza;
        while (actual != nullptr) {
            Nodo* temp = actual;
            actual = actual->siguiente;
            delete temp;
        }
        cout << "Memoria liberada correctamente.\n";
    }
};

int main() {
    ListaEnlazada lista;

    lista.insertar(10);
    lista.insertar(20);
    lista.insertar(30);
    lista.mostrar();

    lista.eliminar(20);
    lista.mostrar();

    lista.eliminar(40);
    lista.mostrar();

    return 0;
}
