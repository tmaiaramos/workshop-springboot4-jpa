package com.educandoweb.coursespring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educandoweb.coursespring.entities.Order;
import com.educandoweb.coursespring.services.OrderService;

/**
 * Camada web / REST (equivalente a um "controller" em APIs HTTP).
 *
 * Responsabilidade típica: mapear URLs e verbos HTTP para chamadas à camada de serviço
 * e montar a resposta HTTP (código de status, corpo JSON, cabeçalhos).
 */
@RestController
/*
 * @RestController = @Controller + @ResponseBody em todos os métodos de mapeamento.
 * O retorno dos métodos (objetos Java) é serializado automaticamente para JSON (por padrão, Jackson).
 */
@RequestMapping(value = "/orders")
/*
 * @RequestMapping no nível da classe: prefixo de URL para todos os endpoints deste resource.
 * Ex.: GET http://localhost:8080/orders → método findAll abaixo.
 */
public class OrderResource {

	/*
	 * INJEÇÃO DE DEPENDÊNCIA:
	 * O Spring fornece a instância de OrderService (anotada com @Service) neste campo antes de atender requisições.
	 */
	@Autowired
	private OrderService OrderService;

	/**
	 * Endpoint GET em /orders (porque a classe já tem @RequestMapping("/orders")).
	 *
	 * {@link ResponseEntity} permite controlar o status HTTP e o corpo; {@code ok().body(list)} = 200 + corpo.
	 */
	@GetMapping
	/*
	 * @GetMapping: atalho para @RequestMapping(method = RequestMethod.GET).
	 * Sem path extra → usa só o prefixo da classe → GET /orders.
	 */
	public ResponseEntity<List<Order>> findAll() {
		List<Order> list = OrderService.findAll();
		return ResponseEntity.ok().body(list);
	}

	/**
	 * Endpoint GET para um único pedido ({@code Order}), por id na URL.
	 *
	 * - {@code @GetMapping("/{id}")} combina com o prefixo da classe: rota final {@code GET /orders/{id}}
	 *   (ex.: {@code /orders/1}).
	 * - {@code @PathVariable Long id}: o Spring lê o trecho da URL que substitui {@code {id}} e converte
	 *   para {@code Long}, passando no parâmetro do método.
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id) {
		// Delega a busca ao OrderService (repositório / JPA por baixo).
		Order obj = OrderService.findById(id);
		// 200 OK + corpo JSON com o pedido (Jackson serializa a entidade Order).
		return ResponseEntity.ok().body(obj);
	}
}
