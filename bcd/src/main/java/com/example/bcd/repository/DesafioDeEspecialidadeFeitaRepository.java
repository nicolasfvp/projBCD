package com.example.bcd.repository;

import com.example.bcd.model.DesafioDeEspecialidadeFeita;
import com.example.bcd.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DesafioDeEspecialidadeFeitaRepository extends JpaRepository<DesafioDeEspecialidadeFeita, Long> {
    List<DesafioDeEspecialidadeFeita> findByEspecialidade(Especialidade especialidade);
}