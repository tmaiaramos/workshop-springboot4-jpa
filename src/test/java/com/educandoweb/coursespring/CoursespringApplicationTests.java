package com.educandoweb.coursespring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Teste de integração mínimo: verifica se o contexto Spring Boot sobe sem erro.
 *
 * Diferença em relação a teste unitário puro: aqui o JUnit sobe (quase) a aplicação inteira — beans,
 * auto-configuração, etc. Se faltar dependência ou houver erro de configuração, o teste falha.
 */
@SpringBootTest
/*
 * @SpringBootTest: carrega o Application Context completo (ou um recorte, conforme propriedades).
 * É o análogo de "subir a app" dentro do processo de teste.
 */
class CoursespringApplicationTests {

	@Test
	/*
	 * @Test (JUnit 5 / Jupiter): marca um método como caso de teste executado pelo runner.
	 */
	void contextLoads() {
		// Corpo vazio de propósito: se o contexto não carregar, esta linha nem roda — o teste já falhou na subida.
	}

}
