/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.view;

import br.com.bankgame.controller.Controller;
import br.com.bankgame.exceptions.ConexaoFalhouException;
import br.com.bankgame.exceptions.ContaJaConectadaException;
import br.com.bankgame.exceptions.DadosInvalidosException;
import br.com.bankgame.model.Jogador;
import br.com.bankgame.model.Sala;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JhoneDarts
 */
public class Lobby extends javax.swing.JFrame {    
    private static List<Sala> salas;
    private static Controller controller;
    private static String idContaLogada = null;
    private static String senhaContaLogada = null;
    private static String saldo = null;
    private static List<Jogador> jogadoresSala;
    
    /**
     * Creates new form Lobby1
     */
    public Lobby(String idContalogoda, String senhaContaLogada, String saldo) { 
        this.saldo = saldo;
        this.idContaLogada = idContalogoda;
        this.senhaContaLogada = senhaContaLogada;
        
        initComponents();
        desconectadoLobbyLabel.setVisible(false);
        reconectarLobbyButton.setVisible(false);
        jogoComecara.setVisible(false);
        comecaraSegundos.setVisible(false);
        
        try{//trocando o icone da aplicação
            Image icon = Toolkit.getDefaultToolkit().getImage("src/br/com/bankgame/imagens/ico32x32.png");
            setIconImage(icon);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "Erro", 0);        
        }
        nickLabel.setText("Jogador: "+idContalogoda);
        saldoLabel.setText("Saldo: "+saldo+" gold");
        try {
            this.controller = Controller.getInstance();
        } catch (ConexaoFalhouException | IOException ex) {
            JOptionPane.showMessageDialog(lobbyPanel, "Falha na conexao com o Servidor. Seu client será encerrado agora.");  
            System.exit(0);
            return;
        } 
        salas = new ArrayList<Sala>();
        controller.solicitarFirstLobby();
    }    
    
    public static void atualizarLobby(Sala salaModificada){
        boolean novaSala = true;
        for (int i=0; i<salas.size(); i++){
            if (salas.get(i).getNome().equals(salaModificada.getNome())){                
                salas.set(i, salaModificada);
                novaSala = false;
            }
        }
        if (salaModificada.getJogadores().size()<1){
            for (int i=0; i<salas.size(); i++){
                if (salas.get(i).getNome().equals(salaModificada.getNome())){                
                    salas.remove(i);
                    System.out.println("delete");
                }
            }
        }
        if(novaSala)
            salas.add(salaModificada);
        //pega o indice da linha selecionada (nao valor id da coluna id)        
        int rowSelectioned = salasTable.getSelectedRow();
        
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel = (DefaultTableModel) salasTable.getModel();               
       
        //zera a tabela
        int qtdLinhas = tableModel.getRowCount()-1;
        for (int i = qtdLinhas; i >= 0; i--) {                    
            tableModel.removeRow(i);
        }
        //constroi a tableModel com os valores de salas
        int id = 1;
        for (Sala sala : salas) {
            String senha;
            if (!sala.getSenha().isEmpty()) {
                senha = "Sim";//tem senha? = sim
            } else {
                senha = "Nao";
            }
            tableModel.addRow(new Object[]{id, sala.getNome(), senha, sala.getJogadores().size() + "/4"});
            id++;
        }
        salasTable.setModel(tableModel);

        // pra manter a linha selecionada              
        if (!(rowSelectioned == -1) && (rowSelectioned < salas.size())) {
            salasTable.setRowSelectionInterval(rowSelectioned, rowSelectioned);
        } 
    }
    
    public static void atualizarFirstLobby(List<Sala> salaList) {     
        salas = salaList;        
        
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel = (DefaultTableModel) salasTable.getModel();
        
        //zera a tabela
        int qtdLinhas = tableModel.getRowCount()-1;
        for (int i = qtdLinhas; i >= 0; i--) {                    
            tableModel.removeRow(i);
        }
        //constroi a tableModel com os valores de salas
        int id = 1;
        for (Sala sala : salas) {
            String senha;
            if (!sala.getSenha().isEmpty()) {
                senha = "Sim";
            } else {
                senha = "Nao";
            }
            tableModel.addRow(new Object[]{id, sala.getNome(), senha, sala.getJogadores().size() + "/4"});
            id++;
        }
        salasTable.setModel(tableModel);            
    }
    
    public static void atualizarSala(List<Jogador> jogadores){
        jogadoresSala = jogadores;
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel = (DefaultTableModel) jogadoresTable.getModel();
        
        //zera a tabela
        int qtdLinhas = tableModel.getRowCount()-1;
        for (int i = qtdLinhas; i >= 0; i--) {                    
            tableModel.removeRow(i);
        }
                
        //constroi a tableModel com os valores de salas
        for (Jogador jogador : jogadores) {
            if (jogador.getLogin().equals(idContaLogada))
                jogador.setLogin(idContaLogada+" [eu]");
            String pronto;
            if (jogador.isPronto()) {
                pronto = "Sim";
            } else {
                pronto = "Nao";
            }
            tableModel.addRow(new Object[]{jogador.getLogin(), pronto});            
        }
        
        if(todosProntos()){
            controller.saldoOK();
        }
    }
    
    public static void atualizarSaldo(String novoSaldo){
        saldo = novoSaldo;
        saldoLabel.setText("Saldo: "+saldo+" gold");
    }
    
    public static boolean todosProntos(){
        int prontos = 0;
        for (Jogador jogador : jogadoresSala){
            if(jogador.isPronto())
                prontos++;
        }
        if(!(prontos == jogadoresSala.size() && jogadoresSala.size()>1))
            return false;
        if (Integer.parseInt(saldo)<500){               
            controller.sairDaSala();
            salaPanel.setVisible(false);
            lobbyPanel.setVisible(true);   
            jogadoresSala.clear();
            //retornar a atualizar o lobbyTable
            controller.solicitarFirstLobby();
            JOptionPane.showMessageDialog(lobbyPanel, "Você não possui gold suficiente!\nSão necessários 500 gold.");
            return false;
        }        
        return true;        
    }
    
    public static void comecarJogo(){
        /*try {
            ///tomar o dinaro da moçada!! 
            controller.transferirBCoin("BankGame", 500);
            saldo = ""+(Integer.parseInt(saldo) - 500);
            saldoLabel.setText("Saldo: "+saldo+" gold");
        } catch (ConexaoFalhouException | DadosInvalidosException ex) {
            JOptionPane.showMessageDialog(salaPanel, "Houston, temos um problema!");
            return;
        }*/
        jogoComecara.setVisible(true);
        comecaraSegundos.setVisible(true);
        try { 
            for (int i = 5; i >= 0; i--) { 
                comecaraSegundos.setText(i+"s...");
                Thread.sleep(1000); // 1 segundo 
            }            
        } catch (InterruptedException e) { 
            System.out.println("Cronometragem interrompida"); 
        }
        ////////////////////////////////////////////////////////////////////////
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!>>>
        //                                                                   <<<
        TelaJogo.getInstance().setVisible(true);
        TelaJogo.setMeuNome(idContaLogada);        
        Inicial.setLobbyVisible(false);
        
        //                               AQUI!!!!                            >>>     
        //                                                                   <<<
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!>>>
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        ////////////////////////////////////////////////////////////////////////
        
    }
    
    public static void servidoresOFF (){
        //se o cara estiver em uma sala, retira-o da sala...
        if (salaPanel.isVisible()){
            salaPanel.setVisible(false);
            lobbyPanel.setVisible(true);
        }
        Controller.destroyController();
        JOptionPane.showMessageDialog(lobbyPanel, "Houve uma falha no servidor, clique no botão \"Reconectar\"");
        //ativa botao reconectar e mensagem de falha
        desconectadoLobbyLabel.setVisible(true);
        reconectarLobbyButton.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lobbyPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        salasTable = new javax.swing.JTable();
        bankGame1 = new javax.swing.JLabel();
        tituloLobbyLabel = new javax.swing.JLabel();
        criarSalaLobbyButton = new javax.swing.JButton();
        entrarLobbyButton = new javax.swing.JButton();
        nickLabel = new javax.swing.JLabel();
        saldoLabel = new javax.swing.JLabel();
        desconectadoLobbyLabel = new javax.swing.JLabel();
        reconectarLobbyButton = new javax.swing.JButton();
        salaPanel = new javax.swing.JPanel();
        bankGame2 = new javax.swing.JLabel();
        tituloSalaLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jogadoresTable = new javax.swing.JTable();
        salaLabel = new javax.swing.JLabel();
        sairSalaButton = new javax.swing.JButton();
        prontoSalaButton = new javax.swing.JButton();
        jogoComecara = new javax.swing.JLabel();
        comecaraSegundos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BankGame");
        setResizable(false);
        getContentPane().setLayout(new java.awt.CardLayout());

        lobbyPanel.setBackground(new java.awt.Color(255, 255, 255));

        salasTable.setBackground(new java.awt.Color(200, 230, 255));
        salasTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        salasTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID:", "Nome:", "Senha:", "Jogadores:"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        salasTable.setToolTipText("");
        salasTable.getTableHeader().setReorderingAllowed(false);
        salasTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                salasTableKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                salasTableKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(salasTable);
        if (salasTable.getColumnModel().getColumnCount() > 0) {
            salasTable.getColumnModel().getColumn(0).setPreferredWidth(10);
            salasTable.getColumnModel().getColumn(1).setPreferredWidth(140);
            salasTable.getColumnModel().getColumn(2).setPreferredWidth(10);
            salasTable.getColumnModel().getColumn(3).setPreferredWidth(25);
        }
        salasTable.getAccessibleContext().setAccessibleName("");

        bankGame1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        bankGame1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bankgame/imagens/ico32x32.png"))); // NOI18N
        bankGame1.setText("BankGame");

        tituloLobbyLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tituloLobbyLabel.setText("Saguão principal");

        criarSalaLobbyButton.setFocusPainted(false);
        criarSalaLobbyButton.setFocusable(false);
        criarSalaLobbyButton.setLabel("Criar Sala");
        criarSalaLobbyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarSalaLobbyButtonActionPerformed(evt);
            }
        });

        entrarLobbyButton.setFocusPainted(false);
        entrarLobbyButton.setLabel("Entrar");
        entrarLobbyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entrarLobbyButtonActionPerformed(evt);
            }
        });

        nickLabel.setText("Jogador: ");

        saldoLabel.setText("Saldo: ");

        desconectadoLobbyLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bankgame/imagens/alerta24.png"))); // NOI18N
        desconectadoLobbyLabel.setText("Desconectado!");
        desconectadoLobbyLabel.setFocusable(false);

        reconectarLobbyButton.setFocusPainted(false);
        reconectarLobbyButton.setFocusable(false);
        reconectarLobbyButton.setLabel("Reconectar");
        reconectarLobbyButton.setMargin(new java.awt.Insets(2, 10, 2, 10));
        reconectarLobbyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reconectarLobbyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lobbyPanelLayout = new javax.swing.GroupLayout(lobbyPanel);
        lobbyPanel.setLayout(lobbyPanelLayout);
        lobbyPanelLayout.setHorizontalGroup(
            lobbyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lobbyPanelLayout.createSequentialGroup()
                .addGroup(lobbyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lobbyPanelLayout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(tituloLobbyLabel))
                    .addGroup(lobbyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(lobbyPanelLayout.createSequentialGroup()
                            .addGap(122, 122, 122)
                            .addComponent(bankGame1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(lobbyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(nickLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(saldoLabel, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGroup(lobbyPanelLayout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addGroup(lobbyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(lobbyPanelLayout.createSequentialGroup()
                                    .addComponent(desconectadoLobbyLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(reconectarLobbyButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(criarSalaLobbyButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(entrarLobbyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        lobbyPanelLayout.setVerticalGroup(
            lobbyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lobbyPanelLayout.createSequentialGroup()
                .addGroup(lobbyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(lobbyPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(bankGame1))
                    .addGroup(lobbyPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nickLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saldoLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tituloLobbyLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(lobbyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(entrarLobbyButton)
                    .addComponent(criarSalaLobbyButton)
                    .addComponent(desconectadoLobbyLabel)
                    .addComponent(reconectarLobbyButton))
                .addContainerGap())
        );

        nickLabel.getAccessibleContext().setAccessibleName("nickLabel");

        getContentPane().add(lobbyPanel, "card2");

        salaPanel.setBackground(new java.awt.Color(255, 255, 255));

        bankGame2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        bankGame2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bankgame/imagens/ico32x32.png"))); // NOI18N
        bankGame2.setText("BankGame");

        tituloSalaLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tituloSalaLabel.setText("Sala");

        jogadoresTable.setBackground(new java.awt.Color(200, 230, 255));
        jogadoresTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jogadoresTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome:", "Preparado:"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jogadoresTable.setToolTipText("");
        jogadoresTable.setFocusable(false);
        jogadoresTable.setRowSelectionAllowed(false);
        jogadoresTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jogadoresTable);
        if (jogadoresTable.getColumnModel().getColumnCount() > 0) {
            jogadoresTable.getColumnModel().getColumn(0).setPreferredWidth(200);
            jogadoresTable.getColumnModel().getColumn(1).setPreferredWidth(10);
        }

        salaLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        salaLabel.setText("Sala: ");

        sairSalaButton.setFocusPainted(false);
        sairSalaButton.setFocusable(false);
        sairSalaButton.setLabel("Sair");
        sairSalaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sairSalaButtonActionPerformed(evt);
            }
        });

        prontoSalaButton.setText("Pronto!");
        prontoSalaButton.setFocusPainted(false);
        prontoSalaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prontoSalaButtonActionPerformed(evt);
            }
        });

        jogoComecara.setText("O jogo começará em: ");
        jogoComecara.setFocusable(false);

        comecaraSegundos.setText("5 s ...");

        javax.swing.GroupLayout salaPanelLayout = new javax.swing.GroupLayout(salaPanel);
        salaPanel.setLayout(salaPanelLayout);
        salaPanelLayout.setHorizontalGroup(
            salaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salaPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bankGame2)
                .addGap(124, 124, 124))
            .addGroup(salaPanelLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(salaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(salaPanelLayout.createSequentialGroup()
                        .addComponent(salaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tituloSalaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(salaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salaPanelLayout.createSequentialGroup()
                            .addComponent(jogoComecara)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(comecaraSegundos)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sairSalaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(prontoSalaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        salaPanelLayout.setVerticalGroup(
            salaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salaPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(bankGame2)
                .addGap(18, 18, 18)
                .addGroup(salaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tituloSalaLabel)
                    .addComponent(salaLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(salaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prontoSalaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sairSalaButton)
                    .addComponent(jogoComecara)
                    .addComponent(comecaraSegundos))
                .addContainerGap())
        );

        jogoComecara.getAccessibleContext().setAccessibleName("O jogo começará");

        getContentPane().add(salaPanel, "card3");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sairSalaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sairSalaButtonActionPerformed
        controller.sairDaSala();        
        
        salaPanel.setVisible(false);
        lobbyPanel.setVisible(true);   
        jogadoresSala.clear();
        //retornar a atualizar o lobbyTable
        controller.solicitarFirstLobby();
    }//GEN-LAST:event_sairSalaButtonActionPerformed

    private void prontoSalaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prontoSalaButtonActionPerformed
        if (Integer.parseInt(saldo)<500){
            JOptionPane.showMessageDialog(lobbyPanel, "Você não possui gold suficiente!\nSão necessários 500 gold.");
            sairSalaButtonActionPerformed(null);            
            return;
        }
        controller.jogadorPronto();        
    }//GEN-LAST:event_prontoSalaButtonActionPerformed

    private void entrarLobbyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entrarLobbyButtonActionPerformed
        if (Integer.parseInt(saldo)<500){
            JOptionPane.showMessageDialog(lobbyPanel, "Você não possui gold suficiente!\nSão necessários 500 gold.");
            return;
        }
        int index = salasTable.getSelectedRow();
        if (index == -1){
            JOptionPane.showMessageDialog(lobbyPanel, "Selecione uma sala.");
            return;
        }
        String nomeSala = salas.get(index).getNome();
        String senhaSala = salas.get(index).getSenha();
        String senhaDigitada = "";

        //pegar senha pros que tem senha
        if(!senhaSala.isEmpty()){
            DigiteSenhaDialog digiteSenhaDialog = new DigiteSenhaDialog(this, true, nomeSala);
            digiteSenhaDialog.setLocationRelativeTo(lobbyPanel);//centraliza no meio da aplicação
            digiteSenhaDialog.show();
            try {
                digiteSenhaDialog.wait();
            } catch (InterruptedException | IllegalMonitorStateException ex) {
                //ignorar
            }
            senhaDigitada = digiteSenhaDialog.getSenhaSala();
            // caso o cara simplesmente feche o jdialog
            if (!(digiteSenhaDialog.getResposta()))
            return;
        }

        //verifica a senha (nao tem problema se nao houver senha)
        if (!(senhaDigitada.equals(senhaSala))){
            JOptionPane.showMessageDialog(lobbyPanel, "Senha incorreta!");
            return;
        }

        //fazer a atualização do lobby parar
        controller.atualizarLobbyCancel();
        //entra na sala
        try {
            if (!controller.entrarSala(nomeSala))
            throw new DadosInvalidosException();
        } catch (ConexaoFalhouException ex) {
            JOptionPane.showMessageDialog(lobbyPanel, "Falha na conexao com o servidor");
            return;
        } catch (DadosInvalidosException ex){
            JOptionPane.showMessageDialog(lobbyPanel, "A sala nao esta mais disponivel!");
            //retornar a atualizar o lobbyTable
            controller.solicitarFirstLobby();
            return;
        }
        controller.solicitarAtualizarSala();
        tituloSalaLabel.setText(nomeSala);
        lobbyPanel.setVisible(false);
        salaPanel.setVisible(true);
    }//GEN-LAST:event_entrarLobbyButtonActionPerformed

    private void criarSalaLobbyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarSalaLobbyButtonActionPerformed
        if (Integer.parseInt(saldo)<500){
            JOptionPane.showMessageDialog(lobbyPanel, "Você não possui gold suficiente!\nSão necessários 500 gold.");
            return;
        }            
        
        CriarSalaDialog criarSalaDialog = new CriarSalaDialog(this, true);
        criarSalaDialog.setLocationRelativeTo(lobbyPanel);//centraliza no meio da aplicação
        criarSalaDialog.show();

        String nomeSala = "";
        String senhaSala = "";
        try {
            criarSalaDialog.wait();
        } catch (InterruptedException | IllegalMonitorStateException ex) {
            if (!criarSalaDialog.getResposta())
            return;
            nomeSala = criarSalaDialog.getNomeSala();
            senhaSala = criarSalaDialog.getSenhaSala();

            //fazer a atualização do lobby parar
            controller.atualizarLobbyCancel();
            //cria a sala
            try {
                if (!controller.criarSala(nomeSala, senhaSala))
                    throw new DadosInvalidosException();
            } catch (ConexaoFalhouException ex1) {
                JOptionPane.showMessageDialog(lobbyPanel, "Falha na conexao com o servidor.");
                return;
            } catch (DadosInvalidosException ex1){
                JOptionPane.showMessageDialog(lobbyPanel, "Nome de sala ja existe!");
                return;
            }
        }

        controller.solicitarAtualizarSala();
        tituloSalaLabel.setText(nomeSala);
        lobbyPanel.setVisible(false);
        salaPanel.setVisible(true);
    }//GEN-LAST:event_criarSalaLobbyButtonActionPerformed

    private void salasTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_salasTableKeyReleased
        int key = evt.getKeyCode();
        if (key == java.awt.event.KeyEvent.VK_ENTER)
        { }
    }//GEN-LAST:event_salasTableKeyReleased

    private void salasTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_salasTableKeyPressed
        int key = evt.getKeyCode();
        if (key == java.awt.event.KeyEvent.VK_ENTER){
            entrarLobbyButtonActionPerformed(null);
        }
    }//GEN-LAST:event_salasTableKeyPressed

    private void reconectarLobbyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reconectarLobbyButtonActionPerformed
        try {
            controller = Controller.getInstance();
            controller.testarConexoes();            
        } catch (ConexaoFalhouException | IOException ex) {
            JOptionPane.showMessageDialog(lobbyPanel, "Não foi possível reconectar!");
            return;
        }
            
        //relogar
        try {
            String saldo = controller.logar(idContaLogada, senhaContaLogada);            
            saldoLabel.setText("Saldo: "+saldo+" gold");
            desconectadoLobbyLabel.setVisible(false);
            reconectarLobbyButton.setVisible(false);
        } catch (ContaJaConectadaException ex) {
            JOptionPane.showMessageDialog(lobbyPanel, "A conta já esta logada ao servidor!");
        } catch (ConexaoFalhouException ex) {
            JOptionPane.showMessageDialog(lobbyPanel, "Falha na conexao com o servidor.");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | DadosInvalidosException ex) {
            JOptionPane.showMessageDialog(lobbyPanel, "Falha ao logar.");
        }
        controller.solicitarFirstLobby();
    }//GEN-LAST:event_reconectarLobbyButtonActionPerformed

    public void cronometrar(int min,int segundos) {
        
        try { 
            for (int i = segundos; i > 0; segundos--) { 
                System.out.print(min +":"+segundos+"\n"); 
                Thread.sleep(1000); // 1 segundo 
            } 
            System.out.println("Sai da Fila"); 
            } catch (InterruptedException e) { 
                System.out.println("Cronometragem interrompida"); 
            } 
        } 
    
    public static JPanel getSalaPanel(){
        return salaPanel;
    }
    public static JPanel getLobbyPanel(){
        return lobbyPanel;
    }
    public static List<Jogador> getJogadoresList(){
        return jogadoresSala;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(Lobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lobby.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lobby(idContaLogada, senhaContaLogada, saldo).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bankGame1;
    private javax.swing.JLabel bankGame2;
    private static javax.swing.JLabel comecaraSegundos;
    private javax.swing.JButton criarSalaLobbyButton;
    private static javax.swing.JLabel desconectadoLobbyLabel;
    private javax.swing.JButton entrarLobbyButton;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JTable jogadoresTable;
    private static javax.swing.JLabel jogoComecara;
    private static javax.swing.JPanel lobbyPanel;
    private javax.swing.JLabel nickLabel;
    private javax.swing.JButton prontoSalaButton;
    private static javax.swing.JButton reconectarLobbyButton;
    private javax.swing.JButton sairSalaButton;
    private javax.swing.JLabel salaLabel;
    private static javax.swing.JPanel salaPanel;
    private static javax.swing.JTable salasTable;
    private static javax.swing.JLabel saldoLabel;
    private javax.swing.JLabel tituloLobbyLabel;
    private javax.swing.JLabel tituloSalaLabel;
    // End of variables declaration//GEN-END:variables
}

// Changes:

//verificar saldo e tomar 500 gold ao iniciar a partida
//   caso alguem nao possua saldo valido neste ponto a partida 
//   nao sera iniciada e o jogador inadinplente sera excluido 
//   voltando pra tela de lobby