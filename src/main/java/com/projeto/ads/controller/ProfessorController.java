package com.projeto.ads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.ads.model.Professor;
import com.projeto.ads.repository.ProfessorRepository;
import com.projeto.ads.service.ServiceProfessor;

@Controller
public class ProfessorController {
    
    @Autowired
    ServiceProfessor serviceProfessor;

    @Autowired
    ProfessorRepository professorRepository;

    @GetMapping("/professor/inserir")
    public ModelAndView insertProfessorGet() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("professor", new Professor());
        mv.setViewName("Professor/inserirProfessor");
        return mv;
    }

    @PostMapping("/professor/inserir") 
    public ModelAndView insertProfessorPost(@ModelAttribute Professor professor) {
        ModelAndView mv = new ModelAndView();

        String out = serviceProfessor.validaProfessor(professor.getEmail(), professor.getCpf());

        if(out != null) {
            mv.addObject("professor", new Professor());
            mv.addObject("msg", out);
            mv.setViewName("Professor/inserirProfessor");
            return mv; 
        }
        professor.setMatricula(serviceProfessor.gerarMatricula());
        professorRepository.save(professor);
        mv.setViewName("redirect:/professor/listar");

        return mv;
    }

    @GetMapping("/professor/listar")
    public ModelAndView insertAlunoGET() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("professores", professorRepository.findAllOrderedById());
        mv.setViewName("Professor/listarProfessores");
        return mv;
    }

    @GetMapping("/professor/alterar/{id}")
    public ModelAndView alterarProfessorGet(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        Professor prof = professorRepository.findById(id).get();
        mv.addObject("professor", prof);
        mv.setViewName("Professor/alterar");
        return mv;
    }

    @PostMapping("/professor/alterar") 
    public ModelAndView alterarProfessorPost(@ModelAttribute Professor professor) {
        ModelAndView mv = new ModelAndView();

        String out = serviceProfessor.alterarProfessor(professor);

        if(out != null) {
            mv.addObject("professor", new Professor());
            mv.addObject("msg", out);
            mv.setViewName("Professor/alterar");
            return mv; 
        }
        professorRepository.save(professor);
        mv.setViewName("redirect:/professor/listar");

        return mv;
    }

    @GetMapping("/professor/alterar")
    public ModelAndView alterarProfessor() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Professor/listarProfessores");
        mv.addObject("professores", professorRepository.findAllOrderedById());
        return mv;
    }

    @GetMapping("/professor/deletar")
    public ModelAndView deletarProfessor() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Professor/listarProfessores");
        mv.addObject("professores", professorRepository.findAllOrderedById());
        return mv;
    }

    @GetMapping("/professor/excluir/{id}")
    public String excluir(@PathVariable("id") Long id) {
        professorRepository.deleteById(id);
        return "redirect:/professor/listar";
    }
}
