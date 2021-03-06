package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

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
            System.out.println(izq.getRaiz());
            if (izq.getRaiz().equals("0")) {
                izq.setRaiz("#false");
            }
            if (izq.getRaiz().equals("1")) {
                izq.setRaiz("#true");
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
                    izqList.get(0).add(izq.getIzq().getRaiz());
                } else {
                    izqList.get(0).add("not " + izq.getRaiz());
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
                    derList.get(0).add(der.getIzq().getRaiz());
                } else {
                    derList.get(0).add("not " + der.getRaiz());
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
            System.out.println("Aqui & " + listaSalida);
            return listaSalida;

        } else if (r.equals("|")){
            if(izq.esHoja()){
                if (izq.getRaiz().equals("-")) {
                    izqList.get(0).add(izq.getIzq().getRaiz());
                } else {
                    izqList.get(0).add("not " + izq.getRaiz());
                }

            } else {
                if (izq != null){
                    izqList = transformarFNC(izq);
                }

            }
            if (der.esHoja()) {
                if (der.getRaiz().equals("-")) {
                    derList.get(0).add(der.getIzq().getRaiz());
                } else {
                    derList.get(0).add("not " + der.getRaiz());
                }

            } else {
                if (der != null){
                    derList = transformarFNC(der);
                }
            }
            int posListaSalida = 0;
            for(int j = 0; j <izqList.size(); j++) {
                for(int i= 0; i < derList.size(); i++){
                    listaSalida.add(new ArrayList<>());
                    listaSalida.get(posListaSalida).addAll(derList.get(i));
                    listaSalida.get(posListaSalida).addAll(izqList.get(j));
                    posListaSalida = posListaSalida+1;
                }
            }
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

    public static void crearArchivoClingo(ArrayList<ArrayList<String>> listaDeProposiciones, PrintWriter writer) {
            writer.print(":- ");
            for (int i = 0; i < listaDeProposiciones.size(); i++) {
                for (int j = 0; j < listaDeProposiciones.get(i).size(); j++) {
                    if(j+1 != listaDeProposiciones.get(i).size()) {
                        writer.print(listaDeProposiciones.get(i).get(j) + ", ");
                    } else {
                        writer.println(listaDeProposiciones.get(i).get(j) + ".");
                    }

                }
                if(i+1 != listaDeProposiciones.size()) {
                    writer.print(":- ");
                }
            }
    }
    public static void recorrerArbol(ArbolBin arbolEntrada) {
        if (arbolEntrada != null) {
            System.out.println("Raiz: " + arbolEntrada.getRaiz());

            if (arbolEntrada.getIzq() != null) {
                System.out.println("La izq de " + arbolEntrada.getRaiz() + " es: " + arbolEntrada.getIzq().getRaiz());
                recorrerArbol(arbolEntrada.getIzq());
            }

            if (arbolEntrada.getDer() != null) {
                System.out.println("La der de " + arbolEntrada.getRaiz() + " es: " + arbolEntrada.getDer().getRaiz());
                recorrerArbol(arbolEntrada.getDer());
            }

        }
    }
    public static void main(String[] args) {
        ArrayList<String> lineasFichero = lecturaLineasArchivo(args);
        HashSet<String> proposiciones = new HashSet<String>();
        String [] partesLineasFichero;
        Pointer pointer = new Pointer(-1);
        ArrayList<ArrayList<String>> listaDeProposiciones = new ArrayList<>();
        ArbolBin arbol;
        try {
            PrintWriter writer = new PrintWriter(args[0].split("\\.")[0]+"2.lp", "UTF-8");
            for (int i = 0; i<lineasFichero.size(); i++) {
                writer.println("% "+ lineasFichero.get(i));
                partesLineasFichero = (lineasFichero.get(i)).split(" ");
                System.out.println(partesLineasFichero[1]);
                for (int j = 0; j<partesLineasFichero.length; j++) {
                    if (!partesLineasFichero[j].equals(">") && !partesLineasFichero[j].equals("&") && !partesLineasFichero[j].equals("-")
                    && !partesLineasFichero[j].equals("=") && !partesLineasFichero[j].equals("%") && !partesLineasFichero[j].equals("|")
                    && !partesLineasFichero[j].equals(".") && !partesLineasFichero[j].equals("0") && !partesLineasFichero[j].equals("1"))
                    {
                        proposiciones.add(partesLineasFichero[j]);
                    }
                }
                arbol = crearArbol(partesLineasFichero, pointer);



                transformarNNF(arbol);

                recorrerArbol(arbol);

                listaDeProposiciones = transformarFNC(arbol);
                System.out.println(listaDeProposiciones);
                crearArchivoClingo(listaDeProposiciones, writer);
                pointer.setValor(-1);
            }
            writer.print("{");
            String [] itr = proposiciones.toArray(String[]::new);
            for(int x = 0; x<itr.length;x++) {
                if (x+1 != itr.length)
                    writer.print(itr[x] + ";");
                else
                    writer.print(itr[x]);
            }
            writer.println("}.");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
