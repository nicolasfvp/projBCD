package com.example.bcd.controller.web;

import com.example.bcd.dto.EspecialidadeJovemDTO;
import com.example.bcd.dto.InsigniaJovemDTO;
import com.example.bcd.dto.PessoaDTO;
import com.example.bcd.dto.RequisitoJovemDTO;
import com.example.bcd.dto.request.*; // Importe todos os DTOs de request
import com.example.bcd.model.*; // Importar modelos para dropdowns
import com.example.bcd.service.PessoaService;
import com.example.bcd.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/")
public class WebController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    public String index() {
        return "index";
    }

    // --- Gerenciamento de Pessoas ---

    @GetMapping("/pessoas/nova")
    public String showAddPessoaForm(Model model) {
        model.addAttribute("pessoa", new PessoaDTO());
        model.addAttribute("tiposSanguineos", pessoaService.listarTiposSanguineos());
        return "pessoas/form";
    }

    @PostMapping("/pessoas/salvar")
    public String savePessoa(@ModelAttribute("pessoa") PessoaDTO pessoaDTO, RedirectAttributes redirectAttributes) {
        try {
            if (pessoaDTO.getIdPessoa() == null) {
                pessoaService.criarPessoa(pessoaDTO);
                redirectAttributes.addFlashAttribute("message", "Pessoa adicionada com sucesso!");
            } else {
                pessoaService.atualizarPessoa(pessoaDTO.getIdPessoa(), pessoaDTO);
                redirectAttributes.addFlashAttribute("message", "Pessoa atualizada com sucesso!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao salvar pessoa: " + e.getMessage());
            return "redirect:/pessoas/nova";
        }
        return "redirect:/pessoas/lista";
    }

    @GetMapping("/pessoas/lista")
    public String listPessoas(Model model) {
        List<PessoaDTO> pessoas = pessoaService.listarTodasPessoas();
        model.addAttribute("pessoas", pessoas);
        return "pessoas/list";
    }

    @GetMapping("/pessoas/editar/{id}")
    public String showEditPessoaForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return pessoaService.buscarPessoaPorId(id)
                .map(pessoaDTO -> {
                    model.addAttribute("pessoa", pessoaDTO);
                    model.addAttribute("tiposSanguineos", pessoaService.listarTiposSanguineos());
                    return "pessoas/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Pessoa não encontrada!");
                    return "redirect:/pessoas/lista";
                });
    }

    @GetMapping("/pessoas/deletar/{id}")
    public String deletePessoa(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            pessoaService.deletarPessoa(id);
            redirectAttributes.addFlashAttribute("message", "Pessoa deletada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao deletar pessoa: " + e.getMessage());
        }
        return "redirect:/pessoas/lista";
    }

    // --- Detalhes do Jovem e Adição de Informações Vinculadas ---

    @GetMapping("/pessoas/detalhes/{idPessoa}")
    public String showPessoaDetails(@PathVariable Long idPessoa, Model model, RedirectAttributes redirectAttributes) {
        return pessoaService.buscarPessoaPorId(idPessoa)
                .map(pessoaDTO -> {
                    model.addAttribute("pessoa", pessoaDTO);
                    return "pessoas/detalhes"; // Novo template para detalhes do jovem
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Pessoa não encontrada!");
                    return "redirect:/pessoas/lista";
                });
    }

    // --- Formulários de Adição de Dados Vinculados ---

    @GetMapping("/pessoas/{idPessoa}/add-vinculo")
    public String showAddVinculoForm(@PathVariable Long idPessoa, Model model, RedirectAttributes redirectAttributes) {
        return pessoaService.buscarPessoaPorId(idPessoa)
                .map(pessoaDTO -> {
                    model.addAttribute("pessoa", pessoaDTO);
                    model.addAttribute("vinculoRequest", new VinculoRequestDTO());
                    model.addAttribute("responsaveis", pessoaService.listarResponsaveis());
                    return "pessoas/add-vinculo";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Pessoa não encontrada!");
                    return "redirect:/pessoas/lista";
                });
    }

    @PostMapping("/pessoas/{idPessoa}/salvar-vinculo")
    public String saveVinculo(@PathVariable Long idPessoa, @ModelAttribute VinculoRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        try {
            pessoaService.registrarVinculo(idPessoa, requestDTO);
            redirectAttributes.addFlashAttribute("message", "Vínculo adicionado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao adicionar vínculo: " + e.getMessage());
        }
        return "redirect:/pessoas/detalhes/" + idPessoa;
    }

    @GetMapping("/pessoas/{idPessoa}/add-saude-dado")
    public String showAddSaudeDadoForm(@PathVariable Long idPessoa, Model model, RedirectAttributes redirectAttributes) {
        return pessoaService.buscarPessoaPorId(idPessoa)
                .map(pessoaDTO -> {
                    model.addAttribute("pessoa", pessoaDTO);
                    model.addAttribute("saudeDadoRequest", new SaudeDadoRequestDTO());
                    model.addAttribute("problemasSaude", pessoaService.listarProblemasSaude());
                    return "pessoas/add-saude-dado";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Pessoa não encontrada!");
                    return "redirect:/pessoas/lista";
                });
    }

    @PostMapping("/pessoas/{idPessoa}/salvar-saude-dado")
    public String saveSaudeDado(@PathVariable Long idPessoa, @ModelAttribute SaudeDadoRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        try {
            pessoaService.registrarSaudeDado(idPessoa, requestDTO);
            redirectAttributes.addFlashAttribute("message", "Dado de saúde adicionado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao adicionar dado de saúde: " + e.getMessage());
        }
        return "redirect:/pessoas/detalhes/" + idPessoa;
    }

    @GetMapping("/pessoas/{idPessoa}/add-desafio-feito")
    public String showAddDesafioFeitoForm(@PathVariable Long idPessoa, Model model, RedirectAttributes redirectAttributes) {
        return pessoaService.buscarPessoaPorId(idPessoa)
                .map(pessoaDTO -> {
                    model.addAttribute("pessoa", pessoaDTO);
                    model.addAttribute("desafioFeitoRequest", new DesafioFeitoRequestDTO());
                    model.addAttribute("desafios", pessoaService.listarDesafios());
                    return "pessoas/add-desafio-feito";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Pessoa não encontrada!");
                    return "redirect:/pessoas/lista";
                });
    }

    @PostMapping("/pessoas/{idPessoa}/salvar-desafio-feito")
    public String saveDesafioFeito(@PathVariable Long idPessoa, @ModelAttribute DesafioFeitoRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        try {
            pessoaService.registrarDesafioFeito(idPessoa, requestDTO);
            redirectAttributes.addFlashAttribute("message", "Desafio feito registrado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao registrar desafio feito: " + e.getMessage());
        }
        return "redirect:/pessoas/detalhes/" + idPessoa;
    }

    @GetMapping("/pessoas/{idPessoa}/add-desafio-especialidade-feita")
    public String showAddDesafioEspecialidadeFeitaForm(@PathVariable Long idPessoa, Model model, RedirectAttributes redirectAttributes) {
        return pessoaService.buscarPessoaPorId(idPessoa)
                .map(pessoaDTO -> {
                    model.addAttribute("pessoa", pessoaDTO);
                    model.addAttribute("desafioEspecialidadeFeitaRequest", new DesafioDeEspecialidadeFeitaRequestDTO());
                    model.addAttribute("desafios", pessoaService.listarDesafios());
                    model.addAttribute("especialidades", pessoaService.listarEspecialidades());
                    return "pessoas/add-desafio-especialidade-feita";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Pessoa não encontrada!");
                    return "redirect:/pessoas/lista";
                });
    }

    @PostMapping("/pessoas/{idPessoa}/salvar-desafio-especialidade-feita")
    public String saveDesafioEspecialidadeFeita(@PathVariable Long idPessoa, @ModelAttribute DesafioDeEspecialidadeFeitaRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        try {
            pessoaService.registrarDesafioDeEspecialidadeFeita(idPessoa, requestDTO);
            redirectAttributes.addFlashAttribute("message", "Desafio de especialidade feito registrado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao registrar desafio de especialidade feito: " + e.getMessage());
        }
        return "redirect:/pessoas/detalhes/" + idPessoa;
    }

    @GetMapping("/pessoas/{idPessoa}/add-noite-acampado")
    public String showAddNoiteAcampadoForm(@PathVariable Long idPessoa, Model model, RedirectAttributes redirectAttributes) {
        return pessoaService.buscarPessoaPorId(idPessoa)
                .map(pessoaDTO -> {
                    model.addAttribute("pessoa", pessoaDTO);
                    model.addAttribute("noiteAcampadoRequest", new NoiteAcampadoRequestDTO());
                    model.addAttribute("acampamentos", pessoaService.listarAcampamentos());
                    return "pessoas/add-noite-acampado";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Pessoa não encontrada!");
                    return "redirect:/pessoas/lista";
                });
    }

    @PostMapping("/pessoas/{idPessoa}/salvar-noite-acampado")
    public String saveNoiteAcampado(@PathVariable Long idPessoa, @ModelAttribute NoiteAcampadoRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        try {
            pessoaService.registrarNoiteAcampado(idPessoa, requestDTO);
            redirectAttributes.addFlashAttribute("message", "Noite acampado registrada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao registrar noite acampado: " + e.getMessage());
        }
        return "redirect:/pessoas/detalhes/" + idPessoa;
    }

    @GetMapping("/pessoas/{idPessoa}/add-desafio-concluido-feito")
    public String showAddDesafioConcluidoFeitoForm(@PathVariable Long idPessoa, Model model, RedirectAttributes redirectAttributes) {
        return pessoaService.buscarPessoaPorId(idPessoa)
                .map(pessoaDTO -> {
                    model.addAttribute("pessoa", pessoaDTO);
                    model.addAttribute("desafioConcluidoFeitoRequest", new DesafioConcluidoFeitoRequestDTO());
                    model.addAttribute("desafiosConcluidos", pessoaService.listarDesafiosConcluidos());
                    return "pessoas/add-desafio-concluido-feito";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Pessoa não encontrada!");
                    return "redirect:/pessoas/lista";
                });
    }

    @PostMapping("/pessoas/{idPessoa}/salvar-desafio-concluido-feito")
    public String saveDesafioConcluidoFeito(@PathVariable Long idPessoa, @ModelAttribute DesafioConcluidoFeitoRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        try {
            pessoaService.registrarDesafioConcluidoFeito(idPessoa, requestDTO);
            redirectAttributes.addFlashAttribute("message", "Desafio concluído feito registrado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao registrar desafio concluído feito: " + e.getMessage());
        }
        return "redirect:/pessoas/detalhes/" + idPessoa;
    }


    // --- Relatórios ---

    @GetMapping("/relatorios")
    public String showReportsMenu() {
        return "reports/main";
    }

    @GetMapping("/relatorios/biograficos")
    public String reportBiograficos(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            PessoaDTO pessoa = relatorioService.getDadosBiograficosJovem(id);
            model.addAttribute("pessoa", pessoa);
            if (pessoa == null) {
                model.addAttribute("error", "Pessoa não encontrada com o ID: " + id);
            }
        }
        return "reports/biograficos";
    }

    @GetMapping("/relatorios/por-especialidade")
    public String reportJovensPorEspecialidade(@RequestParam(required = false) String nomeEspecialidade, Model model) {
        if (nomeEspecialidade != null && !nomeEspecialidade.isEmpty()) {
            List<PessoaDTO> jovens = relatorioService.getJovensPorEspecialidade(nomeEspecialidade);
            model.addAttribute("jovens", jovens);
            if (jovens.isEmpty()) {
                model.addAttribute("message", "Nenhum jovem encontrado para a especialidade '" + nomeEspecialidade + "' ou especialidade não existe.");
            }
        }
        return "reports/por-especialidade";
    }

    @GetMapping("/relatorios/especialidades-insignias")
    public String reportEspecialidadesInsignias(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            try {
                List<EspecialidadeJovemDTO> especialidades = relatorioService.getEspecialidadesDeJovem(id);
                List<InsigniaJovemDTO> insignias = relatorioService.getInsigniasDeJovem(id);
                model.addAttribute("especialidades", especialidades);
                model.addAttribute("insignias", insignias);
            } catch (IllegalArgumentException e) {
                model.addAttribute("error", e.getMessage());
            }
        }
        return "reports/especialidades-insignias";
    }

    @GetMapping("/relatorios/requisitos-cumpridos")
    public String reportRequisitosCumpridos(@RequestParam(required = false) Long idPessoa,
                                            @RequestParam(required = false) Long idEspecialidade,
                                            Model model) {
        if (idPessoa != null && idEspecialidade != null) {
            try {
                List<RequisitoJovemDTO> requisitos = relatorioService.getRequisitosCumpridosParaEspecialidade(idPessoa, idEspecialidade);
                model.addAttribute("requisitos", requisitos);
            } catch (IllegalArgumentException e) {
                model.addAttribute("error", e.getMessage());
            }
        }
        return "reports/requisitos-cumpridos";
    }

    @GetMapping("/relatorios/cruzeiro-do-sul")
    public String reportCruzeiroDoSul(Model model) {
        List<PessoaDTO> jovens = relatorioService.getJovensAptosCruzeiroDoSul();
        model.addAttribute("jovens", jovens);
        if (jovens.isEmpty()) {
            model.addAttribute("message", "Nenhum jovem encontrado apto para o Cruzeiro do Sul.");
        }
        return "reports/cruzeiro-do-sul";
    }
}
