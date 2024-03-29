/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.view;

import br.com.bankgame.controller.Controller;
import br.com.bankgame.exceptions.ConexaoFalhouException;
import br.com.bankgame.exceptions.ContaJaConectadaException;
import br.com.bankgame.exceptions.ContaJaExisteException;
import br.com.bankgame.exceptions.DadosInvalidosException;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author JhoneDarts
 */
public class Inicial extends javax.swing.JFrame {
    private static boolean visible = false;
    private Controller controller;
    private static boolean servidoresON = false;
    private static Lobby lobby;
    
    public static boolean getVisible(){
        return visible;
    }
    
    public static void setLobbyVisible(boolean a){
        lobby.setVisible(a);
    }
    /**
     * Creates new form NewJFrame
     */
    public Inicial() {        
        initComponents();       
        this.visible = true;
        try{//trocando o icone da aplicação
            Image icon = Toolkit.getDefaultToolkit().getImage("src/br/com/bankgame/imagens/ico32x32.png");
            setIconImage(icon);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "Erro", 0);        
        }
        
        try {
            this.controller = Controller.getInstance();
            servidoresON = true;
            servidoresONLabel.setText("Servidores Disponiveis");
            servidoresONLabel.setIcon(new ImageIcon("src/br/com/bankgame/imagens/on16x16.png"));
        } catch (Exception ex) {            
            System.out.println(ex);
            servidoresON = false;
            servidoresONLabel.setIcon(new ImageIcon("src/br/com/bankgame/imagens/off16x16.png"));
            okLoginButton.setEnabled(false);
            criarContaButton.setEnabled(false);
        }    
    }
    
    public static void servidoresOFF(){
        servidoresON = false;
        servidoresONLabel.setIcon(new ImageIcon("src/br/com/bankgame/imagens/off16x16.png"));
        okLoginButton.setEnabled(false);
        criarContaButton.setEnabled(false);
        Controller.destroyController();
        JOptionPane.showMessageDialog(servidoresONLabel, "Houve uma falha no servidor, clique no botão \"Atualizar\"");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inicialPanel = new javax.swing.JPanel();
        bankGame = new javax.swing.JLabel();
        tituloLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        senhaLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        senhaLoginPasswordField = new javax.swing.JPasswordField();
        criarContaButton = new javax.swing.JButton();
        okLoginButton = new javax.swing.JButton();
        servidoresONLabel = new javax.swing.JLabel();
        atualizarButton = new javax.swing.JButton();
        cadastroPanel = new javax.swing.JPanel();
        usernameCadastro = new javax.swing.JLabel();
        senhaCadastro = new javax.swing.JLabel();
        emailCadastro = new javax.swing.JLabel();
        saldoCadastro = new javax.swing.JLabel();
        tituloCadastro = new javax.swing.JLabel();
        usernameCadastroTextField = new javax.swing.JTextField();
        senhaCadastroPasswordField = new javax.swing.JPasswordField();
        emailCadastroTextField = new javax.swing.JTextField();
        saldoCadastroTextField = new javax.swing.JTextField();
        okCadastroButton = new javax.swing.JButton();
        cancelarCadastroButton = new javax.swing.JButton();
        bankCoinCadastroLabel = new javax.swing.JLabel();
        bankGame1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BankGame");
        setResizable(false);
        getContentPane().setLayout(new java.awt.CardLayout());

        inicialPanel.setBackground(new java.awt.Color(255, 255, 255));
        inicialPanel.setFocusable(false);

        bankGame.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        bankGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bankgame/imagens/ico 64x64.png"))); // NOI18N
        bankGame.setText("BankGame");
        bankGame.setFocusable(false);

        tituloLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tituloLabel.setText("Login:");
        tituloLabel.setFocusable(false);

        usernameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usernameLabel.setText("Username:");

        senhaLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        senhaLabel.setText("Senha:");

        usernameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usernameTextFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                usernameTextFieldKeyReleased(evt);
            }
        });

        senhaLoginPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                senhaLoginPasswordFieldKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                senhaLoginPasswordFieldKeyReleased(evt);
            }
        });

        criarContaButton.setFocusPainted(false);
        criarContaButton.setLabel("Criar Conta");
        criarContaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarContaButtonActionPerformed(evt);
            }
        });

        okLoginButton.setText("OK");
        okLoginButton.setFocusPainted(false);
        okLoginButton.setMaximumSize(new java.awt.Dimension(75, 23));
        okLoginButton.setMinimumSize(new java.awt.Dimension(75, 23));
        okLoginButton.setPreferredSize(new java.awt.Dimension(75, 23));
        okLoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okLoginButtonActionPerformed(evt);
            }
        });

        servidoresONLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bankgame/imagens/off16x16.png"))); // NOI18N
        servidoresONLabel.setText("Servidores Indisponiveis");
        servidoresONLabel.setFocusable(false);

        atualizarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bankgame/imagens/atualizacao16x16.png"))); // NOI18N
        atualizarButton.setToolTipText("");
        atualizarButton.setFocusPainted(false);
        atualizarButton.setFocusable(false);
        atualizarButton.setLabel("atualizar");
        atualizarButton.setMargin(new java.awt.Insets(0, 2, 0, 2));
        atualizarButton.setPreferredSize(new java.awt.Dimension(93, 25));
        atualizarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inicialPanelLayout = new javax.swing.GroupLayout(inicialPanel);
        inicialPanel.setLayout(inicialPanelLayout);
        inicialPanelLayout.setHorizontalGroup(
            inicialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inicialPanelLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(inicialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(inicialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bankGame)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inicialPanelLayout.createSequentialGroup()
                            .addGap(93, 93, 93)
                            .addComponent(tituloLabel)
                            .addGap(96, 96, 96)))
                    .addGroup(inicialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(inicialPanelLayout.createSequentialGroup()
                            .addComponent(servidoresONLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(atualizarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(inicialPanelLayout.createSequentialGroup()
                            .addGroup(inicialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(usernameLabel)
                                .addComponent(senhaLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(inicialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inicialPanelLayout.createSequentialGroup()
                                    .addComponent(criarContaButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(okLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                .addComponent(usernameTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(senhaLoginPasswordField, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addGap(79, 79, 79))
        );
        inicialPanelLayout.setVerticalGroup(
            inicialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inicialPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(bankGame)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tituloLabel)
                .addGap(11, 11, 11)
                .addGroup(inicialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(servidoresONLabel)
                    .addComponent(atualizarButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inicialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inicialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(senhaLabel)
                    .addComponent(senhaLoginPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(inicialPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(criarContaButton)
                    .addComponent(okLoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        getContentPane().add(inicialPanel, "card2");

        cadastroPanel.setBackground(new java.awt.Color(255, 255, 255));

        usernameCadastro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usernameCadastro.setText("Username:");

        senhaCadastro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        senhaCadastro.setText("Senha:");

        emailCadastro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        emailCadastro.setText("E-mail:");

        saldoCadastro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        saldoCadastro.setText("Saldo:");

        tituloCadastro.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tituloCadastro.setText("Cadastro");
        tituloCadastro.setFocusable(false);

        okCadastroButton.setFocusPainted(false);
        okCadastroButton.setLabel("OK");
        okCadastroButton.setMaximumSize(new java.awt.Dimension(75, 23));
        okCadastroButton.setMinimumSize(new java.awt.Dimension(75, 23));
        okCadastroButton.setPreferredSize(new java.awt.Dimension(75, 23));
        okCadastroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okCadastroButtonActionPerformed(evt);
            }
        });

        cancelarCadastroButton.setFocusPainted(false);
        cancelarCadastroButton.setLabel("Cancelar");
        cancelarCadastroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarCadastroButtonActionPerformed(evt);
            }
        });

        bankCoinCadastroLabel.setText("BankCoin");
        bankCoinCadastroLabel.setFocusable(false);

        bankGame1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        bankGame1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bankgame/imagens/ico 64x64.png"))); // NOI18N
        bankGame1.setText("BankGame");
        bankGame1.setFocusable(false);

        javax.swing.GroupLayout cadastroPanelLayout = new javax.swing.GroupLayout(cadastroPanel);
        cadastroPanel.setLayout(cadastroPanelLayout);
        cadastroPanelLayout.setHorizontalGroup(
            cadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cadastroPanelLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(cadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameCadastro)
                    .addGroup(cadastroPanelLayout.createSequentialGroup()
                        .addGroup(cadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(senhaCadastro)
                            .addComponent(emailCadastro)
                            .addComponent(saldoCadastro))
                        .addGap(33, 33, 33)
                        .addGroup(cadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(saldoCadastroTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(emailCadastroTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(usernameCadastroTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(senhaCadastroPasswordField))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cadastroPanelLayout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addGroup(cadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cadastroPanelLayout.createSequentialGroup()
                        .addComponent(cancelarCadastroButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okCadastroButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cadastroPanelLayout.createSequentialGroup()
                        .addGroup(cadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tituloCadastro, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cadastroPanelLayout.createSequentialGroup()
                                .addComponent(bankCoinCadastroLabel)
                                .addGap(26, 26, 26)))
                        .addGap(151, 151, 151))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cadastroPanelLayout.createSequentialGroup()
                        .addComponent(bankGame1)
                        .addGap(80, 80, 80))))
        );
        cadastroPanelLayout.setVerticalGroup(
            cadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cadastroPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bankGame1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tituloCadastro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bankCoinCadastroLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(cadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(cadastroPanelLayout.createSequentialGroup()
                        .addGroup(cadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameCadastro, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(usernameCadastroTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(senhaCadastro))
                    .addComponent(senhaCadastroPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(emailCadastro, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(emailCadastroTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(saldoCadastro)
                    .addComponent(saldoCadastroTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cadastroPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okCadastroButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelarCadastroButton))
                .addContainerGap())
        );

        usernameCadastroTextField.getAccessibleContext().setAccessibleName("usernameCadastroTextField");
        senhaCadastroPasswordField.getAccessibleContext().setAccessibleName("senhaCadastroPasswordField");
        emailCadastroTextField.getAccessibleContext().setAccessibleName("emailCadastroTextField");
        saldoCadastroTextField.getAccessibleContext().setAccessibleName("saldoCadastroTextField");

        getContentPane().add(cadastroPanel, "card3");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void okLoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okLoginButtonActionPerformed
        if(!servidoresON){
            JOptionPane.showMessageDialog(inicialPanel, "Servidores Indisponiveis!");
        }        
        String login = usernameTextField.getText();
        String senha = senhaLoginPasswordField.getText();
        
        if(login.isEmpty()||senha.isEmpty()){
            JOptionPane.showMessageDialog(inicialPanel, "Todos campos devem ser preenchidos!");
            return;
        }
        try {
            String saldo = controller.logar(login, senha);
            lobby = new Lobby(login, senha, saldo);
            lobby.setLocationRelativeTo(this);
            lobby.setVisible(true);
            visible = false;
            this.dispose();
        } catch (DadosInvalidosException ex) {
            JOptionPane.showMessageDialog(inicialPanel, "Dados Invalidos!");
            usernameTextField.setText("");
            senhaLoginPasswordField.setText("");
        } catch (ContaJaConectadaException ex) {
            JOptionPane.showMessageDialog(inicialPanel, "A conta já esta logada ao servidor!");
        } catch (ConexaoFalhouException ex) {
            JOptionPane.showMessageDialog(inicialPanel, "Falha na conexao com o servidor.");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(inicialPanel, "Falha ao logar.");
        }         
    }//GEN-LAST:event_okLoginButtonActionPerformed

    private void cancelarCadastroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarCadastroButtonActionPerformed
        //limpa os campos
        usernameCadastroTextField.setText("");
        senhaCadastroPasswordField.setText("");
        emailCadastroTextField.setText("");
        saldoCadastroTextField.setText("");        
        cadastroPanel.setVisible(false);
        inicialPanel.setVisible(true);
    }//GEN-LAST:event_cancelarCadastroButtonActionPerformed

    private void criarContaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_criarContaButtonActionPerformed
        inicialPanel.setVisible(false);
        cadastroPanel.setVisible(true);
    }//GEN-LAST:event_criarContaButtonActionPerformed

    private void atualizarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarButtonActionPerformed
        servidoresONLabel.setText("Atualizando...");
        servidoresONLabel.setIcon(new ImageIcon("src/br/com/bankgame/imagens/reset16.png"));
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    controller = Controller.getInstance();
                    controller.testarConexoes();
                    servidoresON = true;
                    servidoresONLabel.setText("Servidores Disponiveis");
                    servidoresONLabel.setIcon(new ImageIcon("src/br/com/bankgame/imagens/on16x16.png"));
                    okLoginButton.setEnabled(true);
                    criarContaButton.setEnabled(true);
                } catch (ConexaoFalhouException | IOException ex) {
                    System.out.println(ex);
                    servidoresON = false;
                    servidoresONLabel.setText("Servidores Indisponiveis");
                    servidoresONLabel.setIcon(new ImageIcon("src/br/com/bankgame/imagens/off16x16.png"));
                    okLoginButton.setEnabled(false);
                    criarContaButton.setEnabled(false);
                }
            }
        }, 50);
    }//GEN-LAST:event_atualizarButtonActionPerformed

    private void okCadastroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okCadastroButtonActionPerformed
        String login = usernameCadastroTextField.getText();
        String senha = senhaCadastroPasswordField.getText();
        String email = emailCadastroTextField.getText();   
        String saldo1 = saldoCadastroTextField.getText();
        
        if(login.isEmpty()||senha.isEmpty()||email.isEmpty()||saldo1.isEmpty()){
            JOptionPane.showMessageDialog(cadastroPanel, "Todos campos devem ser preenchidos!");
            return;
        }
        
        if(login.contains("!")||login.contains("@")||login.contains("#")||login.contains("$")||
                login.contains("¨")||login.contains("&")||login.contains("*")||login.contains("(")||
                login.contains(")")||login.contains("-")||login.contains("+")||login.contains("=")||
                login.contains("'")||login.contains("\"")||login.contains("§")||login.contains("[")||
                login.contains("]")||login.contains("{")||login.contains("}")||login.contains("º")||
                login.contains("ª")||login.contains("<")||login.contains(">")||login.contains("<")||
                login.contains(":")||login.contains(";")||login.contains(".")||login.contains(",")||
                login.contains("/")||login.contains("?")||login.contains("°")){
            JOptionPane.showMessageDialog(cadastroPanel, "Não é permitido caracteres especiais!\n ex: /, #, $, +, ?, !, etc");
            return;
        }
        int saldo;
        try{
            saldo = Integer.parseInt(saldo1);
            if (saldo<0)
                throw new NumberFormatException();
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(cadastroPanel, "Digite um valor valido para saldo!");
            return;
        }
        
        if(!(email.contains("@")|| email.contains(".com"))){
            JOptionPane.showMessageDialog(cadastroPanel, "Digite um email valido!");      
            return;
        }        
        
        try {
            controller.cadastrar(login, senha, email, saldo);
            lobby = new Lobby(login, senha, ""+saldo);
            lobby.setLocationRelativeTo(this);
            lobby.setVisible(true);
            visible = false;
            this.dispose();
        } catch (ContaJaExisteException ex) {
            JOptionPane.showMessageDialog(cadastroPanel, "Conta ja existe!");
        } catch (ConexaoFalhouException ex) {
            JOptionPane.showMessageDialog(cadastroPanel, "Falha na conexao com o servidor.");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(cadastroPanel, "Erro ao cadastrar!");
        }       
        
    }//GEN-LAST:event_okCadastroButtonActionPerformed

    private void usernameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameTextFieldKeyPressed
        if(!servidoresON){
            JOptionPane.showMessageDialog(inicialPanel, "Clique no botão Atualizar!");
            return;
        }
        
        int key = evt.getKeyCode();
        if (key == java.awt.event.KeyEvent.VK_ENTER){
            if (usernameTextField.getText().isEmpty()){
                JOptionPane.showMessageDialog(inicialPanel, "Preencha o campo \"Username\"!");
            }else if (!usernameTextField.getText().isEmpty() && senhaLoginPasswordField.getText().isEmpty()){
                JOptionPane.showMessageDialog(inicialPanel, "Preencha o campo \"Senha\"!");
            }else{
                okLoginButtonActionPerformed(null);
            }
        }
    }//GEN-LAST:event_usernameTextFieldKeyPressed

    private void senhaLoginPasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_senhaLoginPasswordFieldKeyPressed
        if(!servidoresON){
            JOptionPane.showMessageDialog(inicialPanel, "Clique no botão Atualizar!");
            return;
        }
            
        int key = evt.getKeyCode();
        if (key == java.awt.event.KeyEvent.VK_ENTER){
            if (!usernameTextField.getText().isEmpty() && senhaLoginPasswordField.getText().isEmpty()){
                JOptionPane.showMessageDialog(inicialPanel, "Preencha o campo \"Senha\"!");
            }else if (usernameTextField.getText().isEmpty() && !senhaLoginPasswordField.getText().isEmpty()){
                JOptionPane.showMessageDialog(inicialPanel, "Preencha o campo \"Username\"!");
            }else{
                okLoginButtonActionPerformed(null);
            }
        }
    }//GEN-LAST:event_senhaLoginPasswordFieldKeyPressed

    private void usernameTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameTextFieldKeyReleased
        int key = evt.getKeyCode();
        if (key == java.awt.event.KeyEvent.VK_ENTER)
        { }
    }//GEN-LAST:event_usernameTextFieldKeyReleased

    private void senhaLoginPasswordFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_senhaLoginPasswordFieldKeyReleased
        int key = evt.getKeyCode();
        if (key == java.awt.event.KeyEvent.VK_ENTER)
        { }
    }//GEN-LAST:event_senhaLoginPasswordFieldKeyReleased

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
                new Inicial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton atualizarButton;
    private javax.swing.JLabel bankCoinCadastroLabel;
    private javax.swing.JLabel bankGame;
    private javax.swing.JLabel bankGame1;
    private javax.swing.JPanel cadastroPanel;
    private javax.swing.JButton cancelarCadastroButton;
    private static javax.swing.JButton criarContaButton;
    private javax.swing.JLabel emailCadastro;
    private javax.swing.JTextField emailCadastroTextField;
    private javax.swing.JPanel inicialPanel;
    private javax.swing.JButton okCadastroButton;
    private static javax.swing.JButton okLoginButton;
    private javax.swing.JLabel saldoCadastro;
    private javax.swing.JTextField saldoCadastroTextField;
    private javax.swing.JLabel senhaCadastro;
    private javax.swing.JPasswordField senhaCadastroPasswordField;
    private javax.swing.JLabel senhaLabel;
    private javax.swing.JPasswordField senhaLoginPasswordField;
    private static javax.swing.JLabel servidoresONLabel;
    private javax.swing.JLabel tituloCadastro;
    private javax.swing.JLabel tituloLabel;
    private javax.swing.JLabel usernameCadastro;
    private javax.swing.JTextField usernameCadastroTextField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
