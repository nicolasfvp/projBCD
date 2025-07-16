package com.example.bcd.repository;

import com.example.bcd.model.Especialidade;
import com.example.bcd.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Especialidade> findByNome(String nome);
}
