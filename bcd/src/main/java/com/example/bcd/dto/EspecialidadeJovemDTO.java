package com.example.bcd.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data @Builder
public class EspecialidadeJovemDTO {
    private String nomeEspecialidade;
    private String areaConhecimento;
    private LocalDate dataCumprimento;
    private int nivelAtual;
}