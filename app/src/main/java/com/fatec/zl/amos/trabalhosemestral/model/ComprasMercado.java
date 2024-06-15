package com.fatec.zl.amos.trabalhosemestral.model;

import java.time.LocalDate;

public class ComprasMercado extends Compras {
    private LocalDate data;
    private String mercado;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getMercado() {
        return mercado;
    }

    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    @Override
    public String toString() {
        return  '\'' +" ## Compras de Mercado " + '\'' +
                " # ID-> " + getIDCompra() + '\'' +
                " $ Valor-> " + getValor() + '\'' +
                " # quantidade-> " + getQuantidade() + '\'' +
                " # data-> " + data + '\'' +
                " # mercado-> '" + mercado +  '\'' +
                " ________________________________________________________" +  '\'';
    }
}



