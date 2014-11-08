package br.uefs.bankcoin.model;

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
            jo.put("client", "bcoin");
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

}

