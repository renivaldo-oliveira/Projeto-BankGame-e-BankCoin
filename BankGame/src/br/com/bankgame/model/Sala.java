/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JhoneDarts
 */
public class Sala {
    private String nome;
    private String senha;
    private boolean disponivel;
    private List<Jogador> jogadores;

    public Sala(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        this.disponivel = true;
        this.jogadores = new ArrayList<Jogador>();
    }

    public Sala(String nome, String senha, boolean disponivel, List<Jogador> jogadores) {
        this.nome = nome;
        this.senha = senha;
        this.disponivel = disponivel;
        this.jogadores = jogadores;
    }
    
    

    public String getNome() {
        return nome;
    }
    
    public String getSenha() {
        return senha;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public boolean addJogador(Jogador jogador) {
        if (disponivel){
            this.jogadores.add(jogador);
            if (jogadores.size() == 4)
                disponivel = false;
            return true;
        }     
        return false;
    }
    
    
}

//changes come soon
