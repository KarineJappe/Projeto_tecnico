package com.projetoTecnico.projetoTecnico.repository;

import com.projetoTecnico.projetoTecnico.model.Funcao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncaoRepository extends JpaRepository<Funcao, Long> {

    Funcao findByNome(String nome);

}