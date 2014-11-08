/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.model;

import br.com.bankgame.exceptions.ConexaoFalhouException;
import br.com.bankgame.exceptions.ContaJaConectadaException;
import br.com.bankgame.exceptions.ContaJaExisteException;
import br.com.bankgame.exceptions.DadosInvalidosException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *
 * @author JhoneDarts
 */
public class BankGameClient {
    private Conexao conexao;
    private String saldoConta;
    
    public BankGameClient() throws ConexaoFalhouException, IOException {
        this.conexao = new Conexao();
        this.saldoConta = null;
    }
    
    /**metodo pra criar conta
     * @param login
     * @param senha
     * @param email
     * @param valor
     * @throws ContaJaExisteException
     * @throws br.com.bankgame.exceptions.ConexaoFalhouException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException **/
    public void cadastrar(String login, String senha, String email, int valor) throws ContaJaExisteException, ConexaoFalhouException, NoSuchAlgorithmException, UnsupportedEncodingException{
            senha = criptografar(senha);    
            String answer = conexao.callServerBCoin("cadastrar", JSONParse.generateConta(login, senha, email, valor));
            if (answer.equals("1"))		
                conexao.callServerBGameJustSend("login", login);
            else
                throw new ContaJaExisteException("Essa conta ja existe");
    }

    /**metodo pra realizar login
     * @param login
     * @param senha
     * @throws DadosInvalidosException
     * @throws br.com.bankgame.exceptions.ConexaoFalhouException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException **/
    public String logar(String login, String senha) throws DadosInvalidosException, ConexaoFalhouException, NoSuchAlgorithmException, UnsupportedEncodingException, ContaJaConectadaException{
        senha = criptografar(senha);
        String answer = conexao.callServerBCoin("login", JSONParse.generateConta(login, senha, "", 0));
        if (answer.equals("-1")) 
            throw new DadosInvalidosException();            
        else if (answer.equals("-2"))
            throw new ContaJaConectadaException();
        else
            conexao.callServerBGameJustSend("login", login);
        return answer;
    }
	
    /**metodo pra transferencia
     * @param conta
     * @param valor 
     * @throws DadosInvalidosException
     * @throws br.com.bankgame.exceptions.ConexaoFalhouException **/
    public void transferirBCoin(String conta, int valor) throws ConexaoFalhouException, DadosInvalidosException{		
        if (conexao.callServerBCoin("transferir", JSONParse.generateTransferencia(conta, valor)).equals("-1")) {
            throw new DadosInvalidosException("Erro ao transferir, verifique se a conta eh valida, e se o seu saldo eh suficiente");
        }
    }

    private String criptografar(String original) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(original.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }

        return hexString.toString();
    }
    
    public void solicitarFirstLobby() {
        conexao.callServerBGameJustSend("atualizarfirstlobby", "");        
    }
    
    public void atualizarLobbyCancel() {
        conexao.callServerBGameJustSend("atualizarlobbycancel", "");
    }

    public void testarConexoes(){
        conexao.callServerBCoinJustSend("teste", "");
        conexao.callServerBGameJustSend("teste", "");
    }

    public boolean entrarSala(String nomeSala) throws ConexaoFalhouException {
        String answer = conexao.callServerBGame("entrarsala", nomeSala);
        return answer.equals("1");
    }

    public boolean criarSala(String nome, String senha) throws ConexaoFalhouException {
        String json = JSONParse.generateCriarSala(nome, senha);
        String answer = conexao.callServerBGame("criarsala", json);
        return answer.equals("1");
    }

    public void sairDaSala(){
        conexao.callServerBGameJustSend("sairdasala", "");
    }

    public void solicitarAtualizarSala() {
        conexao.callServerBGameJustSend("atualizarsala", "");
    }

    public void jogadorPronto(){
        conexao.callServerBGameJustSend("pronto", "");
    }

    public void saldoOK() {
        conexao.callServerBGameJustSend("saldook", "");
    }

    public void moverJogador(int jogador, int dado) {
        conexao.callServerBGameJustSend("moverjogador", jogador+""+dado);
    }

    public void enviarJogada(String jsonJogada) {
        conexao.callServerBGameJustSend("efetuarjogada", jsonJogada);
    }

    public void enviarJogadorFaliu(String jsonJogada) {
        conexao.callServerBGameJustSend("jogadorfaliu", jsonJogada);
    }

    public void solicitarPrimeiro() {
        conexao.callServerBGameJustSend("sortearprimeiro", "");
    }

    

    
}
