package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "vinculos")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vinculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVinculo;

    @ManyToOne
    @JoinColumn(name = "idPessoa")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "idResponsavel")
    private Responsavel responsavel;
}