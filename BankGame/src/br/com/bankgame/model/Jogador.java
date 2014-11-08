/**
 * Componente Curricular: Módulo Integrado de Concorrecia e Conectividade
 * Autor: <Jhone Mendes>
 * Data:  <06/09/2014>
 *
 */

package br.com.bankgame.model;

/**
 * 
 *
 * @author Jhone Mendes
 */
public class Jogador {
    private String login;
    private double saldo;
    private boolean pronto;

    /**
     * Inicializa os atributos
     *
     * @param login
     */
    public Jogador(String login) {
        this.login = login;
        this.saldo = 500;
        this.pronto = false;
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
    public double getSaldo() {
        return saldo;
    }

    /**
     *
     * @param saldo
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean isPronto() {
        return pronto;
    }

    public void setPronto(boolean pronto) {
        this.pronto = pronto;
    }
    
    
}
