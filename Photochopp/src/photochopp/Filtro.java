package photochopp;

/**
 *
 * @author Moicho
 */

public class Filtro {
    public double[][] filtro = null;

    public Filtro(double[][] filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filtro inválido.");
        } else {
            this.filtro = filter;
        }
    }
}
