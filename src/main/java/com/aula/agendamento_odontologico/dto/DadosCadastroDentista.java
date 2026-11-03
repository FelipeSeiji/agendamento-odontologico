package com.aula.agendamento_odontologico.dto;

import com.aula.agendamento_odontologico.enums.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record DadosCadastroDentista(
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String cro,
        @NotNull
        Especialidade especialidade,
        @NotNull
        @Valid
        DtoEndereco endereco
) {
}
