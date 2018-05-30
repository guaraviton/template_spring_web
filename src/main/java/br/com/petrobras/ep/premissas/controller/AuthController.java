package br.com.petrobras.ep.premissas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.petrobras.security.ISecurityContext;
import br.com.petrobras.security.model.User;

@RestController
@RequestMapping("/auth")
public class AuthController{
	
	@RequestMapping(method = RequestMethod.GET)
    public User get() {
		ISecurityContext contextoDeSeguranca = ISecurityContext.getContext();
		User usuario = contextoDeSeguranca.getCurrentLoggedUser();
		//contextoDeSeguranca.getUserAuthenticator().logon("usuario", "senha");
		return usuario;
	}
	
	
}