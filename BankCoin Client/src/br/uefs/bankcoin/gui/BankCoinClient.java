package br.uefs.bankcoin.gui;

import br.uefs.bankcoin.controller.ControllerCliente;
import br.uefs.bankcoin.exceptions.ConexaoFalhouException;
import br.uefs.bankcoin.exceptions.ContaJaExisteException;
import br.uefs.bankcoin.exceptions.DadosInvalidosException;
import javax.mail.*;
import javax.mail.internet.MimeMessage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;

/**
 *
 * @author victor rios
 */
public class BankCoinClient implements ActionListener {

    private ControllerCliente cc;
    private JFrame conectar;
    private JButton bc; //botao conectar
    //atributos de login
    private JFrame login;
    private JButton fazerLogin;
    private JButton criarConta;
    private JTextField usuario;
    private JTextField senha;
    //atributos da janela criarconta
    private JFrame jcc; //janela criar conta
    private JTextField novoUsuario;
    private JTextField novaSenha;
    private JTextField email;
    private JTextField valor;
    private JButton criar;
    private JButton cancelar;
    //atributos janela principal
    private JFrame jp; //Janela principal
    private JTextField contaTransferencia;
    private JTextField valorTransferencia;
    private JButton transferir;
    private JButton saldoMoedas;

    public static void main(String[] args) {
        
        BankCoinClient bcc = new BankCoinClient();
        bcc.conectar();

    }

    private void conectar() {
        conectar = new JFrame();  
        conectar.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        conectar.setSize(220, 220);
        conectar.setLayout(new BorderLayout());
        conectar.add(bc = new JButton("Conectar"));
        bc.addActionListener(this);
        conectar.setVisible(true);
    }

    /**
     * cria a janela de login*
     */
    private void login() {
        if (login == null) {
            login = new JFrame();
            login.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            login.setSize(300, 300);
            login.setLayout(new GridLayout(6, 2));
            login.add(new JLabel("Usuario"));
            login.add(usuario = new JTextField(""));
            login.add(new JLabel("Senha"));
            login.add(senha = new JPasswordField(""));
            login.add(criarConta = new JButton("Criar Conta"));
            login.add(fazerLogin = new JButton("Fazer Login"));
            criarConta.addActionListener(this);
            fazerLogin.addActionListener(this);
            login.setVisible(true);
        } else {
            login.setVisible(true);
        }

    }

    private void janelaPrincipal() {
        if (jp == null) {
            jp = new JFrame();
            jp.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            jp.setSize(220, 220);
            jp.setLayout(new GridLayout(3, 2));
            jp.add(new JLabel("ID da conta:"));
            jp.add(contaTransferencia = new JTextField(""));
            jp.add(new JLabel("Valor a transferir:"));
            jp.add(valorTransferencia = new JTextField(""));
            jp.add(saldoMoedas = new JButton("Checar saldo"));
            jp.add(transferir = new JButton("Transferir"));
            saldoMoedas.addActionListener(this);
            transferir.addActionListener(this);
            jp.setVisible(true);
        }

    }

