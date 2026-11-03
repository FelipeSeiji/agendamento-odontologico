package com.aula.agendamento_odontologico.model;

import com.aula.agendamento_odontologico.dto.DadosAtualizacaoDentista;
import com.aula.agendamento_odontologico.dto.DadosCadastroDentista;
import com.aula.agendamento_odontologico.enums.Especialidade;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Table(name = "dentista")
@Entity(name = "Dentista")
@AllArgsConstructor
@NoArgsConstructor
@Getter
//Compara id entre objeto e tabela
@EqualsAndHashCode(of = "id")
public class Dentista {
    //Idenfica o id como primary key
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cro;
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    @Embedded
    private Endereco endereco;
    private Boolean ativo;


    public Dentista(DadosCadastroDentista dadosDentista) {
        this.nome = dadosDentista.nome();
        this.ativo = true;
        this.email = dadosDentista.email();
        this.cro = dadosDentista.cro();
        this.especialidade = dadosDentista.especialidade();
        this.endereco = new Endereco(dadosDentista.endereco());
    }


    public void atualizarDadosDentista(DadosAtualizacaoDentista dadosDentista) {
        if (dadosDentista.nome() != null) {
            this.nome = dadosDentista.nome();
        }

        this.especialidade = dadosDentista.especialidade();

        if (dadosDentista.endereco() != null) {
            this.endereco.atualizarEndereco(dadosDentista.endereco());
        }
    }

    public void atualizarStatus() {
        this.ativo = false;
    }
}
