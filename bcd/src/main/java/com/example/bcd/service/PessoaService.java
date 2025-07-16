package com.example.bcd.service;

import com.example.bcd.dto.PessoaDTO;
import com.example.bcd.model.*;
import com.example.bcd.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TipoSanguineoRepository tipoSanguineoRepository;

    @Autowired
    private DesafioFeitoRepository desafioFeitoRepository;

    @Autowired
    private DesafioRepository desafioRepository;

    @Autowired
    private DesafioDeEspecialidadeFeitaRepository desafioDeEspecialidadeFeitaRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private NoiteAcampadoRepository noiteAcampadoRepository;

    @Autowired
    private AcampamentoRepository acampamentoRepository;

    @Autowired
    private DesafioConcluidoFeitoRepository desafioConcluidoFeitoRepository;

    @Autowired
    private DesafioConcluidoRepository desafioConcluidoRepository;

    @Autowired
    private VinculoRepository vinculoRepository;

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Autowired
    private SaudeDadoRepository saudeDadoRepository;

    @Autowired
    private ProblemaSaudeRepository problemaSaudeRepository;

    @Transactional
    public PessoaDTO criarPessoa(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setTelefone(pessoaDTO.getTelefone());
        pessoa.setEmail(pessoaDTO.getEmail());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());

        if (pessoaDTO.getIdTipoSanguineo() != null) {
            TipoSanguineo tipoSanguineo = tipoSanguineoRepository.findById(pessoaDTO.getIdTipoSanguineo())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo Sanguíneo não encontrado"));
            pessoa.setTipoSanguineo(tipoSanguineo);
        }

        Pessoa savedPessoa = pessoaRepository.save(pessoa);
        pessoaDTO.setIdPessoa(savedPessoa.getIdPessoa());
        pessoaDTO.setTipoSanguineoNome(savedPessoa.getTipoSanguineo() != null ? savedPessoa.getTipoSanguineo().getTipo() : null);
        return pessoaDTO;
    }

    @Transactional
    public PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + id));

        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setTelefone(pessoaDTO.getTelefone());
        pessoa.setEmail(pessoaDTO.getEmail());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());

        if (pessoaDTO.getIdTipoSanguineo() != null) {
            TipoSanguineo tipoSanguineo = tipoSanguineoRepository.findById(pessoaDTO.getIdTipoSanguineo())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo Sanguíneo não encontrado"));
            pessoa.setTipoSanguineo(tipoSanguineo);
        } else {
            pessoa.setTipoSanguineo(null);
        }

        Pessoa updatedPessoa = pessoaRepository.save(pessoa);
        pessoaDTO.setIdPessoa(updatedPessoa.getIdPessoa());
        pessoaDTO.setTipoSanguineoNome(updatedPessoa.getTipoSanguineo() != null ? updatedPessoa.getTipoSanguineo().getTipo() : null);
        return pessoaDTO;
    }

    @Transactional(readOnly = true)
    public Optional<PessoaDTO> buscarPessoaPorId(Long id) {
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    PessoaDTO dto = new PessoaDTO();
                    dto.setIdPessoa(pessoa.getIdPessoa());
                    dto.setNome(pessoa.getNome());
                    dto.setTelefone(pessoa.getTelefone());
                    dto.setEmail(pessoa.getEmail());
                    dto.setDataNascimento(pessoa.getDataNascimento());
                    dto.setIdTipoSanguineo(pessoa.getTipoSanguineo() != null ? pessoa.getTipoSanguineo().getIdTipoSanguineo() : null);
                    dto.setTipoSanguineoNome(pessoa.getTipoSanguineo() != null ? pessoa.getTipoSanguineo().getTipo() : null);
                    return dto;
                });
    }

    @Transactional(readOnly = true)
    public List<PessoaDTO> listarTodasPessoas() {
        return pessoaRepository.findAll().stream()
                .map(pessoa -> {
                    PessoaDTO dto = new PessoaDTO();
                    dto.setIdPessoa(pessoa.getIdPessoa());
                    dto.setNome(pessoa.getNome());
                    dto.setTelefone(pessoa.getTelefone());
                    dto.setEmail(pessoa.getEmail());
                    dto.setDataNascimento(pessoa.getDataNascimento());
                    dto.setIdTipoSanguineo(pessoa.getTipoSanguineo() != null ? pessoa.getTipoSanguineo().getIdTipoSanguineo() : null);
                    dto.setTipoSanguineoNome(pessoa.getTipoSanguineo() != null ? pessoa.getTipoSanguineo().getTipo() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletarPessoa(Long id) {
        pessoaRepository.deleteById(id);
    }

    @Transactional
    public void registrarDesafioFeito(Long idPessoa, Long idDesafio, LocalDate data) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        Desafio desafio = desafioRepository.findById(idDesafio)
                .orElseThrow(() -> new IllegalArgumentException("Desafio não encontrado"));

        DesafioFeito desafioFeito = DesafioFeito.builder()
                .pessoa(pessoa)
                .desafio(desafio)
                .data(data)
                .build();
        desafioFeitoRepository.save(desafioFeito);
    }

    @Transactional
    public void registrarDesafioDeEspecialidadeFeita(Long idPessoa, Long idDesafio, Long idEspecialidade, LocalDate data) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        Desafio desafio = desafioRepository.findById(idDesafio)
                .orElseThrow(() -> new IllegalArgumentException("Desafio não encontrado"));
        Especialidade especialidade = especialidadeRepository.findById(idEspecialidade)
                .orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada"));

        DesafioDeEspecialidadeFeita desafioDeEspecialidadeFeita = DesafioDeEspecialidadeFeita.builder()
                .pessoa(pessoa)
                .desafio(desafio)
                .especialidade(especialidade)
                .data(data)
                .build();
        desafioDeEspecialidadeFeitaRepository.save(desafioDeEspecialidadeFeita);
    }

    @Transactional
    public void registrarNoiteAcampado(Long idPessoa, Long idAcampamento) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        Acampamento acampamento = acampamentoRepository.findById(idAcampamento)
                .orElseThrow(() -> new IllegalArgumentException("Acampamento não encontrado"));

        NoiteAcampado noiteAcampado = NoiteAcampado.builder()
                .pessoa(pessoa)
                .acampamento(acampamento)
                .build();
        noiteAcampadoRepository.save(noiteAcampado);
    }

    @Transactional
    public void registrarDesafioConcluidoFeito(Long idPessoa, Long idDesafioConcluido, LocalDate dataInicio, LocalDate dataFim) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        DesafioConcluido desafioConcluido = desafioConcluidoRepository.findById(idDesafioConcluido)
                .orElseThrow(() -> new IllegalArgumentException("Desafio Concluído não encontrado"));

        DesafioConcluidoFeito desafioConcluidoFeito = DesafioConcluidoFeito.builder()
                .pessoa(pessoa)
                .desafioConcluido(desafioConcluido)
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .build();
        desafioConcluidoFeitoRepository.save(desafioConcluidoFeito);
    }

    @Transactional
    public void registrarVinculo(Long idPessoa, Long idResponsavel) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        Responsavel responsavel = responsavelRepository.findById(idResponsavel)
                .orElseThrow(() -> new IllegalArgumentException("Responsável não encontrado"));

        Vinculo vinculo = Vinculo.builder()
                .pessoa(pessoa)
                .responsavel(responsavel)
                .build();
        vinculoRepository.save(vinculo);
    }

    @Transactional
    public void registrarSaudeDado(Long idPessoa, Long idProblemaSaude) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        ProblemaSaude problemaSaude = problemaSaudeRepository.findById(idProblemaSaude)
                .orElseThrow(() -> new IllegalArgumentException("Problema de Saúde não encontrado"));

        SaudeDado saudeDado = SaudeDado.builder()
                .pessoa(pessoa)
                .problemaSaude(problemaSaude)
                .build();
        saudeDadoRepository.save(saudeDado);
    }
}
