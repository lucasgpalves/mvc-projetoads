package com.projeto.ads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.ads.model.Aluno;
import com.projeto.ads.repository.AlunoRepository;
import com.projeto.ads.service.ServiceAluno;

@Controller
public class AlunoController {
    
    @Autowired
    private ServiceAluno serviceAluno;

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping("/aluno/inserir")
    public ModelAndView insertAluno() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("aluno", new Aluno());
        mv.setViewName("Aluno/inserirAluno");
        return mv;
    }

    @PostMapping("/aluno/inserir")
    public ModelAndView insertAlunoPost(@ModelAttribute Aluno aluno) {
        ModelAndView mv = new ModelAndView();

        String a = serviceAluno.verificarAluno(aluno.getCpf());
        if(a != null) {
            mv.addObject("aluno", new Aluno());
            mv.setViewName("Aluno/inserirAluno");
            mv.addObject("msg", a);
            return mv;
        } else {
            aluno.setMatricula(serviceAluno.gerarMatricula());
            alunoRepository.save(aluno);
            mv.addObject("alunos", alunoRepository.findAll());
            mv.setViewName("redirect:/aluno/listar");
        }

        return mv;
    }

    @GetMapping("/aluno/listar")
    public ModelAndView insertAlunoGET() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("alunos", alunoRepository.findAll());
        mv.setViewName("Aluno/listarAlunos");
        return mv;
    }

    @GetMapping("/aluno/alterar/{id}")
    public ModelAndView alterarAluno(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        Aluno aluno = alunoRepository.findById(id).get();
        mv.addObject("aluno", aluno);
        mv.setViewName("Aluno/alterarAluno");
        return mv;
    }

    @GetMapping("/aluno/alterar")
    public ModelAndView alterarAluno() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("alunos", alunoRepository.findAll());
        mv.setViewName("Aluno/listarAlunos");
        return mv;
    }

    @PostMapping("/aluno/alterar") 
    public ModelAndView alterarAlunoPOST(Aluno aluno) {
        ModelAndView mv = new ModelAndView();
        System.out.println("ALUNO ALTERAR = " + aluno);
        String out = serviceAluno.alterarAluno(aluno, aluno.getId());
        if (out != null) {
            mv.addObject("aluno", aluno);
            mv.setViewName("Aluno/alterarAluno");
            mv.addObject("msg", out);
        } else {
            mv.setViewName("redirect:/aluno/listar");
        }
        return mv;
    }

    @GetMapping("/aluno/excluir/{id}")
    public String excluirAluno(@PathVariable("id") Long id) {
        alunoRepository.deleteById(id);
        return "redirect:/aluno/listar";
    }

    
    @GetMapping("/aluno/deletar")
    public ModelAndView deletarAluno() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("alunos", alunoRepository.findAll());
        mv.setViewName("Aluno/listarAlunos");
        return mv;
    }

    
    
    
}
