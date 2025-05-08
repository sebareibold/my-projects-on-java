package jerarquicas;



public class NodoGen {
    
    private Object elemento;
    private NodoGen hijoIzquierdo;
    private NodoGen hermanoDerecho;
    
    public NodoGen(Object elemento, NodoGen hijoIzquierdo, NodoGen hermanoDerecho){
        this.elemento = elemento;
        this.hijoIzquierdo = hijoIzquierdo;
        this.hermanoDerecho = hermanoDerecho;
    }

    //Observadores    
    public Object getElem(){
        return elemento;
    }
    public NodoGen getHijoIzquierdo(){
        return hijoIzquierdo;
    }
    public NodoGen getHermanoDerecho(){
        return hermanoDerecho;
    }
	
    //Modificadores
    public void setElem(Object elemento){
        this.elemento = elemento;
    }
    public void setHijoIzquierdo(NodoGen hijoIzquierdo){
        this.hijoIzquierdo = hijoIzquierdo;
    }
    public void setHermanoDerecho(NodoGen hermanoDerecho){
        this.hermanoDerecho = hermanoDerecho;
    }
}
