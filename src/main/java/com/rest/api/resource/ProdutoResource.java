package com.rest.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rest.api.model.Produto;
import com.rest.api.repository.IProdutos;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

	@Autowired
	private IProdutos produtos;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listar() {
		return new ResponseEntity<List<Produto>>(produtos.findAll(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@RequestBody Produto produto) {
		
		// Foi criada essa linha para verificar se o id passado ja existe, pq se colocar um id ja existente na hora da criação, irá sobrescrever o id existente
		boolean isCodigoIgual = produtos.findById(produto.getCodigo()).isPresent();
		
		// Foi criada essa linha para verificar se o código de barras passado na hora da criação, ja existe no banco
		boolean isCodigoBarraIgual = produtos.findByCodigoBarras(produto.getCodigoBarras()).isPresent();
		
		if (isCodigoIgual || isCodigoBarraIgual)
			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
			
		produto = produtos.save(produto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(produto.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/codigo/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPorCodigo(@PathVariable("codigo") Long codigo) {
		
		Optional<Produto> produto = produtos.findById(codigo);
		
		if (produto.isPresent())
			return ResponseEntity.status(HttpStatus.OK).body(produto);
		
		return ResponseEntity.notFound().build();
	}
	
	@RequestMapping(value = "/codigoBarras/{codigoBarras}", method = RequestMethod.GET)
	public ResponseEntity<?> buscarPorCodigoBarras(@PathVariable("codigoBarras") String codigoBarras) {
		
		Optional<Produto> produto = produtos.findByCodigoBarras(codigoBarras);
		
		if (produto.isPresent())
			return ResponseEntity.status(HttpStatus.OK).body(produto);
		
		return ResponseEntity.notFound().build();
	}
	
	@RequestMapping(value = "/{codigoBarras}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removerPorCodigoBarras(@PathVariable("codigoBarras") String codigoBarras) {
		
		Optional<Produto> produto = produtos.findByCodigoBarras(codigoBarras);
		
		if (produto.isPresent()) {
			produtos.delete(produto.get());
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
