
package grafos;

import java.util.Objects;

/**
 *
 * @author darkm
 */
public class Vertice {

    private String nombre;

    public Vertice(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vertice vertice = (Vertice) obj;
        return Objects.equals(nombre, vertice.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
