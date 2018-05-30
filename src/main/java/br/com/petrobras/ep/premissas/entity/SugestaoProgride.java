package br.com.petrobras.ep.premissas.entity;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class SugestaoProgride extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	@NotEmpty
	private String mensagem;
	private Date inclusao;
	@NotEmpty
	private String usuarioSugestao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Date getInclusao() {
		return inclusao;
	}
	public void setInclusao(Date inclusao) {
		this.inclusao = inclusao;
	}
	public String getUsuarioSugestao() {
		return usuarioSugestao;
	}
	public void setUsuarioSugestao(String usuarioSugestao) {
		this.usuarioSugestao = usuarioSugestao;
	}
}
