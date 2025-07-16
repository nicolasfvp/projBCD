package com.example.bcd.controller.web;

import com.example.bcd.dto.EspecialidadeJovemDTO;
import com.example.bcd.dto.InsigniaJovemDTO;
import com.example.bcd.dto.PessoaDTO;
import com.example.bcd.dto.RequisitoJovemDTO;
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


    @GetMapping("/pessoas/nova")
    public String showAddPessoaForm(Model model) {
        model.addAttribute("pessoa", new PessoaDTO());
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
