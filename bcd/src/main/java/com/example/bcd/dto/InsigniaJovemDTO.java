package com.example.bcd.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data @Builder
public class InsigniaJovemDTO {
    private String nomeInsignia;
    private LocalDate dataConquista;
}