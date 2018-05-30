package br.com.petrobras.ep.premissas.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.com.petrobras.ep.premissas.dao.BaseDAO;
import br.com.petrobras.ep.premissas.dao.SugestaoProgrideDAO;
import br.com.petrobras.ep.premissas.entity.SugestaoProgride;
import br.com.petrobras.ep.premissas.helper.util.StringUtils;

@Repository
public class SugestaoProgrideDAOImpl extends BaseDAO<SugestaoProgride>implements SugestaoProgrideDAO {
	
	public RowMapper<SugestaoProgride> mappper = new RowMapper<SugestaoProgride>() {
		@Override
		public SugestaoProgride mapRow(ResultSet rs, int rowNum) throws SQLException {
			SugestaoProgride result = new SugestaoProgride();
			result.setId(rs.getInt("SUPR_SQ_SUGESTAO_PROGRIDE"));
			result.setMensagem(rs.getString("SUPR_TX_MENSAGEM"));
			result.setInclusao(rs.getTimestamp("SUPR_DT_INCLUSAO"));
			result.setUsuarioSugestao(rs.getString("USER_CD_USUARIO_SUGESTAO"));
			return result;
		}
	};

	@Override
	public List<SugestaoProgride> query(String mensagem, String usuarioSugestao) {
		StringBuilder sql = new StringBuilder();
		List<Object> parametros = new ArrayList<Object>();
		sql
		.append(" SELECT	SUPR_SQ_SUGESTAO_PROGRIDE, 		\n")
		.append("			SUPR_TX_MENSAGEM, 				\n") 
		.append("			SUPR_DT_INCLUSAO, 				\n")
		.append("			USER_CD_USUARIO_SUGESTAO 		\n")
		.append(" FROM  	SUGESTAO_PROGRIDE				\n")
		.append(" WHERE  	1=1								\n");
		if(StringUtils.isNotBlank(mensagem)) {
			sql.append(" AND SUPR_TX_MENSAGEM like ?		\n");
			parametros.add("%"+mensagem+"&");	
		}
		if(StringUtils.isNotBlank(usuarioSugestao)) {
			sql.append(" AND USER_CD_USUARIO_SUGESTAO = ?	\n");
			parametros.add(usuarioSugestao);	
		}
		sql.append(" order by 1								\n");
		return queryList(sql.toString(), mappper, parametros);
	}

	@Override
	public SugestaoProgride get(Integer id) {
		List<Object> parametros = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql
		.append(" SELECT	SUPR_SQ_SUGESTAO_PROGRIDE, 		\n")
		.append("			SUPR_TX_MENSAGEM, 				\n") 
		.append("			SUPR_DT_INCLUSAO, 				\n")
		.append("			USER_CD_USUARIO_SUGESTAO 		\n")
		.append(" FROM  	SUGESTAO_PROGRIDE				\n")
		.append(" WHERE 	SUPR_SQ_SUGESTAO_PROGRIDE = ?	\n");
		parametros.add(id);
		return queryUnique(sql.toString(), parametros, mappper);
	}

	@Override
	public SugestaoProgride salvar(SugestaoProgride entidade) {
		if(entidade.getId() != null) {
			update(entidade);
		}else {
			insert(entidade);
		}
		return entidade;
	}

	private void insert(SugestaoProgride entidade) {
		List<Object> parametros = new ArrayList<Object>();
		StringBuilder sqlInsert = new StringBuilder();
		sqlInsert
		.append(" INSERT INTO SUGESTAO_PROGRIDE (SUPR_SQ_SUGESTAO_PROGRIDE, SUPR_TX_MENSAGEM, SUPR_DT_INCLUSAO, USER_CD_USUARIO_SUGESTAO) ")
		.append(" VALUES (?, ?, ?, ?) ");
		Integer nextVal = getSequenceNextVal("SQ_SUPR_SQ_SUGESTAO_PROGRIDE");
		parametros.add(nextVal);
		parametros.add(entidade.getMensagem());
		parametros.add(new Date());
		parametros.add(entidade.getUsuarioSugestao());
		update(sqlInsert.toString(), parametros);
		entidade.setId(nextVal);
	}

	private void update(SugestaoProgride entidade) {
		List<Object> parametros = new ArrayList<Object>();
		StringBuilder sqlUpdate = new StringBuilder();
		sqlUpdate
		.append(" UPDATE SUGESTAO_PROGRIDE")
		.append(" SET 	SUPR_TX_MENSAGEM = 			?,")
		.append(" 		USER_CD_USUARIO_SUGESTAO = 	? ")
		.append(" WHERE	SUPR_SQ_SUGESTAO_PROGRIDE = ? ");
		parametros.add(entidade.getMensagem());
		parametros.add(entidade.getUsuarioSugestao());
		parametros.add(entidade.getId());
		update(sqlUpdate.toString(), parametros);
	}

	@Override
	public void excluir(Integer id) {
		StringBuilder sqlUpdate = new StringBuilder();
		sqlUpdate
		.append(" DELETE SUGESTAO_PROGRIDE")
		.append(" WHERE	SUPR_SQ_SUGESTAO_PROGRIDE = ? ");
		update(sqlUpdate.toString(), id);
	}
}