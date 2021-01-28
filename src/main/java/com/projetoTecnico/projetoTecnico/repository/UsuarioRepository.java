
package com.projetoTecnico.projetoTecnico.repository;

import com.projetoTecnico.projetoTecnico.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNome(String nome);
}