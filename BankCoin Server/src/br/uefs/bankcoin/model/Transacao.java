/**
 * Componente Curricular: Módulo Integrado de Concorrecia e Conectividade
 * Autor: <Jhone Mendes>
 * Data:  <06/09/2014>
 *
 */

package br.uefs.bankcoin.model;

/**
 * Representa uma transação do BankCoin, portanto ela possui atributos idConta e 
 * valor, que são o identificador de conta da conta que receberá a o valor
 * representado pelo atributo valor.
 *
 * @author Jhone Mendes
 */
public class Transacao {
    private String idConta;
    private int valor;

    /**
     * Inicializa os atributos
     *
     * @param idConta
     * @param valor
     */
    public Transacao(String idConta, int valor) {
        this.idConta = idConta;
        this.valor = valor;
    }

    /**
     *
     * @return
     */
    public String getIdConta() {
        return idConta;
    }

    /**
     *
     * @return
     */
    public int getValor() {
        return valor;
    }    
    
}
