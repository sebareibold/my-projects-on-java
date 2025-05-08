package jerarquicas;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;


public class ArbolBin {

    //Atributos
    private NodoArbol raiz;

    //Constructor
    public ArbolBin() {
        this.raiz = null;
    }
    
    //Metodo Insertar
    public boolean insertar(Object elemNuevo, Object elemPadre, char lugar) {
        boolean exito = true;

        if (this.raiz == null) {
            //Si el arbol esta vacio, pone el elem nuevo en la raiz
            this.raiz = new NodoArbol(elemNuevo, null, null);
        } else {
            //Si arbol no esta vacio, busca al padre
            NodoArbol nPadre = obtenerNodo(this.raiz, elemPadre);
            //Si padre existe y lugar no esta ocupado lo pone, sino da error
            if (nPadre != null) {
                if (lugar == 'I' && nPadre.getIzquierdo() == null) {
                    nPadre.setIzquierdo(new NodoArbol(elemNuevo, null, null));
                } else if (lugar == 'D' && nPadre.getDerecho() == null) {
                    nPadre.setDerecho(new NodoArbol(elemNuevo, null, null));
                } else {
                    exito = false;
                }
            }
        }
        return exito;
    }
    
    //Metodo Privado obtenerNodo
    private NodoArbol obtenerNodo(NodoArbol n, Object buscado) {
        //Metodo Privado que busca un elemento y devuele el nodo que
        //Lo contiene. si no se encuentra buscado devuelve null
        NodoArbol resultado = null;
        if (n != null) {
            if (n.getElem().equals(buscado)) {
                //Si el buscado es n, lo devuelve
                resultado = n;
            } else {
                //No es el buscado: busca en el HI
                resultado = obtenerNodo(n.getIzquierdo(), buscado);
                //Si no lo encontro en el HI, busca en HD
                if (resultado == null) {
                    resultado = obtenerNodo(n.getDerecho(), buscado);
                }
            }
        }
        return resultado;
    }
    
    //Metodo EsVacio()
    public boolean esVacio() {
        return this.raiz == null;
    }


    //Metodo Padre
    public Object padre(Object elemento) {
        return padreRec(elemento, this.raiz);
    }

    private Object padreRec(Object elemento, NodoArbol nodo) {
        Object padre = null;
        if (nodo != null) {
            if ((nodo.getIzquierdo() != null && nodo.getIzquierdo().getElem() == elemento) || (nodo.getDerecho() != null && nodo.getDerecho().getElem() == elemento)) {
                padre = nodo.getElem();
            } else {
                padre = padreRec(elemento, nodo.getDerecho());
                if (padre == null) {
                    padre = padreRec(elemento, nodo.getIzquierdo());
                }
            }
        }
        return padre;
    }
 
   
    //Metodo Altura
    public int altura() {
        return alturaRecursiva(this.raiz);
    }

    private int alturaRecursiva(NodoArbol nodo) {
        int retorno = -1;
        if (nodo != null) {
            if ((nodo.getDerecho() == null && nodo.getIzquierdo() == null)) {
                //Esto signfica que nodo es una hoja por lo que tenemos que retroceder
                retorno = 0;
            } else {
                retorno = Math.max(alturaRecursiva(nodo.getDerecho()), alturaRecursiva(nodo.getIzquierdo())) + 1;
            }
        }
        return retorno;
    }

    //Metodo Nivel()
    public int nivel(Object elemento) {
        return nivelRec(elemento, this.raiz, 0);
    }
    private int nivelRec(Object elemento, NodoArbol nodo, int nivelActual) {
        int nivel, nivelIzq;

        if (nodo == null) {
            nivel = -1;
        } else if (nodo.getElem().equals(elemento)) {
            nivel = nivelActual;
        } else {
            nivelIzq = nivelRec(elemento, nodo.getIzquierdo(), nivelActual + 1);
            if (nivelIzq > 0) {
                nivel = nivelIzq;
            } else {
                nivel = nivelRec(elemento, nodo.getDerecho(), nivelActual + 1);
            }
        }

        return nivel;
    }
    
    //Metodo Vaciar
    public void vaciar() {
        this.raiz = null;
    }
    
