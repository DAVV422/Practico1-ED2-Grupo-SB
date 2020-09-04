package arboles;

public class ArbolAVL <K extends Comparable<K>, V> 
        extends ArbolBinarioBusqueda<K, V>{
    private static final byte DIFERENCIA_MAXIMA = 1;

    @Override
    public void insertar(K clave, V valor) throws ExcepcionClaveYaExiste {
        raiz = insertar(raiz, clave, valor);
    }

    private NodoBinario<K, V> insertar(NodoBinario<K, V> nodoActual,
            K clave, V valor) throws ExcepcionClaveYaExiste {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            NodoBinario<K, V> nuevoNodo = new NodoBinario<K, V>(clave, valor);
            return nuevoNodo;
        }
        K claveActual = nodoActual.getClave();
        if (clave.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDerecho
                    = insertar(nodoActual.getHijoDerecho(), clave, valor);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return balancear(nodoActual);
        }
        if (clave.compareTo(claveActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzquierdo
                    = insertar(nodoActual.getHijoIzquierdo(), clave, valor);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return balancear(nodoActual);
        }
        throw new ExcepcionClaveYaExiste();
    }

    private NodoBinario<K, V> balancear(NodoBinario<K, V> nodoActual) {
        int alturaRamaIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaRamaDerecha = altura(nodoActual.getHijoDerecho());
        int diferenciaDeAltura = alturaRamaIzquierda - alturaRamaDerecha;
        if (diferenciaDeAltura > DIFERENCIA_MAXIMA) {
            // hay problema en la rama derecha
            NodoBinario<K, V> hijoIzquierdo = nodoActual.getHijoIzquierdo();
            alturaRamaIzquierda = altura(hijoIzquierdo.getHijoIzquierdo());
            alturaRamaDerecha = altura(hijoIzquierdo.getHijoDerecho());
            if (alturaRamaDerecha > alturaRamaIzquierda) {
                return rotacionDobleADerecha(nodoActual);
            } else {
                return rotacionSimpleADerecha(nodoActual);
            }
        } else if (diferenciaDeAltura < -DIFERENCIA_MAXIMA) {
            // hay problema en la rama izquierda
            NodoBinario<K, V> hijoDerecho = nodoActual.getHijoDerecho();
            alturaRamaIzquierda = altura(hijoDerecho.getHijoIzquierdo());
            alturaRamaDerecha = altura(hijoDerecho.getHijoDerecho());
            if (alturaRamaDerecha < alturaRamaIzquierda) {
                return rotacionDobleAIzquierda(nodoActual);
            } else {
                return rotacionSimpleAIzquierda(nodoActual);
            }
        }
        //Si llego por aca entonces 
        //no hay problema, retornamos el mismo nodo        
        return nodoActual;
    }

    private NodoBinario<K, V> rotacionSimpleADerecha(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoARetornar = nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoARetornar.getHijoDerecho());
        nodoARetornar.setHijoDerecho(nodoActual);
        return nodoARetornar;
    }

    private NodoBinario<K, V> rotacionSimpleAIzquierda(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoARetornar = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoARetornar.getHijoIzquierdo());
        nodoARetornar.setHijoIzquierdo(nodoActual);
        return nodoARetornar;
    }

    private NodoBinario<K, V> rotacionDobleADerecha(NodoBinario<K, V> nodoActual) {
        nodoActual.setHijoIzquierdo(rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo()));
        NodoBinario<K, V> nodoARetornar = rotacionSimpleADerecha(nodoActual);
        return nodoARetornar;
    }

    private NodoBinario<K, V> rotacionDobleAIzquierda(NodoBinario<K, V> nodoActual) {
        nodoActual.setHijoDerecho(rotacionSimpleADerecha(nodoActual.getHijoDerecho()));
        NodoBinario<K, V> nodoARetornar = rotacionSimpleAIzquierda(nodoActual);
        return nodoARetornar;
    }

    @Override
    public V eliminar(K clave) throws ExcepcionClaveNoExiste {
        V valorARetornar = buscar(clave);
        if (valorARetornar == null) {
            throw new ExcepcionClaveNoExiste();
        }
        raiz = eliminar(raiz, clave);
        return valorARetornar;
    }

    // 11. Implemente el metodo eliminar de un arbol AVL.
    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual,
            K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDerecho
                    = eliminar(nodoActual.getHijoDerecho(), claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);              
            return balancear(nodoActual);
        }
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzquierdo
                    = eliminar(nodoActual.getHijoIzquierdo(), claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);            
            return balancear(nodoActual);
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
        while (!nodoActual.esVacioHijoIzquierdo()){
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoActual;       
    }
}
