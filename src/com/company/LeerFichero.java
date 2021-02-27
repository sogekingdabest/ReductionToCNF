package com.company;
import java.io.*;

public class LeerFichero {
    public static String [] lecturaLineasArchivo(String [] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String rutaArchivo = "";
        String [] lineasArchivo = null;
        int i = -1;
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
                while ((linea = br.readLine()) != null)
                    lineasArchivo[i+1] = linea;

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
        return lineasArchivo;
    }
}
