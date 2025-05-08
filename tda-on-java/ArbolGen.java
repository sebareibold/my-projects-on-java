package jerarquicas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;


public class ArbolGen {

    private NodoGen raiz;

    // Constructor
    public ArbolGen() {
        raiz = null;
    }

    // Metodo Insertar
    /* Inserta elemNuevo en el arbol dado su nodoPadre (si nodoPadre es distinto de null) */
    public boolean insertar(Object elemNuevo, Object elemPadre) {
        boolean exito = false;
        if (raiz == null) {
            raiz = new NodoGen(elemNuevo, null, null);
            exito = true;
        } else {
            NodoGen nodoPadre = obtenerNodo(raiz, elemPadre);
            if (nodoPadre != null) {
                // Pongo al nuevo nodo como HEI del padre, y corro los demas hacia la derecha
                NodoGen nuevoNodo = new NodoGen(elemNuevo, null, nodoPadre.getHijoIzquierdo());
                nodoPadre.setHijoIzquierdo(nuevoNodo);
                exito = true;
            }

        }
        return exito;
    }

    //Metodo Privado ObtenerNodo
    private NodoGen obtenerNodo(NodoGen n, Object buscado) {
        // busca un elemento y devuelve el nodo que lo contiene, sino encuentra retorna null
        NodoGen resultado = null;
        if (n != null) {
            if (n.getElem().equals(buscado)) {
                resultado = n; // Caso Base: este Nodo tiene el elemBuscado
            } else {
                // si tiene hermano derechos, busco en cada uno hasta encontrarlo
                if (n.getHermanoDerecho() != null) {
                    NodoGen nodoAux = n.getHermanoDerecho();
                    while (nodoAux != null && resultado == null) {
                        resultado = obtenerNodo(nodoAux, buscado);
                        nodoAux = nodoAux.getHermanoDerecho();
                    }
                }
                // sino esta en los derecho, llamo con el hijo extremo izquierdo
                if (resultado == null) {
                    resultado = obtenerNodo(n.getHijoIzquierdo(), buscado);
                }
            }
        }
        return resultado;
    }

    //Metodo Pertenece
    public boolean pertenece(Object elemento) {
        return (obtenerNodo(this.raiz, elemento) != null);
    }

    //Metodo es Vacio
    public boolean esVacio() {
        return (raiz == null);
    }

    public void vaciar() {
        raiz = null;
    }

    //Listados
    public Lista listarPreorden() {
        Lista lis = new Lista();
        if (raiz != null) {
            listarPreOrdenAux(raiz, lis);
        }
        return lis;
    }

    private void listarPreOrdenAux(NodoGen nodo, Lista lis) {
        if (nodo != null) {
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
            listarPreOrdenAux(nodo.getHijoIzquierdo(), lis);
            NodoGen hermano = nodo.getHijoIzquierdo();
            if (hermano != null) {
                hermano = hermano.getHermanoDerecho();
                while (hermano != null) {
                    listarPreOrdenAux(hermano, lis);
                    hermano = hermano.getHermanoDerecho();
                }
            }
        }
    }

    public Lista listarInorden() {
        Lista lis = new Lista();
        if (raiz != null) {
            listarInOrdenAux(raiz, lis);
        }
        return lis;
    }

    private void listarInOrdenAux(NodoGen nodo, Lista lis) {
        if (nodo != null) {
            listarInOrdenAux(nodo.getHijoIzquierdo(), lis);
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
            NodoGen hermano = nodo.getHijoIzquierdo();
            if (hermano != null) {
                hermano = hermano.getHermanoDerecho();
                while (hermano != null) {
                    listarInOrdenAux(hermano, lis);
                    hermano = hermano.getHermanoDerecho();
                }
            }
        }
    }

    public Lista listarPosorden() {
        Lista lis = new Lista();
        if (raiz != null) {
            listarPosOrdenAux(raiz, lis);
        }
        return lis;
    }

    private void listarPosOrdenAux(NodoGen nodo, Lista lis) {
        if (nodo != null) {
            listarPosOrdenAux(nodo.getHijoIzquierdo(), lis);
            NodoGen hermano = nodo.getHijoIzquierdo();
            if (hermano != null) {
                hermano = hermano.getHermanoDerecho();
                while (hermano != null) {
                    listarPosOrdenAux(hermano, lis);
                    hermano = hermano.getHermanoDerecho();
                }
            }
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
        }
    }

