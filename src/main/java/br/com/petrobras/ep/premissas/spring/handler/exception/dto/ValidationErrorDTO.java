package br.com.petrobras.ep.premissas.spring.handler.exception.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO{
	
	private List<ErrorDTO> fieldErrors = new ArrayList<ErrorDTO>();
	
	private Boolean isMensagemEmModal = null;
	
    public ValidationErrorDTO() {
    }
    
    public ValidationErrorDTO(String message) {
    	addError(message);
    }
 
    public void addFieldError(String field, String message) {
        FieldErrorDTO error = new FieldErrorDTO(field, message);
        fieldErrors.add(error);
    }
    
    public void addError(String message) {
        ErrorDTO error = new ErrorDTO(message);
        fieldErrors.add(error);
    }

	public List<ErrorDTO> getFieldErrors() {
		return fieldErrors;
	}

	public Boolean getIsMensagemEmModal() {
		return isMensagemEmModal;
	}

	public void setIsMensagemEmModal(Boolean isMensagemEmModal) {
		this.isMensagemEmModal = isMensagemEmModal;
	}
    
}
