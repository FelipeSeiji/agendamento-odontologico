package com.aula.agendamento_odontologico.controller;

import com.aula.agendamento_odontologico.dto.DadosAutenticacao;
import com.aula.agendamento_odontologico.dto.DadosTokenJWT;
import com.aula.agendamento_odontologico.infra.security.TokenService;
import com.aula.agendamento_odontologico.model.Usuario;
import com.aula.agendamento_odontologico.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

   @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dadosAutenticacao){

        var authenticaonToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(), dadosAutenticacao.senha());
        var authenticaon = manager.authenticate(authenticaonToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authenticaon.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}
