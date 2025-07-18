package com.example.bcd.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DesafioDeEspecialidadeFeitaRequestDTO {
    private Long idDesafio;
    private Long idEspecialidade;
    private LocalDate data;
}
