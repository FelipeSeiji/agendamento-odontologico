package com.aula.agendamento_odontologico.model;

import com.aula.agendamento_odontologico.dto.DtoEndereco;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Endereco {
    private String logradouro;
    private String bairro;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;
    private String cep;


    public Endereco(DtoEndereco dtoEndereco) {
        this.logradouro = dtoEndereco.logradouro();
        this.bairro = dtoEndereco.bairro();
        this.numero = dtoEndereco.numero();
        this.complemento = dtoEndereco.complemento();
        this.cidade = dtoEndereco.cidade();
        this.uf = dtoEndereco.uf();
        this.cep = dtoEndereco.cep();
    }

    public void alterarEndereco(DtoEndereco endereco) {
        this.logradouro = endereco.logradouro();
        this.bairro = endereco.bairro();
        this.numero = endereco.numero();
        this.complemento = endereco.complemento();
        this.cidade = endereco.cidade();
        this.uf = endereco.uf();
        this.cep = endereco.cep();
    }

    public void atualizarEndereco(DtoEndereco endereco) {
        if (endereco.logradouro() != null) {
            this.logradouro = endereco.logradouro();
        }
        if (endereco.bairro() != null) {
            this.bairro = endereco.bairro();
        }
        if (endereco.numero() != null) {
            this.numero = endereco.numero();
        }
        if (endereco.complemento() != null) {
            this.complemento = endereco.complemento();
        }
        if (endereco.cidade() != null) {
            this.cidade = endereco.cidade();
        }
        if (endereco.uf() != null) {
            this.uf = endereco.uf();
        }
        if (endereco.cep() != null) {
            this.cep = endereco.cep();
        }
    }
}
