package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "desafio_concluidos")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class DesafioConcluido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDesafioConcluido;
    private String descricao;
}