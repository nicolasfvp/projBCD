package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "desafio_concluido_feito")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class DesafioConcluidoFeito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDesafioConcluidoFeito;

    @ManyToOne
    @JoinColumn(name = "idDesafioConcluido")
    private DesafioConcluido desafioConcluido;

    @ManyToOne
    @JoinColumn(name = "idPessoa")
    private Pessoa pessoa;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}