package photochopp;

/**
 *
 * @author Luis
 */

public class FiltroSobelVertical extends Filtro{
    private static final double[][] filtroSobelV = {{-1.0,-2.0,-1.0},
                                                    { 0.0, 0.0, 0.0},
                                                    { 1.0, 2.0, 1.0}};
    public FiltroSobelVertical(){
        super(filtroSobelV);

    }
}
