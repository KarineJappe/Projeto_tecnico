package com.projetoTecnico.projetoTecnico.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class Cliente  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    @NotNull
    private String nome;
    @Column(name = "cpf_cnpj")
    @NotNull
    private String cpfCnpj;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cliente", orphanRemoval = true)
    private Set<Endereco> endereco = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cliente", orphanRemoval = true)
    private Set<Telefone> telefone = new HashSet<>();

    public void setTelefone(Set<Telefone> telefones) {
        this.telefone = telefones;

        for(Telefone t : telefones) {
            t.setCliente(this);
        }
    }

    public void setEndereco(Set<Endereco> enderecos) {
        this.endereco = enderecos;

        for(Endereco e : enderecos) {
            e.setCliente(this);
        }
    }
}