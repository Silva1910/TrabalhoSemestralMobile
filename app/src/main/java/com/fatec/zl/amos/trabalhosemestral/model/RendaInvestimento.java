package com.fatec.zl.amos.trabalhosemestral.model;

public class RendaInvestimento extends Renda {
    private float taxa = 1.15f;

    @Override
    public void setValorRenda(float valor) {
        super.setValorRenda(valor * taxa);
    }
}