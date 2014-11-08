/**
 * Componente Curricular: Módulo Integrado de Concorrecia e Conectividade
 * Autor: <Jhone Mendes>
 * Data:  <06/09/2014>
 *
 */

package br.uefs.bankcoin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Responsavel para descompactar os pacotes JSONs enviados pelo cliente.
 *
 * @author Jhone Mendes
 */
public class JSONParse {
    
    public static String generateAtualizacao(List<Conta> contas, int qtdConexoes) {
        if (contas == null)
            contas = new ArrayList<Conta>();
        
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray ();
                
        try {
            for(Conta conta :contas){
                JSONObject aux = new JSONObject();

                aux.put("login", conta.getLogin());
                aux.put("email", conta.getEmail());
                aux.put("senha", conta.getSenha());
                aux.put("saldo", conta.getSaldo());
                ja.put(aux);
            }
            jo.put("contas", ja);
            jo.put("conexoes", qtdConexoes);
        } catch (JSONException e) {
            e.printStackTrace();
        }       
        System.out.println(jo.toString()+" generate");
        return (jo.toString());
    }
    
    /**
     * Desempacota um JSON que contenha os atributos de @Conta
     *
     * @param data
     * @return
     */
    public static List<Conta> degenerateContaList(String data){
        System.out.println(data+" degenerate");
        
        List<Conta> contas = new ArrayList<Conta> ();
        
        try {
            JSONObject jo = new JSONObject(data);
            JSONArray ja = jo.getJSONArray("contas");

            for(int i = 0, tam = ja.length(); i < tam; i++){
                Conta conta = new Conta(ja.getJSONObject(i).getString("login"), ja.getJSONObject(i).getString("senha"),
                        ja.getJSONObject(i).getString("email"), ja.getJSONObject(i).getInt("saldo"));
                contas.add(conta);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contas;
    }
    
    public static int degenerateQtdConexoes(String data){
        int qtdConexoes = 0;
        try {
            JSONObject jo = new JSONObject(data);
            qtdConexoes = jo.getInt("conexoes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return qtdConexoes;
    }
    
    /**
     * Desempacota um JSON que contenha os atributos de @Conta
     *
     * @param data
     * @return
     */
    public static Conta degenerateConta(String data){
        Conta conta = null;
        try {
            JSONObject jo = new JSONObject(data);
            conta = new Conta(jo.getString("login"), jo.getString("senha"), jo.getString("email"), jo.getInt("saldo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return conta;
    }
    
    public static String degenerateClient(String data) {
        String cliente = null;
        try {
            JSONObject jo = new JSONObject(data);
            cliente = jo.getString("client");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cliente;
    }
    
    /**
     * Desempacota um JSON que contenha os atributos de @Transacao
     *
     * @param data
     * @return
     */
    public static Transacao degenerateTransacao(String data){
        Transacao transacao = null;
        try {
            JSONObject jo = new JSONObject(data);
            transacao = new Transacao(jo.getString("idConta"), jo.getInt("valor"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return transacao;
    }

    static String generateTransacaoPai(String login, int saldo, String login0, int saldo0) {
        JSONObject jo = new JSONObject();

        try {
            jo.put("login", login);
            jo.put("saldo", saldo);
            jo.put("login0", login0);
            jo.put("saldo0", saldo0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return (jo.toString());
    }

    

}
