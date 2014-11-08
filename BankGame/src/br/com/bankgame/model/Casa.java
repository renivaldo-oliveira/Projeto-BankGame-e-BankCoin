/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bankgame.model;

import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author JhoneDarts
 */
public class Casa {
    private int dono;
    private int qtdPredios;
    private int valor;
    private int multiplicador;
    private int x;
    private int y;

    public Casa(int valor, int multiplicador, int x, int y) {
        this.dono = 0;
        this.qtdPredios = 0;
        this.valor = valor;
        this.multiplicador = multiplicador;
        this.x = x;
        this.y = y;
    }
    
    public void desenharPosse (Graphics2D g, JFrame tela){
        if(dono==0)
            return;
        if (dono==1){
            if(qtdPredios==0)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseBlue.png").getImage(), x, y, tela);
            if(qtdPredios==1)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseBlue1.png").getImage(), x, y, tela);
            if(qtdPredios==2)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseBlue2.png").getImage(), x, y, tela);
            if(qtdPredios==3)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseBlue3.png").getImage(), x, y, tela);
            if(qtdPredios==4)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseBlue4.png").getImage(), x, y, tela);
        }
        if (dono==2){
            if(qtdPredios==0)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseRed.png").getImage(), x, y, tela);
            if(qtdPredios==1)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseRed1.png").getImage(), x, y, tela);
            if(qtdPredios==2)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseRed2.png").getImage(), x, y, tela);
            if(qtdPredios==3)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseRed3.png").getImage(), x, y, tela);
            if(qtdPredios==4)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseRed4.png").getImage(), x, y, tela);
        }
        if (dono==3){
            if(qtdPredios==0)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseGreen.png").getImage(), x, y, tela);
            if(qtdPredios==1)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseGreen1.png").getImage(), x, y, tela);
            if(qtdPredios==2)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseGreen2.png").getImage(), x, y, tela);
            if(qtdPredios==3)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseGreen3.png").getImage(), x, y, tela);
            if(qtdPredios==4)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/posseGreen4.png").getImage(), x, y, tela);
        }
        if (dono==4){
            if(qtdPredios==0)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/possePurple.png").getImage(), x, y, tela);
            if(qtdPredios==1)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/possePurple1.png").getImage(), x, y, tela);
            if(qtdPredios==2)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/possePurple2.png").getImage(), x, y, tela);
            if(qtdPredios==3)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/possePurple3.png").getImage(), x, y, tela);
            if(qtdPredios==4)
                g.drawImage(new ImageIcon("src/br/com/bankgame/imagens/possePurple4.png").getImage(), x, y, tela);
        }
    }

    public int getDono() {
        return dono;
    }

    public int getQtdPredios() {
        return qtdPredios;
    }

    public int getValor() {
        return valor;
    }

    public int getMultiplicador() {
        return multiplicador;
    }

    public void setDono(int dono) {
        this.dono = dono;
    }

    public void setQtdPredios(int qtdPredios) {
        this.qtdPredios = qtdPredios;
    }
    
    
}
