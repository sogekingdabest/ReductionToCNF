package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

    private static ArbolBin transformarNNF(ArbolBin arbolEntrada){
        String r = arbolEntrada.getRaiz();
        ArbolBin izq = arbolEntrada.getIzq();
        ArbolBin der = arbolEntrada.getDer();
        if (r.equals("-")){
            if (izq.getRaiz().equals("-")){
                arbolEntrada.setRaiz(izq.getIzq().getRaiz());
                arbolEntrada.setIzq(izq.getIzq().getIzq());
                arbolEntrada.setDer(izq.getIzq().getDer());
                if (izq != null){
                    transformarNNF(izq);
                }
                if (der != null){
                    transformarNNF(der);
                }
                return arbolEntrada;
            }

            if (izq.getRaiz().equals("&")){
                arbolEntrada.setRaiz("|");
                if (izq != null){
                    arbolEntrada.setIzq(new ArbolBin("-", izq, null));
                    transformarNNF(izq);
                }
                if (der != null){
                    arbolEntrada.setDer(new ArbolBin("-", der, null));
                    transformarNNF(der);
                }
                return arbolEntrada;
            }

            if (izq.getRaiz().equals("|")){
                arbolEntrada.setRaiz("&");
                if (izq != null){
                    arbolEntrada.setIzq(new ArbolBin("-", izq, null));
                    transformarNNF(izq);
                }
                if (der != null){
                    arbolEntrada.setDer(new ArbolBin("-", der, null));
                    transformarNNF(der);
                }
                return arbolEntrada;
            }

        } else {
            if (izq != null){
                transformarNNF(izq);
            }
            if (der != null){
                transformarNNF(der);
            }
        }
        return arbolEntrada;
    }

    private static ArbolBin transformarFNC(ArbolBin arbolEntrada) {
        return null;
    }

    private static ArbolBin crearArbol(String[] lineasFichero, Pointer pointer){
        String r = lineasFichero[pointer.next()];
        ArbolBin izq = null;
        ArbolBin der = null;
        ArbolBin arbolSalida;

        if (r.equals("&") || r.equals("|") || r.equals(">") || r.equals("=") || r.equals("%") ){
            izq = crearArbol(lineasFichero, pointer);
            der = crearArbol(lineasFichero, pointer);
            if (r.equals(">")) {
                r= "|";
                System.out.println(r + " MACAQUINHO " + pointer.getValor());
                return arbolSalida = new ArbolBin(
                        r,
                        new ArbolBin("-", izq, null),
                        der
                );
            } else if (r.equals("=")) {
                r = "&";
                System.out.println(r + " MACAQUINHO " + pointer.getValor());
                return arbolSalida = new ArbolBin(
                            r,
                            new ArbolBin(
                                    "|",
                                    new ArbolBin("-", izq, null),
                                    der
                            ),
                            new ArbolBin(
                                    "|",
                                    new ArbolBin("-", der, null),
                                    izq
                            )
                        );
            } else if (r.equals("%")){
                r = "|";
                System.out.println(r + " MACAQUINHO " + pointer.getValor());
                return arbolSalida = new ArbolBin(
                        r,
                        new ArbolBin(
                                "&",
                                izq,
                                new ArbolBin("-", der, null)
                        ),
                        new ArbolBin(
                                "&",
                                new ArbolBin("-", izq, null),
                                der
                        )
                );
            }
            System.out.println(r + " MACAQUINHO " + pointer.getValor());
            return arbolSalida = new ArbolBin(r,izq,der);
        } else if(r.equals("-")){
            System.out.println(r + " MACACO " + pointer.getValor());
            izq = crearArbol(lineasFichero, pointer);
            return arbolSalida = new ArbolBin(r,izq,null);
        } else {
            System.out.println(r + " OTROS " + pointer.getValor());
            return arbolSalida = new ArbolBin(r,null, null);
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
        return lineasArchivo;
    }

    public static void main(String[] args) {
        ArrayList<String> lineasFichero = lecturaLineasArchivo(args);
        Pointer pointer = new Pointer(-1);
        System.out.println(lecturaLineasArchivo(args));
        ArbolBin arbol = crearArbol(lineasFichero.get(1).split(" "), pointer);
        System.out.println(arbol.getRaiz());
        ArbolBin macaco = new ArbolBin("-",
                new ArbolBin("-",
                        new ArbolBin("p",null,null),
                        null),
                null
        );
        transformarNNF(macaco);
        System.out.println(macaco.getRaiz());
    }
}
