/**
 * Componente Curricular: Módulo Integrado de Concorrecia e Conectividade
 * Autor: <Jhone Mendes>
 * Data:  <03/09/2014>
 *
 */

package br.com.bankgame.model;

import br.com.bankgame.exceptions.ConexaoFalhouException;
import br.com.bankgame.exceptions.DadosInvalidosException;
import br.com.bankgame.view.Main;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsavel por gerir as atividades de conexao e funcoes usuais do BankCoin,
 * tais como, login, cadastro, salvar dados, etc.
 *
 * @author Jhone Mendes
 */
public class BankGameServer implements Runnable{    

    public static void closeMe(Atendente aThis) {
        atendentes.remove(aThis);
    }
    
    private ServerSocket server;
    private boolean inicializado;
    private boolean executando;
    private Thread thread;
    private static List<Atendente> atendentes;    
    private static List<Sala> salas = new ArrayList<Sala>();
    
    private static Conexao conexao;
    
    /**
     * Inicializa os atributos e recupera os dados para Lista de contas cadastradas
     * e saldo do banco no disco (se houver).
     *
     * @param porta
     * @throws IOException
     * @throws br.com.bankgame.exceptions.ConexaoFalhouException
     */
    public BankGameServer(int porta) throws IOException, ConexaoFalhouException, DadosInvalidosException {        
        conexao = new Conexao(); 
        
        String answer = conexao.callServerBCoin("login", JSONParse.generateMyConta());
        if(answer.equals("-1") || answer.equals("-2"))
            throw new DadosInvalidosException();
        Main.atualizarSaldo(answer);
        atendentes = new ArrayList<Atendente>();        
        
        inicializado = false;
        executando = false;
        open(porta);
    }
    
    private void open (int porta) throws IOException{
        server = new ServerSocket(porta);        
        inicializado = true;
    }
    
