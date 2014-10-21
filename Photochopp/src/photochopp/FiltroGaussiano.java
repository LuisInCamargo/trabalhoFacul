/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photochopp;

import java.awt.image.BufferedImage;

/**
 *
 * @author Luis
 */
public class FiltroGaussiano extends Filtro{
    private double[][] matrix = {{0.0352,0.0387,0.0398,0.0387,0.0352},
                                 {0.0387,0.0425,0.0438,0.0425,0.0387},
                                 {0.0398,0.0438,0.0452,0.0438,0.0398},
                                 {0.0387,0.0425,0.0438,0.0425,0.0397},
                                 {0.0352,0.0387,0.0398,0.0387,0.0352}};
    
    public BufferedImage getModifyImage(BufferedImage img){
        return null;
    }
}
