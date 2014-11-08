package br.com.bankgame.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParse {
    
    public static String generateMyConta() {
        String senha = "123";
        try {
            senha = criptografar(senha);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(BankGameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject jo = new JSONObject();

        try {
            jo.put("login", "BankGame");
            jo.put("senha", senha);
            jo.put("email", "bankgame");
            jo.put("saldo", 0);
            jo.put("client", "superbgame");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (jo.toString());
    }
    
    public static String generateJogadoresList(Sala sala) {

        JSONObject jo = new JSONObject();
        
        try {
            
            JSONArray ja = new JSONArray();
            for (Jogador jogador : sala.getJogadores()) {
                JSONObject aux2 = new JSONObject();
                aux2.put("login", jogador.getLogin());
                aux2.put("pronto", jogador.isPronto());
                ja.put(aux2);
            }
            jo.put("jogadores", ja);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (jo.toString());
    }
    
    /**
     * Desempacota um JSON que contenha os atributos de @Sala
     *
     * @param data
     * @return
     */
    public static Sala degenerateCriarSala(String data) {
        Sala sala = null;
        try {
            JSONObject jo = new JSONObject(data);
            sala = new Sala(jo.getString("nome"), jo.getString("senha"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sala;
    }
    
    public static String generateSalaLobby(Sala sala) {
        JSONObject jo = new JSONObject();

        try {
            jo.put("nome", sala.getNome());
            jo.put("senha", sala.getSenha());
            jo.put("disponivel", sala.isDisponivel());
            
            JSONArray ja = new JSONArray();
            for (Jogador jogador : sala.getJogadores()) {
                JSONObject aux2 = new JSONObject();
                aux2.put("login", jogador.getLogin());
                ja.put(aux2);
            }
            jo.put("jogadores", ja);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (jo.toString());
    }

    public static String generateSalaLobbyList(List<Sala> salas) {
        
        if (salas == null)
            salas = new ArrayList<Sala>();
        
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray ();
                
        try {
            for(Sala sala :salas){
                JSONObject aux = new JSONObject();

                aux.put("nome", sala.getNome());
                aux.put("senha", sala.getSenha());
                aux.put("disponivel", sala.isDisponivel());
                JSONArray jaAux = new JSONArray();
                for (Jogador jogador : sala.getJogadores()){
                    JSONObject aux2 = new JSONObject();
                    aux2.put("login", jogador.getLogin());
                    jaAux.put(aux2);
                }   
                
                aux.put("jogadores", jaAux);
                ja.put(aux);
            }
            jo.put("salas", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (jo.toString());
    }

    public static String generateTransferencia(String idConta, int moedas) {

        JSONObject jo = new JSONObject();

        try {
            jo.put("idConta", idConta);
            jo.put("valor", moedas);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (jo.toString());
    }
    
    public static Jogada degenerateJogada(String data) {
        Jogada jogada = null;
        try{
            List<Venda> vendas = new ArrayList<Venda>();
            JSONObject jo = new JSONObject(data);
            JSONArray ja = jo.getJSONArray("vendas");
            for(int i = 0, tam = ja.length(); i < tam; i++){
                Venda venda = new Venda(ja.getJSONObject(i).getInt("idCasa"), ja.getJSONObject(i).getInt("valor"));
                vendas.add(venda);
            }
            
            jogada = new Jogada(jo.getString("pagamentoIdConta"), jo.getInt("pagamentoValor"), 
                    jo.getInt("compraIdCasa"), jo.getInt("compraValor"), vendas);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jogada;
    }
    
    private static String criptografar(String original) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(original.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }

        return hexString.toString();
    }    

    static String generateJogo(String proximo, SalaDeJogo salaAtual) {
        JSONObject jo = new JSONObject();

        try {
            jo.put("proximo", proximo);
            
            JSONArray ja = new JSONArray();
            for (Jogador jogador : salaAtual.getJogadores()) {
                JSONObject aux2 = new JSONObject();
                aux2.put("nome", jogador.getLogin());
                aux2.put("saldo", jogador.getSaldo());
                ja.put(aux2);
            }
            jo.put("jogadores", ja);
            
            ja = new JSONArray();
            for (Casa casa : salaAtual.getCasas()) {
                JSONObject aux2 = new JSONObject();
                aux2.put("id", casa.getId());
                aux2.put("dono", casa.getDono());
                aux2.put("qtdPredios", casa.getQtdPredios());
                ja.put(aux2);
            }
            jo.put("casas", ja);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (jo.toString());
    }

    static String generatePrimeiro(String primeiro, List<Atendente> atendentes) {
        JSONObject jo = new JSONObject();

        try {
            jo.put("primeiro", primeiro);
            jo.put("blue", atendentes.get(0).getIdContaLogada());
            jo.put("red", atendentes.get(1).getIdContaLogada());
            if(atendentes.size()>2){
                jo.put("green", atendentes.get(2).getIdContaLogada());
                if(atendentes.size()>3)
                    jo.put("purple", atendentes.get(3).getIdContaLogada());
                else
                    jo.put("purple", "");
            }else {
                jo.put("green", "");
                jo.put("purple", "");
            }
            
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (jo.toString());
    }

    
}
