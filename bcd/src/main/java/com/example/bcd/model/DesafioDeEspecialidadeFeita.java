package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "desafio_de_especialidade_feita")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class DesafioDeEspecialidadeFeita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDesafioDeEspecialidadeFeita;

    @ManyToOne
    @JoinColumn(name = "idDesafio")
    private Desafio desafio;

    @ManyToOne
    @JoinColumn(name = "idPessoa")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "idEspecialidade")
    private Especialidade especialidade;
    private LocalDate data;
}