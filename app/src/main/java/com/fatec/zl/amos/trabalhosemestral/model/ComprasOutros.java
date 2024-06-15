package com.fatec.zl.amos.trabalhosemestral.model;

public class ComprasOutros extends Compras{
    private String descricao;


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return '\'' + " ** Outras Compras" +
                " * ID-> " + getIDCompra() +
                " $ Valor-> " + getValor() +
                " * quantidade-> " + getQuantidade() +
                " * descricao-> " + descricao + '\'' +
                " ________________________________________________________" +  '\'';
    }
}

