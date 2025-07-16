package com.example.bcd.dto;

import com.example.bcd.model.TipoSanguineo;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PessoaDTO {
    private Long idPessoa;
    private String nome;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
    private Long idTipoSanguineo;
    private String tipoSanguineoNome;
}