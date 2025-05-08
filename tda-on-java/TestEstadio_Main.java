package TrabajoPracticoFinal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TestEstadio_Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nroestadio, eleccion, posNroIngresado;

        //Creamos el arreglo de estadios 
        estadio[] listEstadios = new estadio[100];

        //Cargamos el arreglo de estadios
        cargaDeArreglosEstadios(listEstadios, "src/TrabajoPracticoFinal/Lista_Estadios.txt");

        do {
            //Desplegamos el Menu
            menuDesplegable();

            //Ingresa la opcion eleguida
            eleccion = sc.nextInt();
            switch (eleccion) {
                case 1:
                    ordenaminetoPorInsercionAsendente(listEstadios);
                    break;
                case 2:
                    ordenaminetoPorInsercionDesendente(listEstadios);
                    break;
                case 3:
                    System.out.println("Ingrese el numero del estadio");
                    nroestadio = sc.nextInt();
                    posNroIngresado = posicionEstadio(nroestadio, listEstadios);
                    if (posNroIngresado >= 0) {
                        System.out.println("El nombre de estadio es: " + listEstadios[posNroIngresado].getNombre());
                        System.out.println("El nombre de estadio abreviado es: " + nombreSinVocales(listEstadios[posNroIngresado].getNombre()));
                    } else {
                        System.out.println("Ingreso un numero de estadio incorrecto");
                    }
                    break;
                case 4:
                    imprimirArr(listEstadios);
                    break;
                default:
                    if (eleccion >= 5) {
                        System.out.println("Finalizo el Programa");
                    } else {
                        System.out.println("Ingreso un numero fuera del rango de eleccion...")   ; 
                    }

            }
        } while (eleccion < 5);
    }

    //Actividad  2, Modulo encargado de cargar el arreglo
    public static void cargaDeArreglosEstadios(estadio[] list, String ubicacion) {
        String linea = null;
        String[] arr = new String[5];
        int pos = 0;
        try {
            FileReader lectorArchivo = new FileReader(ubicacion);
            BufferedReader bufferLectura = new BufferedReader(lectorArchivo);

            while ((linea = bufferLectura.readLine()) != null && pos< list.length) {
                System.out.println(linea);
                //Creamos un arreglo con las palabras de la linea. utilizando la funcion Split
                arr = linea.split("\\|");
                //Creamos el objeto
                list[pos] = new estadio(Integer.parseInt(arr[0]), arr[1], arr[2], Integer.parseInt(arr[3]), arr[4]);
                //Incrementamos pos
                pos++;
            }
            bufferLectura.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage() + "\nSignifica que el archivo del "
                    + "que queriamos leer no existe.");
        } catch (IOException ex) {
            System.err.println("Error leyendo o escribiendo en algun archivo.");
        }
        System.out.println("Carga finalizada.");
        System.out.println("  ");
    }

    //Actividad 3, Modulo Ordenamiento del arreglo de forma Ascendente
    public static void ordenaminetoPorInsercionAsendente(estadio[] list) {
        //Modulo ordenamiento por inserccion
        estadio temp;
        int j;
        for (int p = 1; p < list.length; p++) {
            temp = list[p];
            j = p;
            while (j > 0 && temp.comparateTo(list[j - 1]) < 0) {
                list[j] = list[j - 1];
                j--;
            }
            list[j] = temp;
        }
    }

    //Actividad 3, Modulo Ordenamiento del arreglo de forma Descendente
    public static void ordenaminetoPorInsercionDesendente(estadio[] list) {
        //Modulo ordenamiento por inserccion
        estadio temp;
        int j;
        for (int p = 1; p < list.length; p++) {
            temp = list[p];
            j = p;
            while (j > 0 && temp.comparateTo(list[j - 1]) > 0) {
                list[j] = list[j - 1];
                j--;
            }
            list[j] = temp;
        }
    }

    //Actividad 4, Modulo encargado de retornar el nombre sin vocales y con la primera letra mayuscula
    public static String nombreSinVocales(String text) {
        char c = text.charAt(0);
        String textRet = "";
        if (text.length() == 1) {
            //Caso Base
            if (!esVocal(c) && c != ' ') {
                textRet = textRet + c;
                textRet = textRet.toUpperCase();
            }
        } else {
            //Paso Recursivo
            if (!esVocal(c) && c != ' ') {
                textRet = textRet + c;
                textRet = textRet.toUpperCase() + nombreSinVocales(text.substring(1)).toLowerCase();
            } else {
                textRet = nombreSinVocales(text.substring(1));
            }
        }
        return textRet;
    }

    //Modulo Auxiliar esVocal
    private static boolean esVocal(char c) {
        return (c == 'a' | c == 'e' | c == 'i' | c == 'o' | c == 'u' | c == 'A' | c == 'E' | c == 'I' | c == 'O' | c == 'U');
    }

    //Modulo Auxiliar Imprimir los arreglos.
    public static void imprimirArr(estadio[] arr) {
        for (int pos = 0; pos < arr.length; pos++) {
            System.out.println(arr[pos].toString());
        }
    }

    //Modulo Auxiliar Posicion de estadio
    public static int posicionEstadio(int numero, estadio[] arr) {
        boolean encontrado = false;
        int posRetornar = -1, pos = 0;
        while (!encontrado && pos < arr.length) {
            if (numero == arr[pos].getNumero()) {
                encontrado = true;
                posRetornar = pos;
            }
            pos++;
        }
        return posRetornar;
    }

    //Modulo Auxiliar Menu.
    public static void menuDesplegable() {
        System.out.println("Ingrese el valor:");
        System.out.println(" - 1 Si desea ordenar el arreglo de forma Ascendente (Metodo Utilizado Inserccion)");
        System.out.println(" - 2 Si desea ordenar el arreglo de forma Descendente (Metodo Utilizado Inserccion)");
        System.out.println(" - 3 Si desea visualizar el nombre de un estadio de forma natural y de forma abreviada");
        System.out.println(" - 4 Si desea visualizar el arreglo");
        System.out.println(" - 5 o mas, Si desea terminar el algoritmo");

    }
}
