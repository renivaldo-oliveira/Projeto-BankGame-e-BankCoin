/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.model;

import br.com.bankgame.exceptions.ConexaoFalhouException;
import br.com.bankgame.exceptions.DadosInvalidosException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JhoneDarts
 */
public class Jogo {
    private static List<SalaDeJogo> salasDeJogo = new ArrayList<SalaDeJogo>();

    public static List<SalaDeJogo> getSalasDeJogo() {
        return salasDeJogo;
    }
    
    public static void addJogador(Atendente atendente){
        boolean salaExiste = false;
        for (SalaDeJogo sala : salasDeJogo){
            if (sala.getIdSala().equals(atendente.getIdSalaLogada())){
                sala.getAtendentes().add(atendente);
                sala.getJogadores().add(new Jogador(atendente.getIdContaLogada()));
                salaExiste = true;
            }                
        }
        if (!salaExiste){
            SalaDeJogo sala = new SalaDeJogo(atendente.getIdSalaLogada());
            sala.getAtendentes().add(atendente);
            sala.getJogadores().add(new Jogador(atendente.getIdContaLogada()));
            salasDeJogo.add(sala);            
        }
    }
    
    public static void removeJogador(Atendente atendente){// usada se o jogador "cair" ou falir
        SalaDeJogo salaExcluida = null;
        for (SalaDeJogo sala : salasDeJogo){
            if (sala.getIdSala().equals(atendente.getIdSalaLogada())){
                String proximo = sala.proximo(atendente.getIdContaLogada());
                sala.getAtendentes().remove(atendente);
                Jogador jogadorExcluido = sala.getJogador(atendente.getIdContaLogada());
                sala.getJogadores().remove(jogadorExcluido);
                //limpar o cara da partida, ou melhor, limpar a partida do cara
                for (Casa casa : sala.getCasas()){
                    if(!casa.getDono().equals("")){
                        if (casa.getDono().equals(jogadorExcluido.getLogin())){
                            casa.setDono("");
                            casa.setQtdPredios(0);
                        }
                    }
                }
                /*if(jogadorExcluido.getSaldo()>500){
                    //partilha de bens, eba!!!
                    int bonus = jogadorExcluido.getSaldo();
                    bonus = bonus/sala.getJogadores().size();
                    for (Jogador jogador : sala.getJogadores()){
                        jogador.setSaldo(jogador.getSaldo()+bonus);
                    }                    
                    //notifica os outros jogadores sobre o afk  
                    String jogoAtualizado = JSONParse.generateJogo("!-1"+atendente.getIdContaLogada(), sala);    
                    sala.notificar(jogoAtualizado);
                }else if(jogadorExcluido.getSaldo()<0 && jogadorExcluido.getSaldo()<=500){
                    //salva o saldo do afk
                    try {                        
                        BankGameServer.transferirBCoin(jogadorExcluido.getLogin(), jogadorExcluido.getSaldo());
                    } catch (ConexaoFalhouException | DadosInvalidosException ex) {
                        System.out.println("Houston, temos um problema... (in removerJogador)");
                    }                
                    //notifica os outros jogadores sobre o afk  
                    String jogoAtualizado = JSONParse.generateJogo("!-1"+atendente.getIdContaLogada(), sala);    
                    sala.notificar(jogoAtualizado);
                }else{*/
                    if(!(jogadorExcluido.getSaldo()>500)){
                    //notifica os outros jogadores que um jogador faliu
                    sala.notificar("!--1"+atendente.getIdContaLogada());
                    String jogoAtualizado = JSONParse.generateJogo(proximo, sala);    
                    sala.notificar(jogoAtualizado);
                    if (sala.getJogadores().size() == 1){try {
                        //fim de jogo
                        BankGameServer.transferirBCoin(sala.getJogadores().get(0).getLogin(), sala.getJogadores().get(0).getSaldo());
                        } catch (ConexaoFalhouException | DadosInvalidosException ex) {
                            Logger.getLogger(Jogo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        salaExcluida = sala;
                    }
                }
            }            
        }
        salasDeJogo.remove(salaExcluida);
    }
    
    public static void efetuarJogada(Atendente atendente, String json){
        Jogada jogada = JSONParse.degenerateJogada(json);
        SalaDeJogo salaAtual = null;
        for (SalaDeJogo sala : salasDeJogo){
            if (sala.getIdSala().equals(atendente.getIdSalaLogada()))
                salaAtual = sala;
        }
        //Compra
        if (jogada.getCompraIdCasa() != 0){// vai comprar alguma casa (true)
            if (salaAtual.getCasas().get(jogada.getCompraIdCasa()).getDono().equals("")){
                salaAtual.getCasas().get(jogada.getCompraIdCasa()).setDono(atendente.getIdContaLogada());
            }else{//a casa é dele, entao adicionamos um predio
                int qtdPredios = salaAtual.getCasas().get(jogada.getCompraIdCasa()).getQtdPredios();
                salaAtual.getCasas().get(jogada.getCompraIdCasa()).setQtdPredios(qtdPredios + 1);
            }
            int saldo = salaAtual.getJogador(atendente.getIdContaLogada()).getSaldo();
            salaAtual.getJogador(atendente.getIdContaLogada()).setSaldo(saldo - jogada.getCompraValor());
        }
        //Venda(s)
        if (!jogada.getVendas().isEmpty()){
            for (Venda venda : jogada.getVendas()){
                salaAtual.getCasas().get(venda.getIdCasa()).setDono("");
                salaAtual.getCasas().get(venda.getIdCasa()).setQtdPredios(0);
                int saldo = salaAtual.getJogador(atendente.getIdContaLogada()).getSaldo();
                salaAtual.getJogador(atendente.getIdContaLogada()).setSaldo(saldo + venda.getValor());
            }            
            
        }
        //Pagamento
        if(jogada.getPagamentoValor()<0){
            Jogador jogadorPagamente = salaAtual.getJogador(atendente.getIdContaLogada());
            jogadorPagamente.setSaldo(jogadorPagamente.getSaldo() - jogada.getPagamentoValor());
        }            
        if (jogada.getPagamentoValor()>0){
            Jogador jogadorPago = salaAtual.getJogador(jogada.getPagamentoIdConta());
            if(jogadorPago != null)
                if(!jogadorPago.equals(""))
                    jogadorPago.setSaldo(jogadorPago.getSaldo() + jogada.getPagamentoValor());
            Jogador jogadorPagamente = salaAtual.getJogador(atendente.getIdContaLogada());
            jogadorPagamente.setSaldo(jogadorPagamente.getSaldo() - jogada.getPagamentoValor());
        }
        String proximo = salaAtual.proximo(atendente.getIdContaLogada());
        String jogoAtualizado = JSONParse.generateJogo(proximo, salaAtual);    
        salaAtual.notificar(jogoAtualizado);
    }
    
    public static void moverJogador(String posicao, String idSala){
        for(SalaDeJogo sala : salasDeJogo){
            if (sala.getIdSala().equals(idSala))
                sala.notificar("jogadorposicao", posicao);
        }
    }

    public static void sortearPrimeiro(String idSala){  //quando chegar a mensagem comecarJogo nos clientes        
        for (SalaDeJogo sala : salasDeJogo){     //apos 1s solicitarao este metodo o primeiro que chegar
            if (sala.getIdSala().equals(idSala)) // sorteia e pega o resultado  
                    sala.sortearPrimeiro();      // e o restante só pega o resultado
        }
        
    }
    
}

// colocar tempos nos turnos