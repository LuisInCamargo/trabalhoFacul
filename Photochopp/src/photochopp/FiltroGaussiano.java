package photochopp;

/**
 *
 * @author Luis
 */

public class FiltroGaussiano extends Filtro{
    private static final double[][] filtro = {{0.0352,0.0387,0.0398,0.0387,0.0352},
                                              {0.0387,0.0425,0.0438,0.0425,0.0387},
                                              {0.0398,0.0438,0.0452,0.0438,0.0398},
                                              {0.0387,0.0425,0.0438,0.0425,0.0387},
                                              {0.0352,0.0387,0.0398,0.0387,0.0352}};
    public FiltroGaussiano() {
        super(filtro);
    }
}
