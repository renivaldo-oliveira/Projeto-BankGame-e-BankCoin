/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.model;

import br.com.bankgame.view.Inicial;
import br.com.bankgame.view.Lobby;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JhoneDarts
 */
public class ThreadReceptoraBC  implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private boolean inicializado;
    private boolean executando;
    private Thread thread;    
      
    /**
     * Inizializa os Atributos.
     *
     * @param socket
     * @throws IOException
     */
    public ThreadReceptoraBC (Socket socket) throws IOException{
        this.socket = socket;
        
        this.inicializado = false;
        this.executando = false;
        
        open();
    }
    
    private void open() throws IOException{
        try{
            in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            inicializado = true;
        } catch (IOException e){
            close();
            throw e;
        }
        
        
    }
     private void close(){  
         in = null;
         socket = null;
         
         inicializado = false;
         executando = false;
         thread = null;
     }
     
    /**
     * Inicializa o servico ("atendimento").
     *
     */
    public void start(){
         if(!inicializado||executando){
             return;
         }
         
         executando = true;
         thread = new Thread(this);
         thread.start();
     }
     
    /**
     * Finaliza a conexao liberando os dados alocados.
     *
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException{    
        try {
            PrintStream out = new PrintStream(socket.getOutputStream());
            out.println("logout");
            out.flush();
            out.println("");
            out.flush();
        } catch (IOException | NullPointerException ex) {
            //ignorar
        }
         if (thread!=null)
            thread.join();     
         close();
     }
     
    @Override
    public void run(){
        while(executando){
            try{                
                socket.setSoTimeout(2500);
                String metodo = in.readLine();
                System.out.println("mensagem recebida do servidor bankCoin ("+
                        socket.getInetAddress().getHostName()+": ["+ socket.getPort()+
                        "]): "+metodo);

                if("atualizarsaldo".equals(metodo)){                    
                    String saldo = in.readLine();
                    Lobby.atualizarSaldo(saldo);
                }else{
                    respostaParaConexao(metodo);
                }
                
                if(metodo == null)
                    throw new SocketException();
                
            } catch (SocketTimeoutException e){
                //ignorar            
            } catch (SocketException e){
                //fazer algo caso perca a conexao                
                System.out.println("Servidor bankCoin caiu");
                break;
            } catch (IOException e) {
                System.out.println(e);
                break;
            }            
        }
        
        System.out.println("Encerrando conexao receptoraBC.");
        close();
    }

    private void respostaParaConexao(String resposta) {
        Conexao.setRespostaBC(resposta);
    }
     
}