package com.aula.agendamento_odontologico.dto;

import com.aula.agendamento_odontologico.enums.Especialidade;
import com.aula.agendamento_odontologico.model.Dentista;

public record DadosListagemDentista(
        Long id,
        String nome,
        String email,
        Especialidade especialidade,
        String cro
        ) {

    public DadosListagemDentista(Dentista dentista){
        this(dentista.getId(),dentista.getNome(), dentista.getEmail(), dentista.getEspecialidade(), dentista.getCro());
    }

}
