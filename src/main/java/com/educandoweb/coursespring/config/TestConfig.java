package com.educandoweb.coursespring.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.educandoweb.coursespring.entities.User;
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

	@Override
	public void run(String... args) throws Exception {
		// userRepository.deleteAll(); // descomente se quiser limpar a tabela antes de inserir (útil em retestes).

		// Objetos em memória; id null até o save — o banco gera id por @GeneratedValue(IDENTITY).
		User u1 = new User(null, "Maria Brown", "maria@gmail.com", "123456");
		User u2 = new User(null, "Alex Green", "alex@gmail.com", "123456");
		userRepository.saveAll(Arrays.asList(u1, u2));
	}
}
