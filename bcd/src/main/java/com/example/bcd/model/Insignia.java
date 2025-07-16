package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "insignias")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Insignia {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInsignia;
    private String nome;
}