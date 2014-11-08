/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.model;

import br.com.bankgame.exceptions.ConexaoFalhouException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JhoneDarts
 */
public class Conexao {       
    
    private static String myAnswerBC;    
    private static ThreadReceptoraBC threadReceptoraBC;
    private PrintStream outBC;
    private BufferedReader inBC;
    private Socket socketBC;
    
    public Conexao() throws ConexaoFalhouException, IOException {
        myAnswerBC = null;
        //conectando-se ao bank coin
            try {
            socketBC = new Socket("localhost", 1307);
        } catch (IOException ex) {
            try {
                socketBC = new Socket("localhost", 1308);
            } catch (IOException ex1) {
                try {
                    socketBC = new Socket("localhost", 1309);
                } catch (IOException ex2) {
                    throw new ConexaoFalhouException("Erro ao tentar conectar");
                }
            }
        }  
        inBC = new BufferedReader(new InputStreamReader(socketBC.getInputStream()));
        outBC = new PrintStream(socketBC.getOutputStream());
        outBC.println("novo cliente");
        outBC.flush();
        String porta = inBC.readLine();
        System.out.println(porta);
        socketBC = new Socket("localhost", Integer.parseInt(porta));
        if (!socketBC.isBound()) {
            throw new ConexaoFalhouException("Erro ao tentar conectar");
        }
        
        System.out.println("conectado a bankCoin");
        inBC = null;
        outBC = new PrintStream(socketBC.getOutputStream());
        outBC.println("ok");
        outBC.flush();
        threadReceptoraBC = new ThreadReceptoraBC(socketBC);
        threadReceptoraBC.start();

    }
    
    public void callServerBCoinJustSend(String metodo, String json) {
        outBC.println(metodo);
        outBC.flush();
        outBC.println(json);
        outBC.flush();
    }
    
    public String callServerBCoin(String metodo, String json) throws ConexaoFalhouException{        
        myAnswerBC = null;
        outBC.println(metodo);
        outBC.flush();
        outBC.println(json);
        outBC.flush();
        return respostaServidorBCoin();
    }  
    
    private String respostaServidorBCoin() throws ConexaoFalhouException {
        try {
            Thread.sleep(400);//espera o servidor setar a myAnwer por meio da threadReceptora
        } catch (InterruptedException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (myAnswerBC == null){
            try {
                Thread.sleep(2100);//espera por mais tempo o servidor setar a myAnwer por meio da threadReceptora
            } catch (InterruptedException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }        
        }
        if (myAnswerBC == null)
            throw new ConexaoFalhouException(); 
        return myAnswerBC;
    }
    
    public static void setRespostaBC(String respostaDeOutro) {
        myAnswerBC = respostaDeOutro;
    } 
}
