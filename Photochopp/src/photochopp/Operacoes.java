/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photochopp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Moicho
 */
// IMAGEM NEGATIVO --> 255 - arq.getRGB(i,j)
// IMAGEM EM TONS DE CINZA -- >  red = (int) (((rgb) & 0xff));
//  blue = (int) (((rgb) & 0xff));
//  green = (int) (((rgb) & 0xff));
//  rgb = (red << 16) | (green << 8) | (blue);
public class Operacoes {

    // COMPLEMENTO
    public static BufferedImage complemento(BufferedImage imgOriginal) throws IOException {
        File arq = new File("img_modify_Complement.png");
        for (int i = 0; i < imgOriginal.getWidth(); i++) {
            for (int j = 0; j < imgOriginal.getHeight(); j++) {
                imgOriginal.setRGB(i, j, 255 - imgOriginal.getRGB(i, j));
            }
        }
        ImageIO.write(imgOriginal, "png", arq);
        return ImageIO.read(arq);
    }

    // RGB_TO_GRAY
    public static BufferedImage rgbToCinza(BufferedImage imgOriginal) throws IOException {
        int red = 0;
        int green = 0;
        int blue = 0;
        int intensidade = 0;
        int cor = 0;
        File arq = new File("img_modify_Gray.png");
        for (int i = 0; i < imgOriginal.getWidth(); i++) {
            for (int j = 0; j < imgOriginal.getHeight(); j++) {
                red = (imgOriginal.getRGB(i, j) >> 16) & 0xff;
                green = (imgOriginal.getRGB(i, j) >> 8) & 0xff;
                blue = (imgOriginal.getRGB(i, j) & 0xff);
                intensidade = (int) ((red * 0.3) + (green * 0.59) + (blue * 0.11));
                cor = (intensidade << 16) | (intensidade << 8) | (intensidade);
                imgOriginal.setRGB(i, j, cor);
            }
        }
        ImageIO.write(imgOriginal, "png", arq);
        return ImageIO.read(arq);
    }

    // GAUSSIANO
    public static BufferedImage filtroGaussiano(BufferedImage imgOriginal) throws IOException {
        BufferedImage imgModifyed = convolution(imgOriginal, new FiltroGaussiano());

    }

    // CONVOLUÇÃO DISCRETA 2D
    public static BufferedImage convolution(BufferedImage imgOriginal, Filtro filterQuery) throws IOException {
        File arq = new File("img_modify.png");

        return null;
    }

    private static double[][][] readImage(BufferedImage img) {

        int tamXImage = img.getWidth();
        int tamYImage = img.getHeight();
        int actualPixel, posMaxXFilter, posMaxYFilter;

        double[][][] auxImage = new double[tamXImage][tamYImage][3]; // [X][Y][3 : R,G,B] 
        FiltroGaussiano filter = new FiltroGaussiano();
        
        for (int i = 0; i < tamXImage; i++) {
            for (int j = 0; j < tamYImage; j++) {
                actualPixel = img.getRGB(i, j);
                posMaxXFilter = (5 - i)+ 0;
                posMaxYFilter = 0;
                    
                for (int l = 0; l < posMaxXFilter; l++){
                    for (int c = 0; c < posMaxYFilter; c++) {
                        for (int k = 0; k < 3; k++) {
                              auxImage[i][j][k] = 0;
                        }
                    }
                }
            }
        }
        return auxImage;
    }
}
