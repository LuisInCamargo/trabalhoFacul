
package photochopp;

/**
 *
 * @author Luis
 */

public class FiltroSobelHorizontal extends Filtro {
    private static final double[][] filtroSobelH = {{-1.0,0.0,1.0},
                                                    {-2.0,0.0,2.0},
                                                    {-1.0,0.0,1.0}};    
    public FiltroSobelHorizontal(){
        super(filtroSobelH);
    }
}
