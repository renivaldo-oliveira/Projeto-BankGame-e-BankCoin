/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.controller;

import br.com.bankgame.exceptions.ConexaoFalhouException;
import br.com.bankgame.exceptions.ContaJaConectadaException;
import br.com.bankgame.exceptions.ContaJaExisteException;
import br.com.bankgame.exceptions.DadosInvalidosException;
import br.com.bankgame.model.BankGameClient;
import br.com.bankgame.model.Sala;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *
 * @author JhoneDarts
 */
public class Controller {
    private static Controller instance;   
    private BankGameClient client;
    
    public static Controller getInstance() throws ConexaoFalhouException, IOException{
        if (instance == null)
            instance = new Controller();
        return instance;
    }
    
    public static void destroyController(){
        instance = null;
    }
    
    private Controller() throws ConexaoFalhouException, IOException {
        this.client = new BankGameClient();
    }
    
    public void cadastrar(String login, String senha, String email, int valor) throws ContaJaExisteException, ConexaoFalhouException, NoSuchAlgorithmException, UnsupportedEncodingException{
        client.cadastrar(login, senha, email, valor);
    }
    
    public String logar(String login, String senha) throws DadosInvalidosException, ConexaoFalhouException, NoSuchAlgorithmException, UnsupportedEncodingException, ContaJaConectadaException{
        return client.logar(login, senha);
    }
    
    public void transferirBCoin(String conta, int valor) throws ConexaoFalhouException, DadosInvalidosException{
        client.transferirBCoin(conta, valor);
    }     

    public void testarConexoes(){
        client.testarConexoes();
    }

    public boolean entrarSala(String nomeSala) throws ConexaoFalhouException {
        return client.entrarSala(nomeSala);
    }

    public boolean criarSala(String nome, String senha) throws ConexaoFalhouException {
        return client.criarSala(nome, senha);
    }

    public void sairDaSala(){
        client.sairDaSala();
    }

    public void solicitarFirstLobby() {
        client.solicitarFirstLobby();
    }

    public void atualizarLobbyCancel() {
        client.atualizarLobbyCancel();
    }

    public void solicitarAtualizarSala() {
        client.solicitarAtualizarSala();
    }

    public void jogadorPronto() {
        client.jogadorPronto();
    }

    public void saldoOK() {
        client.saldoOK();
    }

    public void moverJogador(int jogador, int dado) {
        client.moverJogador(jogador, dado);
    }

    public void enviarJogada(String jsonJogada) {
        client.enviarJogada(jsonJogada);
    }

    public void enviarJogadorFaliu(String jsonJogada) {
        client.enviarJogadorFaliu(jsonJogada);
    }

    public void solicitarPrimeiro() {
        client.solicitarPrimeiro();
    }
}
