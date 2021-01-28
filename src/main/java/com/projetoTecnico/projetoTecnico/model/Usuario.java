package com.projetoTecnico.projetoTecnico.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    @JsonIgnore
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="usuario_funcao",
            joinColumns=@JoinColumn(name="usuario_id"),
            inverseJoinColumns=@JoinColumn(name="funcao_id")
    )
    private List<Funcao> funcao;

    public Usuario() {
    }

    public Usuario(String name, String email) {
        super();
        this.nome = nome;
    }
    public Usuario(Usuario usuario) {
        super();
        this.nome = usuario.getNome();
        this.senha = usuario.getSenha();
        this.funcao = usuario.getFuncao();
        this.id = usuario.getId();
    }
    public Usuario(String nome, String senha, List<Funcao> funcao) {
        super();
        this.nome = nome;
        this.funcao = funcao;
        this.senha = senha;
    }

}