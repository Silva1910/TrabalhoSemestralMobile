package com.fatec.zl.amos.trabalhosemestral.model;

public class ComprasRoupas extends Compras {
private String tamanho;
private String tipoRoupa;

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getTipoRoupa() {
        return tipoRoupa;
    }

    public void setTipoRoupa(String tipoRoupa) {
        this.tipoRoupa = tipoRoupa;
    }

    @Override
    public String toString() {
        return '\'' + "!! Compras de Roupas " +
                " ! ID-> " + getIDCompra() +
                " $ Valor-> " + getValor() +
                " ! quantidade-> " + getQuantidade() +
                " ! tamanho-> " + tamanho +
                " ! tipoRoupa-> " + tipoRoupa + '\''+
        " ________________________________________________________" +  '\'';
    }
}

