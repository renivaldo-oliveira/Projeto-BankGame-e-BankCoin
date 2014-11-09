/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.view;

import javax.swing.JOptionPane;

/**
 *
 * @author JhoneDarts
 */
public class DigiteSenhaDialog extends javax.swing.JDialog {
    private static String nomeSala = "Sala: ";
    private boolean resposta = false;
    /**
     * Creates new form digiteSenhaDialog
     */
    public DigiteSenhaDialog(java.awt.Frame parent, boolean modal, String nomeSala) {
        super(parent, modal);
        initComponents();
        this.nomeSala = nomeSala;
        nomeSalaLabel.setText("Sala: "+nomeSala);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        senhaLabel = new javax.swing.JLabel();
        senhaPasswordField = new javax.swing.JPasswordField();
        tituloLabel = new javax.swing.JLabel();
        nomeSalaLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        cancelarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        senhaLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        senhaLabel.setText("Senha:");

        senhaPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                senhaPasswordFieldKeyReleased(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                senhaPasswordFieldKeyPressed(evt);
            }
        });

        tituloLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tituloLabel.setText("Digite a senha");
        tituloLabel.setToolTipText("");

        nomeSalaLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nomeSalaLabel.setText("Sala: cacacasasfasdasdasfasfasfasf");

        okButton.setLabel("OK");
        okButton.setMargin(new java.awt.Insets(2, 10, 2, 10));
        okButton.setMaximumSize(new java.awt.Dimension(75, 23));
        okButton.setMinimumSize(new java.awt.Dimension(75, 23));
        okButton.setPreferredSize(new java.awt.Dimension(75, 23));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelarButton.setLabel("Cancelar");
        cancelarButton.setMargin(new java.awt.Insets(2, 10, 2, 10));
        cancelarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cancelarButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nomeSalaLabel)
                                .addGap(0, 36, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(senhaLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(senhaPasswordField)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(tituloLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tituloLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomeSalaLabel)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(senhaLabel)
                    .addComponent(senhaPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelarButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelarButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (senhaPasswordField.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Digite uma senha!");
            return;
        }
        resposta = true;
        dispose();            
    }//GEN-LAST:event_okButtonActionPerformed

    private void senhaPasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_senhaPasswordFieldKeyPressed
        int key = evt.getKeyCode();
        if (key == java.awt.event.KeyEvent.VK_ENTER){
            if (senhaPasswordField.getText().isEmpty()){
                //ignorar
            }else 
                okButtonActionPerformed(null);            
        }
    }//GEN-LAST:event_senhaPasswordFieldKeyPressed

    private void senhaPasswordFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_senhaPasswordFieldKeyReleased
        int key = evt.getKeyCode();
        if (key == java.awt.event.KeyEvent.VK_ENTER)
        { }
    }//GEN-LAST:event_senhaPasswordFieldKeyReleased

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DigiteSenhaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DigiteSenhaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DigiteSenhaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DigiteSenhaDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DigiteSenhaDialog dialog = new DigiteSenhaDialog(new javax.swing.JFrame(), true, nomeSala);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelarButton;
    private javax.swing.JLabel nomeSalaLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel senhaLabel;
    private javax.swing.JPasswordField senhaPasswordField;
    private javax.swing.JLabel tituloLabel;
    // End of variables declaration//GEN-END:variables
    
    public boolean getResposta(){
        return resposta;
    }

    public String getSenhaSala() {
        return senhaPasswordField.getText();
    }
}
