/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.model;

import br.com.bankgame.view.Inicial;
import br.com.bankgame.view.Lobby;
import br.com.bankgame.view.TelaJogo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 * @author JhoneDarts
 */
public class ThreadReceptoraBG  implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private PrintStream out;
    private boolean inicializado;
    private boolean executando;
    private Thread thread;    
      
    /**
     * Inizializa os Atributos.
     *
     * @param socket
     * @throws IOException
     */
    public ThreadReceptoraBG (Socket socket) throws IOException{
        this.socket = socket;
        
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
                String json = in.readLine();

                System.out.println("mensagem recebida do servidor bankGame ("+
                        socket.getInetAddress().getHostName()+": ["+ socket.getPort()+
                        "]): "+metodo+"  "+json);

                //switch (metodo){

                if("expulsar".equals(metodo))//ainda nao usado
                    break;    

                if("atualizarfirstlobby".equals(metodo)){
                    Lobby.atualizarFirstLobby(JSONParse.degenerateSalaList(json));
                }


                if("atualizarlobby".equals(metodo)){
                    Lobby.atualizarLobby(JSONParse.degenerateSalaLobby(json));
                }                  

                if ("atualizarsala".equals(metodo)){
                    if(json.equals("comecarjogo"))
                        Lobby.comecarJogo();
                    else
                        Lobby.atualizarSala(JSONParse.degenerateSala(json));
                }

                if ("jogadorpronto".equals(metodo)){

                }

                if("resposta".equals(metodo)){
                    respostaParaConexao(json);
                }
                
                //em jogo
                if("atualizarjogo".equals(metodo)){
                    TelaJogo.getInstance().atualizarJogo(json);
                }
                
                if("primeiro".equals(metodo)){
                    TelaJogo.getInstance().primeiro(json);
                }
                
                if("jogadorposicao".equals(metodo)){
                    TelaJogo.getInstance().jogadorPosicao(json);
                }
                
                if(metodo == null && json == null)
                    throw new SocketException();

                out.flush();

            } catch (SocketTimeoutException e){
                //ignorar            
            } catch (SocketException e){
                //fazer algo caso perca a conexao
                if(Inicial.getVisible())
                    Inicial.servidoresOFF();
                else
                    Lobby.servidoresOFF();
                System.out.println("Servidor bankGame caiu");
                Conexao.closeThreadReceptoraBC();
                break;
            } catch (IOException e) {
                System.out.println(e);
                break;
            }            
        }
        
        System.out.println("Encerrando conexao receptora com bakGame.");
        close();
     }

    public void respostaParaConexao(String resposta) {
        Conexao.setRespostaBG(resposta);
    }
     
}