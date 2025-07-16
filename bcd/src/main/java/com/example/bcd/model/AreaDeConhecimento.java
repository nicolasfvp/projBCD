package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "areas_de_conhecimento")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaDeConhecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAreaDeConhecimento;
    private String nome;
}