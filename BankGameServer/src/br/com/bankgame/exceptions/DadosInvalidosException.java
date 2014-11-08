package br.com.bankgame.exceptions;

public class DadosInvalidosException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1690003816298811017L;

	public DadosInvalidosException(String msg){
		super(msg);
	}

    public DadosInvalidosException () {
        super();
    }

}
