/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.view;

import br.com.bankgame.model.Casa;
import br.com.bankgame.model.JSONParse;
import br.com.bankgame.model.Venda;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JhoneDarts
 */
public class OpcoesJogada extends javax.swing.JDialog {
    private static Casa casa;
    private static List<Venda> casasParaVenda;
    private static int meuId;
    private static int idCasa;
    private int pagamentoValor = 0;
    private static String pagamentoIdConta;
    private int compraIdCasa =0;
    private int compraValor =0;
    private List<Venda> casasVendidas;
    private static int meuSaldo;
    private boolean jogadorFaliu;
    private String json;
    private int valorTotalVendas;
    
    private static boolean bonusRodada;    
    private static int dado;
    /**
     * Creates new form OpcoesJogada
     * @param casa
     * @param parent
     * @param modal
     */
    public OpcoesJogada(Casa casa, List<Venda> casasParaVenda, int meuId, int meuSaldo, boolean bonusRodada, int idCasa, String idConta, int dado, java.awt.Frame parent, boolean modal) {        
        super(parent, modal);
        initComponents();
        this.casa = casa;
        this.casasParaVenda =  casasParaVenda;
        this.meuId = meuId;
        this.meuSaldo = meuSaldo;
        this.idCasa = idCasa;
        this.pagamentoIdConta = idConta;
        this.dado = dado;
        this.casasVendidas = new ArrayList<Venda>();
        this.jogadorFaliu = false;
        this.valorTotalVendas = 0;
        this.bonusRodada = bonusRodada;
        verificarOpcoes();
    }
    
