package com.aula.agendamento_odontologico.dto;

import com.aula.agendamento_odontologico.enums.Especialidade;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoDentista(
        @NotNull
        Long id,
        String nome,
        Especialidade especialidade,
        DtoEndereco endereco
) {
}
