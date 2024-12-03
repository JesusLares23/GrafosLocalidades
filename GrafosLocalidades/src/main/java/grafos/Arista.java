
package grafos;

/**
 *
 * @author Jesús Amarillas - 207653, Angel Beltrán - 244865, Jesús Lares - 233383
 */
public class Arista {

    private Vertice origen;
    private Vertice destino;
    int peso;

    public Arista(Vertice origen, Vertice destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }
    
    public Vertice getOrigen() {
        return origen;
    }

    public Vertice getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }
    
    @Override
    public String toString() {
        return origen.getNombre() + " - " + destino.getNombre() + " : " + peso;
    }
}
