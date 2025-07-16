package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tipo_sanguineos")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoSanguineo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoSanguineo;
    private String tipo;
}