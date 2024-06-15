package com.fatec.zl.amos.trabalhosemestral.model;

public abstract class Compras {

   protected int IDCompra;

   public int getIDCompra() {
      return IDCompra;
   }

   public void setIDCompra(int IDCompra) {
      this.IDCompra = IDCompra;
   }

   protected int quantidade;

   protected float valor;

   public int getQuantidade() {
      return quantidade;
   }

   public void setQuantidade(int quantidade) {
      this.quantidade = quantidade;
   }

   public float getValor() {
      return valor;
   }

   public void setValor(float valor) {
      this.valor = valor;
   }
}
