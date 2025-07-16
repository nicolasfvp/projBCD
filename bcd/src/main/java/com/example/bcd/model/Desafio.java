package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "desafios")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Desafio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDesafio;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "idInsignia")
    private Insignia insignia;
}