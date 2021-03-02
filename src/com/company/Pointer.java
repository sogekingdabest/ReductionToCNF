package com.company;

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
