package com.algaworks.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.TituloRepository;
import com.algaworks.cobranca.repository.filter.TituloFilter;

@Service
public class TituloService {
	
	@Autowired
	private TituloRepository tituloRepository;
	
	public void salvar(Titulo titulo) {
		try {
			tituloRepository.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido!");
		}
	}

	public void excluir(Long codigo) {
		tituloRepository.delete(codigo);
	}

	public List<Titulo> filtrar(TituloFilter filtro) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		return tituloRepository.findByDescricaoContaining(descricao);
	}

	public List<Titulo> listarTodos() {
		return tituloRepository.findAll();
	}

	public Titulo buscar(Long codigo) {
		return tituloRepository.findOne(codigo);
	}

	public String receber(Long codigo) {
		Titulo titulo = tituloRepository.findOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		tituloRepository.save(titulo);
		return StatusTitulo.RECEBIDO.getDescricao();
	}
}
