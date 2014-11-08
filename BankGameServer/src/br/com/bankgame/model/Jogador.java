/**
 * Componente Curricular: Módulo Integrado de Concorrecia e Conectividade
 * Autor: <Jhone Mendes>
 * Data:  <06/09/2014>
 *
 */

package br.com.bankgame.model;

import java.io.Serializable;

/**
 * Contém atributos necessarios para uma conta no BankCoin, tais como, login
 * (identificador de conta), senha e saldo.
 *
 * @author Jhone Mendes
 */
public class Jogador implements Serializable{
    private String login;
    private int saldo;
    private boolean pronto;
    private boolean saldoOK;

    /**
     * Inicializa os atributos
     *
     * @param login
     */
    public Jogador(String login) {
        this.login = login;
        this.saldo = 500;
        this.pronto = false;
        this.saldoOK = false;
    }

    /**
     *
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }
   
    /**
     *
     * @return
     */
    public int getSaldo() {
        return saldo;
    }

    /**
     *
     * @param saldo
     */
    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public boolean isPronto() {
        return pronto;
    }

    public void setPronto(boolean pronto) {
        this.pronto = pronto;
    }

    public boolean isSaldoOK() {
        return saldoOK;
    }

    public void setSaldoOK(boolean saldoOK) {
        this.saldoOK = saldoOK;
    }
    
    
}
