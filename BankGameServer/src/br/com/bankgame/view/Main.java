/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.view;

import br.com.bankgame.controller.Controller;
import br.com.bankgame.exceptions.ConexaoFalhouException;
import br.com.bankgame.exceptions.DadosInvalidosException;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author JhoneDarts
 */
public class Main extends javax.swing.JFrame {
    private Timer timer;
    private static Controller controller;
    /**
     * Creates new form Main
     */
    public Main() {
        controller = new Controller();
        timer = new Timer();
        initComponents();
        try{
            Image icon = Toolkit.getDefaultToolkit().getImage("src/br/com/bankgame/imagens/icoS 32x32.png");
            setIconImage(icon);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e, "Erro", 0);        
        }
        
        reset.setEnabled(false);
    }
    
    public static void atualizarSaldo(String saldo){
        saldoLabel.setText("Saldo: "+saldo);
    }
    
    public static void desligarServer(){
        try {                
            controller.power();
        } catch (IOException | ConexaoFalhouException | DadosInvalidosException ex) {
            //ignorar
        }
        status.setText("Desligado");
        power.setIcon(new ImageIcon("src/br/com/bankgame/imagens/off32x32.png"));
        reset.setEnabled(false);
        saldoLabel.setText("");
        JOptionPane.showMessageDialog(status, "Servidor Bank Coin Indisponivel!");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        StatusServer = new javax.swing.JLabel();
        reset = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        power = new javax.swing.JButton();
        saldoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BankGame Server");
        setResizable(false);

        StatusServer.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        StatusServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bankgame/imagens/icoS 64x64.png"))); // NOI18N
        StatusServer.setText("Servidor");

        reset.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Status: ");
        jLabel1.setAutoscrolls(true);

        status.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        status.setText("Desligado");

        power.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        power.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/bankgame/imagens/off32x32.png"))); // NOI18N
        power.setLabel("Power");
        power.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                powerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(reset)
                        .addGap(201, 201, 201))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(status)
                        .addGap(174, 174, 174))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(StatusServer))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(158, 158, 158)
                                .addComponent(power)))
                        .addGap(0, 119, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(saldoLabel)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saldoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(StatusServer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(status))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(power)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reset)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        saldoLabel.getAccessibleContext().setAccessibleName("Saldo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void powerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_powerActionPerformed
        try{
            if(controller.power()){
                status.setText("Ligado");
                power.setIcon(new ImageIcon("src/br/com/bankgame/imagens/on32x32.png"));
                reset.setEnabled(true);
            }else{
                status.setText("Desligado");
                power.setIcon(new ImageIcon("src/br/com/bankgame/imagens/off32x32.png"));
                reset.setEnabled(false);
                saldoLabel.setText("");
            }
        }catch(IOException | ConexaoFalhouException ex){
            JOptionPane.showMessageDialog(rootPane, "Servidor Bank Coin Indisponivel!");
        } catch (DadosInvalidosException ex) {
            JOptionPane.showMessageDialog(rootPane, "Nao foi possivel Logar em BankCoin!");
        }
    }//GEN-LAST:event_powerActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        power.setIcon(new ImageIcon("src/br/com/bankgame/imagens/reset32.png"));
        reset.setEnabled(false);
        saldoLabel.setText("");
        status.setText("Reiniciando..");
        timer.schedule(new TimerTask() {
            public void run() {
                try{
                    controller.reset();
                    status.setText("Ligado");
                    power.setIcon(new ImageIcon("src/br/com/bankgame/imagens/on32x32.png"));
                    reset.setEnabled(true);
                }catch(IOException | ConexaoFalhouException ex){
                    JOptionPane.showMessageDialog(rootPane, "Servidor Bank Coin Indisponivel!");
                } catch (DadosInvalidosException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Nao foi possivel Logar em BankCoin!");
                }
            }
        }, 1000);             
        
    }//GEN-LAST:event_resetActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Windows (introduced in Java SE 6) is not available, stay with the default look and feel.
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel StatusServer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private static javax.swing.JButton power;
    private static javax.swing.JButton reset;
    private static javax.swing.JLabel saldoLabel;
    private static javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
}