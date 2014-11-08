/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uefs.bankcoin.controller;

import br.uefs.bankcoin.gui.Main;
import br.uefs.bankcoin.model.BankCoinServer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JhoneDarts
 */
public class Controller {
    private BankCoinServer servidor;
    private boolean ligado;
    private int porta = 1307;
    
    public Controller (){
        ligado = false;
    }
    
    public boolean power(){
       if (ligado){ 
            try {
                //desligar
                servidor.stop();
                servidor = null;
                ligado = false;
                return false;
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }else{      
           try {
               //ligar
               servidor = new BankCoinServer(porta);
               servidor.start();
               ligado = true;
               return true;
           } catch (IOException ex) {
               Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
           }
           
        } 
        return false;
    }
    
    public boolean reset(){
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
                        
            try {
                //ligar
                servidor = new BankCoinServer(porta);
                servidor.start();
                ligado = true;
                return true;
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return false;
    }
}
