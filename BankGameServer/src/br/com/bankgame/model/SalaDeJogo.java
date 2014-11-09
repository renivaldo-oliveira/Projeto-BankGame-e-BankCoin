/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author JhoneDarts
 */
public class SalaDeJogo {
    private String idSala;
    private List<Atendente> atendentes;
    private List<Jogador> jogadores;
    private List<Casa> casas;
    private String primeiro;

    public SalaDeJogo(String idSala) {
        this.idSala = idSala;
        this.atendentes = new ArrayList<Atendente>();
        this.jogadores =  new ArrayList<Jogador>();
        this.casas = new ArrayList<Casa>();
        for (int i=0; i<20; i++)
            casas.add(new Casa(i, "", 0));   
        this.primeiro = null;
    }
    
    public void notificar(String json){
        for (Atendente atendente : atendentes){
            atendente.enviar("atualizarjogo", json);
        }
    }
    
    public void notificar(String metodo, String json){
        for (Atendente atendente : atendentes){
            atendente.enviar(metodo, json);
        }
    }

    public String getIdSala() {
        return idSala;
    }

    public List<Atendente> getAtendentes() {
        return atendentes;
    }

    public Jogador getJogador(String login){
        Jogador jogador = null;
        for (Jogador jogadorAux : jogadores){
            if (jogadorAux.getLogin().equals(login))
                jogador = jogadorAux;
        }
        return jogador;
    }
    
    public List<Jogador> getJogadores() {
        return jogadores;
    }   

    public List<Casa> getCasas() {
        return casas;
    }

    public String proximo(String atual) {
        String proximo = null;
        int i;
        for (i=0; i<atendentes.size(); i++){
            Atendente atendente = atendentes.get(i);
            if (atendente.getIdContaLogada().equals(atual)){
                if((i+1) == atendentes.size())
                    proximo = atendentes.get(0).getIdContaLogada();
                else
                    proximo = atendentes.get(i+1).getIdContaLogada();
            }
        }
       
        return proximo;
    }

    public void sortearPrimeiro() { 
        Random random = new Random();
        int a = 0;
        if (primeiro == null){
            a = random.nextInt(jogadores.size());
            primeiro = jogadores.get(a).getLogin();            
            primeiro = JSONParse.generatePrimeiro(primeiro, atendentes);
            notificar("primeiro", primeiro);
        }
        
    }

    public String getPrimeiro() {
        return primeiro;
    }
    
    
    
}