    private void verificarOpcoes(){
        if(casasParaVenda.isEmpty())
            VenderButton.setEnabled(false);
        if(bonusRodada){
            statusRodadaLabel.setText("Voce ganhou 150 gold!");
            pagamentoValor = -150;
        }
        if(casa.getValor()<100){//verificar a casas especiais
            comprarButton.setEnabled(false);
            adicionarPredioButton.setEnabled(false);
            if(casa.getValor()==-50){
                statusRodadaLabel.setText("Voce ganhou 50 gold!");
                pagamentoValor += casa.getValor();
            }else if(casa.getValor()==-150){ //casa inicio
                //ignora, pois ja foi creditado. (segundo if)
            }else{
                int valor;
                if (casa.getQtdPredios()==0)
                    valor = casa.getValor();
                else
                    valor = casa.getValor();
                                 
                pagamentoValor += valor;
                if((meuSaldo - pagamentoValor) <=0)
                    jogadorFaliu = true;
                statusRodadaLabel.setText("Voce pagou "+valor+" gold!\n Saldo: "+(meuSaldo-pagamentoValor));
            }
        }else{
            if (casa.getDono()==0){
                statusRodadaLabel.setText("Imovel sem dono. Preco: "+casa.getValor());
                adicionarPredioButton.setEnabled(false);
                pagamentoValor += 0;
            }else{
                comprarButton.setEnabled(false);
                if(casa.getMultiplicador()>1){//companias
                    adicionarPredioButton.setEnabled(false);
                    if (casa.getDono()==meuId){
                        statusRodadaLabel.setText("Voce tem posse desta compania.");
                        pagamentoValor += 0;
                    }else{
                        pagamentoValor += dado*casa.getMultiplicador();
                        if((meuSaldo - pagamentoValor) <=0)
                            jogadorFaliu = true;
                        statusRodadaLabel.setText("Voce pagou "+pagamentoValor+" gold!\n Saldo: "+(meuSaldo-pagamentoValor));
                    }
                }else{//restante
                    if(casa.getDono()==meuId){
                        statusRodadaLabel.setText("Você tem posse deste imóvel.\nAdicionar Prédio: "+casa.getValor()/2+" gold");
                        pagamentoValor += 0;
                    }else{
                        adicionarPredioButton.setEnabled(false);
                        int valor;
                        if (casa.getQtdPredios()==0)
                            valor = (int) (casa.getValor()*0.1);
                        else
                            valor = (int) (casa.getValor()*0.1)*(casa.getQtdPredios() + 1);

                        pagamentoValor += valor;
                        if((meuSaldo - pagamentoValor) <=0)
                            jogadorFaliu = true;
                        statusRodadaLabel.setText("Voce pagou "+valor+" gold!\n Saldo: "+(meuSaldo-pagamentoValor));   
                    }
                }
            }
        }
        if (meuSaldo<casa.getValor())
            comprarButton.setEnabled(false);
        if(casa.getDono() == meuId)
            if(meuSaldo<casa.getValor()/2 || casa.getQtdPredios()==4)
                adicionarPredioButton.setEnabled(false);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comprarButton = new javax.swing.JButton();
        VenderButton = new javax.swing.JButton();
        adicionarPredioButton = new javax.swing.JButton();
        statusRodadaLabel = new javax.swing.JLabel();
        desejaLabel = new javax.swing.JLabel();
        finalizarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        comprarButton.setText("Comprar");
        comprarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comprarButtonActionPerformed(evt);
            }
        });

        VenderButton.setText("Vender");
        VenderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VenderButtonActionPerformed(evt);
            }
        });

        adicionarPredioButton.setText("Adicionar Predio");
        adicionarPredioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarPredioButtonActionPerformed(evt);
            }
        });

        statusRodadaLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        desejaLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        desejaLabel.setText("Deseja:");

        finalizarButton.setText("Finalizar");
        finalizarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalizarButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusRodadaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(desejaLabel)
                .addGap(92, 92, 92))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(finalizarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comprarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(VenderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adicionarPredioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusRodadaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desejaLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(comprarButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VenderButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(adicionarPredioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(finalizarButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comprarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comprarButtonActionPerformed
        statusRodadaLabel.setText("Imovel adquirido.");
        casa.setDono(meuId);
        compraIdCasa = idCasa;
        compraValor = casa.getValor();
        comprarButton.setEnabled(false);
        
    }//GEN-LAST:event_comprarButtonActionPerformed

    private void VenderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VenderButtonActionPerformed
        Vendas vendas = new Vendas(casasParaVenda, TelaJogo.getInstance(), true);
        vendas.setLocationRelativeTo(this);
        vendas.show();
        
        try{
            vendas.wait();
        } catch (InterruptedException | IllegalMonitorStateException ex) {
            //ignorar
        }
        casasVendidas.add(vendas.getVenda());
        
        for(Venda venda : casasVendidas){
            valorTotalVendas += venda.getValor();
        }
        if((meuSaldo + valorTotalVendas)>0)
            jogadorFaliu = false;
        if(casasParaVenda.isEmpty())
            VenderButton.setEnabled(false);          
            
    }//GEN-LAST:event_VenderButtonActionPerformed

    private void adicionarPredioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarPredioButtonActionPerformed
        statusRodadaLabel.setText("Predio adquirido.");
        casa.setDono(meuId);
        compraIdCasa = idCasa;
        compraValor = casa.getValor()/2;
        comprarButton.setEnabled(false);
        adicionarPredioButton.setEnabled(false);
    }//GEN-LAST:event_adicionarPredioButtonActionPerformed

    private void finalizarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalizarButtonActionPerformed
        json = JSONParse.generateJogada(pagamentoIdConta, pagamentoValor, compraIdCasa, compraValor, casasVendidas);
        dispose();
    }//GEN-LAST:event_finalizarButtonActionPerformed
    
    public boolean getJogadorFaliu(){
        return jogadorFaliu;
    }
    
    public String getJsonJogada(){
        return json;
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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OpcoesJogada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OpcoesJogada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OpcoesJogada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OpcoesJogada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OpcoesJogada dialog = new OpcoesJogada(casa, casasParaVenda, meuId, meuSaldo, bonusRodada, idCasa, pagamentoIdConta, dado, new javax.swing.JFrame(), true);
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
    private javax.swing.JButton VenderButton;
    private javax.swing.JButton adicionarPredioButton;
    private javax.swing.JButton comprarButton;
    private javax.swing.JLabel desejaLabel;
    private javax.swing.JButton finalizarButton;
    private javax.swing.JLabel statusRodadaLabel;
    // End of variables declaration//GEN-END:variables
}
