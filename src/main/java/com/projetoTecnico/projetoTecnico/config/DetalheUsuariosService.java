package com.projetoTecnico.projetoTecnico.config;

import com.projetoTecnico.projetoTecnico.model.Usuario;
import com.projetoTecnico.projetoTecnico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class DetalheUsuariosService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public DetalheUsuariosService (UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNome(username);
        if (usuario == null) {
            throw new UsernameNotFoundException(String.format("Usuário não existe!", username));
        }
        return new UserRepositoryUserDetails(usuario);
    }

    private final static class UserRepositoryUserDetails extends Usuario implements UserDetails {

        private static final long serialVersionUID = 1L;

        private UserRepositoryUserDetails(Usuario usuario) {
            super(usuario);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return getFuncao();
        }

        @Override
        public String getUsername() {
            return this.getNome();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public String getPassword() {
            return  super.getSenha();
        }

    }

}