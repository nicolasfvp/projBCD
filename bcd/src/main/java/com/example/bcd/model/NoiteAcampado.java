package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "noites_acampados")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoiteAcampado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNoiteAcampado;

    @ManyToOne
    @JoinColumn(name = "idAcampamento")
    private Acampamento acampamento;

    @ManyToOne
    @JoinColumn(name = "idPessoa")
    private Pessoa pessoa;
}