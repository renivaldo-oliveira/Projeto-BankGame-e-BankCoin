/**
 * Componente Curricular: Módulo Integrado de Concorrecia e Conectividade
 * Autor: <Jhone Mendes>
 * Data:  <06/09/2014>
 *
 */
package br.uefs.bankcoin.model;

/**
 * Usada para transportar os valores de saldo, do banco e do cliente logado.
 *
 * @author Jhone Mendes
 */
public class Saldos {
    private int conta;
    private int global;

    /**
     * Inicializa os atributos
     *
     * @param conta
     * @param global
     */
    public Saldos(int conta, int global) {
        this.conta = conta;
        this.global = global;
    }

    /**
     *
     * @return
     */
    public int getConta() {
        return conta;
    }

    /**
     *
     * @return
     */
    public int getGlobal() {
        return global;
    }
}
