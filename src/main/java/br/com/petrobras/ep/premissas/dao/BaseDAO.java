package br.com.petrobras.ep.premissas.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import br.com.petrobras.ep.premissas.helper.exception.AppQueryException;
import br.com.petrobras.ep.premissas.helper.util.ConverterUtils;
import br.com.petrobras.ep.premissas.helper.util.StringUtils;

public abstract class BaseDAO<E> {

	private static final Logger LOGGER = Logger.getLogger(BaseDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	protected String getSequenceName(){return "";}

	protected Integer getSequenceNextVal() {
		return this.getSequenceNextVal(getSequenceName());
	}
	
	protected Integer getSequenceNextVal(String sequenceName) {
		return this.queryForInteger("select "+sequenceName+".nextval from dual");
	}
	
	public boolean queryForBoolean(String sql, Object... parameters) {
		String value = queryForString(sql, parameters);
		return Boolean.valueOf(value);
	}
	
	public String queryForString(String sql, Object... parameters) {
		return queryForObject(sql, new SingleColumnRowMapper<String>(String.class), parameters);
	}
	
	public Integer queryForInteger(String sql, Object... parameters) {
		return queryForObject(sql, new SingleColumnRowMapper<Integer>(Integer.class), parameters);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T queryForObject(String sql, SingleColumnRowMapper<T> mapper, Object[] parameters) {
		List<E> list = queryList(sql, (RowMapper<E>) mapper, parameters);
		if (list != null && list.size() > 0) {
			return (T) list.get(0);
		}
		return null;
	}
	
	public List<E> queryList(String sql, List<Object> parametros, RowMapper<E> mapper) {
		return queryList(sql, mapper, parametros.toArray(new Object[parametros.size()]));
	}
	
	public E queryUnique(String sql, List<Object> parametros, RowMapper<E> mapper) {
		List<E> lista = queryList(sql, mapper, parametros.toArray(new Object[parametros.size()]));
		if(lista.isEmpty()){
			return null;
		}
		if(lista.size() > 1){
			throw new AppQueryException("Mais de um resultado retornado no metodo queryUnique");
		}
		return lista.get(0);
	}
	
	public List<E> queryList(String sql, RowMapper<E> rowMapper, List<Object> parametros) {
		return queryList(sql, rowMapper, parametros != null ? parametros.toArray(new Object[parametros.size()]) : (Object[]) null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<E> queryList(String sql, RowMapper<E> rowMapper, Object... parameters) {
		long time = System.currentTimeMillis();
		try {
			List result = jdbcTemplate.query(sql, parameters, rowMapper);
			if (LOGGER.isDebugEnabled()) {
				printSqlTime(time, sql, parameters);
			}
			return result;
		} catch (Exception e) {
			LOGGER.error("Erro ao executar o comando SQL: " + sql + "com os parametros:" + toSqlLog(parameters), e);
			throw new AppQueryException("Erro ao executar o comando SQL", e);
		}
	}

	public int update(String sql, List<Object> parametros) {
		return update(sql, parametros != null ? parametros.toArray(new Object[parametros.size()]) : (Object[]) null);
	}
	
	public int update(String sql, Object... parameters) {
		long time = System.currentTimeMillis();
		try {
			int update = jdbcTemplate.update(sql, parameters);
			if (LOGGER.isDebugEnabled()) {
				printSqlTime(time, sql, parameters);
			}
			return update;
		} catch (Exception e) {
			LOGGER.error("Erro ao executar a query:" + sql + " com os parametros:" + toSqlLog(parameters) + ". "
					+ e.getMessage(), e);
			throw new AppQueryException("Erro ao executar a query:" + sql + " com os parametros:" + toSqlLog(parameters)
					+ ". " + e.getMessage(), e);
		}
	}
	
	protected void printSqlTime(long startTime, String sql, Object[] params) {
		long secs = ConverterUtils.toSeconds(System.currentTimeMillis() - startTime);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Tempo [" + secs + " secs] " + toSqlLog(sql, params));
		}
	}

	protected String toSqlLog(String sql, Object[] params) {
		StringBuilder log = new StringBuilder("SQL [");
		log.append(StringUtils.trimInsideWithOneSpace(sql.replaceAll("\n", "").replaceAll("\t", "")));
		log.append("] ");
		log.append(toSqlLog(params));
		return log.toString();
	}

	protected String toSqlLog(Object[] params) {
		StringBuilder log = new StringBuilder();
		if (params != null && params.length > 0) {
			log.append(" Parametros [");
			for (Object param : params) {
				log.append(StringUtils.toString(param)).append(",");
			}
			log.deleteCharAt(log.length() - 1);
			log.append("]");
		}
		return log.toString();
	}

	public String buildInClause(List<? extends Object> values, boolean quotation) {
		StringBuilder in = new StringBuilder();

		String simpleQuotation = quotation ? "'" : StringUtils.EMPTY_STRING;

		if (values != null && values.size() > 0) {
			for (Object value : values) {
				in.append(simpleQuotation).append(escape(value.toString())).append(simpleQuotation).append(",");
			}
			in.deleteCharAt(in.length() - 1);
		}
		return in.toString();
	}
	
	public static String escape(String value) {
		return StringUtils.toString(value).replaceAll("'", "''");
	}
}
