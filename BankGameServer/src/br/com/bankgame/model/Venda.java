/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.model;

/**
 *
 * @author JhoneDarts
 */
class Venda {
    private int idCasa;
    private int valor;

    public Venda(int idCasa, int valor) {
        this.idCasa = idCasa;
        this.valor = valor;
    }

    public int getIdCasa() {
        return idCasa;
    }

    public int getValor() {
        return valor;
    }
    
}
