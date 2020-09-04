package arboles;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ArbolBinarioBusqueda<K extends Comparable<K>, V>
        implements IArbolBusqueda<K, V> {

    protected NodoBinario<K, V> raiz;

    public ArbolBinarioBusqueda() {
    }

    protected NodoBinario<K, V> nodoVacioParaElArbol() {
        return (NodoBinario<K, V>) NodoBinario.nodoVacio();
    }

    @Override
    public void insertar(K clave, V valor) throws ExcepcionClaveYaExiste {
        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<K, V>(clave, valor);
            return;
        }
        //en caso de que el arbol no es vacio
        NodoBinario<K, V> nodoActual = this.raiz;
        NodoBinario<K, V> nodoAnterior = nodoVacioParaElArbol();
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            if (clave.compareTo(nodoActual.getClave()) > 0) {
                //pregunto si clave > nodoActual.getClave
                nodoActual = nodoActual.getHijoDerecho();
            } else if (clave.compareTo(nodoActual.getClave()) < 0) {
                //pregunto si clave < nodoActual.getClave
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                //Si llego por aca quiere decir que la clave a insertar
                //es igual que la clave del nodo Actual, entonces 
                //no se debe insertar
                throw new ExcepcionClaveYaExiste("La clave que quiere"
                        + "insertar ya existe en su arbol");
            }
        }
        // Si termino el bucle porque el nodoActual es nulo, entonces
        //ya encontre donde insertar la clave y su valor
        NodoBinario<K, V> nuevoNodo = new NodoBinario<>(clave, valor);
        if (clave.compareTo(nodoAnterior.getClave()) > 0) {
            nodoAnterior.setHijoDerecho(nuevoNodo);
        } else {
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
        }
    }

    @Override
    public V eliminar(K clave) throws ExcepcionClaveNoExiste {
        V valorARetornar = buscar(clave);
        if (valorARetornar == null) {
            throw new ExcepcionClaveNoExiste();
        }
        this.raiz = eliminar(this.raiz, clave);
        return valorARetornar;
    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual,
            K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDerecho
                    = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzquierdo
                    = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return nodoActual;
        }
        //Ya encontre el nodo
        //caso 1
        if (nodoActual.esHoja()) {
            return (NodoBinario<K, V>) NodoBinario.nodoVacio();
        }
        //caso 2
        if (nodoActual.esVacioHijoIzquierdo()
                && !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDerecho();
        }
        if (!nodoActual.esVacioHijoIzquierdo()
                && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        //caso 3
        NodoBinario<K, V> nodoReemplazo
                = this.buscarNodoSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> posibleNuevoHijo = eliminar(nodoActual, nodoReemplazo.getClave());
        nodoReemplazo.setHijoIzquierdo(nodoActual.getHijoIzquierdo());
        nodoReemplazo.setHijoDerecho(nodoActual.getHijoDerecho());
        nodoActual.setHijoIzquierdo((NodoBinario<K, V>) NodoBinario.nodoVacio());
        nodoActual.setHijoDerecho((NodoBinario<K, V>) NodoBinario.nodoVacio());
        return nodoReemplazo;
    }

    private NodoBinario<K, V> buscarNodoSucesor(NodoBinario<K, V> nodoActual) {
        while (!nodoActual.esVacioHijoIzquierdo()) {
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoActual;
    }

    @Override
    public V buscar(Comparable clave) {
        NodoBinario<K, V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            if (clave.compareTo(nodoActual.getClave()) > 0) {
                //pregunto si clave > nodoActual.getClave
                nodoActual = nodoActual.getHijoDerecho();
            } else if (clave.compareTo(nodoActual.getClave()) < 0) {
                //pregunto si clave < nodoActual.getClave
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                return nodoActual.getValor();
            }
        }
        //Si salgo del bucle porque el nodo Actual es vacio
        //entonces la clave no esta en el arbol por lo tanto
        //no hay nada que devolver y retorno nulo
        return null;
    }

    @Override
    public boolean contiene(K clave) {
        return this.buscar(clave) != null;
    }

    @Override
    public int size() {
        if (this.esArbolVacio()) {
            return 0;
        }
        int contadorDeNodos = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            contadorDeNodos = contadorDeNodos + 1;
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return contadorDeNodos;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        if (alturaPorIzquierda > alturaPorDerecha) {
            return alturaPorIzquierda + 1;
        }
        return alturaPorDerecha + 1;
    }

    public int alturaIterativa() {
        if (this.esArbolVacio()) {
            return 0;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        int alturaDelArbol = 0;
        while (!colaDeNodos.isEmpty()) {
            int nodosDelNivel = colaDeNodos.size();
            while (nodosDelNivel > 0) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                nodosDelNivel = nodosDelNivel - 1;
            }
            alturaDelArbol = alturaDelArbol + 1;
        }
        return alturaDelArbol;
    }

    @Override
    public void vaciar() {
        this.raiz = nodoVacioParaElArbol();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
        if (this.esArbolVacio()) {
            return -1;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        int nivelDelArbol = -1;
        while (!colaDeNodos.isEmpty()) {
            int nodosDelNivel = colaDeNodos.size();
            while (nodosDelNivel > 0) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                nodosDelNivel = nodosDelNivel - 1;
            }
            nivelDelArbol = nivelDelArbol + 1;
        }
        return nivelDelArbol;
    }

    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        meterEnPilaParaInOrden(nodoActual, pilaDeNodos);

        while (!pilaDeNodos.isEmpty()) {
            nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoDerecho()) {
                nodoActual = nodoActual.getHijoDerecho();
                meterEnPilaParaInOrden(nodoActual, pilaDeNodos);
            }
        }
        return recorrido;
    }

    private void meterEnPilaParaInOrden(NodoBinario<K, V> nodoActual, Stack<NodoBinario<K, V>> pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            nodoActual = nodoActual.getHijoIzquierdo();
        }
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);
        while (!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoDerecho()) {
                pilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return recorrido;
    }

    private void ayuda(Stack<NodoBinario<K, V>> pilaDeNodos, NodoBinario<K, V> nodoActual) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            if (!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        NodoBinario<K, V> nodoActual = this.raiz;
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        this.ayuda(pilaDeNodos, nodoActual);
        while (!pilaDeNodos.isEmpty()) {
            nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> tope = pilaDeNodos.peek();
                if (!tope.esVacioHijoDerecho() && tope.getHijoDerecho() != nodoActual) {
                    this.ayuda(pilaDeNodos, tope.getHijoDerecho());
                }
            }
        }
        return recorrido;
    }

    public List<K> recorridoEnPostOrdenRecursivo() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPostOrdenRecursivo(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrdenRecursivo(NodoBinario<K, V> nodoActual,
            List<K> recorrido) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnPostOrdenRecursivo(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPostOrdenRecursivo(nodoActual.getHijoDerecho(), recorrido);
        recorrido.add(nodoActual.getClave());
    }

    public List<K> recorridoInOrdenRecursivo() {
        List<K> recorrido = new LinkedList<>();
        recorridoInOrdenRecursivoAmigo(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoInOrdenRecursivoAmigo(NodoBinario<K, V> nodoActual,
            List<K> recorrido) {
        //simular N para controlar la recursividad
        //con la altura del arbol
        //Simulo que si tengo N, estoy preguntando si N == 0
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoInOrdenRecursivoAmigo(nodoActual.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoActual.getClave());
        recorridoInOrdenRecursivoAmigo(nodoActual.getHijoDerecho(), recorrido);
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return recorrido;
    }

    public int cantidadDeNodosConHijosIzquierdo() {
        return cantidadDeNodosConHijosIzquierdo(this.raiz);
    }

    private int cantidadDeNodosConHijosIzquierdo(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidadDeSoloHIPorIzquierda
                = this.cantidadDeNodosConHijosIzquierdo(nodoActual.getHijoIzquierdo());
        int cantidadDeSoloHIPorDerecha
                = this.cantidadDeNodosConHijosIzquierdo(nodoActual.getHijoDerecho());
        if (nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()) {
            return cantidadDeSoloHIPorIzquierda + cantidadDeSoloHIPorDerecha + 1;
        }
        return cantidadDeSoloHIPorIzquierda + cantidadDeSoloHIPorDerecha;
    }

    @Override
    public String toString() {
        //String arbol = 192;
        return "ArbolBinarioBusqueda{" + '}';
    }

    @Override
    public void mostrarArbol() {
        if (this.esArbolVacio()) {
            System.out.println("Arbol Vacio");
            return;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        List<K> listaInOrden = this.recorridoEnInOrden();
        while (!colaDeNodos.isEmpty()) {
            int nodosDelNivel = colaDeNodos.size();
            String nivel = "";
            Queue<NodoBinario<K, V>> colaDeNodosAnterior = new LinkedList<>();
            while (nodosDelNivel > 0) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                colaDeNodosAnterior.add(nodoActual);
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                nodosDelNivel = nodosDelNivel - 1;
            }
            imprimirNivel(colaDeNodosAnterior, listaInOrden, nivel);
        }
    }

    private void imprimirNivel(Queue<NodoBinario<K, V>> colaDeNodos, List<K> listaInOrden, String nivelAImprimir) {
        int indice = 0;
        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.peek();
            if (listaInOrden.get(indice).compareTo(nodoActual.getClave()) == 0) {
                nodoActual = colaDeNodos.poll();
                if (!nodoActual.esHoja()) {
                    if (!nodoActual.esVacioHijoIzquierdo()) {
                        nivelAImprimir = nivelAImprimir + '-';
                        nivelAImprimir = nivelAImprimir + nodoActual.getClave();
                    } else {
                        nivelAImprimir = nivelAImprimir + ' ';
                    }
                    if (!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()) {
                        nivelAImprimir = nivelAImprimir + nodoActual.getClave();
                        nivelAImprimir = nivelAImprimir + '-';
                    } else if (!nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()) {
                        nivelAImprimir = nivelAImprimir + '-';
                    } else {
                        nivelAImprimir = nivelAImprimir + ' ';
                    }
                } else {
                    nivelAImprimir = nivelAImprimir + ' ';
                    nivelAImprimir = nivelAImprimir + nodoActual.getClave();
                    nivelAImprimir = nivelAImprimir + ' ';
                }
            } else {
                nivelAImprimir = nivelAImprimir + "    ";
            }
            indice++;
        }
        System.out.println(nivelAImprimir);
    }

    // 1. Implemente un metodo recursivo que retorne la cantidad de nodos 
    //hojas que existen en arbol binario.
    public int cantidadDeNodosHojas() {
        return cantidadDeNodosHojas(this.raiz);
    }

    private int cantidadDeNodosHojas(NodoBinario<K, V> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidadDeNodosHojasPorIzquierda = cantidadDeNodosHojas(nodoActual.getHijoIzquierdo());
        int cantidadDeNodosHojasPorDerecha = cantidadDeNodosHojas(nodoActual.getHijoDerecho());
        if (nodoActual.esHoja()) {
            return cantidadDeNodosHojasPorDerecha + cantidadDeNodosHojasPorIzquierda + 1;
        }
        return cantidadDeNodosHojasPorDerecha + cantidadDeNodosHojasPorIzquierda;
    }

    // 2. Implemente un metodo iterativo que retorne la cantidad de nodos 
    //hojas que existen en arbol binario.
    public int cantidadDeNodosHojasIterativo() {
        int cantidadDeNodosHojas = 0;
        if (this.esArbolVacio()) {
            return 0;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
            if (nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()) {
                cantidadDeNodosHojas = cantidadDeNodosHojas + 1;
            }
        }
        return cantidadDeNodosHojas;
    }

    // 3. Implemente un metodo recursivo que retorne la cantidad de nodos 
    //hojas que existen en arbol binario, pero solo en el nivel N.
    public int cantidadDeNodosHojasEnNivelN(int n) {
        return cantidadDeNodosHojasEnNivelN(this.raiz, n, (byte) 0);
    }

    private int cantidadDeNodosHojasEnNivelN(NodoBinario<K, V> nodoActual,
            int nivelN, byte nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        } else if (nivelN == nivelActual) {
            return (nodoActual.esHoja()) ? 1 : 0;
        }
        int cantidadDeNodosHojasEnNivelNIzq
                = cantidadDeNodosHojasEnNivelN(nodoActual.getHijoIzquierdo(),
                        nivelN, (byte) (nivelActual + 1));
        int cantidadDeNodosHojasEnNivelNPorDer
                = cantidadDeNodosHojasEnNivelN(nodoActual.getHijoDerecho(),
                        nivelN, (byte) (nivelActual + 1));
        return cantidadDeNodosHojasEnNivelNIzq + cantidadDeNodosHojasEnNivelNPorDer;
    }
    // 4. Implemente un metodo iterativo que retorne la cantidad de nodos 
    //hojas que existen en arbol binario, pero solo en el nivel N.

    public int cantidadDeNodosHojasEnNivelNIterativo(int nivel) {
        if (this.esArbolVacio()) {
            return 0;
        }
        byte nivelActual = 0;
        int contadorDeNodosHojasEnNivelN = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            int contadorDeNodosPorNivel = colaDeNodos.size();
            while (contadorDeNodosPorNivel > 0) {
                NodoBinario<K, V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                if (nivelActual == nivel && nodoActual.esHoja()) {
                    contadorDeNodosHojasEnNivelN = contadorDeNodosHojasEnNivelN++;
                }
                contadorDeNodosPorNivel--;
            }
            nivelActual++;
        }
        return contadorDeNodosHojasEnNivelN;
    }

    // 5. Implemente un metodo recursivo que retorne la cantidad de nodos 
    //hojas que existen en arbol binario, pero solo antes del nivel N.    
    public int cantidadDeNodosHojasAntesDeNivelN(int n) {
        return cantidadDeNodosHojasAntesDeNivelN(this.raiz, n, (byte) 0);
    }

    private int cantidadDeNodosHojasAntesDeNivelN(NodoBinario<K, V> nodoActual,
            int nivelN, byte nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidadDeNodosHojasAntesDeNivelNIzq
                = cantidadDeNodosHojasAntesDeNivelN(nodoActual.getHijoIzquierdo(),
                        nivelN, (byte) (nivelActual + 1));
        int cantidadDeNodosHojasAntesDeNivelNPorDer
                = cantidadDeNodosHojasAntesDeNivelN(nodoActual.getHijoDerecho(),
                        nivelN, (byte) (nivelActual + 1));
        if (nivelActual >= nivelN) {
            return 0;
        } else if (nodoActual.esHoja()) {
            return 1;
        }
        return cantidadDeNodosHojasAntesDeNivelNIzq + cantidadDeNodosHojasAntesDeNivelNPorDer;
    }

    // 6. Implemente un metodo recursivo que retorne verdadero,si un arbol  
    //binario esta balanceado segun las reglas que establece un arbol AVL,
    //falso en caso contrario.
    public boolean arbolBalanceado() {
        return arbolBalanceado(this.raiz);
    }

    private boolean arbolBalanceado(NodoBinario<K, V> nodoActual) {
        boolean arbolBalanceado = true;
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return arbolBalanceado;
        } else if (arbolBalanceado == false) {
            return arbolBalanceado;
        }
        arbolBalanceado = arbolBalanceado(nodoActual.getHijoIzquierdo());
        arbolBalanceado = arbolBalanceado(nodoActual.getHijoDerecho());
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        if (alturaPorIzquierda - alturaPorDerecha > 1 || alturaPorIzquierda - alturaPorDerecha < -1) {
            arbolBalanceado = false;
        }
        return arbolBalanceado;
    }

    // 7. Implemente un metodo iterativo que la logica de un recorrido en
    //PostOrden que retorne verdadero,si un arbol binario esta balanceado 
    //segun las reglas que establece un arbol AVL, falso en caso contrario.
    public boolean arbolBalanceadoIterativo() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return true;
        }
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);
        boolean arbolBalanceado = true;
        while (!pilaDeNodos.isEmpty() && arbolBalanceado == true) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
            int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
            if (alturaPorIzquierda - alturaPorDerecha > 1 || alturaPorIzquierda - alturaPorDerecha < -1) {
                arbolBalanceado = false;
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                pilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return arbolBalanceado;
    }

    // 8. Implemente que reciba en listas de parametros las llaves y los 
    //valores de los recorridos en PreOrden e InOrden respectivamente y que  
    //reconstruya el arbol binario original. Su metodo no debe usar   el
    //metodo insertar.
    private int buscarPosicionEnLista(List<K> recorrido, K clave) {
        int posicion = 0;
        while (recorrido.size() > posicion) {
            if (clave.compareTo(recorrido.get(posicion)) == 0) {
                return posicion;
            }
            posicion = posicion + 1;
        }
        return -1;
    }

    public void reconstruirEnPreOrden(List<K> claveEnRecorridoInOrden,
            List<V> valorEnRecorridoInOrden, List<K> claveEnRecorridoPreOrden,
            List<V> valorEnRecorridoPreOrden) {
        this.raiz = reconstruirConPreOrden(claveEnRecorridoInOrden, valorEnRecorridoInOrden,
                claveEnRecorridoPreOrden, valorEnRecorridoPreOrden);
    }

    private NodoBinario<K, V> reconstruirConPreOrden(List<K> claveEnRecorridoInOrden,
            List<V> valorEnRecorridoInOrden, List<K> claveEnRecorridoPreOrden,
            List<V> valorEnRecorridoPreOrden) {
        if (claveEnRecorridoInOrden.isEmpty()) {
            return (NodoBinario<K, V>) NodoBinario.nodoVacio();
        }
        int posClaveEnPreOrden = 0;
        K claveDeNodoActual = claveEnRecorridoPreOrden.get(posClaveEnPreOrden);
        V valorDeNodoActual = (V) claveEnRecorridoPreOrden.get(posClaveEnPreOrden);
        int posClaveEnInOrden = this.buscarPosicionEnLista(claveEnRecorridoInOrden, claveDeNodoActual);
        if (posClaveEnInOrden > 0) {
            List<K> subListaClaveEnPreOrdenIzq
                    = claveEnRecorridoPreOrden.subList(posClaveEnPreOrden + 1, posClaveEnInOrden + 1);
            List<V> subListaValorEnPreOrdenIzq
                    = valorEnRecorridoPreOrden.subList(posClaveEnPreOrden + 1, posClaveEnInOrden + 1);
            List<K> subListaClaveEnInOrdenIzq
                    = claveEnRecorridoInOrden.subList(posClaveEnPreOrden, posClaveEnInOrden);
            List<V> subListaValorEnInOrdenIzq
                    = valorEnRecorridoInOrden.subList(posClaveEnPreOrden, posClaveEnInOrden);
            List<K> subListaClaveEnPreOrdenDer
                    = claveEnRecorridoPreOrden.subList(posClaveEnInOrden + 1, claveEnRecorridoPreOrden.size());
            List<V> subListaValorEnPreOrdenDer
                    = valorEnRecorridoPreOrden.subList(posClaveEnInOrden + 1, claveEnRecorridoPreOrden.size());
            List<K> subListaClaveEnInOrdenDer
                    = claveEnRecorridoInOrden.subList(posClaveEnInOrden + 1, claveEnRecorridoInOrden.size());
            List<V> subListaValorEnInOrdenDer
                    = valorEnRecorridoInOrden.subList(posClaveEnInOrden + 1, claveEnRecorridoInOrden.size());
            NodoBinario<K, V> nodoActual = new NodoBinario<>(claveDeNodoActual, valorDeNodoActual);
            NodoBinario<K, V> hijoIzqDelNodoActual
                    = this.reconstruirConPreOrden(subListaClaveEnInOrdenIzq, subListaValorEnInOrdenIzq,
                            subListaClaveEnPreOrdenIzq, subListaValorEnPreOrdenIzq);
            NodoBinario<K, V> hijoDerDelNodoActual
                    = this.reconstruirConPreOrden(subListaClaveEnInOrdenDer, subListaValorEnInOrdenDer,
                            subListaClaveEnPreOrdenDer, subListaValorEnPreOrdenDer);
            nodoActual.setHijoIzquierdo(hijoIzqDelNodoActual);
            nodoActual.setHijoDerecho(hijoDerDelNodoActual);
            return nodoActual;
        } else if (claveEnRecorridoPreOrden.size() > 1) {
            NodoBinario<K, V> nodoActual = new NodoBinario<>(claveDeNodoActual, valorDeNodoActual);
            List<K> subListaClaveEnPreOrdenDer
                    = claveEnRecorridoPreOrden.subList(posClaveEnInOrden + 1, claveEnRecorridoPreOrden.size());
            List<V> subListaValorEnPreOrdenDer
                    = valorEnRecorridoPreOrden.subList(posClaveEnInOrden + 1, claveEnRecorridoPreOrden.size());
            List<K> subListaClaveEnInOrdenDer
                    = claveEnRecorridoInOrden.subList(posClaveEnInOrden + 1, claveEnRecorridoInOrden.size());
            List<V> subListaValorEnInOrdenDer
                    = valorEnRecorridoInOrden.subList(posClaveEnInOrden + 1, claveEnRecorridoInOrden.size());
            NodoBinario<K, V> hijoDerDelNodoActual
                    = this.reconstruirConPreOrden(subListaClaveEnInOrdenDer, subListaValorEnInOrdenDer,
                            subListaClaveEnPreOrdenDer, subListaValorEnPreOrdenDer);
            nodoActual.setHijoDerecho(hijoDerDelNodoActual);
            return nodoActual;
        } else {
            NodoBinario<K, V> nodoActual = new NodoBinario<>(claveDeNodoActual, valorDeNodoActual);
            return nodoActual;
        }
    }

    // 9. Implemente un metodo privado que reciba un nodo binario de un arbol 
    //binario y que retorne cual seria su sucesor InOrden de la clave de
    //dicho nodo.
    private NodoBinario<K, V> buscarNodo(Comparable clave) {
        NodoBinario<K, V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            if (clave.compareTo(nodoActual.getClave()) > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else if (clave.compareTo(nodoActual.getClave()) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                return nodoActual;
            }
        }
        return null;
    }

    public K sucesorInOrden(K clave) throws ExcepcionClaveNoExiste {
        NodoBinario<K, V> nodoActual = buscarNodo(clave);
        nodoActual = sucesorInOrden(nodoActual);
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            return nodoActual.getClave();
        }
        throw new ExcepcionClaveNoExiste("Sin Sucesor");
    }

    private NodoBinario<K, V> sucesorInOrden(NodoBinario<K, V> nodoActual) {
        nodoActual = nodoActual.getHijoDerecho();
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            while (!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
            }
        }
        return nodoActual;
    }

    // 10. Implemente un metodo privado que reciba un nodo binario de un arbol 
    //binario y que retorne cual seria su predecesor InOrden de la clave de
    //dicho nodo.
    public K predecesorInOrden(K clave) throws ExcepcionClaveNoExiste {
        NodoBinario<K, V> nodoActual = buscarNodo(clave);
        nodoActual = predecesorInOrden(nodoActual);
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            return nodoActual.getClave();
        }
        throw new ExcepcionClaveNoExiste("Sin Predecesor");
    }

    private NodoBinario<K, V> predecesorInOrden(NodoBinario<K, V> nodoActual) {
        nodoActual = nodoActual.getHijoIzquierdo();
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            while (!nodoActual.esVacioHijoDerecho()) {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
        return nodoActual;
    }

    //-----------------------------------------------------------------------
    // 11. Implemente el metodo eliminar de un arbol AVL. 
    //--------------------Implementado en la clase AVL                   
    //-----------------------------------------------------------------------
    // 12. Para un arbol binario implemente un metodo que retorne la cantidad
    //de nodos que tienen ambos hijos luego del nivel N.
    public int cantidadNodosConAmbosHijosEnNivelN(int nivelN) {
        return cantidadNodosConAmbosHijosEnNivelN(this.raiz, (byte) nivelN, (byte) 0);
    }

    private int cantidadNodosConAmbosHijosEnNivelN(NodoBinario<K, V> nodoActual, byte nivelN, byte nivelActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        } else if (nivelN == nivelActual) {
            if (!nodoActual.esVacioHijoDerecho() && !nodoActual.esVacioHijoIzquierdo()) {
                return 1;
            }
        }
        int cantidadPorIzquierda
                = cantidadNodosConAmbosHijosEnNivelN(nodoActual.getHijoIzquierdo(), nivelN, (byte) (nivelActual + 1));
        int cantidadPorDerecha
                = cantidadNodosConAmbosHijosEnNivelN(nodoActual.getHijoDerecho(), nivelN, (byte) (nivelActual + 1));
        return cantidadPorDerecha + cantidadPorIzquierda;
    }
}
