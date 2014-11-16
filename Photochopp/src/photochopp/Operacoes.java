
package photochopp;

/**
 *
 * @author Luis
 */

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Operacoes {
    // COMPLEMENTO
    public static BufferedImage complemento(BufferedImage imgOriginal) throws IOException {
        int red, green, blue, cor;
        for (int i = 0; i < imgOriginal.getWidth(); i++) {
            for (int j = 0; j < imgOriginal.getHeight(); j++) {
                red = 255 - (imgOriginal.getRGB(i, j) >> 16) & 0xff;
                green = 255 - (imgOriginal.getRGB(i, j) >> 8) & 0xff;
                blue = 255 - (imgOriginal.getRGB(i, j) & 0xff);
                cor = (red << 16) | (green << 8) | (blue);
                imgOriginal.setRGB(i, j, 255 - imgOriginal.getRGB(i, j));
            }
        }
        return imgOriginal;
    }

    // RGB_TO_GRAY
    public static BufferedImage rgbToCinza(BufferedImage imgOriginal) throws IOException {
        int red, green, blue, intensidade, cor;
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
        return imgOriginal;
    }

    // GAUSSIANO
    public static BufferedImage filtroGaussiano(BufferedImage imgOriginal) throws IOException {
        double[][][] imgModifyed = convolution(imgOriginal, new FiltroGaussiano());
        imgOriginal = writeImage(imgModifyed);
        return imgOriginal;
    }

    //Bordas
    public static BufferedImage bordas(String limiar, BufferedImage imgOriginal) throws IOException {
        // Converte a imgOriginal para cinza.
        imgOriginal = rgbToCinza(imgOriginal);

        double[][][] imgWx = convolution(imgOriginal, new FiltroSobelHorizontal());
        double[][][] imgWy = convolution(imgOriginal, new FiltroSobelVertical());
        double[][][] imgG = new double[imgWy.length][imgWy.length][3];

        for (int i = 0; i < imgOriginal.getWidth(); i++) {
            for (int j = 0; j < imgOriginal.getHeight(); j++) {
                for (int k = 0; k < 3; k++) {
                    imgG[i][j][k] = Math.sqrt(Math.pow(imgWx[i][j][k], 2) + Math.pow(imgWy[i][j][k], 2));
                }
            }
        }
        BufferedImage finalImg = writeBordasImage(imgWx, Integer.parseInt(limiar));
        return finalImg;
    }

    //Filtro Linear
    public static BufferedImage filtroLinear(BufferedImage imgOriginal, double[][] filtro) throws IOException {
        double[][][] imgAux = Operacoes.convolution(imgOriginal, new Filtro(filtro));
        BufferedImage img = writeImage(imgAux);
        return img;
    }

    // Convolution
    public static double[][][] convolution(BufferedImage imgOriginal, Filtro filtroQuery) throws IOException {

        int tamXImage = imgOriginal.getWidth();
        int tamYImage = imgOriginal.getHeight();
        int actualPixel, coordX, coordY, tamXFiltro;
        int red, blue, green;

        double[][][] auxImage = new double[tamXImage][tamYImage][3]; // [X][Y][3 : R,G,B] 
        tamXFiltro = filtroQuery.filtro.length;

        for (int i = 0; i < tamXImage; i++) {
            for (int j = 0; j < tamYImage; j++) {
                for (int x = 0; x < tamXFiltro; x++) {
                    for (int y = 0; y < tamXFiltro; y++) {
                        coordX = i - (tamXFiltro - 1) / 2 + x;
                        coordY = j - (tamXFiltro - 1) / 2 + y;
                        if (coordX < 0) {
                            coordX = 0;
                        } else {
                            if (coordX > tamXImage - 1) {
                                coordX = tamXImage - 1;
                            }
                        }
                        if (coordY < 0) {
                            coordY = 0;
                        } else {
                            if (coordY > tamYImage - 1) {
                                coordY = tamYImage - 1;
                            }
                        }
                        actualPixel = imgOriginal.getRGB(coordX, coordY);
                        red = (actualPixel >> 16) & 0xff;
                        green = (actualPixel >> 8) & 0xff;
                        blue = actualPixel & 0xff;
                        auxImage[i][j][0] += red * filtroQuery.filtro[x][y];
                        auxImage[i][j][1] += green * filtroQuery.filtro[x][y];
                        auxImage[i][j][2] += blue * filtroQuery.filtro[x][y];
                    }
                }
            }

        }
        return auxImage;
    }

    // Ira pegar o array[][][] e colocar numa BufferedImage
    private static BufferedImage writeImage(double[][][] img) { // Converte os valores da imagem e trabalha eles dentro de um
        // vetor de doubles

        int tamXImage = img.length;;
        int tamYImage = img[0].length;

        int valorRGB;
        int[] temp = new int[3];
        float cor;

        BufferedImage imgAux = new BufferedImage(tamXImage, tamYImage, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < tamXImage; i++) {
            for (int j = 0; j < tamYImage; j++) {
                for (int x = 0; x < 3; x++) {
                    temp[x] = (int) img[i][j][x];
                    if (temp[x] < 0) {
                        temp[x] = 0;
                    } else {
                        if (temp[x] > 255) {
                            temp[x] = 255;
                        }
                    }
                }
                valorRGB = (temp[0] << 16) | (temp[1] << 8) | (temp[2]);
                imgAux.setRGB(i, j, valorRGB);
            }
        }
        return imgAux;
    }

    // Ira pegar o array[][][] e delimitar os valores de acordo com o limiar
    private static BufferedImage writeBordasImage(double[][][] img, int limiar) { // Converte os valores da imagem e trabalha eles dentro de um
        int tamXImage = img.length;;
        int tamYImage = img[0].length;

        int valorRGB;
        int[] temp = new int[3];

        BufferedImage imgAux = new BufferedImage(tamXImage, tamYImage, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < tamXImage; i++) {
            for (int j = 0; j < tamYImage; j++) {
                for (int x = 0; x < 3; x++) {
                    temp[x] = (int) img[i][j][x];
                    if (temp[x] >= limiar) {
                        temp[x] = 255;
                    } else {
                        temp[x] = 0;
                    }
                }
                valorRGB = (temp[0] << 16) | (temp[1] << 8) | (temp[2]);
                imgAux.setRGB(i, j, valorRGB);
            }
        }
        return imgAux;
    }
}
