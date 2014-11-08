/**
 * Componente Curricular: Módulo Integrado de Concorrecia e Conectividade
 * Autor: <Jhone Mendes>
 * Data:  <06/09/2014>
 *
 */

package br.uefs.bankcoin.model;

import java.io.Serializable;

/**
 * Contém atributos necessarios para uma conta no BankCoin, tais como, login
 * (identificador de conta), senha e saldo.
 *
 * @author Jhone Mendes
 */
public class Conta implements Serializable{
    private String login;
    private String senha;
    private String email;
    private int saldo;

    /**
     * Inicializa os atributos
     *
     * @param login
     * @param senha
     * @param email
     * @param saldo
     */
    public Conta(String login, String senha, String email, int saldo) {
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.saldo = saldo;
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
    public String getSenha() {
        return senha;
    }

    /**
     *
     * @param senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
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
    
    
}
