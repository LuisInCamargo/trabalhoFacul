/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import photochopp.Operacoes;
import photochopp.Filtro;

/**
 *
 * @author Moicho
 */
public class Trabalho {

    public static void main(String[] args) throws IOException {

        String filtro_In = args[0];
        String path_In = null;
        String path_Out = null;
        //Atribuindo comandos à strings.
        if ((args[0] == null) || (args[1] == null) || (args[0] == null)) {
            throw new IllegalArgumentException("Argumentos inválidos");
        } else {
            filtro_In = args[0];
            path_In = args[1];
            path_Out = args[2];
        }

        try {
            //Arquivos de entrada e saida
            File arqIn = new File(path_In);
            File arqOut = new File(path_Out);

            //Filtro
            Filtro filter = new Filtro();

            //BufferedImage
            BufferedImage imgOriginal = ImageIO.read(arqIn);
            BufferedImage imgModificada = null;

            //Operacoes
            Operacoes operacao = new Operacoes();

            //Processamento
            if (filtro_In.trim().equalsIgnoreCase("complemento")) {
                imgModificada = photochopp.Operacoes.toComplement(imgOriginal);
            } else {
                if (filtro_In.trim().equalsIgnoreCase("rgb-para-cinza")) {
                    imgModificada = photochopp.Operacoes.toshadesOfGray(imgOriginal);
                } else {
                    if (filtro_In.trim().equalsIgnoreCase("filtrogaussiano")) {
                        filter.setFilter("filtroGaussiano");
                        imgModificada = photochopp.Operacoes.conDis2D(imgOriginal, filter);
                    } else {
                        if (filtro_In.trim().equalsIgnoreCase("bordaslimiar")) {
                            filter.setFilter("bordaslimiar");
                            imgModificada = photochopp.Operacoes.conDis2D(imgOriginal, filter);
                        } else {
                            throw new IllegalArgumentException("Argumento de filtro inválida.");
                        }
                    }
                }
            }

            ImageIO.write(imgModificada, "png", arqOut);
        } catch (Exception e) {
            System.err.println("Error : " + e.getMessage());
        }
    }
}
