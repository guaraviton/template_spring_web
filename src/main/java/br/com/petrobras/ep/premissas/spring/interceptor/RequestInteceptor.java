package br.com.petrobras.ep.premissas.spring.interceptor;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.petrobras.ep.premissas.helper.util.StringUtils;

public class RequestInteceptor extends HandlerInterceptorAdapter  {

	private static final Log LOGGER = LogFactory.getLog(RequestInteceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		boolean erro = ex != null;
		if(erro){
			enviarEmailAdmin(ex, request);
		}
		
		if(LOGGER.isInfoEnabled()){
			LOGGER.info("[" + handler + "] tempo de execucao do request : " + getTempoExecucao(request) + "ms");
		}
	}
	
	private Long getTempoExecucao(HttpServletRequest request) {
		long startTime = (Long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();
		return (endTime - startTime);
	}

	private void enviarEmailAdmin(Exception ex, HttpServletRequest request) {
		try{
			StringBuilder conteudo = new StringBuilder();
			conteudo
			.append("Erro ao executar recurso:")
			.append("<br>")
			.append(getRecurso(request))
			.append("<br>")
			.append("<br>")
			.append("Para usuario:" )
			.append("<br>")
			//.append(authService.get().getUsuario().getLogin())
			.append("<br>")
			.append("<br>")
			.append("Mensagem:")
			.append("<br>")
			.append(ex.getMessage())
			.append("<br>")
			.append("<br>")
			.append("Causa:")
			.append("<br>")
			.append(ex.getCause())
			.append("<br>")
			.append("<br>")
			.append("StackTrace:")
			.append("<br>")
			.append(getStackTrace(ex));
			//emailService.enviar(EmailService.EMAIL_ADMIN, "Erro administrativo " + getRecurso(request), conteudo.toString());
		}catch(Exception e){}
	}
	
	private String getStackTrace(Exception ex){
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	private String getRecurso(HttpServletRequest request) {
		StringBuilder recurso = new StringBuilder();
		recurso
		/*
		 HandlerMethod handlerMethod = (HandlerMethod) handler;
		 .append(handlerMethod.getBeanType().getSimpleName())
		.append(".")
		.append(handlerMethod.getMethod().getName())
		.append("(")
		.append(handlerMethod.getMethod().getParameterTypes())
		.append(") ")*/
		.append(request.getMethod())
		.append(" ")
		.append(request.getPathInfo())
		.append(StringUtils.isNotBlank(request.getQueryString()) ? "?" + request.getQueryString() : "");
		return recurso.toString();
	}
}