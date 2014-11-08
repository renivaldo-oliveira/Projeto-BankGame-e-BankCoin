package br.uefs.bankcoin.exceptions;

public class ContaJaExisteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4694691555702356650L;

	public ContaJaExisteException(String msg){
		super(msg);
	}
}
