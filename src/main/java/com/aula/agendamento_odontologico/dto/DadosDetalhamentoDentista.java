package com.aula.agendamento_odontologico.dto;

import com.aula.agendamento_odontologico.enums.Especialidade;
import com.aula.agendamento_odontologico.model.Dentista;

public record DadosDetalhamentoDentista(
        Long id,
        String nome,
        String email,
        String cro,
        Especialidade especialidade,
        com.aula.agendamento_odontologico.model.Endereco endereco

) {

    public DadosDetalhamentoDentista(Dentista dentista){
        this(dentista.getId(), dentista.getNome(), dentista.getEmail(),
                dentista.getCro(), dentista.getEspecialidade(), dentista.getEndereco());
    }

}
