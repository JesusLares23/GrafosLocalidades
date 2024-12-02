
package grafos;

/**
 *
 * @author darkm
 */
public class BusquedaUnion {

    int[] padre;
    int[] rango;

    public BusquedaUnion(int tam) {
        padre = new int[tam];
        rango = new int[tam];
        for (int i = 0; i < tam; i++) {
            padre[i] = i;
            rango[i] = 0;
        }
    }

    public int encontrar(int elemento) {
        if (padre[elemento] != elemento) {
            padre[elemento] = encontrar(padre[elemento]); // Path compression
        }
        return padre[elemento];
    }

    public boolean union(int set1, int set2) {
        int root1 = encontrar(set1);
        int root2 = encontrar(set2);

        if (root1 != root2) {
            if (rango[root1] > rango[root2]) {
                padre[root2] = root1;
            } else if (rango[root1] < rango[root2]) {
                padre[root1] = root2;
            } else {
                padre[root2] = root1;
                rango[root1]++;
            }
            return true;
        }
        return false;
    }
}
