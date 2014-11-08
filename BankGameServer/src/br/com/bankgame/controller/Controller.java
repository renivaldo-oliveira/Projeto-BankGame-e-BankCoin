/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.controller;

import br.com.bankgame.exceptions.ConexaoFalhouException;
import br.com.bankgame.exceptions.DadosInvalidosException;
import br.com.bankgame.view.Main;
import br.com.bankgame.model.BankGameServer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JhoneDarts
 */
public class Controller {
    private BankGameServer servidor;
    private boolean ligado;
    private int porta = 1337;
    
    public Controller (){
        ligado = false;
    }
    
    public boolean power() throws IOException, ConexaoFalhouException, DadosInvalidosException{
        if (ligado) {
            try {
                //desligar
                servidor.stop();
                
                servidor = null;
                ligado = false;
                return false;
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{      
            //ligar
            servidor = new BankGameServer(porta);
            servidor.start();            
            ligado = true;
            return true;  
        } 
        return false;
    }
    
    public boolean reset() throws IOException, ConexaoFalhouException, DadosInvalidosException{
        if(ligado){
            try {
                //desligar
                servidor.stop();
                servidor = null;
                //porta += 1;
                System.out.println(porta);
                ligado = false;                
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }                   
            //ligar
            servidor = new BankGameServer(porta);
            servidor.start();
            ligado = true;
            return true;           
            
        }
        return false;
    }

}
