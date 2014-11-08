/**
 * Componente Curricular: Módulo Integrado de Concorrecia e Conectividade
 * Autor: <Jhone Mendes>
 * Data:  <03/09/2014>
 *
 */


package br.uefs.bankcoin.model;

import br.uefs.bankcoin.util.Arquivos;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
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
public class BankCoinServer implements Runnable{    
    
    //private static BankCoinServer servidor;
    
    private ServerSocket server;
    private static Voldemort voldemort;
    private boolean inicializado;
    private boolean executando;
    private Thread thread;
    private static List<Atendente> atendentes;
    private List<Hospedeiro> hospedeiros;
    
    private static List<Conta> contas;
    private static int saldoGlobal;
    private static int qtdConexoes;
    
    private BufferedReader in;
    private PrintStream out;
    
    public static void closeMe(Atendente aThis) {
        System.out.println("aqui em closeMe");
        atendentes.remove(aThis);
    }
    
    /**
     * Inicializa os atributos e recupera os dados para Lista de contas cadastradas
     * e saldo do banco no disco (se houver).
     *
     * @param porta
     * @throws IOException
     */
    public BankCoinServer(int porta) throws IOException{
        
        qtdConexoes = 0;
        atendentes = new ArrayList<Atendente>();
        hospedeiros = new ArrayList<Hospedeiro>();
        try {
            contas = (List<Conta>) Arquivos.carregar("contas");
            saldoGlobal = (int) Arquivos.carregar("saldoGlobal");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex+" em carregar dados do banco.");
        } catch (IOException ex) {
            contas = new ArrayList<Conta>();
            saldoGlobal = 0;
        }
        
        voldemort = Voldemort.getInstance();
        
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
        
        for(Hospedeiro hospedeiro : hospedeiros){
            try{
                hospedeiro.stop();
            } catch(Exception e){
                System.out.println(e);
            }
        }
        
        server.close();
        inicializado = false;
        executando = false;
        thread = null;
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
        voldemort.stop();
        executando = false;
        if (thread!=null)
            thread.join();
        
