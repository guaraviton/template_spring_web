package br.com.petrobras.ep.premissas.spring.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.TimeZone;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.springframework.web.WebApplicationInitializer;


public class WebAppInitializer implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		configurarBeanUtilsConverter();
		configurarTimeZone();
		configurarBuildNumber(servletContext);
	}

	private void configurarBuildNumber(ServletContext servletContext) {
		InputStream inputStream = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF");
		String buildNumber = "";
		try {
			Manifest manifest = new Manifest(inputStream);
			buildNumber = manifest.getMainAttributes().getValue("buildNumber");
		} catch (IOException e) {}
		
		if("".equals(buildNumber)){
			buildNumber = getTime();
		}
		servletContext.setAttribute("buildNumber", buildNumber);
	}

	private String getTime() {
		return String.valueOf(System.currentTimeMillis());
	}

	private void configurarTimeZone() {
		TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
		TimeZone.setDefault(tz);
	}

	private void configurarBeanUtilsConverter() {
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new DateConverter(null), Date.class);
	}
}