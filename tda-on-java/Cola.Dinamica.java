package lineales.dinamicas;


public class Cola {
    
    //Atributos
    private Nodo frente;
    private Nodo fin;
    
    //Constructor
    public Cola() {
        this.frente = null;
        this.fin = null;
    }
    
    //Metodo Poner
    public boolean poner(Object elemento) {
        Nodo nuevo = new Nodo(elemento, null);
        if (this.fin == null) {
            this.fin = nuevo;
        } else {
            this.fin.setEnlace(nuevo);
            this.fin = nuevo;
        }
        if (this.frente == null) {
            this.frente = nuevo;
        }
        return true;
    }
    
    //Metodo Sacar
    public boolean sacar() {
        boolean exito = true;
        if (this.frente == null) {
            exito = false;
        } else {
            this.frente = this.frente.getEnlace(); // Si es el ultimo elemento, apunta al null.
            if (this.frente == null) {
                this.fin = null; // Para que fin no quede apuntando al nodo viejo, lo fuerzo a ir a null.
            }
        }
        return exito;
    }
    
    //Metodo obtenerFrente
    public Object obtenerFrente() {
        Object salida;
        if (frente == null) {
            salida = null;
        } else {
            salida = frente.getElem();
        }
        return salida;
    }
    
    //Metodo esVacia
    public boolean esVacia() {
        return (frente == null);
    }
    
    //Metodo vaciar
    public void vaciar() {
        this.fin = null;
        this.frente = null;
    }
    
    //Metodo clonar
    public Cola clone() {
        Cola colaClon = new Cola();
        Nodo aux1 = this.frente;
        colaClon.frente = new Nodo(aux1.getElem(), null);
        aux1 = aux1.getEnlace();
        Nodo aux2 = colaClon.frente;
        while (aux1 != null) {
            aux2.setEnlace(new Nodo(aux1.getElem(), null));
            aux2 = aux2.getEnlace();
            aux1 = aux1.getEnlace();
        }
        colaClon.fin = aux2;
        return colaClon;
    }
    
    //Metodo toString
    public String toString() {
        String salida = "";
        if (frente == null) {
            salida = "Cola Vacia";
        } else {
            Nodo aux = this.frente;
            salida = "[";
            while (aux != null) {
                salida = salida + aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux!=null) {
                    salida = salida + ",";
                }
            }
            salida = salida + "]";
        }
        return salida;
    }
}
