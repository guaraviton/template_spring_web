package br.com.petrobras.ep.premissas.spring.config;

import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.petrobras.ep.premissas.spring.interceptor.RequestInteceptor;

@Configuration
@ComponentScan(basePackages = { "br.com.petrobras.ep.premissas" })
@PropertySource({ "classpath:webapp.properties" })
public class MvcWebAppConfig extends WebMvcConfigurationSupport {

	private static final Locale locale = new Locale("pt", "BR");
	
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		super.addCorsMappings(registry);
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver lr = new SessionLocaleResolver();
		lr.setDefaultLocale(locale);
		Locale.setDefault(locale);
		return lr;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.add(jacksonConverter());
		addDefaultHttpMessageConverters(converters);
	}

	public MappingJackson2HttpMessageConverter jacksonConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.getObjectMapper().disable(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		converter.getObjectMapper().disable(
				DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		converter.getObjectMapper().disable(
				SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		converter.getObjectMapper().enable(
				DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		converter.getObjectMapper().enable(
				DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY);
		converter.getObjectMapper().setSerializationInclusion(
				JsonInclude.Include.NON_EMPTY);
		return converter;
	}

	@Bean
	public RequestInteceptor logAcessoInteceptor() {
		return new RequestInteceptor();
	}

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(logAcessoInteceptor());
	}
}