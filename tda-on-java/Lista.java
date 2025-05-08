package lineales.dinamicas;



public class Lista {

    private Nodo cabecera;

    //Constructor
    public Lista() {
        this.cabecera = null;
    }

    //Metodo Insertar
    public boolean insertar(Object nuevoElem, int pos) {
        //Insertar el elemento nuevo en la posicion pos
        //Detecta y reporta error posicion invalida
        boolean exito = true;
        if (pos < 1 || pos > longitud() + 1) {
            exito = false;
        } else {
            if (pos == 1) { //Crea un nuevo nodo y se enlaza en la cabecera
                this.cabecera = new Nodo(nuevoElem, this.cabecera);
            } else {
                //Avanza hasta el elemento en la posicion pos-1
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                // Crea el nodo y lo enlaza
                Nodo nuevo = new Nodo(nuevoElem, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
        }
        if (exito) {
            //longitud++;
        }
        //Nunca hay error de lista llena, entonces devuelve true
        return exito;
    }

    //Metodo Eliminar
    public boolean eliminar(int pos) {
        boolean exito = false;
        if (!esVacia() && (pos >= 1 && pos <= longitud())) {
            if (pos == 1) {
                //Tenemos en cuenta el caso especial
                //Se actualiza la cabacera para qu referencie al seundo Nodo
                this.cabecera = this.cabecera.getEnlace();
            } else {
                //Se ubica hasta el elemento en la posicion pos-1
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                //Eliminarmos, Se conecta el nodo referenciado por aux con el nodo una posición más allá del siguiente
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
            exito = true;
        }
        return exito;
    }

    //Metodo Recuperar
    public Object recuperar(int pos) {
        Object elem = null;
        if (pos >= 1 && pos <= this.longitud()) {
            Nodo aux = this.cabecera;
            int i = 1;
            while (i < pos) {
                aux = aux.getEnlace();
                i++;
            }
            elem = aux.getElem();
        }
        return elem;
    }

    //Metodo Localizar
    public int localizar(Object elem) {
        int pos = -1, posAux = 1;
        boolean encontrado = false;
        if (this.cabecera != null) {
            Nodo aux = this.cabecera;
            while (!encontrado && posAux <= longitud()) {
                if (aux.getElem().equals(elem)) {
                    pos = posAux;
                    encontrado = true;
                } else {
                    aux = aux.getEnlace();
                    posAux++;
                }
            }
        }
        return pos;
    }

    //Metodo Longitud
    public int longitud() {
        int longitud = 0;
        Nodo aux = this.cabecera;
        while (aux != null) {
            aux = aux.getEnlace();
            longitud = longitud + 1;
        }
        return longitud;
    }
    
    //Metodo esVacia
    public boolean esVacia() {
        return this.cabecera == null;
    }

    //Metodo Vaciar
    public void vaciar() {
        this.cabecera = null; //El recolector de basura se lleva todo.
    }

    //Metodo Clone
    public Lista clone() {
        Lista clonLista = new Lista();

        if (longitud() != 0) {
            Nodo aux1 = this.cabecera;
            //Creamos un nodo con el elemento de aux1 que apunte a null
            clonLista.cabecera = new Nodo(aux1.getElem(), null);
            Nodo aux2 = clonLista.cabecera;
            //Avanzamos
            aux1 = aux1.getEnlace();

            while (aux1 != null) {
                //Crea el nodo y lo enlaza 
                aux2.setEnlace(new Nodo(aux1.getElem(), null));
                //Moves aux2  y aux1 al mismo tiempo hasta que se termine la cola original
                aux2 = aux2.getEnlace();
                aux1 = aux1.getEnlace();
            }
        }
        return clonLista;
    }

    //Metodo to String
    public String toString() {
        String text = "";
        if (longitud() == 0) {
            text = "Lista Vacia";
        } else {
            Nodo aux = this.cabecera;
            while (aux != null) {
                text = text + aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null) {
                    text = text + ",";
                }
            }
            text = "[" + text + "]";
        }
        return text;
    }
    
 }
