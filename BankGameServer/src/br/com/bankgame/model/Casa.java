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
public class Casa {
    private int id;
    private String dono;
    private int qtdPredios;

    public Casa(int id, String dono, int qtdPredios) {
        this.id = id;
        this.dono = dono;
        this.qtdPredios = qtdPredios;
    }

    public int getId() {
        return id;
    }
    
    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public int getQtdPredios() {
        return qtdPredios;
    }

    public void setQtdPredios(int qtdPredios) {
        this.qtdPredios = qtdPredios;
    }
    
}
