package br.com.petrobras.ep.premissas.spring.handler.exception;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.petrobras.ep.premissas.helper.exception.AppException;
import br.com.petrobras.ep.premissas.helper.util.BeanProperties;
import br.com.petrobras.ep.premissas.helper.util.MessageUtils;
import br.com.petrobras.ep.premissas.spring.handler.exception.dto.ValidationErrorDTO;

@ControllerAdvice
public class AppExceptionHandler {
	
	private static final Log LOGGER = LogFactory.getLog(AppExceptionHandler.class);
	
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ValidationErrorDTO handleException(Exception ex) {
		LOGGER.error("Erro geral", ex);
	    return new ValidationErrorDTO(MessageUtils.get("erro.geral"));
	}
	
	@ExceptionHandler({AppException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDTO handleValidationException(AppException appException) {
		LOGGER.error("Erro AppException", appException);
	    return new ValidationErrorDTO(MessageUtils.get(appException.getMessage()));
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDTO handleValidationException(MethodArgumentNotValidException mav) {
		BindingResult result = mav.getBindingResult();
        List<ObjectError> allErrors = result.getAllErrors();
        return processarObjectErrors(allErrors, result.getTarget());
	}
	
	@ExceptionHandler({HttpMessageNotReadableException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ValidationErrorDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException mav) {
		return new ValidationErrorDTO(MessageUtils.get("erro.campos.obrigatorios"));
	}
	
	private ValidationErrorDTO processarObjectErrors(List<ObjectError> objectErrors, Object target){
        ValidationErrorDTO dto = new ValidationErrorDTO();
        dto.setIsMensagemEmModal((Boolean) BeanProperties.getProperty(target, "isMensagemEmModal"));
        String localizedErrorMessage = "";
        String field;
        for (ObjectError objectError: objectErrors) {
        	localizedErrorMessage = resolveLocalizedErrorMessage(objectError);
        	if(objectError instanceof FieldError){
        		field = ((FieldError)objectError).getField();
        	}else{
        		field = null;
        	}
            dto.addFieldError(field, localizedErrorMessage);
        }
        return dto;
    }

	private String resolveLocalizedErrorMessage(ObjectError objectError) {
        String localizedErrorMessage = "";
        Integer index = getIndex(objectError.getCodes()[0]);
        for(String key : objectError.getCodes()){
        	try{
        		localizedErrorMessage = MessageUtils.get(key, index != null ? index : null);
        		break;
        	}catch(NoSuchMessageException ex){}
        }
        if("".equals(localizedErrorMessage)){
        	try{
        		localizedErrorMessage = MessageUtils.get(objectError.getDefaultMessage());	
        	}catch(NoSuchMessageException e){
        		localizedErrorMessage = ((FieldError)objectError).getField() + " " + objectError.getDefaultMessage().toLowerCase();
        	}
        }
        return localizedErrorMessage;
    }

	private Integer getIndex(String errorCode) {
		if(errorCode.indexOf("]") < 0) {
			return null;
		}
		String[] indexSplit = errorCode.split("]");
		if(indexSplit != null && indexSplit.length > 0){
			String index = indexSplit[0];
			return Integer.valueOf(index.substring(index.length() - 1, index.length())) + 1;
		}
		return null;
	}
}
