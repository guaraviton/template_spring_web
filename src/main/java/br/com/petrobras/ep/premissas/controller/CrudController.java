package br.com.petrobras.ep.premissas.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.petrobras.ep.premissas.entity.BaseEntity;
import br.com.petrobras.ep.premissas.service.CrudService;


public abstract class CrudController<S extends BaseEntity>{
	
	public abstract CrudService<S> getService();
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
    public S salvar(@Valid @RequestBody S entity) {
		return getService().salvar(entity);
    }
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public S get(@PathVariable Integer id) {
		return getService().get(id);
    }
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean excluir(@PathVariable Integer id) {
		getService().excluir(id);
		return true;
    }
	
}
