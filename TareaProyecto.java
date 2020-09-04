package uagrm.edu.ficct.tareaed2sb;

import arboles.IArbolBusqueda;
import arboles.ArbolBinarioBusqueda;
import arboles.ExcepcionClaveNoExiste;
import arboles.ExcepcionClaveYaExiste;
import arboles.ArbolAVL;
import java.util.LinkedList;
import java.util.List;

public class TareaProyecto {

    public static void main(String[] args)
            throws ExcepcionClaveYaExiste, ExcepcionClaveNoExiste {
        IArbolBusqueda<Integer, String> arbol = null;
        char tipoArbol;
        System.out.println("Elija el tipo de arbol con que desea trabajar:");
        System.out.println("B : Arbol Binario\nA : Arbol AVL");
        System.out.println("Tipo de Arbol: ");
        tipoArbol = (char) System.in.read();
        switch (tipoArbol) {
            case 'B':
            case 'b':
                arbol = new ArbolBinarioBusqueda<>();
                break;
            case 'A':
            case 'a':
                arbol = new ArbolAVL<>();
                break;
            default:
                System.err.print("Tipo Incorrecto");
                break;
        }
        System.out.println("---------------------");
        System.out.println("Arbol Inicial");
        arbol.insertar(54, "Christian");
        arbol.insertar(65, "Liz");
        arbol.insertar(5, "Julio");
        arbol.insertar(19, "Dilker");
        arbol.insertar(82, "Gabriel");
        arbol.insertar(18, "Luis");
        arbol.insertar(72, "Alejandro");
        arbol.insertar(32, "Carlos");
        arbol.insertar(2, "Martha");
        arbol.insertar(30, "Belen");

        //System.out.println("");
        System.out.println("Prueba de los ejercicios");
        arbol.mostrarArbol();
        System.out.println("----------------------");
        //-------------------------------------------------------------------------
        System.out.println("Ejercicio 1: Cantidad de Nodos Hojas (Recursivo)");
        System.out.println(((ArbolBinarioBusqueda) arbol).cantidadDeNodosHojas());
        //-------------------------------------------------------------------------
        System.out.println("Ejercicio 2: Cantidad de Nodos Hojas (Iterativo)");
        System.out.println(((ArbolBinarioBusqueda) arbol).cantidadDeNodosHojasIterativo());
        //-------------------------------------------------------------------------
        System.out.println("Ejercicio 3: Cantidad de Nodos Hojas Solo en Nivel N (Recursivo/Nivel 2)");
        System.out.println(((ArbolBinarioBusqueda) arbol).cantidadDeNodosHojasEnNivelN(2));
        //-------------------------------------------------------------------------
        System.out.println("Ejercicio 4: Cantidad de Nodos Hojas Solo en Nivel N (Iterativo/Nivel 3)");
        System.out.println(((ArbolBinarioBusqueda) arbol).cantidadDeNodosHojasEnNivelNIterativo(3));
        //-------------------------------------------------------------------------
        System.out.println("Ejercicio 5: Cantidad de Nodos Hojas Antes del Nivel N (Nivel 3)");
        System.out.println(((ArbolBinarioBusqueda) arbol).cantidadDeNodosHojasEnNivelNIterativo(3));
        //-------------------------------------------------------------------------
        System.out.println("Ejercicio 6: ¿Arbol Balanceado? (Revursivo)");
        if (arbol instanceof ArbolAVL) {
            System.out.println("Es un Arbol AVL");
        } else {
            System.out.println(((ArbolBinarioBusqueda) arbol).arbolBalanceado());
        }
        //-------------------------------------------------------------------------
        System.out.println("Ejercicio 7: ¿Arbol Balanceado? (Iterativo)");
        if (arbol instanceof ArbolAVL) {
            System.out.println("Es un Arbol AVL");
        } else {
            System.out.println(((ArbolBinarioBusqueda) arbol).arbolBalanceadoIterativo());
        }
        //-------------------------------------------------------------------------
        System.out.println("Ejercicio 8: Reconstruir Arbol Binario");

        //P: [54, 5, 2, 19, 18, 32, 65, 82, 72]
        List<Integer> listaDeClavesPreOrden = new LinkedList<>();
        listaDeClavesPreOrden.add(54);
        listaDeClavesPreOrden.add(5);
        listaDeClavesPreOrden.add(2);
        listaDeClavesPreOrden.add(19);
        listaDeClavesPreOrden.add(18);
        listaDeClavesPreOrden.add(32);
        listaDeClavesPreOrden.add(65);
        listaDeClavesPreOrden.add(82);
        listaDeClavesPreOrden.add(72);

        List<String> listaDeValorPreOrden = new LinkedList<>();
        listaDeValorPreOrden.add("Christian");
        listaDeValorPreOrden.add("Julio");
        listaDeValorPreOrden.add("Martha");
        listaDeValorPreOrden.add("Dilker");
        listaDeValorPreOrden.add("Luis");
        listaDeValorPreOrden.add("Carlos");
        listaDeValorPreOrden.add("Liz");
        listaDeValorPreOrden.add("Gabriel");
        listaDeValorPreOrden.add("Alejandro");

        //I: [2, 5, 18, 19, 32, 54, 65, 72, 82]         
        List<Integer> listaDeClavesInOrden = new LinkedList<>();
        listaDeClavesInOrden.add(2);
        listaDeClavesInOrden.add(5);
        listaDeClavesInOrden.add(18);
        listaDeClavesInOrden.add(19);
        listaDeClavesInOrden.add(32);
        listaDeClavesInOrden.add(54);
        listaDeClavesInOrden.add(65);
        listaDeClavesInOrden.add(72);
        listaDeClavesInOrden.add(82);

        List<String> listaDeValorInOrden = new LinkedList<>();
        listaDeValorInOrden.add("Martha");
        listaDeValorInOrden.add("Julio");
        listaDeValorInOrden.add("Luis");
        listaDeValorInOrden.add("Dilker");
        listaDeValorInOrden.add("Carlos");
        listaDeValorInOrden.add("Christian");
        listaDeValorInOrden.add("Liz");
        listaDeValorInOrden.add("Alejandro");
        listaDeValorInOrden.add("Gabriel");

        System.out.println("Lista de Clave en In Orden: " + listaDeClavesInOrden);
        System.out.println("Lista de Valor en In Orden: " + listaDeValorInOrden);
        System.out.println("Lista de Clave en Pre Orden: " + listaDeClavesPreOrden);
        System.out.println("Lista de Valor en Pre Orden: " + listaDeValorPreOrden);

        System.out.println("Arbol Reconstruido:");
        ArbolBinarioBusqueda<Integer, String> arbol2 = new ArbolBinarioBusqueda<>();
        arbol2.reconstruirEnPreOrden(listaDeClavesInOrden, listaDeValorInOrden,
                listaDeClavesPreOrden, listaDeValorPreOrden);
        arbol2.mostrarArbol();
        //-------------------------------------------------------------------------
        System.out.println("Ejercicio 9: Sucesor In Orden (Sucesor In Orden de 54)");
        System.out.println(((ArbolBinarioBusqueda) arbol).sucesorInOrden(54));
        //-------------------------------------------------------------------------
        System.out.println("Ejercicio 10: Predecesor In Orden (Predecesor de 54)");
        System.out.println(((ArbolBinarioBusqueda) arbol).predecesorInOrden(54));
        //-------------------------------------------------------------------------
        System.out.println("Ejercicio 11: Eliminar(54) (Arbol AVL)");
        if (arbol instanceof ArbolAVL) {
            ((ArbolAVL) arbol).eliminar(54);

        } else {
            System.out.println("No es un arbol AVL");
        }
        //-------------------------------------------------------------------------
        System.out.println("Ejercicio 12: Cantidad nodos con ambos hijos luego de nivel (Nivel 1)");
        System.out.println(((ArbolBinarioBusqueda) arbol).cantidadNodosConAmbosHijosEnNivelN(1));
        //-------------------------------------------------------------------------
        System.out.println("Prueba Finalizada");

        /*System.out.println("-----------------------------------------------");
        int n = 0;
        arbol = null;    
        int nivel = 0;
        int clave = 0;
        String valor = "";
        while (n < 14) {
            System.out.println("---------Menu-----------");
            System.out.println("0.- Insertar Nodo");
            System.out.println("1.- Cantidad de Nodos Hojas (Recursivo)");
            System.out.println("2.- Cantidad de Nodos Hojas (Iterativo)");
            System.out.println("3.- Cantidad de Nodos Hojas en de Nivel N (Recursivo)");
            System.out.println("4.- Cantidad de Nodos Hojas en Nivel N (Iterativo)");
            System.out.println("5.- Cantidad de Nodos Hojas Antes de Nivel N");
            System.out.println("6.- ¿Arbol Balanceado? (Iterativo)");
            System.out.println("7.- ¿Arbol Balanceado? (Recursivo)");
            System.out.println("8.- Reconstruir Arbol");
            System.out.println("9.- Sucesor In Orden");
            System.out.println("10.- Predecesor In Orden");
            System.out.println("11.- Eliminar (AVL)");
            System.out.println("12.- Cantidad de Nodos con Ambos hijos luego de Nivel N (Iterativo)");
            System.out.println("13.- Mostrar Arbol");
            System.out.println("14.- Salir");
            System.out.println("Opcion: ");
            n = System.in.read();

            if (n == 0) {
                System.out.println("Clave (Integer): ");
                clave = (Integer) System.in.read();
                System.out.println("Clave (String): ");
                valor = System.in.toString();
                arbol.insertar(clave, valor);
            }
            if (n == 1) {
                System.out.println(((ArbolBinarioBusqueda) arbol).cantidadDeNodosHojas());
            }
            if (n == 2) {
                System.out.println(((ArbolBinarioBusqueda) arbol).cantidadDeNodosHojasIterativo());
            }
            if (n == 3) {
                System.out.println("Nivel: ");
                nivel = (Integer) System.in.read();
                System.out.println(((ArbolBinarioBusqueda) arbol).cantidadDeNodosHojasEnNivelN(nivel));
            }
            if (n == 4) {
                System.out.println("Nivel: ");
                nivel = (Integer) System.in.read();
                System.out.println(((ArbolBinarioBusqueda) arbol).cantidadDeNodosHojasEnNivelNIterativo(nivel));
            }
            if (n == 5) {
                System.out.println("Nivel: ");
                nivel = (Integer) System.in.read();
                System.out.println(((ArbolBinarioBusqueda) arbol).cantidadDeNodosHojasAntesDeNivelN(nivel));
            }
            if (n == 6) {
                if (arbol instanceof ArbolAVL) {
                    System.out.println("Es un Arbol AVL");
                } else {
                    System.out.println(((ArbolBinarioBusqueda) arbol).arbolBalanceado());
                }
            }
            if (n == 7) {
                if (arbol instanceof ArbolAVL) {
                    System.out.println("Es un Arbol AVL");
                } else {
                    System.out.println(((ArbolBinarioBusqueda) arbol).arbolBalanceadoIterativo());
                }
            }
            if (n == 8) {
                System.out.println("Cantidad de claves a introducir: ");
                int nL = (Integer) System.in.read();
                listaDeClavesPreOrden = new LinkedList<>();
                List<String> listaDeValoresPreOrden = new LinkedList<>();
                listaDeClavesInOrden = new LinkedList<>();
                List<String> listaDeValoresInOrden = new LinkedList<>();
                System.out.println("Lista en In Orden: ");
                for (int i = 0; i < nL; i++) {
                    System.out.println("Clave: ");
                    clave = (Integer) System.in.read();
                    System.out.println("Clave (Integer): ");
                    clave = (Integer) System.in.read();
                    System.out.println("Clave (String): ");
                    valor = System.in.toString();
                    listaDeClavesInOrden.add(clave);
                    listaDeValoresInOrden.add(valor);
                }
                System.out.println("Lista En Pre Orden: ");
                for (int i = 0; i < nL; i++) {
                    System.out.println("Clave: ");
                    clave = (Integer) System.in.read();
                    System.out.println("Clave (Integer): ");
                    clave = (Integer) System.in.read();
                    System.out.println("Clave (String): ");
                    valor = System.in.toString();
                    listaDeClavesPreOrden.add(clave);
                    listaDeValoresPreOrden.add(valor);
                }
                ArbolBinarioBusqueda<Integer, String> arbolReconstruido = new ArbolBinarioBusqueda<>();
                arbolReconstruido.reconstruirEnPreOrden(listaDeClavesInOrden, listaDeValoresInOrden,
                        listaDeClavesPreOrden, listaDeValoresPreOrden);
            }
            if (n == 9) {
                System.out.println("Clave: ");
                clave = (Integer) System.in.read();
                System.out.println(((ArbolBinarioBusqueda) arbol).sucesorInOrden(clave));
            }
            if (n == 10) {
                System.out.println("Clave: ");
                clave = (Integer) System.in.read();
                System.out.println(((ArbolBinarioBusqueda) arbol).predecesorInOrden(clave));
            }
            if (n == 11) {
                System.out.println("Clave: ");
                clave = (Integer) System.in.read();
                if (arbol instanceof ArbolAVL) {
                    ((ArbolAVL) arbol).eliminar(clave);
                } else {
                    System.out.println("No es un arbol AVL");
                }
            }
            if (n == 12) {
                System.out.println("Nivel: ");
                nivel = (Integer) System.in.read();
                System.out.println(((ArbolBinarioBusqueda) arbol).cantidadNodosConAmbosHijosEnNivelN(nivel));
            }
            if (n == 13) {
                arbol.mostrarArbol();
            }
            System.out.println("Programa finalizado");
        }
    }
    */
}
