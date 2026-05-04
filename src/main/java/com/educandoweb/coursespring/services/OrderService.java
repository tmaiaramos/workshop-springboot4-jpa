package com.educandoweb.coursespring.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educandoweb.coursespring.entities.Order;
import com.educandoweb.coursespring.repositories.OrderRepository;

/**
 * Camada de serviço: concentra regras de negócio e orquestra repositórios.
 *
 * Por que existir entre Resource e Repository?
 * - O Resource (controller REST) deve ser fino: receber HTTP, chamar serviço, devolver resposta.
 * - O Service evita que regras fiquem espalhadas nos controllers e facilita testes e reuso.
 * - O Repository só fala com o banco; o serviço combina operações e validações.
 *
 * Neste exemplo o serviço delega {@code findAll()} e {@code findById} ao repositório — típico no início
 * do curso; depois costuma-se adicionar validações, transações, integração com outros serviços, etc.
 */
@Service
/*
 * @Service: especialização de @Component. Indica ao Spring que esta classe é um bean de "lógica de negócio".
 * Semanticamente diferente de @Component, mas o efeito no container é o mesmo: instância única (singleton
 * por padrão) gerenciada pelo Spring e elegível à injeção.
 */
public class OrderService {

	/*
	 * INJEÇÃO DE DEPENDÊNCIA (por campo):
	 * - Você não faz "new OrderRepository()" (nem poderia: é interface).
	 * - O Spring, ao criar o OrderService, vê que precisa de um OrderRepository e injeta o bean já existente.
	 *
	 * Alternativa moderna (muitos projetos preferem): injeção por CONSTRUTOR — torna dependências obrigatórias
	 * e facilita testes unitários sem reflexão.
	 */
	@Autowired
	// @Autowired: "cole aqui um bean compatível com este tipo". Se houver mais de um candidato, é preciso @Qualifier.
	private OrderRepository OrderRepository;

	/** Lista todos os pedidos persistidos (delega ao repositório / JPA). */
	public List<Order> findAll() {
		return OrderRepository.findAll();
	}

	/**
	 * Busca um pedido ({@code Order}) pela chave primária.
	 *
	 * O {@code OrderRepository} herda de {@code JpaRepository}, que oferece {@code findById(Long)}.
	 * Esse método retorna {@link Optional} em vez de {@code Order} direto: se não existir linha com esse id
	 * no banco, o Optional fica "vazio" (o repositório não devolve {@code null} de Order nesse retorno).
	 *
	 * {@code obj.get()} devolve o {@code Order} se houver valor; se o Optional estiver vazio, lança
	 * {@code java.util.NoSuchElementException} (o cliente HTTP verá erro 500 se não houver tratamento
	 * no controller). Em APIs REST o curso costuma depois trocar isso por 404 (recurso não encontrado).
	 *
	 * @param id identificador do pedido (mesmo valor da coluna de chave primária na tabela mapeada por {@code Order}).
	 * @return entidade {@code Order} encontrada
	 */
	public Order findById(Long id) {
		// findById do JpaRepository: consulta por chave primária; Optional encapsula presença ou ausência de linha.
		Optional<Order> obj = OrderRepository.findById(id);
		// get() extrai o Order; se não houver pedido com esse id, Optional vazio → NoSuchElementException.
		return obj.get();
	}
}
