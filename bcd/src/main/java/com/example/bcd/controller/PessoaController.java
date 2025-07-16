package com.example.bcd.controller;

import com.example.bcd.dto.EspecialidadeJovemDTO;
import com.example.bcd.dto.InsigniaJovemDTO;
import com.example.bcd.dto.PessoaDTO;
import com.example.bcd.dto.RequisitoJovemDTO;
import com.example.bcd.service.PessoaService;
import com.example.bcd.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private RelatorioService relatorioService;

    @PostMapping
    public ResponseEntity<PessoaDTO> criarPessoa(@RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO novaPessoa = pessoaService.criarPessoa(pessoaDTO);
        return new ResponseEntity<>(novaPessoa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizarPessoa(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO) {
        PessoaDTO pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoaDTO);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> getPessoaById(@PathVariable Long id) {
        return pessoaService.buscarPessoaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> getAllPessoas() {
        List<PessoaDTO> pessoas = pessoaService.listarTodasPessoas();
        return ResponseEntity.ok(pessoas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable Long id) {
        pessoaService.deletarPessoa(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idPessoa}/desafios-feitos/{idDesafio}")
    public ResponseEntity<Void> registrarDesafioFeito(
            @PathVariable Long idPessoa,
            @PathVariable Long idDesafio,
            @RequestParam LocalDate data) {
        pessoaService.registrarDesafioFeito(idPessoa, idDesafio, data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idPessoa}/desafios-especialidade-feitos/{idDesafio}/{idEspecialidade}")
    public ResponseEntity<Void> registrarDesafioDeEspecialidadeFeita(
            @PathVariable Long idPessoa,
            @PathVariable Long idDesafio,
            @PathVariable Long idEspecialidade,
            @RequestParam LocalDate data) {
        pessoaService.registrarDesafioDeEspecialidadeFeita(idPessoa, idDesafio, idEspecialidade, data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idPessoa}/noites-acampados/{idAcampamento}")
    public ResponseEntity<Void> registrarNoiteAcampado(
            @PathVariable Long idPessoa,
            @PathVariable Long idAcampamento) {
        pessoaService.registrarNoiteAcampado(idPessoa, idAcampamento);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idPessoa}/desafios-concluidos-feitos/{idDesafioConcluido}")
    public ResponseEntity<Void> registrarDesafioConcluidoFeito(
            @PathVariable Long idPessoa,
            @PathVariable Long idDesafioConcluido,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        pessoaService.registrarDesafioConcluidoFeito(idPessoa, idDesafioConcluido, dataInicio, dataFim);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idPessoa}/vinculos/{idResponsavel}")
    public ResponseEntity<Void> registrarVinculo(
            @PathVariable Long idPessoa,
            @PathVariable Long idResponsavel) {
        pessoaService.registrarVinculo(idPessoa, idResponsavel);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idPessoa}/saude-dados/{idProblemaSaude}")
    public ResponseEntity<Void> registrarSaudeDado(
            @PathVariable Long idPessoa,
            @PathVariable Long idProblemaSaude) {
        pessoaService.registrarSaudeDado(idPessoa, idProblemaSaude);
        return ResponseEntity.ok().build();
    }

    // --- Endpoints para Relatórios ---

    @GetMapping("/{idPessoa}/biografia")
    public ResponseEntity<PessoaDTO> getDadosBiograficosJovem(@PathVariable Long idPessoa) {
        PessoaDTO dados = relatorioService.getDadosBiograficosJovem(idPessoa);
        if (dados != null) {
            return ResponseEntity.ok(dados);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/por-especialidade")
    public ResponseEntity<List<PessoaDTO>> getJovensPorEspecialidade(@RequestParam String nomeEspecialidade) {
        List<PessoaDTO> jovens = relatorioService.getJovensPorEspecialidade(nomeEspecialidade);
        return ResponseEntity.ok(jovens);
    }

    @GetMapping("/{idPessoa}/especialidades-insignias")
    public ResponseEntity<?> getEspecialidadesEInsigniasDeJovem(@PathVariable Long idPessoa) {
        List<EspecialidadeJovemDTO> especialidades = relatorioService.getEspecialidadesDeJovem(idPessoa);
        List<InsigniaJovemDTO> insignias = relatorioService.getInsigniasDeJovem(idPessoa);

        return ResponseEntity.ok(
                java.util.Map.of("especialidades", especialidades, "insignias", insignias)
        );
    }

    @GetMapping("/{idPessoa}/especialidades/{idEspecialidade}/requisitos-cumpridos")
    public ResponseEntity<List<RequisitoJovemDTO>> getRequisitosCumpridosParaEspecialidade(
            @PathVariable Long idPessoa,
            @PathVariable Long idEspecialidade) {
        List<RequisitoJovemDTO> requisitos = relatorioService.getRequisitosCumpridosParaEspecialidade(idPessoa, idEspecialidade);
        return ResponseEntity.ok(requisitos);
    }

    @GetMapping("/aptos-cruzeiro-do-sul")
    public ResponseEntity<List<PessoaDTO>> getJovensAptosCruzeiroDoSul() {
        List<PessoaDTO> jovens = relatorioService.getJovensAptosCruzeiroDoSul();
        return ResponseEntity.ok(jovens);
    }
}
