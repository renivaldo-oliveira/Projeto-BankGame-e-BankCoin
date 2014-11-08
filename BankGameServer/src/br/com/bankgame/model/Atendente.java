/**
 * Componente Curricular: Módulo Integrado de Concorrecia e Conectividade
 * Autor: <Jhone Mendes>
 * Data:  <03/09/2014>
 *
 */

package br.com.bankgame.model;

import br.com.bankgame.exceptions.ConexaoFalhouException;
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
    private BufferedReader in = null;
    private PrintStream out = null;
    private boolean inicializado;
    private boolean executando;
    private Thread thread;
    
    private boolean clientDesconectado;
    
    private boolean firstLobby;
    private String idContaLogada = "darts";
    private String idSalaLogada = "";    
    /**
     * Inizializa os Atributos.
     *
     * @param socket
     * @throws Exception
     */
    public Atendente (Socket socket) throws Exception{
        this.firstLobby = true;
        this.socket = socket;
        
        this.inicializado = false;
        this.executando = false;
        this.clientDesconectado = false;
        
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
         idContaLogada = null;
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
                
                if("login".equals(metodo)){
                    idContaLogada = json;
                }  
                
                if ("criarsala".equals(metodo)){
                    if(BankGameServer.criarSala(json, idContaLogada)){//json = nome e senha
                        enviarResposta("1");
                        System.out.println("Resposta para "+socket.getInetAddress()+": 1");
                        idSalaLogada = JSONParse.degenerateCriarSala(json).getNome();
                    }else{
                        enviarResposta("0");
                        System.out.println("Resposta para "+socket.getInetAddress()+": 0"); 
                    }                    
                }
                
                if ("entrarsala".equals(metodo)){
                    if(BankGameServer.entrarSala(json, idContaLogada)){//json = nome da sala (não é json)
                        enviarResposta("1");
                        System.out.println("Resposta para "+socket.getInetAddress()+": 1"); 
                        idSalaLogada = json;
                    }else{
                        System.out.println("Resposta para "+socket.getInetAddress()+": 0");
                        enviarResposta("0");
                    }
                }
                
                if ("sairdasala".equals(metodo)){                     
                    BankGameServer.sairDaSala(idSalaLogada, idContaLogada); 
                    idSalaLogada = "";
                }
                
                if ("pronto".equals(metodo)){
                    BankGameServer.jogadorPronto(idSalaLogada, idContaLogada);                    
                }
                
                if ("saldook".equals(metodo)){
                    BankGameServer.saldoOK(idSalaLogada, idContaLogada);
                }
                
                if ("atualizarsala".equals(metodo)){
                    String resposta = null;
                    for(Sala sala : BankGameServer.getSalas()){
                        if (sala.getNome().equals(idSalaLogada))
                            resposta = JSONParse.generateJogadoresList(sala);
                    }
                    out.println("atualizarsala");
                    out.flush();
                    out.println(resposta);
                }
                
                if ("atualizarfirstlobby".equals(metodo)){
                    String salasJson = JSONParse.generateSalaLobbyList(BankGameServer.getSalas());
                    System.out.println("Resposta para "+socket.getInetAddress()+": "+salasJson);
                    out.println("atualizarfirstlobby");
                    out.flush();
                    out.println(salasJson);      
                    firstLobby = false;
                }
                
                if ("atualizarlobbycancel".equals(metodo)){
                    firstLobby = true;
                } 
                
                //em jogo
                if("sortearprimeiro".equals(metodo)){
                    Jogo.sortearPrimeiro(idSalaLogada);
                }
                if("moverjogador".equals(metodo)){
                    Jogo.moverJogador(json, idSalaLogada);
                }
                 
                if ("efetuarjogada".equals(metodo)){
                    Jogo.efetuarJogada(this, json);//json = informações de pagamento, compra e vendas
                }
                
                if ("jogadorfaliu".equals(metodo)){
                    Jogo.efetuarJogada(this, json);
                    Jogo.removeJogador(this);
                }
                
            } catch (SocketTimeoutException e){
                //ignorar
            } catch (SocketException e){
                //remover o cara da sala
                BankGameServer.sairDaSala(idSalaLogada, idContaLogada);
                Jogo.removeJogador(this);
                clientDesconectado = true;
                break;
            } catch (IOException e){
                System.out.println(e);
                break;
            }
        }
        
        System.out.println("Encerrando conexao com: "+socket.getInetAddress()+".");
        close();
        if(clientDesconectado)BankGameServer.closeMe(this);
        
     }
     
     public void enviarAtualizacaoLobby(String json){
        if(firstLobby)//vai enviar o primeiro Lobby?
            return;
        if(out==null){
            try {
                out = new PrintStream(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(Atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        out.println("atualizarlobby");
        out.flush();
        out.println(json);
        out.flush();
     }
     private void enviarResposta(String resposta){
        out.println("resposta");
        out.flush();
        out.println(resposta);
        out.flush();
     }
     public void enviar(String metodo, String json){
        if(!firstLobby)//nao vai enviar o primeiro Lobby?
            return;
        if(json.equals("comecarjogo"))
            Jogo.addJogador(this);
        if(out==null){
            try {
                out = new PrintStream(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(Atendente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        out.println(metodo);
        out.flush();
        out.println(json);
        out.flush();
     }

     public String getIdSalaLogada(){
         return idSalaLogada;
     }

    public String getIdContaLogada() {
        return idContaLogada;
    }
     
     
}
// changes:

//quando o client for desconectado em jogo, pegar saldo do afk e 
//   repartir-lo com todos os jogadores da sala, retira o afk do
//   jogo e salva o saldo no bankCoin do usuario correspondente