package br.com.petrobras.ep.premissas.helper.exception;


public class AppException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppException(String mensagem, Exception e) {
		super(mensagem, e);
	}
	
	public AppException(String mensagem) {
		super(mensagem);
	}
	
	public AppException(Exception e) {
		super(e);
	}
}
