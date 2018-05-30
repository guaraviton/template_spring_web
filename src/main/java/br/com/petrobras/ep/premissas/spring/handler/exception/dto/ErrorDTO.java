package br.com.petrobras.ep.premissas.spring.handler.exception.dto;

public class ErrorDTO{
	
	private String message;

	public ErrorDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
