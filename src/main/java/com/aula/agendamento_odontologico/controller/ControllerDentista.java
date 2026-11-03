package com.aula.agendamento_odontologico.controller;

import com.aula.agendamento_odontologico.dto.DadosAtualizacaoDentista;
import com.aula.agendamento_odontologico.dto.DadosCadastroDentista;
import com.aula.agendamento_odontologico.dto.DadosDetalhamentoDentista;
import com.aula.agendamento_odontologico.dto.DadosListagemDentista;
import com.aula.agendamento_odontologico.model.Dentista;
import com.aula.agendamento_odontologico.repository.DentistaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/dentista")
@RestController
public class ControllerDentista {

    @Autowired //Deixa a reponsabilidade para o spring instanciar
    private DentistaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastroDentista(@RequestBody @Valid DadosCadastroDentista dadosDentista, UriComponentsBuilder uriBuilder) {
        var dentista = new Dentista(dadosDentista);
        repository.save(dentista);
        var uri = uriBuilder.path("/dentista/{id}").buildAndExpand(dentista.getId()).toUri();
        return  ResponseEntity.created(uri).body(new DadosDetalhamentoDentista(dentista));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemDentista>> listarDentistas(Pageable pageable) {
        var page = repository.findAllByAtivoTrue(pageable).map(DadosListagemDentista::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity alterarDadosDentista(@RequestBody @Valid DadosAtualizacaoDentista dadosDentista) {
        var dentista = repository.getReferenceById(dadosDentista.id());
        dentista.atualizarDadosDentista(dadosDentista);
        return ResponseEntity.ok(new DadosDetalhamentoDentista(dentista));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity excluirDentista(@PathVariable Long id) {
        var dentista = repository.getReferenceById(id);
        dentista.atualizarStatus();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity detalharDentista(@PathVariable Long id) {
        var dentista = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoDentista(dentista));
    }

}
