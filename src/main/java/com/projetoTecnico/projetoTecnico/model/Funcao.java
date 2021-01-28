package com.projetoTecnico.projetoTecnico.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Funcao implements GrantedAuthority {


    public Funcao(String nome) {
        this.nome = nome;
    }
    public Funcao() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;


    @Override
    public String getAuthority() {
        return  this.nome;
    }
}
