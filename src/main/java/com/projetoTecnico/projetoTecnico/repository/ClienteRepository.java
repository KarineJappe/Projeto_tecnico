package com.projetoTecnico.projetoTecnico.repository;

import com.projetoTecnico.projetoTecnico.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findClienteByCpfCnpjIgnoreCase(String cpfCnpj);
}
