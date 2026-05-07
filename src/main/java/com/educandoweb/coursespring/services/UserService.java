package com.educandoweb.coursespring.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.educandoweb.coursespring.entities.User;
import com.educandoweb.coursespring.repositories.UserRepository;
import com.educandoweb.coursespring.resources.exceptions.DatabaseException;
import com.educandoweb.coursespring.services.exceptions.ResourceNotFoundException;

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
public class UserService {

	/*
	 * INJEÇÃO DE DEPENDÊNCIA (por campo):
	 * - Você não faz "new UserRepository()" (nem poderia: é interface).
	 * - O Spring, ao criar o UserService, vê que precisa de um UserRepository e injeta o bean já existente.
	 *
	 * Alternativa moderna (muitos projetos preferem): injeção por CONSTRUTOR — torna dependências obrigatórias
	 * e facilita testes unitários sem reflexão.
	 */
	@Autowired
	// @Autowired: "cole aqui um bean compatível com este tipo". Se houver mais de um candidato, é preciso @Qualifier.
	private UserRepository userRepository;

	/** Lista todos os usuários persistidos (delega ao repositório / JPA). */
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * Busca um usuário pela chave primária.
	 *
	 * O {@code UserRepository} herda de {@code JpaRepository}, que oferece {@code findById(Long)}.
	 * Esse método retorna {@link Optional} em vez de {@code User} direto: se não existir linha com esse id
	 * no banco, o Optional fica "vazio" (não existe null de User embutido no retorno do repositório).
	 *
	 * {@code obj.get()} devolve o {@code User} se houver valor; se o Optional estiver vazio, lança
	 * {@code java.util.NoSuchElementException} (o cliente HTTP verá erro 500 se não houver tratamento
	 * no controller). Em APIs REST o curso costuma depois trocar isso por 404 (recurso não encontrado).
	 *
	 * @param id identificador do usuário (mesmo valor da coluna de chave na tabela).
	 * @return entidade encontrada
	 */
	public User findById(Long id) {
		// findById do JpaRepository: consulta por chave primária; retorno Optional evita null explícito.
		Optional<User> obj = userRepository.findById(id);
		// get() extrai o User; se não houver registro com esse id, Optional vazio → NoSuchElementException.
		//return obj.get();
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public User insert(User obj) {
		return userRepository.save(obj);
	}

	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}	catch (DataIntegrityViolationException e) {			
			throw new DatabaseException(e.getMessage());
		}
	}

	public User update(Long id, User obj) {
		
		// O findById carrega o objeto da base de dados, o getReferenceById carrega o objeto sem carregar da base de dados.
		// carrega somente a referência do objeto em entity, mas não carrega os dados do objeto. Essa operação é mais eficiente.	
		User entity = userRepository.getReferenceById(id);
		updateData(entity, obj);
		return userRepository.save(entity);
	}

	private void updateData(User entity, User obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setPhone(obj.getPhone());
	}
}
