
package grafos;

/**
 *
 * @author Jesús Amarillas - ######, Angel Beltrán - 244865, Jesús Lares - 233383
 */
public class Arista {

    int fuente;
    int origen;
    int destino;

    public Arista(int fuente, int destino, int peso) {
        this.fuente = fuente;
        this.origen = destino;
        this.destino = peso;
    }
}
