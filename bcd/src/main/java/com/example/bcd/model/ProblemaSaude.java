package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "problemas_saude")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemaSaude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProblemaSaude;
    private String nomeProblema;
    private String descricao;
}