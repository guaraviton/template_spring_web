package br.com.petrobras.ep.premissas.helper.exception;

/**
 * Excecao generica para representar os possiveis problemas nas operacoes de consultas executadas pelos DAOs
 * 
 * 
 */
public class AppQueryException extends AppException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param message
	 */
	public AppQueryException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param mensagem
	 * @param e
	 */
	public AppQueryException(String mensagem, Exception e) {
		super(mensagem, e);
	}

}
