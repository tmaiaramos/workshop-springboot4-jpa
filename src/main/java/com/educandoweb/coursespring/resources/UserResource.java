package com.educandoweb.coursespring.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.educandoweb.coursespring.entities.User;
import com.educandoweb.coursespring.services.UserService;

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
@RequestMapping(value = "/users")
/*
 * @RequestMapping no nível da classe: prefixo de URL para todos os endpoints deste resource.
 * Ex.: GET http://localhost:8080/users → método findAll abaixo.
 */
public class UserResource {

	/*
	 * INJEÇÃO DE DEPENDÊNCIA:
	 * O Spring fornece a instância de UserService (anotada com @Service) neste campo antes de atender requisições.
	 */
	@Autowired
	private UserService userService;

	/**
	 * Endpoint GET em /users (porque a classe já tem @RequestMapping("/users")).
	 *
	 * {@link ResponseEntity} permite controlar o status HTTP e o corpo; {@code ok().body(list)} = 200 + corpo.
	 */
	@GetMapping
	/*
	 * @GetMapping: atalho para @RequestMapping(method = RequestMethod.GET).
	 * Sem path extra → usa só o prefixo da classe → GET /users.
	 */
	public ResponseEntity<List<User>> findAll() {
		List<User> list = userService.findAll();
		return ResponseEntity.ok().body(list);
	}

	/**
	 * Endpoint GET para um único usuário, por id na URL.
	 *
	 * - {@code @GetMapping("/{id}")} combina com o prefixo da classe: rota final {@code GET /users/{id}}
	 *   (ex.: {@code /users/1}).
	 * - {@code @PathVariable Long id}: o Spring lê o trecho da URL que substitui {@code {id}} e converte
	 *   para {@code Long}, passando no parâmetro do método.
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		// Delega a regra de busca ao serviço (camada que fala com o repositório / JPA).
		User obj = userService.findById(id);
		// 200 OK + corpo JSON com o usuário (Jackson serializa o objeto User).
		return ResponseEntity.ok().body(obj);
	}

	/**
	 * Endpoint POST para inserir um novo usuário.
	 *
	 * - {@code @PostMapping}: atalho para @RequestMapping(method = RequestMethod.POST).
	 * - {@code @RequestBody User obj}: o Spring lê o corpo da requisição e converte para {@code User}.
	 */
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User obj) {
		obj = userService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);		
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
