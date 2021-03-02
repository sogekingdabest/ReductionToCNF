package com.company;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

    private static void crearArbol(String[] lineasFichero, int pointer){
        String r = lineasFichero[++pointer];

        if (r.equals("&") || r.equals("|") || r.equals(">") || r.equals("=") || r.equals("%") ){
            System.out.println(r + " MACAQUINHO " + pointer);
            crearArbol(lineasFichero, pointer);
        } else if(r.equals("-") || r.equals("0") || r.equals("1")){
            return;
        } else {
            return;
        }
    }

    private static ArrayList<String> lecturaLineasArchivo(String [] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String rutaArchivo = "";
        ArrayList<String> lineasArchivo = new ArrayList<String>();
        System.out.println(args[0]);
        if (args.length == 0)
            System.exit(0);
        if (args.length == 1) {
            rutaArchivo = args[0];
            try {
                // Apertura del fichero y creacion de BufferedReader para poder
                // hacer una lectura comoda (disponer del metodo readLine()).
                archivo = new File(rutaArchivo);
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);

                // Lectura del fichero
                String linea;
                while ((linea = br.readLine()) != null){
                    System.out.println("Linea: " + linea);
                    lineasArchivo.add(linea);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // En el finally cerramos el fichero, para asegurarnos
                // que se cierra tanto si todo va bien como si salta
                // una excepcion.
                try {
                    if (null != fr) {
                        fr.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        System.out.println(lineasArchivo);
        return lineasArchivo;
    }

    public static void main(String[] args) {
        ArrayList<String> lineasFichero = lecturaLineasArchivo(args);
        int pointer = -1;
        System.out.println(lecturaLineasArchivo(args));
        //crearArbol(lineasFichero[0].split(" "), pointer);
    }
}
