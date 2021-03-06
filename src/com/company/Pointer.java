package com.company;

/*** AUTORES ***/
/** Angel Alvarez Rey Login UDC: angel.alvarez.rey **/
/** Daniel Olañeta Fariña Login UDC: daniel.olaneta.farina **/
/** GitHub: https://github.com/Lorudarkuh (Angel Alvarez Rey)**/
/** GitHub: https://github.com/sogekingdabest (Daniel Olañeta Fariña)**/
/*** ***/

public class Pointer {
    private int valor;

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int next() {
        return ++this.valor;
    }

    public Pointer(int valor) {
        this.valor = valor;
    }
}
