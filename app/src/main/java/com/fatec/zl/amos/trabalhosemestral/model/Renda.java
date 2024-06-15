package com.fatec.zl.amos.trabalhosemestral.model;

import java.util.ArrayList;
import java.util.List;

public class Renda {
    protected float valorRenda;

    protected int IDRenda;
    protected String fonteRenda;

    private boolean rendaInvestida;

    public boolean isRendaInvestida() {
        return rendaInvestida;
    }

    public void setRendaInvestida(boolean rendaInvestida) {
        this.rendaInvestida = rendaInvestida;
    }

    public int getIDRenda() {
        return IDRenda;
    }

    public void setIDRenda(int IDRenda) {
        this.IDRenda = IDRenda;
    }

    protected static List<Renda> rendas = new ArrayList<>();
    protected float rendaTotal;

    public float getRendaTotal() {
        return rendaTotal;
    }

    public float getValorRenda() {
        return valorRenda;
    }

    public void setValorRenda(float valorRenda) {
        this.valorRenda = valorRenda;
    }

    public String getFonteRenda() {
        return fonteRenda;
    }

    public void setFonteRenda(String fonteRenda) {
        this.fonteRenda = fonteRenda;
    }

    public float calcularRendaTotal() {
        rendaTotal = 0;

        for (Renda r : rendas) {
            rendaTotal += r.getValorRenda();
        }
        return rendaTotal;
    }

    public static List<Renda> getRendas() {
        return rendas;
    }

    @Override
    public String toString() {
        return '\'' + " $$ Renda " +
                " ID da Renda" +  getIDRenda() +
                " $$ valor da Renda-> " + getValorRenda() +
                " $$ fonte da Renda-> " + getFonteRenda() +

        " ________________________________________________________" +  '\'';
    }
}
