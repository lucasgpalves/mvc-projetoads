package com.projeto.ads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.projeto.ads.model.Aluno;

public interface AlunoRepository extends CrudRepository<Aluno, Long>{
    
    public Aluno findByCpf(String cpf);

    @Query("SELECT a FROM Aluno a WHERE a.id=(SELECT Max(a2.id) FROM Aluno a2)")
    public Aluno findLastInsertedAluno();

    // public List<Aluno> findAllOrderedById();

}
