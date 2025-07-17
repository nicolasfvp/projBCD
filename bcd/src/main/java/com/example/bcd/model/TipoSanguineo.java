package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tipos_sanguineos")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoSanguineo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoSanguineo;
    private String tipo;

    @OneToMany
    private List<Pessoa> Pessoa;
}