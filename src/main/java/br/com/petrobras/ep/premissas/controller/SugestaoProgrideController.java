package br.com.petrobras.ep.premissas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.petrobras.ep.premissas.entity.SugestaoProgride;
import br.com.petrobras.ep.premissas.service.CrudService;
import br.com.petrobras.ep.premissas.service.SugestaoProgrideService;

@RestController
@RequestMapping("/sugestaoProgride")
public class SugestaoProgrideController extends CrudController<SugestaoProgride>{
	
	@Autowired
	SugestaoProgrideService sugestaoProgrideService;
	
	@RequestMapping(method = RequestMethod.GET)
    public List<SugestaoProgride> query(@RequestParam(required=false, defaultValue="") String mensagem, @RequestParam(required=false, defaultValue="") String usuarioSugestao) {
		return sugestaoProgrideService.query(mensagem, usuarioSugestao);
	}

	@Override
	public CrudService<SugestaoProgride> getService() {
		return sugestaoProgrideService;
	}
}