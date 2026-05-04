package com.educandoweb.coursespring.services;

/**
 * Declaração de uma anotação personalizada (meta-anotação em Java).
 *
 * A palavra-chave {@code @interface} (com arroba) define um novo tipo de anotação — não é a mesma coisa
 * que {@code interface} de serviço. Aqui {@code Services} é um marcador vazio: você poderia
 * usá-la em classes/métodos como {@code @Services} para documentação ou para ferramentas que leem anotações
 * via reflexão.
 *
 * Este arquivo não participa da injeção de dependência nem do REST do projeto enquanto ninguém usar
 * {@code @Services} em outro lugar. Se foi criado por engano no curso, pode removê-lo; se o instrutor
 * for usá-lo depois, mantenha.
 */
public @interface Services {

}
