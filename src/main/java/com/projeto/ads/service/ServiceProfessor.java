package com.projeto.ads.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.ads.model.Professor;
import com.projeto.ads.repository.ProfessorRepository;

@Service
public class ServiceProfessor {
    
    @Autowired
    private ProfessorRepository professorRepository;

    public String validaProfessor(String email, String cpf) {
        String out = null;
        Professor aux = professorRepository.findByCpf(cpf);
        Professor aux2 = professorRepository.findByEmail(email);
        if (aux != null && aux.getCpf().equals(cpf)) {
            out = "Já existe professor com esse cpf!!";
        }

        if (aux != null && aux.getEmail().equals(email)) {
            if (out != null) {
                out = out + ", Já existe professor com esse email!!";
            } else {
                out = " Já existe professor com esse email";
            }

            return out;
        }

        if (aux2 != null && aux2.getEmail().equals(email)) {
            out = "Já existe professor com esse email!!";
        } 

        return out;
    }

    public String gerarMatricula() {
        Date data = new Date();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        int ano = calendario.get(Calendar.YEAR);
        Professor professor = professorRepository.findLastInsertedProfessor();

        if (professor == null) {
            return ano + "1";
        } else {
            String  out = ano + "";
            return out+(professor.getId()+1);
        }
    }
}
