package br.com.petrobras.ep.premissas.service;

import org.springframework.transaction.annotation.Transactional;

import br.com.petrobras.ep.premissas.entity.BaseEntity;

public interface CrudService<T extends BaseEntity> {

	@Transactional
	T salvar(T entidade);
	
	@Transactional
	void excluir(Integer id);
	
	T get(Integer id);
}