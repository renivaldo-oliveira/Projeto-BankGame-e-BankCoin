package br.com.bankgame.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParse {
    public static String generateConta(String login, String senha, String email, int saldo) {

        JSONObject jo = new JSONObject();

        try {
            jo.put("login", login);
            jo.put("senha", senha);
            jo.put("email", email);
            jo.put("saldo", saldo);
            jo.put("client", "bgame");
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
        
    public static Sala degenerateSalaLobby(String data){      
        Sala sala = null;
        JSONObject jo;        
        
        try {
             jo = new JSONObject(data);

            List<Jogador> jogadores = new ArrayList<Jogador>();
            JSONArray jaAux = jo.getJSONArray("jogadores");
            for(int j=0, tamanho = jaAux.length(); j<tamanho; j++){
                Jogador jogador = new Jogador(jaAux.getJSONObject(j).getString("login"));
                jogadores.add(jogador);
            }
            sala = new Sala(jo.getString("nome"), jo.getString("senha"),
                    jo.getBoolean("disponivel"), jogadores);            
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sala;
    }
    
    public static List<Sala> degenerateSalaList(String data){        
        
        List<Sala> salas = new ArrayList<Sala>();
        
        try {
            JSONObject jo = new JSONObject(data);
            JSONArray ja = jo.getJSONArray("salas");

            for(int i = 0, tam = ja.length(); i < tam; i++){
                List<Jogador> jogadores = new ArrayList<Jogador>();
                JSONArray jaAux = ja.getJSONObject(i).getJSONArray("jogadores");
                for(int j=0, tamanho = jaAux.length(); j<tamanho; j++){
                    Jogador jogador = new Jogador(jaAux.getJSONObject(j).getString("login"));
                    jogadores.add(jogador);
                }
                Sala sala = new Sala(ja.getJSONObject(i).getString("nome"), ja.getJSONObject(i).getString("senha"),
                        ja.getJSONObject(i).getBoolean("disponivel"), jogadores);
                salas.add(sala);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return salas;
    }
    
    public static String generateCriarSala(String nome, String senha) {

        JSONObject jo = new JSONObject();

        try {
            jo.put("nome", nome);
            jo.put("senha", senha);           
            
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (jo.toString());
    }

    public static List<Jogador> degenerateSala(String data){      
        List<Jogador> jogadores = new ArrayList<Jogador>();       
        
        try {
             JSONObject jo = new JSONObject(data);

            
            JSONArray ja = jo.getJSONArray("jogadores");
            for(int j=0, tamanho = ja.length(); j<tamanho; j++){
                Jogador jogador = new Jogador(ja.getJSONObject(j).getString("login"));
                jogador.setPronto(ja.getJSONObject(j).getBoolean("pronto"));
                jogadores.add(jogador);
            }
            
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jogadores;
    }

    public static String generateJogada(String pagamentoIdConta, int pagamentoValor,
            int compraIdCasa, int compraValor, List<Venda> vendas) {
        
        JSONObject jo = new JSONObject();
        try {
            jo.put("pagamentoIdConta", pagamentoIdConta);
            jo.put("pagamentoValor", pagamentoValor);           
            jo.put("compraIdCasa", compraIdCasa);
            jo.put("compraValor", compraValor);  
            
            JSONArray ja = new JSONArray();
            for (Venda venda : vendas) {
                JSONObject aux2 = new JSONObject();
                aux2.put("idCasa", venda.getIdCasa());
                aux2.put("valor", venda.getValor());
                ja.put(aux2);
            }
            jo.put("vendas", ja);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (jo.toString());
    }
    
}