    /**
     * cria a janela de criar conta*
     */
    private void criarConta() {
        if (jcc == null) {
            jcc = new JFrame();
            jcc.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            jcc.setSize(350, 300);
            jcc.setLayout(new GridLayout(5, 2));
            jcc.add(new JLabel("Nome de usuario:"));
            jcc.add(novoUsuario = new JTextField(""));
            jcc.add(new JLabel("Sua Senha:"));
            jcc.add(novaSenha = new JPasswordField(""));
            jcc.add(new JLabel("Email:"));
            jcc.add(email = new JTextField(""));
            jcc.add(new JLabel("Quantida de Moedas:"));
            jcc.add(valor = new JTextField(""));
            jcc.add(cancelar = new JButton("Cancelar"));
            jcc.add(criar = new JButton("Criar Conta"));
            cancelar.addActionListener(this);
            criar.addActionListener(this);
            jcc.setVisible(true);
        } else {
            jcc.setVisible(true);
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(fazerLogin)) {

            try {
                String username = usuario.getText();
                if(username.contains("!")||username.contains("@")||username.contains("#")||username.contains("$")||
                        username.contains("¨")||username.contains("&")||username.contains("*")||username.contains("(")||
                        username.contains(")")||username.contains("-")||username.contains("+")||username.contains("=")||
                        username.contains("'")||username.contains("\"")||username.contains("§")||username.contains("[")||
                        username.contains("]")||username.contains("{")||username.contains("}")||username.contains("º")||
                        username.contains("ª")||username.contains("<")||username.contains(">")||username.contains("<")||
                        username.contains(":")||username.contains(";")||username.contains(".")||username.contains(",")||
                        username.contains("/")||username.contains("?")||username.contains("°")){
                    JOptionPane.showMessageDialog(login, "Não é permitido caracteres especiais!\n ex: /, #, $, +, ?, !, etc");
                    return;
                }
                cc.FazerLogin(username, senha.getText());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(login, e.getMessage());
                return;
            } catch (DadosInvalidosException e) {
                JOptionPane.showMessageDialog(login, e.getMessage());
                return;
            } catch (ConexaoFalhouException e) {
                JOptionPane.showMessageDialog(login, e.getMessage());
                return;
            } catch (NoSuchAlgorithmException ex) {
                JOptionPane.showMessageDialog(login, "Erro desconhecido");
                return;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(login, "Digite um valor valido");
                return;
            }
            login.setVisible(false);
            this.janelaPrincipal();
        } else if (ae.getSource().equals(criarConta)) {
            login.setVisible(false);
            this.criarConta();
        } else if (ae.getSource().equals(cancelar)) {
            jcc.setVisible(false);
            this.login();
        } else if (ae.getSource().equals(criar)) {
            try {
                cc.CriarConta(novoUsuario.getText(), novaSenha.getText(), email.getText(), Integer.parseInt(valor.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(jcc, "Digite valores validos");
                return;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(jcc, e.getMessage());
                return;
            } catch (ContaJaExisteException e) {
                JOptionPane.showMessageDialog(jcc, e.getMessage());
                return;
            } catch (ConexaoFalhouException e) {
                JOptionPane.showMessageDialog(jcc, e.getMessage());
                return;
            } catch (NoSuchAlgorithmException ex) {
                JOptionPane.showMessageDialog(jcc, "Erro desconhecido");
                return;
            }
            jcc.setVisible(false);
            janelaPrincipal();
        } else if (ae.getSource().equals(transferir)) {
            try {
                cc.Transferir(contaTransferencia.getText(), valorTransferencia.getText());

            } catch (IOException e) {
                JOptionPane.showMessageDialog(jp, e.getMessage());
                return;
            } catch (DadosInvalidosException e) {
                JOptionPane.showMessageDialog(jp, e.getMessage());
                return;
            } catch (ConexaoFalhouException e) {
                JOptionPane.showMessageDialog(jp, e.getMessage());
                return;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(jp, "Digite um valor valido");
                return;
            }
            JOptionPane.showMessageDialog(jp, "Operacao realizada com sucesso!");
        } else if (ae.getSource().equals(saldoMoedas)) {
            String saldo = null;
            try {
                saldo = cc.saldoMoedas();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(jp, e.getMessage());
                return;
            } catch (ConexaoFalhouException e) {
                JOptionPane.showMessageDialog(jp, e.getMessage());
                return;
            }
            JOptionPane.showMessageDialog(jp, saldo);
        } else if (ae.getSource().equals(bc)) {

            try {
                cc = new ControllerCliente();
                conectar.setVisible(false);
                login();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(conectar, e.getMessage());
                return;
            } catch (ConexaoFalhouException e) {
                JOptionPane.showMessageDialog(conectar, e.getMessage());
                return;
            }
        }

    }

    /*private boolean tentaConexao(){
     try {
     cc = new ControllerCliente("localhost", 1307); // tenta conectar no servidor 1
     return true;
     } catch (NumberFormatException e) {} catch (IOException e) {
     } catch (ConexaoFalhouException e) {
     try {
     cc = new ControllerCliente("localhost", 1308); // tenta conectar no servidor 2
     return true;
     } catch (NumberFormatException e1) {				
     } catch (IOException e1) {
     } catch (ConexaoFalhouException e1) {
     try {
     cc = new ControllerCliente("localhost", 1309); // tenta conectar no servidor 3
     return true;
     } catch (NumberFormatException e2) {
     } catch (IOException e2) {
     } catch (ConexaoFalhouException e2) { //apresenta mensagem de erro de conex�o
     JOptionPane.showMessageDialog(conectar, e2.getMessage());
     }
     }
     }
		
     return false;
     }*/
    private boolean enviaCodigo(String email) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom("bankcoin.naoresponda@gmail.com"); //COLOCAR O EMAIL REMETENTE
            msg.setRecipients(Message.RecipientType.TO,
                    email);
            msg.setSubject("Seu código BANKCOIN");
            msg.setSentDate(new Date());
            msg.setText("Ol�, copie o c�digo abaixo para criar sua conta no bank coin:\n"
                    + criptografar(email));
            Transport.send(msg, "bankcoin.naoresponda@gmail.com", "12345678bank"); //COLOCAR O EMAIL REMETENTE E A SENHA
        } catch (MessagingException mex) {
            System.out.println("send failed, exception: " + mex);
        }

        return false;
    }

    private String criptografar(String original) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(original.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }

        return hexString.toString().substring(0, 11);
    }
}
