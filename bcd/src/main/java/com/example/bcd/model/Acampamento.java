package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "acampamentos")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Acampamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAcampamento;
    private String nome;
    private LocalDate data;
}