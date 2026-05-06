package com.educandoweb.coursespring.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.educandoweb.coursespring.entities.Category;
import com.educandoweb.coursespring.entities.Order;
import com.educandoweb.coursespring.entities.Product;
import com.educandoweb.coursespring.entities.User;
import com.educandoweb.coursespring.entities.enums.OrderStatus;
import com.educandoweb.coursespring.repositories.CategoryRepository;
import com.educandoweb.coursespring.repositories.OrderRepository;
import com.educandoweb.coursespring.repositories.ProductRepository;
import com.educandoweb.coursespring.repositories.UserRepository;

/**
 * Configuração específica do perfil "test": popular o banco H2 com dados iniciais (seed).
 *
 * Fluxo no Spring Boot:
 * 1. Esta classe é um bean de configuração ({@code @Configuration}).
 * 2. Só entra no contexto se o perfil ativo for {@code test} ({@code @Profile("test")}).
 * 3. Implementa {@link CommandLineRunner}: o método {@link #run} é executado após
 *    o contexto subir (bom lugar para seeds ou tarefas de inicialização).
 * 4. Usa {@link UserRepository} injetado para persistir entidades — mesma ideia de DI que no {@code UserService}.
 */
@Configuration
/*
 * @Configuration: indica classe de configuração; métodos @Bean (se houver) registram objetos no container.
 * Mesmo sem @Bean aqui, a classe em si vira bean e o CommandLineRunner é detectado.
 */
@Profile("test")
/*
 * @Profile("test"): este bean só é criado quando spring.profiles.active contém "test"
 * (ex.: application-test.properties ou -Dspring.profiles.active=test).
 * Assim você não popula o banco de produção com usuários fictícios por engano.
 */
public class TestConfig implements CommandLineRunner {

	@Autowired
	// Injeção do repositório JPA gerado pelo Spring Data — necessário para saveAll no banco em memória (H2).
	private UserRepository userRepository;
	@Autowired
	// Injeção do repositório JPA gerado pelo Spring Data — necessário para saveAll no banco em memória (H2).
	private OrderRepository orderRepository;
	@Autowired
	// Injeção do repositório JPA gerado pelo Spring Data — necessário para saveAll no banco em memória (H2).
	private CategoryRepository categoryRepository;
	@Autowired
	// Injeção do repositório JPA gerado pelo Spring Data — necessário para saveAll no banco em memória (H2).
	private ProductRepository productRepository;

	@Override
	public void run(String... args) throws Exception {
		// userRepository.deleteAll(); // descomente se quiser limpar a tabela antes de inserir (útil em retestes).

		Category c1 = new Category(null, "Electronics");
		Category c2 = new Category(null, "Books");
		Category c3 = new Category(null, "Computers");

		Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", 90.5, "");
		Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", 2190.0, "");
		Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", 1250.0, "");
		Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", 1200.0, "");
		Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", 100.99, "");

		categoryRepository.saveAll(Arrays.asList(c1, c2, c3));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));

		// Objetos em memória; id null até o save — o banco gera id por @GeneratedValue(IDENTITY).
		User u1 = new User(null, "Maria Brown", "maria@gmail.com", "123456");
		User u2 = new User(null, "Alex Green", "alex@gmail.com", "123456");

		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
		Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), OrderStatus.WAITING_PAYMENT, u2);
		Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), OrderStatus.WAITING_PAYMENT, u1);		

		userRepository.saveAll(Arrays.asList(u1, u2));
		orderRepository.saveAll(Arrays.asList(o1, o2, o3));		
	}
}
