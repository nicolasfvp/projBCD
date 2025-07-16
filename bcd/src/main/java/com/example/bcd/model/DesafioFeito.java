package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "desafio_feitos")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class DesafioFeito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDesafioFeito;

    @ManyToOne
    @JoinColumn(name = "idDesafio")
    private Desafio desafio;

    @ManyToOne
    @JoinColumn(name = "idPessoa")
    private Pessoa pessoa;
    private LocalDate data;
}