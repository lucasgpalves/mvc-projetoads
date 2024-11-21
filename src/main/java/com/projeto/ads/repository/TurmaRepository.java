package com.projeto.ads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.projeto.ads.model.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long>{
    
    @Query("SELECT t FROM Turma t WHERE t.codTurma = :codTurma")
    public Turma findByCodTurma(String codTurma);

    @Query("SELECT t FROM Turma t ORDER BY t.id")
    public List<Turma> findAllOrderedById(); 

    public List<Turma> findAllByOrderByCodTurmaAsc();

    @Query("SELECT t FROM Turma t WHERE t.id= (SELECT MAX(t2.id) FROM Turma t2)")
    public Turma findLastInsertedTurma();
}
