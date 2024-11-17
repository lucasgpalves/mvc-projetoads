package com.projeto.ads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.projeto.ads.model.Professor;

public interface ProfessorRepository extends CrudRepository<Professor, Long>{

    @Query("SELECT p FROM Professor p WHERE p.id = (SELECT MAX(p2.id) FROM Professor p2)")
    public Professor findLastInsertedProfessor();

    public Professor findByCpf(String cpf);

    public Professor findByEmail(String email);

    @Query("SELECT p FROM Professor p ORDER BY p.id")
    public List<Professor> findAllOrderedById();
    
}
