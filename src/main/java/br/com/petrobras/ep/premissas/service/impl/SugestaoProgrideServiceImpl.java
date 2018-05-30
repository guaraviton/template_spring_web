package br.com.petrobras.ep.premissas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.petrobras.ep.premissas.dao.SugestaoProgrideDAO;
import br.com.petrobras.ep.premissas.entity.SugestaoProgride;
import br.com.petrobras.ep.premissas.service.SugestaoProgrideService;

@Service
public class SugestaoProgrideServiceImpl implements SugestaoProgrideService {

	@Autowired
	SugestaoProgrideDAO abrangenciaDAO;

	@Override
	public List<SugestaoProgride> query(String mensagem, String usuarioSugestao) {
		return abrangenciaDAO.query(mensagem, usuarioSugestao);
	}

	@Override
	public SugestaoProgride get(Integer id) {
		return abrangenciaDAO.get(id);
	}

	@Override
	public SugestaoProgride salvar(SugestaoProgride entidade) {
		return abrangenciaDAO.salvar(entidade);
	}

	@Override
	public void excluir(Integer id) {
		abrangenciaDAO.excluir(id);
	}
}