    public Lista listarPorNiveles() {
        Cola q = new Cola();
        Lista lis = new Lista();
        if (this.raiz != null) {
            q.poner(raiz);
            while (!q.esVacia()) {
                NodoGen nodo = (NodoGen) q.obtenerFrente();
                q.sacar();
                lis.insertar(nodo.getElem(), lis.longitud() + 1);
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null) {
                    q.poner(hijo);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return lis;
    }

    //Metodo Altura
    public int altura() {
        int altura = -1;
        if (raiz != null) {
            altura = alturaAux(raiz);
        }
        return altura;
    }

    private int alturaAux(NodoGen nodo) {
        int alturaRetorno = 0;
        if (nodo != null) {
            if (nodo.getHijoIzquierdo() != null) {
                alturaRetorno = 1 + alturaAux(nodo.getHijoIzquierdo());
            }
            if (nodo.getHermanoDerecho() != null) {
                int alturaHermanoDerecho = alturaAux(nodo.getHermanoDerecho());
                if (alturaHermanoDerecho > alturaRetorno) {
                    alturaRetorno = alturaHermanoDerecho;
                }
            }
        }
        return alturaRetorno;
    }

    public boolean esHoja(NodoGen nodo) {
        return nodo.getHijoIzquierdo() == null;
    }

    @Override
    public String toString() {
        String retorno = "Arbol Vacio";
        if (raiz != null) {
            retorno = toStringAux(raiz);
        }
        return retorno;
    }

    private String toStringAux(NodoGen nodo) {
        String salida = "";
        if (nodo != null) {
            salida += nodo.getElem().toString() + "->";
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                salida += hijo.getElem().toString();
                hijo = hijo.getHermanoDerecho();
                if (hijo != null) {
                    salida += ", ";
                }
            }
            hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                salida += "\n" + toStringAux(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        }
        return salida;
    }

    //Metodo Padre
    public Object padre(Object elem) {
        Object retorno = null;
        if (raiz != null) {
            if (raiz.getElem().equals(elem)) {
                retorno = null;
            } else {
                NodoGen nodoPadre = padreAux(raiz, elem);
                if (nodoPadre != null) {
                    retorno = nodoPadre.getElem(); // saco el elemento que esta en en el nodo padre
                }
            }

        }
        return retorno;
    }

    private NodoGen padreAux(NodoGen nodo, Object buscado) {
        // retorna el nodo padre del elemento buscado
        // busca el nodo primero en todos los hijos que tenga
        // si no lo encuentra, va hijo por hijo haciendo su llamado recursivo
        NodoGen retornoPadre = null;

        if (nodo != null) {
            NodoGen hijo = nodo.getHijoIzquierdo();
            boolean encontrado = false;
            while (!encontrado && hijo != null) {
                if (hijo.getElem().equals(buscado)) {
                    encontrado = true;
                    retornoPadre = nodo;
                } else {
                    hijo = hijo.getHermanoDerecho();
                }
            }

            if (!encontrado) {
                hijo = nodo.getHijoIzquierdo();
                // si lo encuentra retornoPadre no seria null xlotanto terminaria el recorrido
                while (hijo != null && retornoPadre == null) {
                    retornoPadre = padreAux(hijo, buscado);
                    hijo = hijo.getHermanoDerecho();
                }
            }

        }
        return retornoPadre;
    }

    //Metodo Grado
    public int grado() {
        int grado = -1;
        if (raiz != null) {
            grado = gradoAux(raiz);
        }
        return grado;
    }

    private int gradoAux(NodoGen nodo) {
        int mayorGrado = 0;
        int esteGrado = 0;
        if (nodo != null) {
            NodoGen esteHijo = nodo.getHijoIzquierdo();
            while (esteHijo != null) {
                esteGrado = esteGrado + 1;
                esteHijo = esteHijo.getHermanoDerecho();
            }
            mayorGrado = esteGrado;
            esteHijo = nodo.getHijoIzquierdo();
            while (esteHijo != null) {
                int aux1 = gradoAux(esteHijo);
                if (aux1 > mayorGrado) {
                    mayorGrado = aux1;
                }
                esteHijo = esteHijo.getHermanoDerecho();
            }
        }
        return mayorGrado;
    }

    public int gradoSubarbol(Object elem) {
        NodoGen nodo = obtenerNodo(raiz, elem);
        int gradoSub = -1;
        if (nodo != null) {
            gradoSub = gradoAux(nodo);
        }
        return gradoSub;
    }

    public int nivel(Object elem) {
        int nivelRetorno = -1;
        if (raiz != null) {
            nivelRetorno = nivelAux(raiz, elem, 0);
        }
        return nivelRetorno;
    }

    private int nivelAux(NodoGen nodo, Object elem, int nivel) {
        // funciona similar al metodo padre
        int retornoNivel = -1;
        // caso base implicito 1: nodo null (retorna -1)
        if (nodo != null) {
            if (nodo.getElem().equals(elem)) {
                retornoNivel = nivel; // caso base 2 explicito: si el elemento es este, retorno este nivel
            } else {
                // sino busco en cada hijo que este nodo tenga
                NodoGen hijo = nodo.getHijoIzquierdo();
                boolean encontrado = false;
                while (!encontrado && hijo != null) {
                    retornoNivel = nivelAux(hijo, elem, nivel + 1); // paso recursivo con dicho hijo, ahora el nivel aumenta en 1 ya que esta debajo
                    if (retornoNivel != -1) {
                        encontrado = true;
                    } else {
                        hijo = hijo.getHermanoDerecho(); // busco en el siguiente hijo
                    }
                }
            }
        }
        return retornoNivel;
    }

    public Lista ancestros(Object elem) {
        Lista ancestros = new Lista();
        if (raiz != null) {
            ancestrosAux(raiz, elem, ancestros);
        }
        return ancestros;
    }

    private boolean ancestrosAux(NodoGen nodo, Object elem, Lista anc) {
        boolean retorno = false;
        // caso base implicito: nodo null, retorna false
        if (nodo != null) {
            if (elem.equals(nodo.getElem())) {
                retorno = true; // caso base explicito: este elemento es igual al elem (retorno true) (no lo inserto en la lista)
            } else {
                // sino busco en sus hijos
                NodoGen hijo = nodo.getHijoIzquierdo();
                boolean encontrado = false;
                // si alguno de sus hijos/nietos/etc.. (recursivamente) es el elem buscado, a este elem lo inserto
                // si lo encontre en algun hijo, no sigo recorriendo los otros
                while (hijo != null && !encontrado) {
                    // llamado recursivo (con hijo, mismo elem, misma lista)
                    // si es ancestro, a este lo inserto
                    // retorno es true (para el llamado de mas arriba, sino unicamente inserto al padre de elem)
                    if (ancestrosAux(hijo, elem, anc)) {
                        anc.insertar(nodo.getElem(), anc.longitud() + 1);
                        retorno = true;
                        encontrado = true;
                    } else {
                        // sino busco en el siguiente hijo(o hermano)
                        hijo = hijo.getHermanoDerecho();
                    }
                }
            }
        }
        return retorno;
    }

    public ArbolGen clone() {
        ArbolGen arbolClon = new ArbolGen();
        arbolClon.raiz = cloneAux(this.raiz);
        return arbolClon;
    }

    private NodoGen cloneAux(NodoGen nodo) {
        NodoGen nodoClon = null;
        if (nodo != null) {
            nodoClon = new NodoGen(nodo.getElem(), null, null);
            NodoGen hijo = nodo.getHijoIzquierdo();
            if (hijo != null) {
                nodoClon.setHijoIzquierdo(cloneAux(hijo));
                NodoGen hermanoCopia = nodoClon.getHijoIzquierdo();
                NodoGen hermanoOriginal = hijo.getHermanoDerecho();
                NodoGen ultimoHermanoCopia = hermanoCopia;
                while (hermanoOriginal != null) {
                    NodoGen hermanoClon = cloneAux(hermanoOriginal);
                    ultimoHermanoCopia.setHermanoDerecho(hermanoClon);
                    ultimoHermanoCopia = hermanoClon;
                    hermanoOriginal = hermanoOriginal.getHermanoDerecho();
                }
            }
        }
        return nodoClon;
    }

    //Metodo Equals
    public boolean equals(ArbolGen otroArbol) {
        return equalsAux(raiz, otroArbol.raiz);
    }

    private boolean equalsAux(NodoGen esteNodo, NodoGen otroNodo) {
        boolean retorno = true;
        if (esteNodo != null && otroNodo != null) {
            if (!esteNodo.getElem().equals(otroNodo.getElem())) {
                retorno = false;
            }
            if (retorno != false) {
                NodoGen esteHijo = esteNodo.getHijoIzquierdo();
                NodoGen otroHijo = otroNodo.getHijoIzquierdo();
                while (retorno && (esteHijo != null && otroHijo != null)) {
                    retorno = equalsAux(esteHijo, otroHijo);
                    esteHijo = esteHijo.getHermanoDerecho();
                    otroHijo = otroHijo.getHermanoDerecho();
                }
                if (retorno) {
                    retorno = (esteHijo == null && otroHijo == null);
                }
            }
        } else if (esteNodo == null && otroNodo != null) {
            retorno = false;
        } else if (esteNodo != null && otroNodo == null) {
            retorno = false;
        }
        return retorno;
    }

    //Metodo sonFrontera()
    public boolean sonFrontera(Lista unaLista) {
        //Aclaracion: En el caso especial de que el arbol sea vacio se retornara FALSO
        boolean sonFrontera = false;
        if (this.raiz != null) {
            sonFrontera = esFrontera(this.raiz, unaLista);
        }
        return sonFrontera;
    }

    private boolean esFrontera(NodoGen nodo, Lista unaLista) {
        boolean esFrontera = true;
        if (nodo != null) {
            if (nodo.getHijoIzquierdo() == null) {
                //En este caso es Frontere entonces lo compara con los elems de la lista.
                esFrontera = unaLista.localizar(nodo.getElem()) >= 0;
            } else {
                //En este caso no es Frontera y procede hacer el llamado recursivo con sus hijos.
                esFrontera = esFrontera(nodo.getHijoIzquierdo(), unaLista);
                //Si  es frontera procedemos a ver las otras hojas, en caso de no serla retorna falso.
                if (esFrontera) {
                    NodoGen aux = nodo.getHijoIzquierdo();
                    while (aux != null && esFrontera) {
                        esFrontera = esFrontera(aux, unaLista);
                        aux = aux.getHermanoDerecho();
                    }
                }
            }
        }
        return esFrontera;
    }
}
