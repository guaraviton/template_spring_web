package br.com.petrobras.ep.premissas.spring.persistence;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import br.com.petrobras.ep.premissas.helper.exception.AppException;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:configuracao.properties" })
public class PersistenceConfig {

	private static final Logger LOGGER = Logger.getLogger(PersistenceConfig.class);

	@Value("${db.config.datasource}")
	private String jndiDataSource;

	@Bean(destroyMethod = "")
	public DataSource dataSource() throws Exception {
		JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		DataSource dataSource = getDataSource(dsLookup, jndiDataSource);
		return dataSource;
	}
	
	public DataSource getDataSource(JndiDataSourceLookup dsLookup, String jndiName) {
		try {
			DataSource dataSourceRef = dsLookup.getDataSource(jndiName);
			LOGGER.warn("Encontrou " + jndiName);
			return dataSourceRef;
		} catch (DataSourceLookupFailureException e) {
			throw new AppException("Nao foi possivel obter datasource para " + jndiName, e);
		}
	}

	@Bean(destroyMethod = "")
	public JdbcTemplate jdbcTemplate() throws Exception {
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public DataSourceTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean(destroyMethod = "")
    public TransactionTemplate transactionTemplate() throws Exception {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(transactionManager());
        return transactionTemplate;
    }  

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}