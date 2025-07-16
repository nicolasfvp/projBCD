package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "especialidades")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Especialidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecialidade;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "idAreaDeConhecimento")
    private AreaDeConhecimento areaDeConhecimento;
}