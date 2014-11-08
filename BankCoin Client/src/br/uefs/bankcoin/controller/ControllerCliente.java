package br.uefs.bankcoin.controller;

import br.uefs.bankcoin.exceptions.ConexaoFalhouException;
import br.uefs.bankcoin.exceptions.ContaJaExisteException;
import br.uefs.bankcoin.exceptions.DadosInvalidosException;
import br.uefs.bankcoin.model.JSONParse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ControllerCliente{
	private BufferedReader in;
	private PrintStream out;
	private Socket socket;
        private String saldoConta;
        private String saldoGlobal;
	
	public ControllerCliente() throws IOException, ConexaoFalhouException, NumberFormatException{
	    try{
                socket = new Socket("localhost",1307);  
            } catch (IOException ex) {
                try{
                    socket = new Socket("localhost",1308);
                } catch (IOException ex1) {
                    try{
                        socket = new Socket("localhost",1309);
                    } catch (IOException ex2) {
                        throw new ConexaoFalhouException("Erro ao tentar conectar");
                    }
                }
            }
            System.out.println("eah");/*
	    if (!socket.isBound()) {
                socket = new Socket("localhost",1308);
                if (!socket.isBound()){
                    socket = new Socket("localhost",1309);
                    if (!socket.isBound())
                        throw new ConexaoFalhouException("Erro ao tentar conectar");
                }
            }*/
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream());
            
            out.println("novo cliente");
            out.flush();
            String porta = in.readLine();
            System.out.println(porta);
            
            
            socket = new Socket("localhost", Integer.parseInt(porta));
            
	    if (!socket.isBound()) 
                throw new ConexaoFalhouException("Erro ao tentar conectar");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream());
            
            out.println("ok");
            out.flush();
            this.saldoConta = null;
            this.saldoGlobal = null;
	}
	

	/**metodo pra criar conta
	 * @throws IOException 
	 * @throws ContaJaExisteException **/
	public void CriarConta(String login, String senha, String email, int valor) throws IOException, ContaJaExisteException, ConexaoFalhouException, NoSuchAlgorithmException, NumberFormatException{
		senha = criptografar(senha);
                out.println("cadastrar");
                out.flush();
		out.println(JSONParse.generateConta(login, senha, email, valor));
                out.flush();
		if (!respostaServidor()) throw new ContaJaExisteException("Essa conta ja existe");
	}
	
	/**metodo pra realizar login
	 * @throws DadosInvalidosException **/
	public void FazerLogin(String login, String senha) throws IOException, DadosInvalidosException, ConexaoFalhouException, NoSuchAlgorithmException, NumberFormatException{
		senha = criptografar(senha);
                out.println("login");
                out.flush();
		out.println(JSONParse.generateConta(login, senha, "", 0));
                out.flush();
		if (!respostaServidor("login")) throw new DadosInvalidosException("Seus dados est�o incorretos");
	}
	
	/**metodo pra transferencia
	 * @throws IOException 
	 * @throws DadosInvalidosException **/
	public void Transferir(String conta, String valor) throws IOException, DadosInvalidosException, ConexaoFalhouException, NumberFormatException{
		out.println("transferir");
                out.flush();
		out.println(JSONParse.generateTransferencia(conta, Integer.parseInt(valor)));
                out.flush();
		if (!respostaServidor("transferir")) 
                    throw new DadosInvalidosException("Erro ao transferir, verifique se a conta � valida, e se o seu saldo � suficiente");
	}
	
	public String saldoMoedas() throws IOException, ConexaoFalhouException{
            out.println("saldos");
            out.flush();
            out.println("");
            out.flush();
            respostaServidor("saldos");
            return ("Sua conta possui: "+saldoConta+"\nO banco possui "+saldoGlobal+" moedas");
	}
        
        private boolean respostaServidor() throws ConexaoFalhouException{
            String answer = null;
            try{
                    socket.setSoTimeout(2500);
                    answer = in.readLine();
                    if (answer == null)
                            throw new ConexaoFalhouException("Servidores Off-line!!");
                    System.out.println(answer);
            }catch(SocketTimeoutException e){
                    //ignorar
            }catch(Exception e){
                    throw new ConexaoFalhouException("Falha na conexao com o servidor!!");
            }
            return answer.equals("1");		
	}
        
        private boolean respostaServidor(String s) throws ConexaoFalhouException{
            if (s.equals("transferir")){
                String answer = null;
                try{
                    socket.setSoTimeout(2500);
                    answer = in.readLine();
                    if (answer == null)
                            throw new ConexaoFalhouException("Servidores Off-line!!");
                    System.out.println(answer);
                }catch(SocketTimeoutException e){
                        //ignorar
                }catch(Exception e){
                        throw new ConexaoFalhouException("Falha na conexao com o servidor!!");
                }
                return !(answer.equals("-1"));
            }
            if (s.equals("login")){
                String answer = null;
                try{
                        socket.setSoTimeout(2500);
                        answer = in.readLine();
                        if (answer == null)
                                throw new ConexaoFalhouException("Servidores Off-line!!");
                        System.out.println(answer);
                }catch(SocketTimeoutException e){
                        //ignorar
                }catch(Exception e){
                        throw new ConexaoFalhouException("Falha na conexao com o servidor!!");
                }
                return !(answer.equals("-1") || answer.equals("-2"));
            }
            if (s.equals("saldos")){                               
                try{
                        socket.setSoTimeout(2500);
                        saldoConta = in.readLine();
                        saldoGlobal = in.readLine();
                        if (saldoConta == null || saldoGlobal == null)
                            throw new ConexaoFalhouException("Servidores Off-line!");
                        if (saldoConta.equals("-1"))
                            return false;                        
                        return true;
                }catch(SocketTimeoutException e){
                        //ignorar
                }catch(Exception e){
                        throw new ConexaoFalhouException("Falha na conexao com o servidor!!");
                }
            }
            return false;
	}
        
	/*
	private boolean respostaServidor(){
		int lido = -1;
		while(lido!=1||lido!=0){
			try {
				lido = Integer.parseInt(in.readLine());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lido==1;
	}
        */
        private String criptografar(String original) throws NoSuchAlgorithmException, UnsupportedEncodingException{
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(original.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            
            return hexString.toString();
    }
    
}