    private void close() throws IOException {
        for(Atendente atendente : atendentes){
            try{                
                atendente.stop();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        conexao = null;
        Jogo.getSalasDeJogo().clear();
        salas.clear();
        server.close();        
        inicializado = false;
        executando = false;
    }
    
    /**
     * Inicializa o servico de servidor
     *
     */
    public void start() {
        if (!inicializado || executando){
            return;
        }
        
        executando = true; 
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Encerra o servidor liberando os dados alocados.
     *
     * @throws InterruptedException
     */
    public void stop() throws InterruptedException{
        System.out.println("Encerrando Servidor.");        
        conexao.callServerBCoinJustSend("logout", "");
        try {
            close();
        } catch (IOException ex) {
            
        }
        if(thread!=null)
            thread.join();
        inicializado = false;
        executando = false;
        thread = null;
    }
    
    @Override
    public void run(){
        
        System.out.println("Aguardando conexoes...");
        while(executando){
            try{
                server.setSoTimeout(2500);
                
                //porta de entrada... 
                Socket socket = server.accept();   
                
                System.out.println("Conexão estabelecida com: "+socket.getInetAddress());
                Atendente atendente = new Atendente(socket);
                atendente.start();
                atendentes.add(atendente);
                 
            }catch (SocketTimeoutException e){
                //ignorar
            }catch(Exception e){
                System.out.println(e);
                break;
            }
        }
        try {
            close();
            System.out.println("close");
        } catch (IOException ex) {
            Logger.getLogger(BankGameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**metodo pra transferencia
     * @param conta
     * @param valor 
     * @throws DadosInvalidosException
     * @throws br.com.bankgame.exceptions.ConexaoFalhouException **/
    public static void transferirBCoin(String conta, int valor) throws ConexaoFalhouException, DadosInvalidosException{		
        String answer = conexao.callServerBCoin("transferir", JSONParse.generateTransferencia(conta, valor));
        if(answer.equals("-1"))
            throw new DadosInvalidosException();
        Main.atualizarSaldo(answer);
    }    

    public static boolean criarSala(String json, String login) {
        Sala sala = JSONParse.degenerateCriarSala(json);
        for(Sala salaAux : salas){
            if (salaAux.getNome().equals(sala.getNome()))
                return false;
        }
        Jogador jogador = new Jogador(login);
        sala.addJogador(jogador);
        salas.add(sala);        
        
        //envia para todos os clientes conectados que ja pegaram o primeiro lobby
        //   e nao cancelaram a atualizacao do lobby
        //nao envia pro cara que solicitou criar a sala
        json = JSONParse.generateSalaLobby(sala);
        for (Atendente atendente : atendentes){
                atendente.enviarAtualizacaoLobby(json);
        }
        return true;
    }
    
    public static boolean entrarSala(String idSala, String nomeJogdor){
        for(Sala sala : salas){
            if (sala.getNome().equals(idSala)){
                Jogador jogador = new Jogador(nomeJogdor);
                if (sala.addJogador(jogador)){
                    //envia para todos os clientes conectados que ja pegaram o primeiro lobby
                    //   e nao cancelaram a atualizacao do lobby
                    //nao envia pro cara que solicitou entrar na sala
                    String json = JSONParse.generateSalaLobby(sala);
                    for (Atendente atendente : atendentes) {
                        if(atendente.getIdSalaLogada().equals(idSala))
                            atendente.enviar("atualizarsala", JSONParse.generateJogadoresList(sala));
                        atendente.enviarAtualizacaoLobby(json);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void jogadorPronto(String idSala, String nomeJogador){
        for(Sala sala : salas){
            if (sala.getNome().equals(idSala)){
                for (Jogador jogador : sala.getJogadores()){
                    if (jogador.getLogin().equals(nomeJogador)){
                        if (jogador.isPronto())
                            jogador.setPronto(false);
                        else 
                            jogador.setPronto(true);
                        for (Atendente atendente : atendentes) {
                            if(atendente.getIdSalaLogada().equals(idSala))
                                atendente.enviar("atualizarsala", JSONParse.generateJogadoresList(sala));
                        }
                    }                        
                }
            }
        }
    }
    
    public static void saldoOK(String idSalaLogada, String idContaLogada) {
        Sala salaExcluida = null;
        for(Sala sala : salas){
            if (sala.getNome().equals(idSalaLogada)){
                int prontosEOks = 0;
                for (Jogador jogador : sala.getJogadores()){
                    if(jogador.isSaldoOK())
                        prontosEOks++;
                    if (jogador.getLogin().equals(idContaLogada)){
                        //achei o jogador em questao                        
                        jogador.setSaldoOK(true); 
                        prontosEOks++;
                    }
                    System.out.println("ok "+idContaLogada+  ", prontos "+prontosEOks);
                }
                if (prontosEOks == (sala.getJogadores().size()))
                    for (Atendente atendente : atendentes) {
                        if(atendente.getIdSalaLogada().equals(idSalaLogada)){
                            atendente.enviar("atualizarsala", "comecarjogo");
                            salaExcluida = sala;
                        }
                }
            }
        }
        salas.remove(salaExcluida);
    }
    
    public static void sairDaSala(String idSala, String nomeJogador){        
        Sala salaAux = null;
        Jogador jogadorExcluido = null;
        int prontos = 0;
        for(Sala sala : salas){
            if (sala.getNome().equals(idSala)){
                for (Jogador jogador : sala.getJogadores()){                    
                    if (jogador.getLogin().equals(nomeJogador)){                                               
                        jogadorExcluido = jogador;
                        salaAux = sala;                                                
                    }else if (jogador.isPronto())
                        prontos++;
                }
            }
        }
        if (salaAux == null)
            return;
        
        salaAux.getJogadores().remove(jogadorExcluido);
        salaAux.setDisponivel(true);
        if ((prontos == salaAux.getJogadores().size()) && (prontos != 1)) {//se o restante dos jogadores estiverem prontos
            for (Jogador jogador : salaAux.getJogadores()){                //desprotifica todos, exceto se for um só restante
                jogador.setPronto(false);
            }
        }
        //envia pra todos os clientes....
        String json = JSONParse.generateSalaLobby(salaAux);
        for (Atendente atendente : atendentes) {
            if(atendente.getIdSalaLogada().equals(idSala))
                atendente.enviar("atualizarsala", JSONParse.generateJogadoresList(salaAux));
            atendente.enviarAtualizacaoLobby(json);
        }
        if(salaAux.getJogadores().size()==0)
            salas.remove(salaAux);
    }

    public static List<Sala> getSalas() {
        return salas;
    }
    
    public static Sala getSala(String nome){
        Sala sala = null;
        for(Sala aux : salas){
            if (aux.getNome().equals(nome))
               sala = aux; 
        }
        return sala;
    }
    
    
}
