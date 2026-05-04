package com.educandoweb.coursespring.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidade de domínio (modelo do negócio) que representa um usuário.
 *
 * Em orientação a objetos: é uma classe com estado (atributos) e comportamento
 * (métodos getters/setters). O JPA/Hibernate a mapeia para uma tabela
 * no banco de dados — ou seja, cada instância de {@code User} pode corresponder a uma linha em {@code tb_user}.
 */
@Entity
/*
 * @Entity (Jakarta Persistence / JPA): marca esta classe como entidade persistível.
 * O provedor JPA (no seu projeto, Hibernate) sabe que deve criar/gerenciar o mapeamento objeto-relacional.
 */
@Table(name = "tb_user")
/*
 * @Table: nome da tabela no banco. Se omitir, o Hibernate costuma usar o nome da classe (ex.: "User").
 * Aqui forçamos "tb_user" para seguir um padrão de nomenclatura (prefixo tb_).
 */
public class User implements Serializable {
	/*
	 * Serializable: contrato Java para serialização (transformar objeto em bytes e vice-versa).
	 * Útil em sessões HTTP distribuídas, cache, etc. serialVersionUID evita avisos e ajuda na compatibilidade
	 * entre versões da classe.
	 */
	private static final long serialVersionUID = 1L;

	@Id
	/*
	 * @Id: este campo é a chave primária da entidade (e da linha na tabela).
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/*
	 * @GeneratedValue: o valor do id é gerado pelo banco (não pela aplicação).
	 * GenerationType.IDENTITY → típico de colunas AUTO_INCREMENT (MySQL) ou IDENTITY (SQL Server, H2).
	 * Ao inserir, você pode passar null no id e o banco preenche.
	 */
	private Long id;
	private String name;
	private String email;
	private String password;

	@OneToMany(mappedBy = "client")
	private List<Order> orders = new ArrayList<>();

	/** Construtor vazio exigido pelo JPA para instanciar a entidade via reflexão. */
	public User() {
	}

	/**
	 * Construtor com argumentos: conveniente para criar objetos no código (ex.: seed no {@code TestConfig}).
	 * O {@code id} pode ser {@code null} na criação; o banco gera após {@code save}.
	 */
	public User(Long id, String name, String email, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * hashCode e equals baseados no {@code id}: duas instâncias com o mesmo id são consideradas "iguais"
	 * em coleções (Set, Map) e em comparações lógicas de identidade de entidade.
	 *
	 * Observação: enquanto o id ainda é {@code null} (objeto novo, não persistido), equals/hashCode podem
	 * se comportar de forma menos útil — padrão comum em entidades JPA.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		User user = (User) obj;
		if (id == null) {
			if (user.id != null) return false;
		} else if (!id.equals(user.id)) return false;
		return true;
	}

	/** Representação em texto para logs e debug (evite logar senha em produção). */
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
}
