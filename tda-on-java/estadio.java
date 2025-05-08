
package TrabajoPracticoFinal;

public class estadio {
   private int numero;
   private String nombre;
   private String ciudad;
   private int capacidad;
   private String mundialDisputado;
   
   
    //Constructor
    public estadio(int numero) {
        this.numero = numero;
    }
   
    public estadio(int numero, String nombre, String ciudad, int capacidad, String mundialDisputado) {
        this.numero = numero;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.mundialDisputado = mundialDisputado;
    }
   
    //Observadores
    public int getNumero() {
        return numero;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getMundialDisputado() {
        return mundialDisputado;
    }
    @Override
    public String toString() {
        return "estadio{" + "numero=" + numero + ", nombre=" + nombre + ", ciudad=" + ciudad + ", capacidad=" + capacidad + ", mundialDisputado=" + mundialDisputado + '}';
    }
    
 
    //Modificadores 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public void setMundialDisputado(String mundialDisputado) {
        this.mundialDisputado = mundialDisputado;
    }
    
    //Comparadores
    public boolean equals(estadio es) {
        return this.numero == es.getNumero();
    }
    
    public int comparateTo(estadio es){
        int valor = this.ciudad.compareTo(es.getCiudad());
        if(valor==0){
            valor = this.nombre.compareTo(es.getNombre()); 
        }
       return valor;
    }
    
}

