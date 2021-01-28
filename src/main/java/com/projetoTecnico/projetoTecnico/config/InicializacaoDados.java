package com.projetoTecnico.projetoTecnico.config;


import com.projetoTecnico.projetoTecnico.model.Funcao;
import com.projetoTecnico.projetoTecnico.model.Usuario;
import com.projetoTecnico.projetoTecnico.repository.FuncaoRepository;
import com.projetoTecnico.projetoTecnico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class InicializacaoDados implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    FuncaoRepository funcaoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {

        List<Usuario> users = usuarioRepository.findAll();

        if (users.isEmpty()) {
            createUser("Admin",  passwordEncoder.encode("123456"), Const.FUNCAO_ADMIN);
            createUser("Cliente",  passwordEncoder.encode("123456"), Const.FUNCAO_CLIENT);
        }

    }

    public void createUser(String nome, String senha, String funcaoNome) {

        Funcao funcao = new Funcao(funcaoNome);

        this.funcaoRepository.save(funcao);
        Usuario usuario = new Usuario(nome, senha, Arrays.asList(funcao));
        usuarioRepository.save(usuario);
    }

}
