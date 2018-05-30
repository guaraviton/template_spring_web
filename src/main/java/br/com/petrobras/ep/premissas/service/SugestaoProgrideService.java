package br.com.petrobras.ep.premissas.service;

import java.util.List;

import br.com.petrobras.ep.premissas.entity.SugestaoProgride;

public interface SugestaoProgrideService extends CrudService<SugestaoProgride>{
	
	List<SugestaoProgride> query(String mensagem, String usuarioSugestao);
	
}