    //Metodo Clone
    public ArbolBin clone() {
        ArbolBin clon = new ArbolBin();
        NodoArbol nodoAux = new NodoArbol(this.raiz.getElem(), null, null);
        clon.raiz = nodoAux;
        cloneRec(clon.raiz, this.raiz);
        return clon;
    }
    private void cloneRec(NodoArbol nodoClon, NodoArbol nodoActual) {
        if (nodoActual != null) {
            if (nodoActual.getDerecho() != null) {
                NodoArbol hijoDerecho = new NodoArbol(nodoActual.getDerecho().getElem(), null, null);
                nodoClon.setDerecho(hijoDerecho);
                cloneRec(nodoClon.getDerecho(), nodoActual.getDerecho());
            }
            if (nodoActual.getIzquierdo() != null) {
                NodoArbol hijoIzquierdo = new NodoArbol(nodoActual.getIzquierdo().getElem(), null, null);
                nodoClon.setIzquierdo(hijoIzquierdo);
                cloneRec(nodoClon.getIzquierdo(), nodoActual.getIzquierdo());
            }
        }
    }
    
    //Metodo toString
    public String toString() {
        String salida;
        if (raiz == null) {
            salida = "Arbol Vacio";
        } else {
            salida = "Raiz: " + raiz.getElem().toString() + "\n";
            salida = salida + toStringAux(raiz);
        }
        return salida;
    }

    private String toStringAux(NodoArbol nodo) {
        String retorno;
        retorno = nodo.getElem().toString(); // comenzamos mostrando el elem del nodo
        if (nodo.getIzquierdo() != null) {
            retorno += " HI: " + nodo.getIzquierdo().getElem().toString(); // si no es nulo el nodoarbol izquierdo, concateno su elemento
        } else {
            retorno += " HI: - "; // sino muestro -
        }
        if (nodo.getDerecho() != null) {
            retorno += " HD: " + nodo.getDerecho().getElem().toString(); // lo mismo para el derecho
        } else {
            retorno += " HD: - ";
        }
        retorno = retorno + "\n"; // salto de linea para mostrar que ya termino el nodo correspondiente
        if (nodo.getIzquierdo() != null) {
            retorno = retorno + toStringAux(nodo.getIzquierdo()); // ahora le concateno lo que tiene su hijo izquierdo (Sino es nulo)
        }
        if (nodo.getDerecho() != null) {
            retorno = retorno + toStringAux(nodo.getDerecho()); // luego lo que hay en el derecho
        }
        return retorno;
    }
    
   //Listar Pre Orden
    public Lista listarPreorden() {
        //Retornar una lista con los elemenotos del arbol en PREORDEN
        Lista lis = new Lista();
        listarPreordenAux(this.raiz, lis);
        return lis;
    }
    private void listarPreordenAux(NodoArbol nodo, Lista lis) {
        //Metodo recursivo privado porque su parameto es de tipo NodoArbol
        if (nodo != null) {
            //Visita el elemento en el nodo
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
            //Recorre a sus hijos en pre orden
            listarPreordenAux(nodo.getIzquierdo(), lis);
            listarPreordenAux(nodo.getDerecho(), lis);
        }

    }
 
    //Listar In Orden
    public Lista listarInorden() {
        //Retornar una lista con los elemenotos del arbol en PREORDEN
        Lista lis = new Lista();
        listarInordenAux(this.raiz, lis);
        return lis;
    }
    private void listarInordenAux(NodoArbol nodo, Lista lis) {
        //Metodo recursivo privado porque su parameto es de tipo NodoArbol
        if (nodo != null) {
            //Recorre a sus hijos 
            listarInordenAux(nodo.getIzquierdo(), lis);
            //Visita el elemento en el nodo
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
            listarInordenAux(nodo.getDerecho(), lis);
        }
    }

    //Listar Pos Orden
    public Lista listarPosorden() {
        //Retornar una lista con los elemenotos del arbol en PREORDEN
        Lista lis = new Lista();
        listarPosordenAux(this.raiz, lis);
        return lis;
    }
    private void listarPosordenAux(NodoArbol nodo, Lista lis) {
        //Metodo recursivo privado porque su parameto es de tipo NodoArbol
        if (nodo != null) {
            //Recorre a sus hijos
            listarPosordenAux(nodo.getIzquierdo(), lis);
            listarPosordenAux(nodo.getDerecho(), lis);

            //Visita el elemento en el nodo
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
        }
    }
    
    //Metodo ListarNiveles
    public Lista listarPorNiveles() {
        Lista lis = new Lista();
        if (this.raiz != null) {
            //Creamos una cola
            Cola colAux = new Cola();

            //Añadis la raiz a la cola
            colAux.poner(this.raiz);

            while (!colAux.esVacia()) {
                NodoArbol nodoAux = (NodoArbol) colAux.obtenerFrente();
                lis.insertar(nodoAux.getElem(), lis.longitud() + 1);
                colAux.sacar();

                if (nodoAux.getIzquierdo() != null) {
                    colAux.poner(nodoAux.getIzquierdo());
                }
                if (nodoAux.getDerecho() != null) {
                    colAux.poner(nodoAux.getDerecho());
                }
            }
        }
        return lis;
    }
    
}
