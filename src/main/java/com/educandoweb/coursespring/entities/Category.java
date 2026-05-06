package com.educandoweb.coursespring.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Entidade de domínio que representa uma categoria de produtos (ex.: Electronics, Books).
 *
 * O JPA/Hibernate mapeia cada instância para uma linha em {@code tb_category}. O relacionamento
 * com {@link Product} é muitos-para-muitos: o lado <strong>inverso</strong> fica aqui
 * ({@code mappedBy = "categories"} na {@link Product}); a tabela de junção {@code tb_product_category}
 * é definida na entidade {@link Product}.
 */
@Entity
/*
 * @Entity: marca a classe como entidade JPA persistível (Hibernate gerencia tabela e SQL).
 */
@Table(name = "tb_category")
/*
 * @Table: nome físico da tabela no banco (prefixo tb_ alinhado ao restante do projeto).
 */
public class Category implements Serializable{
    /*
     * Serializable: permite serialização Java (cache, clusters, etc.); serialVersionUID estabiliza compatibilidade.
     */
    private static final long serialVersionUID = 1L;

    @Id
    /*
     * @Id: chave primária da entidade.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*
     * IDENTITY: o banco gera o valor do id no insert (H2/MySQL etc.).
     */
    private Long id;
    /** Nome exibido da categoria (coluna simples na tb_category). */
    private String name;

    /*
     * Lado inverso do N:N com Product: não cria coluna FK em tb_category; o dono do relacionamento
     * é Product (JoinTable lá). mappedBy aponta para o campo "categories" em Product.
     * HashSet: sem duplicatas na coleção em memória; lazy loading padrão para @ManyToMany.
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();

    /** Construtor vazio exigido pelo JPA. */
    public Category() {
    }

    /**
     * Construtor conveniente para seeds (ex.: {@code TestConfig}): {@code id} null até o primeiro {@code save}.
     */
    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * hashCode/equals por {@code id}: identidade de entidade em {@link Set} e {@link java.util.Map}.
     * Com {@code id == null} antes de persistir, o comportamento segue o padrão usual em entidades JPA.
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
        Category category = (Category) obj;
        if (id == null) {
            if (category.id != null) return false;
        } else if (!id.equals(category.id)) return false;
        return true;
    }

    /** Representação para debug/logs. */
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", products=" + products + "]";
    }
}
