package com.projetoTecnico.projetoTecnico.controller;

import com.projetoTecnico.projetoTecnico.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClientesSelecionados {

    private List<Long> clientes;
}
