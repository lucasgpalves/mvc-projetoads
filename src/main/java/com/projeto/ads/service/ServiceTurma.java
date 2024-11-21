package com.projeto.ads.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.ads.model.Turma;
import com.projeto.ads.repository.TurmaRepository;

@Service
public class ServiceTurma {
    
    @Autowired
    TurmaRepository turmaRepository;

    public String gerarCodigo(String curso, String periodo, String turno) {
        Date data = new Date();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        int ano = calendario.get(Calendar.YEAR);

        Turma aux = turmaRepository.findLastInsertedTurma();

        if(aux == null) {
            return curso+"-"+periodo+"-"+turno+"-"+ano+"1";
        } else {
            String out = curso+"-"+periodo+"-"+turno+"-"+ano+"";
            return out + (aux.getId()+1);
        }
    }
}
