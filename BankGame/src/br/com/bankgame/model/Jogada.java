/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.model;

import java.util.List;

/**
 *
 * @author JhoneDarts
 */
public class Jogada {
    private String pagamentoIdConta;
    private int pagamentoValor;
    private int compraIdCasa;
    private int compraValor;
    private List<Venda> vendas;

    public Jogada(String pagamentoIdConta, int pagamentoValor, int compraIdCasa, int compraValor, List<Venda> vendas) {
        this.pagamentoIdConta = pagamentoIdConta;
        this.pagamentoValor = pagamentoValor;
        this.compraIdCasa = compraIdCasa;
        this.compraValor = compraValor;
        this.vendas = vendas;
    }

    public String getPagamentoIdConta() {
        return pagamentoIdConta;
    }

    public void setPagamentoIdConta(String pagamentoIdConta) {
        this.pagamentoIdConta = pagamentoIdConta;
    }

    public int getPagamentoValor() {
        return pagamentoValor;
    }

    public void setPagamentoValor(int pagamentoValor) {
        this.pagamentoValor = pagamentoValor;
    }

    public int getCompraIdCasa() {
        return compraIdCasa;
    }

    public void setCompraIdCasa(int compraIdCasa) {
        this.compraIdCasa = compraIdCasa;
    }

    public int getCompraValor() {
        return compraValor;
    }

    public void setCompraValor(int compraValor) {
        this.compraValor = compraValor;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }
    
    
    
}
