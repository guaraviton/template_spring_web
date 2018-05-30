package br.com.petrobras.ep.premissas.dao;

import java.util.List;

import br.com.petrobras.ep.premissas.entity.SugestaoProgride;

public interface SugestaoProgrideDAO extends CrudDAO<SugestaoProgride>{
	
	List<SugestaoProgride> query(String mensagem, String usuarioSugestao);
	
}