package com.company;


public class Main {

    private static void crearArbol(String[] lineasFichero, int pointer){
        String r = lineasFichero[pointer++];

        if (r.equals("&") || r.equals("|") || r.equals(">") || r.equals("=") || r.equals("%") ){
            System.out.println(r + " MACAQUINHO " + pointer);
            crearArbol(lineasFichero, pointer);
        } else if(r.equals("-") || r.equals("0") || r.equals("1")){
            return;
        } else {
            return;
        }
    }

    public static void main(String[] args) {
        String[] lineasFichero = LeerFichero.lecturaLineasArchivo(args);
        int pointer = -1;
        crearArbol(lineasFichero[0].split(" "), pointer);
    }
}
