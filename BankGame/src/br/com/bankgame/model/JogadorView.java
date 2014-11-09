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
public class JogadorView {  
    private int id;
    private String nome;
    private int x;
    private int y;
    private int casa;
    private int saldo;
    private boolean bonusRodada;

    public JogadorView(int x, int y, int id, String nome) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.nome = nome;
        this.casa = 0;
        this.saldo = 500;
        this.bonusRodada = false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public int getId(){
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCasa() {
        return casa;
    }

    public void setCasa(int casa) {
        this.casa = casa;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public boolean isBonusRodada() {
        return bonusRodada;
    }

    public void setBonusRodada(boolean bonusRodada) {
        this.bonusRodada = bonusRodada;
    }
    
    
}
