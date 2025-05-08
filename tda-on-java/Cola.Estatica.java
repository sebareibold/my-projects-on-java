package lineales.estaticas;



public class Cola {

    private Object[] arreglo;
    private int frente;
    private int fin;
    private static final int TAMANIO = 10;

    public Cola() {
        this.arreglo = new Object[this.TAMANIO];
        this.frente = 0;
        this.fin = 0;
    }

    public boolean sacar() {
        boolean exito = true;
        if (this.esVacia()) {
            exito = false;
        } else {
            this.arreglo[this.frente] = null; // Saco el elemento
            this.frente = (this.frente + 1) % TAMANIO;
            exito = true;
        }
        return exito;
    }

    public boolean poner(Object nuevoElemento) {
        boolean exito;
        if (!(frente == (fin+1)%TAMANIO)) {
            arreglo[fin] = nuevoElemento;
            fin = (fin + 1) % TAMANIO;
            exito = true;
        } else {
            // La cola esta llena
            exito = false;
        }
        return exito;
    }

    public boolean esVacia() {
        return (fin == frente);
    }

    public Object obtenerFrente() {
        Object elemento;
        if (this.esVacia()) {
            elemento = null;
        } else {
            elemento = arreglo[this.frente];
        }
        return elemento;
    }
    
    public void vaciar(){
        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] = null;
        }
        this.frente = 0;
        this.fin = 0;
    }
    
      public Cola clone() {
        Cola otraCola = new Cola();
        int auxFrente = frente;
        while (((fin + 1) % TAMANIO) == auxFrente) {
            otraCola.arreglo[auxFrente] = this.arreglo[auxFrente];
            auxFrente = (auxFrente + 1) % TAMANIO;
        }
        otraCola.fin = this.fin;
        otraCola.frente = this.frente;
        return otraCola;
    }
    
  public String toString() {
        int auxFrente = frente;
        String txt="";
        while (((fin + 1) % TAMANIO) == auxFrente) {
            txt = txt + this.arreglo[auxFrente];
            auxFrente = (auxFrente + 1) % TAMANIO;
        }
        return txt;
    }
}
