/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uefs.bankcoin.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author JhoneDarts
 */
public class Hospedeiro implements Runnable{
    private Socket socket;
    private int nServer;
    private BufferedReader in;
    private PrintStream out;
    private boolean inicializado;
    private boolean executando;
    private Thread thread;
    
    /**
     * Inizializa os Atributos.
     *
     * @param socket
     * @param nServer
     * @throws Exception
     */
    public Hospedeiro (Socket socket, int nServer) throws Exception{
        this.socket = socket;
        this.nServer = nServer;
        
        this.inicializado = false;
        this.executando = false;
        
        open();
    }
    
    private void open() throws IOException{
        try{
            in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream());
            inicializado = true;
        } catch (IOException e){
            close();
            throw e;
        }
        
        
    }
     private void close(){
         if (in != null){
             try{
                 in.close();
             }catch(Exception e){
                 System.out.println(e);
             }
         }
         if (out != null){
             try{
                 out.close();
             }catch(Exception e){
                 System.out.println(e);
             }
         }
         try{
             socket.close();
         }catch(Exception e){
             System.out.println(e);
         }
         
         in = null;
         out = null;
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
         executando = false;
         if (thread!=null)
            thread.join();     
     }
     
     @Override
     public void run(){
        while(executando){
            try{     
                socket.setSoTimeout(2500);
                      
                String msg = in.readLine();
                System.out.println("mensagem recebida do servidor ("+
                        socket.getInetAddress().getHostName()+": ["+ socket.getPort()+
                        "]): "+msg);
                
                //switch (metodo){              
                
                if("atualizar".equals(msg)){
                    String a = Voldemort.getMudancas(nServer);
                    out.println(a);
                    System.out.println(a+" hospedeiro");
                }
                
                if("stop".equals(msg))
                    break;

                                
                out.flush();
            }catch(SocketTimeoutException e){
                //ignorar
            }catch(Exception e){
                System.out.println(e);
                break;
            }
        }
        System.out.println("Encerrando conexao com o servidor.");           
        close();
     }
}
