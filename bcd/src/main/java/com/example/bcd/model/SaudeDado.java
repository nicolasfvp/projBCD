package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "saude_dados")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaudeDado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSaudeDado;

    @ManyToOne
    @JoinColumn(name = "idPessoa")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "idProblemaSaude")
    private ProblemaSaude problemaSaude;
}