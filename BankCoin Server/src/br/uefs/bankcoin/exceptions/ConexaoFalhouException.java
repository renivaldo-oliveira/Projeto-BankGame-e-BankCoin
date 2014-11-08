package br.uefs.bankcoin.exceptions;

public class ConexaoFalhouException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1006441415347409622L;

	public ConexaoFalhouException(String msg){
		super(msg);
	}
}
