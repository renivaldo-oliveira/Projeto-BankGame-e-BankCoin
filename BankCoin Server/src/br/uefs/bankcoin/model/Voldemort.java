/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uefs.bankcoin.model;

import br.uefs.bankcoin.util.Arquivos;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JhoneDarts
 */
class Voldemort {
    int delay = 5000;   // delay de 5 seg.
    int interval = 5000;  // intervalo de 5 seg.
    Timer timer = new Timer();
    
    private static List<Conta> mudancas2;
    private static List<Conta> mudancas3;
    private String meuIp = "localhost";
    
    
    private String ip2 = "localhost";
    private int porta2 = 1309;  
    private boolean server2On = false;
    private int qtdConexoes2;
    private String ip3 = "localhost";
    private int porta3 = 1308;
    private boolean server3On = false;
    private int qtdConexoes3;
    
    private BufferedReader in2;
    private PrintStream out2;
    private Socket socket2;
    
    private BufferedReader in3;
    private PrintStream out3;
    private Socket socket3;
    
    private static Voldemort instance;
    
    public static Voldemort getInstance(){
        if (instance == null){
            instance = new Voldemort();
        }
        return instance;
    }
    
    private Voldemort() {
        //loucura loucura loucura
        try {
            mudancas2 = (List<Conta>) Arquivos.carregar("mudancas2");
            mudancas3 = (List<Conta>) Arquivos.carregar("mudancas3");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex+" em carregar dados 'mudancas' do banco.");
        } catch (IOException ex) {
            mudancas2 = new ArrayList<Conta>();       
            mudancas3 = new ArrayList<Conta>(); 
        }             
        
        
        try {            
            socket2 = new Socket(ip2,porta2); // se conecta ao seu irmao, o n° 2    
            server2On = true;   
            socket2.setSoTimeout(2500);
            in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
            
            out2 = new PrintStream(socket2.getOutputStream());
            out2.println("server2");
            out2.flush();  
        } catch (SocketTimeoutException e) {
            //ignorar
        } catch (IOException ex) {
            socket2 = null;
            server2On = false;                     
        }
        
