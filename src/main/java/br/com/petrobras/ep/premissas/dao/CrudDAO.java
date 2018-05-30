package br.com.petrobras.ep.premissas.dao;

import br.com.petrobras.ep.premissas.entity.BaseEntity;

public interface CrudDAO<T extends BaseEntity> {

	T salvar(T entidade);
	
	T get(Integer id);
	
	void excluir(Integer id);
}
