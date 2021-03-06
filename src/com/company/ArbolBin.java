package com.company;

/*** AUTORES ***/
/** Angel Alvarez Rey Login UDC: angel.alvarez.rey **/
/** Daniel Olañeta Fariña Login UDC: daniel.olaneta.farina **/
/** GitHub: https://github.com/Lorudarkuh (Angel Alvarez Rey)**/
/** GitHub: https://github.com/sogekingdabest (Daniel Olañeta Fariña)**/
/*** ***/

public class ArbolBin {
    private String raiz;
    private ArbolBin izq;
    private ArbolBin der;

    public String getRaiz() {
        return raiz;
    }

    public void setRaiz(String raiz) {
        this.raiz = raiz;
    }

    public ArbolBin getIzq() {
        return izq;
    }

    public void setIzq(ArbolBin izq) {
        this.izq = izq;
    }

    public ArbolBin getDer() {
        return der;
    }

    public void setDer(ArbolBin der) {
        this.der = der;
    }

    public ArbolBin(String raiz, ArbolBin izq, ArbolBin der) {
        this.raiz = raiz;
        this.izq = izq;
        this.der = der;
    }

    public boolean esHoja(){
        return (this.getDer() == null);
    }
}
