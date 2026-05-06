package com.educandoweb.coursespring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educandoweb.coursespring.entities.Product;

/**
 * Camada de acesso a dados (Repository / DAO moderno com Spring Data JPA).
 *
 * Aqui você declara apenas uma interface — não escreve a implementação manualmente.
 * Em tempo de execução, o Spring Data JPA cria um proxy (implementação dinâmica) que:
 * - sabe traduzir chamadas como {@code findAll()}, {@code save()}, {@code findById()} para SQL;
 * - usa o {@link jakarta.persistence.EntityManager} / Hibernate por baixo;
 * - registra esse objeto como um bean no container (pode ser injetado com {@code @Autowired}).
 *
* Injeção de dependência: quem precisar de {@code ProductRepository} (ex.: {@code ProductService},
 * {@code TestConfig}) declara o tipo desta interface; o Spring injeta a implementação gerada.
 *
 * @param Product tipo da entidade gerenciada
 * @param Long tipo da chave primária ({@link Product#getId})
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
	/*
	 * JpaRepository<Product, Long> traz de graça muitos métodos: save, saveAll, findById, findAll, delete, etc.
	 * Você pode adicionar métodos com nomes no padrão Spring Data (ex.: findByEmail(String email))
	 * e o framework monta a consulta automaticamente.
	 */
}
