/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.view;

import br.com.bankgame.controller.Controller;
import br.com.bankgame.exceptions.ConexaoFalhouException;
import br.com.bankgame.model.Casa;
import br.com.bankgame.model.JogadorView;
import br.com.bankgame.model.Venda;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author JhoneDarts
 */
public class TelaJogo extends javax.swing.JFrame{
    private static String meuNome;
    private static TelaJogo instance = null;
    private int ALTURA;
    private int LARGURA;
    private int dado;

    private final String background_imagem = "src/br/com/bankgame/imagens/background.png";
    private ImageIcon background;    
    private ImageIcon icoBlue;
    private ImageIcon icoRed;
    private ImageIcon icoGreen;
    private ImageIcon icoPurple;    
    private JogadorView playerBlue;
    private JogadorView playerRed;
    private JogadorView playerGreen;    
    private JogadorView playerPurple;
    private List<Casa> casas;
    private String turno;
    private String contador;
    private Controller controller;
    private boolean fimDeJogo;
    private boolean ganhei;
    
    public static void setMeuNome(String nome){
        meuNome = nome;
    }
    
    public static TelaJogo getInstance(){
        if(instance == null)
            instance = new TelaJogo();
        return instance;
    }
    
    private TelaJogo (){     
        setFocusable(true);
        fimDeJogo = false;
        ganhei = false;
        ALTURA = 660;
        LARGURA = 795;
        dado = 0;
        try {
            this.controller = Controller.getInstance();
        } catch (ConexaoFalhouException | IOException ex) {
            JOptionPane.showMessageDialog(this, "Falha na conexao com o Servidor. Seu client será encerrado agora.");  
            System.exit(0);
            return;
        }
        background = new ImageIcon(background_imagem);
        icoBlue = new ImageIcon("src/br/com/bankgame/imagens/icoBlue64.png");
        icoRed = new ImageIcon("src/br/com/bankgame/imagens/icoRed64.png");
        icoGreen = new ImageIcon("src/br/com/bankgame/imagens/icoGreen64.png");
        icoPurple = new ImageIcon("src/br/com/bankgame/imagens/icoPurple64.png");
        
        playerBlue = new JogadorView(714, 515, 1, "");//colcar null
        playerRed = new JogadorView(654, 515, 2, "");
        playerGreen = new JogadorView(654, 545, 3, "");
        playerPurple = new JogadorView(714, 545, 4, "");
        
        turno = "Aguarde sua vez...";
        contador = "";
        
        initComponents();        
        //solicitar o sorteo do primeiro
        controller.solicitarPrimeiro();
    }
    
