package com.example.bcd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pessoas")
@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPessoa;
    private String nome;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "idTipoSanguineo")
    private TipoSanguineo tipoSanguineo;

    @OneToMany(mappedBy = "pessoa")
    private List<Vinculo> vinculos;

    @OneToMany(mappedBy = "pessoa")
    private List<DesafioConcluidoFeito> desafiosConcluidosFeitos;

    @OneToMany(mappedBy = "pessoa")
    private List<SaudeDado> saudeDados;

    @OneToMany(mappedBy = "pessoa")
    private List<ProblemaSaude> problemasSaude;

    @OneToMany(mappedBy = "pessoa")
    private List<DesafioFeito> desafiosFeitos;

    @OneToMany(mappedBy = "pessoa")
    private List<DesafioDeEspecialidadeFeita> desafiosDeEspecialidadeFeitas;

    @OneToMany(mappedBy = "pessoa")
    private List<NoiteAcampado> noitesAcampados;
}