package com.projeto.ads.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.ads.model.Turma;
import com.projeto.ads.model.TurmaAluno;

@Repository
public interface TurmaAlunoRepository extends JpaRepository<TurmaAluno, Long>{
    @Transactional
    public void deleteByTurma(Turma turma);
}
