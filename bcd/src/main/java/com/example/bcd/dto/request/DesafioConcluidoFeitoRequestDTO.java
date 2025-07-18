package com.example.bcd.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DesafioConcluidoFeitoRequestDTO {
    private Long idDesafioConcluido;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