        server = null;
        inicializado = false;
        executando = false;
        thread = null;
    }
    
    @Override
    public void run(){
        System.out.println("saldo global: "+saldoGlobal+"\n");
        for (Conta conta : contas){
            System.out.println(conta.getLogin());
        }
        System.out.println("\nAguardando mensagem...");
        while(executando){
            try{
                server.setSoTimeout(2500);
                Socket socket = server.accept();
                //porta de entrada...
                 in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
                 out = new PrintStream(socket.getOutputStream());
                 String msg = in.readLine();
                 
                 if(msg.equals("server2")){
                     System.out.println("Conexão com servidor 3 estabelecida");
                     Hospedeiro hospedeiro = new Hospedeiro(socket, 2);
                     hospedeiro.start();
                     hospedeiros.add(hospedeiro);
                 }
                 
                 if(msg.equals("server3")){
                     System.out.println("Conexão com servidor 2 estabelecida");
                     Hospedeiro hospedeiro = new Hospedeiro(socket, 3);
                     hospedeiro.start();
                     hospedeiros.add(hospedeiro);
                 }
                 
                 if (msg.equals("novo cliente")){
                     //out.println(voldemort.balancear());
                     out.println(voldemort.balancearPorta());
                     out.flush();
                 }
                 
                 if(msg.equals("ok")){

                    qtdConexoes += 1;

                    System.out.println("Conexão estabelecida");

                    Atendente atendente = new Atendente(socket);
                    atendente.start();
                    atendentes.add(atendente);
                 }
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
            Logger.getLogger(BankCoinServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * faz chamada para inicializar o servidor e aguarda a resposta para encerra-lo.
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     
    public static void main(String[] args) throws InterruptedException, IOException{
        System.out.println("Iniciando Servidor...");
        servidor = new BankCoinServer(1307);
        servidor.start();
        
        /*System.out.println("Pressione Enter para encerrar o servidor.");
        new Scanner(System.in).nextLine();
        System.out.println("Encerrando Servidor.");
        servidor.stop();
    }
     */
        
    
    
    /**
     * Verifica na lista de contas se existe de entrada.
     *
     * @param contaAcesso
     * @return
     */
    public static String login(Conta contaAcesso, String clientAcesso){        
        for (Conta conta : contas){
            if (conta.getLogin().equals(contaAcesso.getLogin()) &&
                    conta.getSenha().equals(contaAcesso.getSenha())){
                for (Atendente atendente : atendentes){
                    if(atendente.getContaLogada()!=null)
                        if (atendente.getContaLogada().getLogin().equals(contaAcesso.getLogin()))
                            if(atendente.getClient().equals(clientAcesso) && !clientAcesso.equals("superbgame"))
                                return "-2";//conta ja logada                        
                }
                return ""+conta.getSaldo();// saldo, conta logada com sucesso!
            }
        }
        return "-1";//conta inexistente
    }
    
    /**
     * Verifica na lista de contas se existe de entrada, se nao houver, ela sera
     * inserida e salva em disco rigido.
     *
     * @param contaAcesso
     * @return
     * @throws IOException
     */
    public static boolean cadastrar(Conta contaAcesso) throws IOException{        
        for (Conta conta : contas){
            if (conta.getLogin().equals(contaAcesso.getLogin()))
                return false;
        }
        contas.add(contaAcesso);
        
        saldoGlobal += contaAcesso.getSaldo();
        System.out.println(saldoGlobal);
        Arquivos.salvar(contas, "contas");
        Arquivos.salvar(saldoGlobal, "saldoGlobal");
        voldemort.salvarMudanca(contaAcesso);
        return true;
    }
    
    /**
     * Realiza a transferencia do valor da conta logada para a conta indicada,
     * verificando se é possivel.
     *
     * @param json pacote json contendo os valores de transferencia.
     * @param login conta logada.
     * @param client
     * @return
     * @throws IOException
     */
    public static String transferir(String json, String login, String client) throws IOException{
        Transacao transacao = JSONParse.degenerateTransacao(json);
        if(transacao.getValor()<0 || transacao.getIdConta().equals(login))
            return "-1";
        for (Conta contaDoadora : contas){
            if (contaDoadora.getLogin().equals(login)){
                if(0>contaDoadora.getSaldo() - transacao.getValor())          
                    return "-1";
                for (Conta contaReceptora : contas){
                    if (contaReceptora.getLogin().equals(transacao.getIdConta())){
                        contaReceptora.setSaldo(contaReceptora.getSaldo() + transacao.getValor());
                        contaDoadora.setSaldo(contaDoadora.getSaldo() - transacao.getValor());
                        Arquivos.salvar(contas, "contas");
                        
                        //atualizar o valor de saldo
                        for (Atendente atendente : atendentes){
                            if (atendente.getContaLogada()!=null){
                                if(contaReceptora.getLogin().equals(atendente.getContaLogada().getLogin()))
                                    atendente.enviarSaldo(contaReceptora.getSaldo());
                                if (contaDoadora.getLogin().equals(atendente.getContaLogada().getLogin()) 
                                        && !client.equals(atendente.getClient()))
                                    atendente.enviarSaldo(contaDoadora.getSaldo());
                            }
                        }
                        
                        return ""+contaDoadora.getSaldo();// responde com o valor de saldo
                    }
                }
            }
        }
        return "-1";
    }
    
    /**
     * Verifica os saldos, de conta e do banco, retornando-os.
     *
     * @param login
     * @return
     */
    public static Saldos verificarSaldo(String login){
        Saldos saldos = null;
        for (Conta conta : contas){
            if (conta.getLogin().equals(login))
                saldos = new Saldos(conta.getSaldo(), saldoGlobal);
        }
        return saldos;
    }
    
    public static int getQtdConexoes(){
        return qtdConexoes;
    }
    
    public static void DecrementarConexoes() {
        qtdConexoes -= 1;
    }
    
    public static void atualizarContas(List<Conta> contasNovas){
        for (Conta conta : contas){
            for (Conta contaAux : contasNovas){
                if(conta.getLogin().equals(contaAux.getLogin())){
                    contas.remove(conta);
                }else{
                    saldoGlobal += contaAux.getSaldo();
                }                   
            }
        }
        
        for (Conta conta : contasNovas){
            contas.add(conta);
        }
        try {
            Arquivos.salvar(contas, "contas");
            Arquivos.salvar(saldoGlobal, "saldoGlobal");
        } catch (IOException ex) {
            System.out.println("Erro ao salvar atualizacao!");
        }
        
    }   
   
}
//log geral:

//erro nas trocas de msgs(atualizar/voldemort), nao tenho a menor ideia do que seja, 
//algumas vezes a string json chega faltando o "{" inicial,
//outras nao chega nada.
//sempre esta enviando corretamente.

//algo muito loco esta acontecendo com a informação qtdConexoes 

//ordem 
// quando o server for | 1307 | 1308 | 1309 |
// seu server 2 sera   | 1309 | 1307 | 1308 |
// seu server 3 sera   | 1308 | 1309 | 1307 |
//por conta disso,
//a inversao de 2 pra 3 e 3 pra 2 é proposital na porta de entrada,
// para que tudo seja perfeito.