        try {
            socket3 = new Socket(ip3,porta3); // se conecta ao seu irmao, o n° 3        
            server3On = true; 
            socket3.setSoTimeout(2500);
            in3 = new BufferedReader(new InputStreamReader(socket3.getInputStream()));
            out3 = new PrintStream(socket3.getOutputStream());
            out3.println("server3");
            out3.flush();
        } catch (SocketTimeoutException e) {
            //ignorar
        } catch (IOException ex) {
            socket3 = null;
            server3On = false;
        }
        //sincronizar();
    }
    
    public String balancear(){
        String ip = null;
        if (!server2On && !server3On){
            ip = meuIp;
        }else if(server2On && !server3On){
            if(qtdConexoes2<=BankCoinServer.getQtdConexoes())
                ip = ip2; 
            else
                ip = meuIp;
        }else if(!server2On && server3On){
            if(qtdConexoes3<=BankCoinServer.getQtdConexoes())
                ip = ip3;   
            else
                ip = meuIp;
        }else{
            if (qtdConexoes2<=qtdConexoes3){            
                if(qtdConexoes2<=BankCoinServer.getQtdConexoes())
                    ip = ip2;                     
                else
                    ip = meuIp;
            }else{
                if(qtdConexoes3<=BankCoinServer.getQtdConexoes())
                    ip = ip3;   
                else
                    ip = meuIp;
            }
        }
        return ip;
    }
    public String balancearPorta(){
        
        int menorConexoes = qtdConexoes2;
        if(menorConexoes > BankCoinServer.getQtdConexoes()){
            menorConexoes = BankCoinServer.getQtdConexoes();
        }
        if(menorConexoes < qtdConexoes3){
            menorConexoes = qtdConexoes3;
        }
        
        
        System.out.println("-----------------------server 2: "+qtdConexoes2);
        System.out.println("-----------------------server 3: "+qtdConexoes3);
        String porta = null;
        if (!server2On && !server3On){
            porta = "1307";
        }else if(server2On && !server3On){
            if(qtdConexoes2<=BankCoinServer.getQtdConexoes()){
                porta = "1309"; 
                System.out.println("1");
            }else
                porta = "1307";
        }else if(!server2On && server3On){
            if(qtdConexoes3<=BankCoinServer.getQtdConexoes())
                porta = "1308";   
            else
                porta = "1307";
        }else{
            if (qtdConexoes2<=qtdConexoes3){            
                if(qtdConexoes2<=BankCoinServer.getQtdConexoes()){
                    porta = "1309"; 
                    System.out.println("2");
                }else
                    porta = "1307";
            }else{
                if(qtdConexoes3<=BankCoinServer.getQtdConexoes())
                    porta = "1308";   
                else
                    porta = "1307";
            }
        }
        return porta;
    }
        
    public void salvarMudanca (Conta conta){
        mudancas2.add(conta);
        mudancas3.add(conta);
        try {
            Arquivos.salvar(mudancas2, "mudancas2");
            Arquivos.salvar(mudancas3, "mudancas3");
        } catch (IOException ex) {
            Logger.getLogger(Voldemort.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    public static String getMudancas(int i) {
        String mudancas = null;
        if (i == 2){
            mudancas = JSONParse.generateAtualizacao(mudancas2, BankCoinServer.getQtdConexoes());
            mudancas2 = new ArrayList<Conta>();
            try {
                Arquivos.salvar(mudancas2, "mudancas2");
            } catch (IOException ex) {
                Logger.getLogger(Voldemort.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            mudancas = JSONParse.generateAtualizacao(mudancas3, BankCoinServer.getQtdConexoes());
            mudancas3 = new ArrayList<Conta>();
            try {
                Arquivos.salvar(mudancas3, "mudancas3");
            } catch (IOException ex) {
                Logger.getLogger(Voldemort.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(mudancas+" getMudancas");
        return mudancas;
    }
        
    private void  atualizarBanco(){
        if(server2On){
            out2.println("atualizar");
            out2.flush();
            String jsonMudancas;
            try {
                socket2.setSoTimeout(2500);
                jsonMudancas = in2.readLine();
                System.out.println(jsonMudancas+" atualizarBanco");     
                qtdConexoes2 = JSONParse.degenerateQtdConexoes(jsonMudancas);
                mesclar(jsonMudancas);
                
                System.out.println(qtdConexoes2);
            } catch (SocketTimeoutException ex) {
                //ignorar
            } catch (IOException ex) {
                socket2 = null;
                server2On = false;
                System.out.println("Server 2 Off");
            }            
        }else{
            try {
                socket2 = new Socket(ip2, porta2); // se conecta ao seu irmao, o n° 2    
                server2On = true;
                socket2.setSoTimeout(2500);
                in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
                out2 = new PrintStream(socket2.getOutputStream());
                out2.println("server2");
                out2.flush();
                out2.println("atualizar");
                out2.flush();
                
                String jsonMudancas;
                jsonMudancas = in2.readLine();
                mesclar(jsonMudancas);
                qtdConexoes2 = JSONParse.degenerateQtdConexoes(jsonMudancas);
                   
            } catch (SocketTimeoutException ex) {
                //ignorar
            } catch (IOException ex) {
                //ignora
                socket2 = null;
                server2On = false;
                System.out.println("Server 2 Off");
            }

        }
        
        if(server3On){
            out3.println("atualizar");
            out3.flush();
            String jsonMudancas;
            try {
                socket3.setSoTimeout(2500);
                jsonMudancas = in3.readLine();
                
                System.out.println();
                mesclar(jsonMudancas);
                qtdConexoes3 = JSONParse.degenerateQtdConexoes(jsonMudancas);
            } catch (SocketTimeoutException ex) {
                //ignorar
            } catch (IOException e) {
                socket3 = null;
                server3On = false;  
                System.out.println("Server 3 Off");
            }            
        }else{
            try {
                socket3 = new Socket(ip3, porta3); // se conecta ao seu irmao, o n° 3    
                server3On = true;
                socket3.setSoTimeout(2500);
                in3 = new BufferedReader(new InputStreamReader(socket3.getInputStream()));
                out3 = new PrintStream(socket3.getOutputStream());
                out3.println("server3");
                out3.flush();
                out3.println("atualizar");
                out3.flush();
                
                String jsonMudancas;
                jsonMudancas = in3.readLine();
                mesclar(jsonMudancas);
                qtdConexoes3 = JSONParse.degenerateQtdConexoes(jsonMudancas);
            } catch (SocketTimeoutException ex) {
                //ignorar
            } catch (IOException e) {
                //ignorar
                socket3 = null;
                server3On = false;  
                System.out.println("Server 3 Off");
            }
        }
        System.out.println("server 2: "+qtdConexoes2);
        System.out.println("server 3: "+qtdConexoes3);
    }
    
    private void mesclar(String json){
        List contas = JSONParse.degenerateContaList(json);        
        BankCoinServer.atualizarContas(contas);        
    }   
     
    public void sincronizar() {        
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("atualizar");                
                atualizarBanco();                
            }
        }, delay, interval);
    }
    
    public void stop(){
        if(server2On){
            out2.println("stop");
            out2.flush();
        }
        if (server3On){
            out3.println("stop");
            out3.flush();
        }
        try {
            Arquivos.salvar(mudancas2, "mudancas2");
            Arquivos.salvar(mudancas3, "mudancas3");
        } catch (IOException ex) {
            Logger.getLogger(Voldemort.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        timer.cancel();
        instance = null;
    }
    
}
