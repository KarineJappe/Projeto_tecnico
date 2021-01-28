package com.projetoTecnico.projetoTecnico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

public class Endereco {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean principal;
    private String logradouro;
    private int numero;
    private String bairro;
    private String complemento;
    private String cidade;
    private String cep;
    private String uf;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