    public void initComponents (){
        setTitle("BankGame");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(LARGURA, ALTURA);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        //trocando o icone da aplicação
        try{
            Image icon = Toolkit.getDefaultToolkit().getImage("src/br/com/bankgame/imagens/ico32x32.png");
            setIconImage(icon);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "Erro", 0);        
        }                
        //inicializando as casas
        int xInicial = 652; int yInicial = 545; int xVariacao = 127; int yVariacao = 98;
        casas = new ArrayList<Casa>();
        casas.add(new Casa(-150, 1, xInicial, yInicial));//0 (inicio)
        casas.add(new Casa(250, 1, xInicial - xVariacao, yInicial));//1
        casas.add(new Casa(100, 1, xInicial - xVariacao*2, yInicial));//2
        casas.add(new Casa(150, 10, xInicial - xVariacao*3, yInicial));//3
        casas.add(new Casa(180, 1, xInicial - xVariacao*4, yInicial));//4
        casas.add(new Casa(30, 1, xInicial - xVariacao*5, yInicial));//5        
        casas.add(new Casa(200, 1, xInicial - xVariacao*5, yInicial - yVariacao));//6
        casas.add(new Casa(120, 1, xInicial - xVariacao*5, yInicial - yVariacao*2));//7
        casas.add(new Casa(220, 1, xInicial - xVariacao*5, yInicial - yVariacao*3));//8
        casas.add(new Casa(100, 5, xInicial - xVariacao*5, yInicial - yVariacao*4));//9
        casas.add(new Casa(-50, 1, xInicial - xVariacao*5, yInicial - yVariacao*5));//10        
        casas.add(new Casa(200, 1, xInicial - xVariacao*4, yInicial - yVariacao*5));//11
        casas.add(new Casa(150, 1, xInicial - xVariacao*3, yInicial - yVariacao*5));//12
        casas.add(new Casa(140, 1, xInicial - xVariacao*2, yInicial - yVariacao*5));//13
        casas.add(new Casa(120, 1, xInicial - xVariacao, yInicial - yVariacao*5));//14
        casas.add(new Casa(60, 1, xInicial, yInicial - yVariacao*5));//15        
        casas.add(new Casa(170, 1, xInicial, yInicial - yVariacao*4));//16
        casas.add(new Casa(200, 15, xInicial, yInicial - yVariacao*3));//17
        casas.add(new Casa(160, 1, xInicial, yInicial - yVariacao*2));//18
        casas.add(new Casa(270, 1, xInicial, yInicial - yVariacao));//19    
        
    }
    
    @Override
    public void paint (Graphics g){
        super.paint(g);
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);        
        g2.drawImage(background.getImage(), 0, 24, this);
        
        for (Casa casa : casas){
            casa.desenharPosse(g2, this);
        }
        if(!playerBlue.getNome().equals(""))
        g2.drawImage(icoBlue.getImage(), playerBlue.getX(), playerBlue.getY(), this);
        if(!playerRed.getNome().equals(""))
            g2.drawImage(icoRed.getImage(), playerRed.getX(), playerRed.getY(), this);
        if(!playerGreen.getNome().equals(""))
            g2.drawImage(icoGreen.getImage(), playerGreen.getX(), playerGreen.getY(), this);
        if(!playerPurple.getNome().equals(""))
            g2.drawImage(icoPurple.getImage(), playerPurple.getX(), playerPurple.getY(), this);   
        desenharStatus(g2);
        desenharFimDeJogo(g2);
    }
    
    private void desenharStatus (Graphics2D g){
        Font estilo = new Font("Tahoma", Font.BOLD, 14);
        g.setColor(Color.black);        
        g.setFont(estilo);        
        FontMetrics metrica = this.getFontMetrics(estilo);
        
        g.drawString(turno, (LARGURA - metrica.stringWidth(turno))/2, ALTURA/2 -140);
        g.drawString(contador, (LARGURA - metrica.stringWidth(contador))/2, ALTURA/2 +18 -140);
        if(!playerBlue.getNome().equals("")){
            g.drawString(playerBlue.getNome(), (LARGURA - metrica.stringWidth(playerBlue.getNome()))/2 -200, ALTURA/2 +160);
            g.drawString(playerBlue.getSaldo()+"", (LARGURA - metrica.stringWidth(playerBlue.getNome()))/2 -200, ALTURA/2 +18 +160);
        }        
        if(!playerRed.getNome().equals("")){
            g.drawString(playerRed.getNome(), (LARGURA - metrica.stringWidth(playerRed.getNome()))/2 - 80, ALTURA/2 +160);
            g.drawString(playerRed.getSaldo()+"", (LARGURA - metrica.stringWidth(playerRed.getNome()))/2 -80, ALTURA/2 +18 +160);
        }        
        if(!playerGreen.getNome().equals("")){
            g.drawString(playerGreen.getNome(), (LARGURA - metrica.stringWidth(playerGreen.getNome()))/2 +60, ALTURA/2 +160);
            g.drawString(playerGreen.getSaldo()+"", (LARGURA - metrica.stringWidth(playerGreen.getNome()))/2 +60, ALTURA/2 +18 +160);
        }
        if(!playerPurple.getNome().equals("")){
            g.drawString(playerPurple.getNome(), (LARGURA - metrica.stringWidth(playerPurple.getNome()))/2 +180, ALTURA/2 +160);
            g.drawString(playerPurple.getSaldo()+"", (LARGURA - metrica.stringWidth(playerPurple.getNome()))/2 +180, ALTURA/2 +18 +160);
        }  
        
        if(dado!=0){
            g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/dado70.png").getImage(), 570, 160, this);
            estilo = new Font("Tahoma", Font.ITALIC, 32);
            g.setFont(estilo);
            g.drawString(dado+"", 545, 215);
        }        
    }
    
    public void moverJogador(int jogador, int casas){
        JogadorView posicaoJogador = null;
        if (jogador == 1)
            posicaoJogador = playerBlue;
        if (jogador == 2)
            posicaoJogador = playerRed;
        if (jogador == 3)
            posicaoJogador = playerGreen;
        if (jogador == 4)
            posicaoJogador = playerPurple;
        for (int i=1; i<=casas; i++){
            if (posicaoJogador.getCasa()>=0 && posicaoJogador.getCasa() <5){ 
                posicaoJogador.setX(posicaoJogador.getX() - 127);                
            }
            if (posicaoJogador.getCasa()>=5 && posicaoJogador.getCasa() <10){
                posicaoJogador.setY(posicaoJogador.getY() - 98);
            }
            if (posicaoJogador.getCasa()>=10 && posicaoJogador.getCasa() <15){
                posicaoJogador.setX(posicaoJogador.getX() + 127);
            }
            if (posicaoJogador.getCasa()>=15 && posicaoJogador.getCasa() <20){
                posicaoJogador.setY(posicaoJogador.getY() + 98);
            }
            posicaoJogador.setCasa(posicaoJogador.getCasa()+1);
            if(posicaoJogador.getCasa() == 20){
                posicaoJogador.setCasa(0);
                posicaoJogador.setBonusRodada(true);
                System.out.println("bonus");
            }
            repaint();          
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                System.out.println("nao esperou, in moverJogador");
            }
        }
    }
    public void primeiro(String json){                 
        String primeiro = null;
        try {
            JSONObject jo = new JSONObject(json);            
            playerBlue.setNome(jo.getString("blue"));
            playerRed.setNome(jo.getString("red"));
            playerGreen.setNome(jo.getString("green"));
            playerPurple.setNome(jo.getString("purple"));
            primeiro = jo.getString("primeiro");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("\""+primeiro+"\".equals("+meuNome+")");
        if(primeiro.equals(meuNome))
            minhaVez();
    }
    
    public void atualizarJogo(String json){
        if (json.contains("!--1")){
            String nomeJogador = json.substring(3);
            contador = "O Jogador "+nomeJogador+" faliu!";
            if(playerBlue.getNome().equals(nomeJogador))
                playerBlue.setNome("");
            if(playerRed.getNome().equals(nomeJogador))
                playerRed.setNome("");
            if(playerGreen.getNome().equals(nomeJogador))
                playerGreen.setNome("");
            if(playerPurple.getNome().equals(nomeJogador))
                playerPurple.setNome("");
            repaint();
            return;
        }
        String proximo = null;
        try {
            JSONObject jo = new JSONObject(json);            
            proximo = jo.getString("proximo");
            JSONArray ja = jo.getJSONArray("jogadores");  
            if(ja.length()==1){
                fimDeJogo = true;
                ganhei = true;
                return;
            }
            for(int j=0, tamanho = ja.length(); j<tamanho; j++){
                String nome = ja.getJSONObject(j).getString("nome");
                int saldo = ja.getJSONObject(j).getInt("saldo");
                if(nome.equals(playerBlue.getNome()))
                    playerBlue.setSaldo(saldo);
                if(nome.equals(playerRed.getNome()))
                    playerRed.setSaldo(saldo);
                if(nome.equals(playerGreen.getNome()))
                    playerGreen.setSaldo(saldo);
                if(nome.equals(playerPurple.getNome()))
                    playerPurple.setSaldo(saldo);                
            }
            JSONArray ja1 = jo.getJSONArray("casas");
            for(int j=0, tamanho = ja1.length(); j<tamanho; j++){
                String dono = ja1.getJSONObject(j).getString("dono");
                int don = 0;
                if(dono.equals(playerBlue.getNome()))
                    don = 1;
                if(dono.equals(playerRed.getNome()))
                    don = 2;
                if(!playerGreen.getNome().equals(""))
                    if(dono.equals(playerGreen.getNome()))
                        don = 3;
                if(!playerPurple.getNome().equals(""))                    
                    if(dono.equals(playerPurple.getNome()))
                        don = 4;
                casas.get(j).setDono(don);
                casas.get(j).setQtdPredios(ja1.getJSONObject(j).getInt("qtdPredios"));
            }            
            //////////////////////////////////            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        
        if(proximo.equals(meuNome)){
            repaint();
            minhaVez();
        }else{            
            turno = "Aguarde sua vez!";
            dado = 0;
            if(proximo.contains("!--1"))
                contador = proximo+" esta desconectado.";
            else if(proximo.contains("!-1"))
                contador = proximo+" esta falido!";
            else
                contador = proximo+" esta realizando sua jogada.";
            repaint();
            //iniciar contador....
        }
    }
    
    public void minhaVez(){
        if(fimDeJogo)
            turno = "Fim de jogo!";
        turno = "Sua vez de jogar!";
        contador = "";        
        dado = rolarDado();
        repaint();
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            System.out.println("nao esperou, in minha vez");
        }
        int casaFinal = 0;
        int meuId = 0;
        int meuSaldo = 0;
        boolean bonusRodada = false;
        if (meuNome.equals(playerBlue.getNome())){
            moverJogador(1, dado);
            controller.moverJogador(1, dado);
            casaFinal = playerBlue.getCasa();
            meuId = 1;
            meuSaldo = playerBlue.getSaldo();
            bonusRodada = playerBlue.isBonusRodada();
            if(playerBlue.isBonusRodada()){
                contador = "Ganhou 150 pela rodada!";
                playerBlue.setSaldo(playerBlue.getSaldo()+150);
            }
        }
        if (meuNome.equals(playerRed.getNome())){
            moverJogador(2, dado);
            controller.moverJogador(2, dado);
            casaFinal = playerRed.getCasa();
            meuId = 2;
            meuSaldo = playerRed.getSaldo();
            bonusRodada = playerRed.isBonusRodada();
            if(playerRed.isBonusRodada()){
                contador = "Ganhou 150 pela rodada!";
                playerRed.setSaldo(playerRed.getSaldo()+150);
            }
        }
        if (meuNome.equals(playerGreen.getNome())){
            moverJogador(3, dado);
            controller.moverJogador(3, dado);
            casaFinal = playerGreen.getCasa();
            meuId = 3;
            meuSaldo = playerGreen.getSaldo();
            bonusRodada = playerGreen.isBonusRodada();
            if(playerGreen.isBonusRodada()){
                contador = "Ganhou 150 pela rodada!";
                playerGreen.setSaldo(playerGreen.getSaldo()+150);
            }
        }
        if (meuNome.equals(playerPurple.getNome())){
            moverJogador(4, dado);
            controller.moverJogador(4, dado);
            casaFinal = playerPurple.getCasa();
            meuId = 4;
            meuSaldo = playerPurple.getSaldo();
            bonusRodada = playerPurple.isBonusRodada();
            if(playerPurple.isBonusRodada()){
                contador = "Ganhou 150 pela rodada!";
                playerPurple.setSaldo(playerPurple.getSaldo()+150);
            }
        }
        repaint();
        List<Venda> casasParaVenda = new ArrayList<Venda>();
        for (int i=0; i<casas.size(); i++){
            Casa casa = casas.get(i);
            if (casa.getDono()==meuId)
                casasParaVenda.add(new Venda(i,(casa.getValor() + (casa.getValor()/2)*casa.getQtdPredios())/2));
        }
        Casa casa = casas.get(casaFinal);
        String dono = "";
        if (casa.getDono()!=0){
            if(casa.getDono()==1)
                dono = playerBlue.getNome();
            if(casa.getDono()==2)
                dono = playerRed.getNome();
            if(casa.getDono()==3)
                dono = playerGreen.getNome();
            if(casa.getDono()==4)
                dono = playerPurple.getNome();
        }            
        
        OpcoesJogada opcoesJogada = new OpcoesJogada(casa, casasParaVenda, meuId, meuSaldo, bonusRodada, casaFinal, dono, dado, this, true);
        opcoesJogada.setLocationRelativeTo(this);
        opcoesJogada.show();
        try {
            opcoesJogada.wait();
        } catch (InterruptedException | IllegalMonitorStateException ex) {
            //ignorar
        }
        if(opcoesJogada.getJogadorFaliu()){
            controller.enviarJogadorFaliu(opcoesJogada.getJsonJogada()); 
            fimDeJogo = true;
        }else
            controller.enviarJogada(opcoesJogada.getJsonJogada());
        //enviar pro server
        if(bonusRodada){
            if(meuId == 1)
                playerBlue.setBonusRodada(false);
            if(meuId == 2)
                playerRed.setBonusRodada(false);
            if(meuId == 3)
                playerGreen.setBonusRodada(false);
            if(meuId == 4)
                playerPurple.setBonusRodada(false);
        }
        dado = 0;
    }
    
    public void jogadorPosicao(String json){
        int eu = 0;
        if (meuNome.equals(playerBlue.getNome()))
            eu = 1;
        if (meuNome.equals(playerRed.getNome()))
            eu = 2;
        if (meuNome.equals(playerGreen.getNome()))
            eu = 3;
        if (meuNome.equals(playerPurple.getNome()))
            eu = 4;
        if (eu!=1){
            if(json.equals("11"))
                moverJogador(1, 1);
            if(json.equals("12"))
                moverJogador(1, 2);
            if(json.equals("13"))
                moverJogador(1, 3);
            if(json.equals("14"))
                moverJogador(1, 4);
            if(json.equals("15"))
                moverJogador(1, 5);
            if(json.equals("16"))
                moverJogador(1, 6);
        }
        if (eu!=2){
            if(json.equals("21"))
                moverJogador(2, 1);
            if(json.equals("22"))
                moverJogador(2, 2);
            if(json.equals("23"))
                moverJogador(2, 3);
            if(json.equals("24"))
                moverJogador(2, 4);
            if(json.equals("25"))
                moverJogador(2, 5);
            if(json.equals("26"))
                moverJogador(2, 6);
        }
        if (eu!=3){
            if(json.equals("31"))
                moverJogador(3, 1);        
            if(json.equals("32"))
                moverJogador(3, 2);        
            if(json.equals("33"))
                moverJogador(3, 3);        
            if(json.equals("34"))
                moverJogador(3, 4);        
            if(json.equals("35"))
                moverJogador(3, 5);        
            if(json.equals("36"))
                moverJogador(3, 6);   
        }
        if (eu!=4){
            if(json.equals("41"))
                moverJogador(4, 1);        
            if(json.equals("42"))
                moverJogador(4, 2);        
            if(json.equals("43"))
                moverJogador(4, 3);        
            if(json.equals("44"))
                moverJogador(4, 4);        
            if(json.equals("45"))
                moverJogador(4, 5);        
            if(json.equals("46"))
                moverJogador(4, 6);
        }
    }
 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Inicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaJogo().setVisible(true);
            }
        });
    }    

    private int rolarDado() {
        Random random = new Random();
        int a = random.nextInt(6)+1;
        //desenhar na tela
        return a;
    }

    private void desenharFimDeJogo(Graphics2D g) {
        if (!fimDeJogo)
            return;
        if(ganhei){
            g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/winner.png").getImage(), LARGURA/2 -200, ALTURA/2 - 161, this);
            
        }else
            g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/loser.png").getImage(), LARGURA/2 -200, ALTURA/2 - 161, this);
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                JOptionPane.showMessageDialog(null, "Fim de jogo!");
                dispose();
                Inicial.setLobbyVisible(true);
                controller.sairDaSala();
                Lobby.getSalaPanel().setVisible(false);
                Lobby.getLobbyPanel().setVisible(true);   
                Lobby.getJogadoresList().clear();
                controller.solicitarFirstLobby();
                instance = null;
            }
        }, 6000);
    }
}
