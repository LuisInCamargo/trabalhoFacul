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
    public static BufferedImage toComplement(BufferedImage imgOriginal) throws IOException {
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
    public static BufferedImage toshadesOfGray(BufferedImage imgOriginal) throws IOException {
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

    // CONVOLUÇÃO DISCRETA 2D
    public static BufferedImage conDis2D(BufferedImage imgOriginal, Filtro filterQuery) throws IOException {
        File arq = new File("img_modify.png");
        FiltroGaussiano filterAux = new FiltroGaussiano();

        if (filterAux.getFilter().equalsIgnoreCase("filtrogaussiano")) {
            return filterAux.getModifyImage(imgOriginal);
        } else {
            //conferir se a imagem passou pelo processo de cinza
            if (imgOriginal.getType() == 1 || imgOriginal.getType() == 2) {
                if (filterAux.getFilter().equalsIgnoreCase("bordaslimiar")) {
                }
            }
        }
        return null;
    }
}
