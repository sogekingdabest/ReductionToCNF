package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static ArbolBin transformarNNF(ArbolBin arbolEntrada){
        if (arbolEntrada == null) return arbolEntrada;
        String r = arbolEntrada.getRaiz();
        ArbolBin izq = arbolEntrada.getIzq();
        ArbolBin der = arbolEntrada.getDer();
        if (r.equals("-")){
            if (izq.getRaiz().equals("-")){
                arbolEntrada.setRaiz(izq.getIzq().getRaiz());
                arbolEntrada.setIzq(izq.getIzq().getIzq());
                arbolEntrada.setDer(izq.getIzq().getDer());
                if (izq != null){
                    transformarNNF(arbolEntrada.getIzq());
                }
                if (der != null){
                    transformarNNF(arbolEntrada.getDer());
                }
                return arbolEntrada;
            }

            if (izq.getRaiz().equals("&")){
                arbolEntrada.setRaiz("|");
                if (izq.getIzq() != null){
                    arbolEntrada.setIzq(new ArbolBin("-", izq.getIzq(), null));
                    transformarNNF(arbolEntrada.getIzq());
                }
                if (izq.getDer() != null){
                    arbolEntrada.setDer(new ArbolBin("-", izq.getDer(), null));
                    transformarNNF(arbolEntrada.getDer());
                }
                return arbolEntrada;
            }

            if (izq.getRaiz().equals("|")){
                arbolEntrada.setRaiz("&");
                if (izq.getIzq() != null){
                    arbolEntrada.setIzq(new ArbolBin("-", izq.getIzq(), null));
                    transformarNNF(arbolEntrada.getIzq());
                }
                if (izq.getDer() != null){
                    arbolEntrada.setDer(new ArbolBin("-", izq.getDer(), null));
                    transformarNNF(arbolEntrada.getDer());
                }
                return arbolEntrada;
            }

        } else {
            if (izq != null){
                transformarNNF(arbolEntrada.getIzq());
            }
            if (der != null){
                transformarNNF(arbolEntrada.getDer());
            }
        }
        return arbolEntrada;
    }

    private static ArrayList<ArrayList<String>> transformarFNC(ArbolBin arbolEntrada) {
        String r = arbolEntrada.getRaiz();
        ArrayList<ArrayList<String>> rList = new ArrayList<>();
        rList.add(new ArrayList<String>());

        ArrayList<ArrayList<String>> izqList = new ArrayList<>();
        izqList.add(new ArrayList<String>());

        ArrayList<ArrayList<String>> derList = new ArrayList<>();
        derList.add(new ArrayList<String>());

        ArrayList<String> aux;

        ArbolBin izq = arbolEntrada.getIzq();
        ArbolBin der = arbolEntrada.getDer();
        ArrayList<ArrayList<String>> listaSalida = new ArrayList<>();

        if (r.equals("&")){

            if(izq.esHoja() ){
                if (izq.getRaiz().equals("-")) {
                    izqList.get(0).add("not " + izq.getIzq().getRaiz());
                } else {
                    izqList.get(0).add(izq.getRaiz());
                }
                listaSalida.add(izqList.get(0));

            } else {
                if (izq != null){
                    izqList = transformarFNC(izq);
                }
                for (int i = 0; i < izqList.size(); i++){
                    listaSalida.add(izqList.get(i));
                }
            }
            if (der.esHoja()) {
                if (der.getRaiz().equals("-")) {
                    derList.get(0).add("not " + der.getIzq().getRaiz());
                } else {
                    derList.get(0).add(der.getRaiz());
                }
                listaSalida.add(derList.get(0));
            } else {
                if (der != null){
                    derList = transformarFNC(der);
                }
                for (int i = 0; i < derList.size(); i++){
                    listaSalida.add(derList.get(i));
                }
            }
            return listaSalida;

        } else if (r.equals("|")){
            if(izq.esHoja()){
                if (izq.getRaiz().equals("-")) {
                    izqList.get(0).add("not " + izq.getIzq().getRaiz());
                } else {
                    izqList.get(0).add(izq.getRaiz());
                }

            } else {
                if (izq != null){
                    izqList = transformarFNC(izq);
                }

            }
            if (der.esHoja()) {
                if (der.getRaiz().equals("-")) {
                    derList.get(0).add("not " + der.getIzq().getRaiz());
                } else {
                    derList.get(0).add(der.getRaiz());
                }

            } else {
                if (der != null){
                    derList = transformarFNC(der);
                }
            }
            //System.out.println(izqList);

            for(int i= 0; i < derList.get(0).size(); i++){
                izqList.get(0).add(derList.get(0).get(i));
            }
            listaSalida = izqList;
            /*
            for (int i = 0; i < izqList.size(); i++){
                for (int j = 0; j < derList.size(); j++){
                    for (int x = 0; x < izqList.get(i).size(); x++){
                        for (int y = 0; y < derList.get(j).size(); y++){

                            aux = new ArrayList<>();
                            if (izqList.get(i).get(x).equals(derList.get(j).get(y))){
                                aux.add(izqList.get(i).get(x));
                                listaSalida.add(aux);
                            } else if(izqList.get(i).get(x).equals("-" + derList.get(j).get(y)) ||
                                        derList.get(j).get(y).equals("-" + izqList.get(i).get(x))) {
                                //No se hace nada porque se anulan
                            } else {
                                aux.add(izqList.get(i).get(x));
                                aux.add(derList.get(j).get(y));
                                listaSalida.add(aux);
                            }

                        }
                    }
                }
            }*/
            return listaSalida;

        } else {

            if (arbolEntrada.esHoja()) {
                if (arbolEntrada.getRaiz().equals("-")) {
                    rList.get(0).add(arbolEntrada.getRaiz() + arbolEntrada.getIzq().getRaiz());
                } else {
                    rList.get(0).add(arbolEntrada.getRaiz());
                }
            }
            listaSalida.add(rList.get(0));
            return listaSalida;

        }
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
                return arbolSalida = new ArbolBin(
                        r,
                        new ArbolBin("-", izq, null),
                        der
                );
            } else if (r.equals("=")) {
                r = "&";
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
            return arbolSalida = new ArbolBin(r,izq,der);
        } else if(r.equals("-")){
            izq = crearArbol(lineasFichero, pointer);
            return arbolSalida = new ArbolBin(r,izq,null);
        } else {
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
                    //System.out.println("Linea: " + linea);
                    lineasArchivo.add("- "+ linea);
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

    public static void crearArchivoClingo(ArrayList<ArrayList<String>> listaDeProposiciones, PrintWriter writer) {
            writer.print(":- ");
            for (int i = 0; i < listaDeProposiciones.size(); i++) {
                for (int j = 0; j < listaDeProposiciones.get(i).size(); j++) {
                    writer.print(listaDeProposiciones.get(i).get(j));
                    if (listaDeProposiciones.get(i).size()>1) {
                        if (j+1 < listaDeProposiciones.get(i).size()) {
                            writer.println(".");
                            writer.print(":- ");
                        }
                    }
                }
                if (i+1 != listaDeProposiciones.size())
                    writer.print(", ");
            }
            writer.println(".");
    }

    public static void main(String[] args) {
        ArrayList<String> lineasFichero = lecturaLineasArchivo(args);
        Pointer pointer = new Pointer(-1);
        ArrayList<ArrayList<String>> listaDeProposiciones = new ArrayList<>();
        ArbolBin arbol;
        try {
            PrintWriter writer = new PrintWriter(args[0].split("\\.")[0]+"2.lp", "UTF-8");
            for (int i = 0; i<lineasFichero.size(); i++) {
                arbol = crearArbol(lineasFichero.get(i).split(" "), pointer);
                transformarNNF(arbol);
                listaDeProposiciones = transformarFNC(arbol);
                //System.out.println(arbol.getRaiz());
                System.out.println(listaDeProposiciones);
                System.out.println(listaDeProposiciones.get(0));
                crearArchivoClingo(listaDeProposiciones, writer);
                pointer.setValor(-1);
            }

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
