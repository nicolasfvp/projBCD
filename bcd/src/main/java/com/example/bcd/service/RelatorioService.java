package com.example.bcd.service;

import com.example.bcd.dto.EspecialidadeJovemDTO;
import com.example.bcd.dto.InsigniaJovemDTO;
import com.example.bcd.dto.PessoaDTO;
import com.example.bcd.dto.RequisitoJovemDTO;
import com.example.bcd.model.*;
import com.example.bcd.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    @Autowired
    private DesafioDeEspecialidadeFeitaRepository desafioDeEspecialidadeFeitaRepository;
    @Autowired
    private DesafioFeitoRepository desafioFeitoRepository;
    @Autowired
    private InsigniaRepository insigniaRepository;
    @Autowired
    private DesafioConcluidoRepository desafioConcluidoRepository;

    @Transactional(readOnly = true)
    public PessoaDTO getDadosBiograficosJovem(Long idPessoa) {
        return pessoaRepository.findById(idPessoa)
                .map(pessoa -> {
                    PessoaDTO dto = new PessoaDTO();
                    dto.setIdPessoa(pessoa.getIdPessoa());
                    dto.setNome(pessoa.getNome());
                    dto.setTelefone(pessoa.getTelefone());
                    dto.setEmail(pessoa.getEmail());
                    dto.setDataNascimento(pessoa.getDataNascimento());
                    dto.setTipoSanguineoNome(pessoa.getTipoSanguineo() != null ? pessoa.getTipoSanguineo().getTipo() : null);
                    return dto;
                })
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<PessoaDTO> getJovensPorEspecialidade(String nomeEspecialidade) {
        Optional<Especialidade> especialidadeOpt = especialidadeRepository.findByNome(nomeEspecialidade);

        if (especialidadeOpt.isEmpty()) {
            return List.of();
        }

        Especialidade especialidade = especialidadeOpt.get();

        return desafioDeEspecialidadeFeitaRepository.findByEspecialidade(especialidade).stream()
                .map(DesafioDeEspecialidadeFeita::getPessoa)
                .distinct()
                .map(pessoa -> {
                    PessoaDTO dto = new PessoaDTO();
                    dto.setIdPessoa(pessoa.getIdPessoa());
                    dto.setNome(pessoa.getNome());
                    dto.setTelefone(pessoa.getTelefone());
                    dto.setEmail(pessoa.getEmail());
                    dto.setDataNascimento(pessoa.getDataNascimento());
                    dto.setTipoSanguineoNome(pessoa.getTipoSanguineo() != null ? pessoa.getTipoSanguineo().getTipo() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EspecialidadeJovemDTO> getEspecialidadesDeJovem(Long idPessoa) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new IllegalArgumentException("Jovem não encontrado com ID: " + idPessoa));

        return pessoa.getDesafiosDeEspecialidadeFeitas().stream()
                .collect(Collectors.groupingBy(DesafioDeEspecialidadeFeita::getEspecialidade))
                .entrySet().stream()
                .map(entry -> {
                    Especialidade especialidade = entry.getKey();
                    LocalDate ultimaData = entry.getValue().stream()
                            .map(DesafioDeEspecialidadeFeita::getData)
                            .max(LocalDate::compareTo)
                            .orElse(null);

                    int nivel = 0; // Implementar lógica para calcular nível (1, 2, 3)

                    return EspecialidadeJovemDTO.builder()
                            .nomeEspecialidade(especialidade.getNome())
                            .areaConhecimento(especialidade.getAreaDeConhecimento() != null ? especialidade.getAreaDeConhecimento().getNome() : "N/A")
                            .dataCumprimento(ultimaData)
                            .nivelAtual(nivel)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InsigniaJovemDTO> getInsigniasDeJovem(Long idPessoa) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new IllegalArgumentException("Jovem não encontrado com ID: " + idPessoa));

        return pessoa.getDesafiosFeitos().stream()
                .filter(df -> df.getDesafio().getInsignia() != null)
                .collect(Collectors.groupingBy(df -> df.getDesafio().getInsignia()))
                .entrySet().stream()
                .map(entry -> {
                    Insignia insignia = entry.getKey();
                    LocalDate dataConquista = entry.getValue().stream()
                            .map(DesafioFeito::getData)
                            .max(LocalDate::compareTo)
                            .orElse(null);
                    return InsigniaJovemDTO.builder()
                            .nomeInsignia(insignia.getNome())
                            .dataConquista(dataConquista)
                            .build();
                })
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<RequisitoJovemDTO> getRequisitosCumpridosParaEspecialidade(Long idPessoa, Long idEspecialidade) {
        Pessoa pessoa = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new IllegalArgumentException("Jovem não encontrado com ID: " + idPessoa));
        Especialidade especialidade = especialidadeRepository.findById(idEspecialidade)
                .orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada com ID: " + idEspecialidade));

        return pessoa.getDesafiosDeEspecialidadeFeitas().stream()
                .filter(df -> df.getEspecialidade().equals(especialidade))
                .map(df -> RequisitoJovemDTO.builder()
                        .nomeDesafio(df.getDesafio().getNome())
                        .dataCumprimento(df.getData())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PessoaDTO> getJovensAptosCruzeiroDoSul() {
        return pessoaRepository.findAll().stream()
                .filter(this::verificaAptidaoCruzeiroDoSul)
                .map(pessoa -> {
                    PessoaDTO dto = new PessoaDTO();
                    dto.setIdPessoa(pessoa.getIdPessoa());
                    dto.setNome(pessoa.getNome());
                    dto.setTelefone(pessoa.getTelefone());
                    dto.setEmail(pessoa.getEmail());
                    dto.setDataNascimento(pessoa.getDataNascimento());
                    dto.setTipoSanguineoNome(pessoa.getTipoSanguineo() != null ? pessoa.getTipoSanguineo().getTipo() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private boolean verificaAptidaoCruzeiroDoSul(Pessoa jovem) {
        boolean possuiLoboCacador = jovem.getDesafiosConcluidosFeitos().stream()
                .anyMatch(dcf -> dcf.getDesafioConcluido().getDescricao().equals("Lobo Caçador"));
        if (!possuiLoboCacador) return false;

        long countInsignias = getInsigniasDeJovem(jovem.getIdPessoa()).size();
        if (countInsignias < 1) return false;

        List<EspecialidadeJovemDTO> especialidadesDoJovem = getEspecialidadesDeJovem(jovem.getIdPessoa());

        if (especialidadesDoJovem.size() < 5) return false;

        long countAreasDistintas = especialidadesDoJovem.stream()
                .map(EspecialidadeJovemDTO::getAreaConhecimento)
                .filter(nomeArea -> !nomeArea.equals("N/A"))
                .distinct()
                .count();
        if (countAreasDistintas < 3) return false;

        boolean recomendadoVelhoLobo = true;
        if (!recomendadoVelhoLobo) return false;


        return true;
    }

    // Você precisará adicionar um método no PessoaRepository para buscar especialidades pelo nome.
    // Exemplo: No PessoaRepository, adicione:
    // Optional<Especialidade> findByNome(String nome);
}