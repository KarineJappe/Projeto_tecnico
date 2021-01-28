package com.projetoTecnico.projetoTecnico.controller;


import com.projetoTecnico.projetoTecnico.config.Const;
import com.projetoTecnico.projetoTecnico.model.Usuario;
import com.projetoTecnico.projetoTecnico.repository.FuncaoRepository;
import com.projetoTecnico.projetoTecnico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncaoRepository funcaoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Secured({Const.FUNCAO_ADMIN})
    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario){
        usuario = this.usuarioRepository.save(usuario);
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @Secured({Const.FUNCAO_ADMIN})
    @PutMapping
    public ResponseEntity<Usuario> edit(@RequestBody Usuario usuario){
        usuario = this.usuarioRepository.save(usuario);
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }




}