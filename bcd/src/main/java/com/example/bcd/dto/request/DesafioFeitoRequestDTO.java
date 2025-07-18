package com.example.bcd.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DesafioFeitoRequestDTO {
    private Long idDesafio;
    private LocalDate data;
}
