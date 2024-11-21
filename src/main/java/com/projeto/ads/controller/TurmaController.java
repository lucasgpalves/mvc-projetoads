package com.projeto.ads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.ads.model.Turma;
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


}
