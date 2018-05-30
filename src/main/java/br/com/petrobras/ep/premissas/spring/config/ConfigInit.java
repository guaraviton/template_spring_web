package br.com.petrobras.ep.premissas.spring.config;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.ClasspathLocationStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigInit {

	private static final Log LOGGER = LogFactory.getLog(ConfigInit.class);
	private static final String PROPERTIES_FILE_NAME = "configuracao.properties";
	private static final String PROPERTIES_LOCAL_FILE_NAME = "localhost.properties";
	
	@Bean
	public FileBasedConfiguration configuracaoGlobal() throws Exception {
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
		.configure(params.properties()
		.setLocationStrategy(new ClasspathLocationStrategy())
		.setFileName(PROPERTIES_FILE_NAME));
		return builder.getConfiguration();
	}
	
	@Bean
	public FileBasedConfiguration configuracaoLocal() throws Exception {
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
		.configure(params.properties()
		.setLocationStrategy(new ClasspathLocationStrategy())
		.setFileName(PROPERTIES_LOCAL_FILE_NAME));
		try{
			return builder.getConfiguration();
		}catch(ConfigurationException e){
			LOGGER.warn("Nao encontrou arquivo local " + PROPERTIES_LOCAL_FILE_NAME);
		}
		return null;
	}
	
}
