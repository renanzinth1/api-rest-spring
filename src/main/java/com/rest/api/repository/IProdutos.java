package com.rest.api.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.model.Produto;

public interface IProdutos extends JpaRepository<Produto, Long> {
	
	public Optional<Produto> findByCodigoBarras(String codigoBarras);

}
