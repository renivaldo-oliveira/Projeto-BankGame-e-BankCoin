/**
 * Componente Curricular: Módulo Integrado de Concorrecia e Conectividade
 * Autor: <Jhone Mendes>
 * Data:  <03/09/2014>
 *
 */

package br.uefs.bankcoin.model;

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
 * Auxilia @BankCoinServer realizando conexoes com os clientes e os "atendendo".
 *
 * @author Jhone Mendes
 */
public class Atendente implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private PrintStream out;
    private boolean inicializado;
    private boolean executando;
    private Thread thread;
    private boolean clientDesconectado;
    private Conta contaLogada;    
    private String client;
    
    /**
     * Inizializa os Atributos.
     *
     * @param socket
     * @throws Exception
     */
    public Atendente (Socket socket) throws Exception{
        this.socket = socket;        
        this.inicializado = false;
        this.executando = false;
        this.clientDesconectado = false;
        
        this.contaLogada = null;
        this.client = "";
        
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
         contaLogada = null;
         client = "";
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
                      
                String metodo = in.readLine();
                
                String json = in.readLine();
                System.out.println("mensagem recebida do cliente ("+
                        socket.getInetAddress().getHostName()+": ["+ socket.getPort()+
                        "]): "+metodo+"  "+json);
                
                //switch (metodo){
                
                if("logout".equals(metodo))
                    throw new SocketException();

                if("login".equals(metodo)){
                    System.out.println("metodo login");
                    Conta contaAcesso = JSONParse.degenerateConta(json);//json = login, senha e client, ex: alex, 123, bgame.
                    String clientAcesso = JSONParse.degenerateClient(json);
                    String answer = BankCoinServer.login(contaAcesso, clientAcesso);  
                    if (!(answer.equals("-1") || answer.equals("-2"))){                        
                        contaLogada = contaAcesso;
                        client = clientAcesso;
                        System.out.println(contaLogada.getLogin());
                    }
                    System.out.println(answer); 
                    out.println(answer);                                        
                }
                if("cadastrar".equals(metodo)){
                    System.out.println("metodo cadastrar");
                    Conta contaAcesso1 = JSONParse.degenerateConta(json);
                    if (BankCoinServer.cadastrar(contaAcesso1)) {  
                        contaLogada = contaAcesso1;
                        out.println("1");   
                        System.out.println("1"); 
                    }else{
                        out.println("0");
                        System.out.println("0"); 
                    }
                }
                if("transferir".equals(metodo)){
                    String answer = BankCoinServer.transferir(json, contaLogada.getLogin(), client);
                    if(!answer.equals("-1")){
                        out.println(answer);
                        System.out.println("1"); 
                    }else{
                        System.out.println("0");
                        out.println("-1"); 
                    }
                }                    
                if("saldos".equals(metodo)){
                    Saldos saldos = BankCoinServer.verificarSaldo(contaLogada.getLogin());
                    if(saldos!=null){
                        out.println(saldos.getConta());
                        out.flush();
                        out.println(saldos.getGlobal());
                        System.out.println("1"); 
                    }else{
                        out.println("-1");
                        System.out.println("0"); 
                    }
                } 
                              
                out.flush();
            }catch(SocketTimeoutException e){
                //ignorar
            } catch (SocketException e){                
                clientDesconectado = true;
                break;
            }catch(Exception e){
                System.out.println(e);
                break;
            }
        }
        System.out.println("Encerrando conexao.");   
        BankCoinServer.DecrementarConexoes();
        close();
        if(clientDesconectado)BankCoinServer.closeMe(this);// remove o este atentende da lista de atendentes
     }

    public Conta getContaLogada() {
        return contaLogada;
    }

    public String getClient() {
        return client;
    }
    

    public void enviarSaldo(int saldo) {
        if(out==null){
            try {
                out = new PrintStream(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(Atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        out.println("atualizarsaldo");
        out.flush();
        out.println(saldo);
        out.flush();
    }
    
    
     
}
