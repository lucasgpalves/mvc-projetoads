package com.projeto.ads.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.ads.model.Aluno;
import com.projeto.ads.model.Turma;
import com.projeto.ads.repository.AlunoRepository;
import com.projeto.ads.repository.ProfessorRepository;
import com.projeto.ads.repository.TurmaRepository;
import com.projeto.ads.service.ServiceTurma;

@Controller
public class TurmaController {
    
    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    ServiceTurma serviceTurma;

    @Autowired
    AlunoRepository alunoRepository;

    @GetMapping("/turma/inserir")
    public ModelAndView inserirTurmaGet() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("professores", professorRepository.findAllOrderedById());
        mv.addObject("turma", new Turma());
        mv.setViewName("Turma/inserirTurma");
        return mv;
    }

    @PostMapping("/turma/inserir") 
    public ModelAndView inserirTurmaPost(@ModelAttribute Turma turma) {
        ModelAndView mv = new ModelAndView();
        turma.setCodTurma(serviceTurma.gerarCodigo(turma.getCurso().toString().toLowerCase(), 
                                                turma.getPeriodo().toString().toLowerCase(), 
                                                turma.getTurno().toLowerCase()));
        turmaRepository.save(turma);
        mv.setViewName("redirect:/turma/listar");
        return mv;
    }

    @GetMapping("/turma/listar")
    public ModelAndView listarTurmas() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("turmas", turmaRepository.findAllOrderedById());
        mv.setViewName("Turma/listarTurmas");
        return mv;
    }

    @GetMapping("/turma/inserirAlunosTurma")
    public ModelAndView carregarFormulario() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("turmas", turmaRepository.findAllByOrderByCodTurmaAsc());
        mv.addObject("alunos", List.of());
        mv.addObject("turma", new Turma());
        mv.setViewName("Turma/inserirAlunosTurma");
        return mv;
    }

    @GetMapping("/turma/buscarAlunos/{id}")
    @ResponseBody
    public List<Aluno> buscarAlunos(@PathVariable("id") Long turmaId) {
        System.out.println("METODO BUSCARALUNOS");
        List<Aluno> alunos = turmaRepository.findById(turmaId)
                .map(turma -> alunoRepository.buscarPorCursoETurno(turma.getCurso(), turma.getTurno()))
                .orElse(List.of());
    
        System.out.println(alunos);
        return alunos;
    }
    
    @PostMapping("/turma/inserirAlunosTurma")
    public ModelAndView inserirAlunosNaTurma(@ModelAttribute Turma turma, @RequestParam List<Long> alunosSelecionados) {
        ModelAndView mv = new ModelAndView();
        Turma auxTurma = turmaRepository.findById(turma.getId()).get();
        serviceTurma.salvarTurmasComAlunos(auxTurma, alunosSelecionados);
        mv.setViewName("redirect:/turma/listar");
        return mv;
    }

}
