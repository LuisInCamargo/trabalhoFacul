package app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import photochopp.*;

/**
 *
 * @author Moicho
 */
public class Trabalho {

    private static void succeed(String filter) {
        JPanel panel = new JPanel();
        String filtro = null;
        if (filter.equals("No")) {
            filtro = " !! Nenhum filtro foi aplicado !!";
        } else {
            if (filter.equals("filtro-linear")) {
                filtro = "Filtro Linear ";
            } else {
                if (filter.equals("bordas")) {
                    filtro = "Filtro de Bordas ";
                } else {
                    if (filter.equals("rgb-para-cinza")) {
                        filtro = "Filtro de RGB para Cinza ";
                    } else {
                        if (filter.equals("complemento")) {
                            filtro = "Filtro de Complemento ";
                        } else {
                            if (filter.equals("filtro-gaussiano")) {
                                filtro = "Filtro Gaussiano ";
                            }
                        }
                    }
                }
            }
            filtro = "!! " + filtro + "aplicado com sucesso !!";
        }

        panel.add(new JLabel(filtro));
        JOptionPane.showMessageDialog(null, panel, "Succeed", JOptionPane.INFORMATION_MESSAGE);
    }

    private static boolean isValid(String filter) {
        return filter.equals("filtro-linear") || filter.equals("bordas") || filter.equals("filtro-gaussiano")
                || filter.equalsIgnoreCase("rgb-para-cinza") || filter.equals("complemento");
    }

    public static void main(String[] args) throws IOException {
        try {
            BufferedImage imgOriginal, imgModificada;
            if ((args[0].length() != 0) && (isValid(args[0]))) {
                if (args[0].equals("bordas")) { // filtro de bordas
                    imgOriginal = ImageIO.read(new File(args[2])); // args[2] : Imagem Original / args[3] : Arguivo modificado
                    imgModificada = Operacoes.bordas(args[1], imgOriginal); // args[1] : limiar
                    ImageIO.write(imgModificada, "jpg", new File(args[3]));
                    succeed(args[0]);
                } else {
                    if (args[0].equals("filtro-linear")) { // filtro-linear
                        Filtro filtroLinear;
                        int tam = Integer.parseInt(args[1]);
                        double[][] filtro = new double[tam][tam];
                        int contador = 2; // Começa na pos 0  do "filtro"
                        for (double[] filtro1 : filtro) {
                            for (int j = 0; j < filtro1.length; j++) {
                                String valor = args[contador];
                                if (!valor.equals("null")) {
                                    filtro1[j] = Double.parseDouble(valor);
                                } else {
                                    throw new IllegalArgumentException((contador - 1) + " argumento  do filtro-linear : null");
                                }
                                contador++;
                            }
                        }
                        imgOriginal = ImageIO.read(new File(args[contador])); // Imagem Original
                        imgModificada = Operacoes.filtroLinear(imgOriginal, filtro); //filtroLinear(imagemDeEntrada, filtro)
                        ImageIO.write(imgModificada, "jpg", new File(args[contador + 1])); // Escrita no arquivo de saida
                    } else {
                        imgOriginal = ImageIO.read(new File(args[1])); // args[1] : Imagem Original / args[2] : Arguivo modificado
                        if (args[0].equals("rgb-para-cinza")) { // Filtro : Rgb-To-Cinza 
                            imgModificada = Operacoes.rgbToCinza(imgOriginal);
                            ImageIO.write(imgModificada, "jpg", new File(args[2]));
                        } else {
                            if (args[0].equals("filtro-gaussiano")) {    // Filtro : Gaussiano
                                imgModificada = Operacoes.filtroGaussiano(imgOriginal);
                                ImageIO.write(imgModificada, "jpg", new File(args[2]));
                            } else {
                                if (args[0].equals("complemento")) { // Filtro : Complemento
                                    imgModificada = Operacoes.complemento(imgOriginal);
                                    ImageIO.write(imgModificada, "jpg", new File(args[2]));
                                }
                            }
                        }
                    }
                    succeed(args[0]);
                }
            } else {
                succeed("No");
            }
        } catch (IOException e) {
            System.err.println("Error : " + e.toString());
        }
    }
}
