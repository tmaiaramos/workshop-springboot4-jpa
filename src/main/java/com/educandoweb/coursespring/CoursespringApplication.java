package com.educandoweb.coursespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da aplicação Spring Boot.
 *
 * O que acontece ao subir a aplicação (resumo):
 * - A JVM executa {@code main}.
 * - {@link SpringApplication#run} cria o Application Context (o "container" do Spring).
 * - O Spring varre os pacotes (a partir deste pacote e subpacotes), encontra classes anotadas
 *   ({@code @Component}, {@code @Service}, {@code @RestController}, etc.) e as registra como beans.
 * - Em seguida resolve injeção de dependência: preenche construtores/campos {@code @Autowired}
 *   com as implementações corretas.
 * - Sobe o servidor embutido (Tomcat, por padrão) e fica escutando requisições HTTP.
 */
@SpringBootApplication
/*
 * @SpringBootApplication é uma "anotação composta": equivale, em essência, a juntar três coisas:
 * - @Configuration     → esta classe pode declarar beans (@Bean) e participar da configuração.
 * - @EnableAutoConfiguration → o Spring Boot configura sozinho coisas comuns (DataSource, JPA, web, etc.)
 *       com base no que está no classpath (ex.: spring-boot-starter-data-jpa).
 * - @ComponentScan     → procurar componentes (beans) a partir do pacote desta classe e subpacotes.
 *
 * Por isso, colocar suas classes em pacotes "abaixo" de com.educandoweb.coursespring faz o Spring encontrá-las.
 */
public class CoursespringApplication {

	/**
	 * Método padrão de entrada em aplicações Java.
	 *
	 * @param args argumentos passados na linha de comando (opcional; Spring Boot também os usa internamente).
	 */
	public static void main(String[] args) {
		// Inicia o contexto Spring e a aplicação web. O primeiro argumento diz QUAL classe tem @SpringBootApplication
		// (usado para localizar a configuração e o pacote base do scan).
		SpringApplication.run(CoursespringApplication.class, args);
	}

}
