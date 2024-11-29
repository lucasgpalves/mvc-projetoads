package com.projeto.ads.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "turma_aluno")
@SequenceGenerator(name = "seq_turma_aluno", sequenceName = "seq_turma_aluno", allocationSize = 1, initialValue = 1)
public class TurmaAluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_turma_aluno")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "turma_id")
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

      // Construtor sem argumentos
      public TurmaAluno() {
    }

    // Construtor com argumentos
    public TurmaAluno(Long id, Turma turma, Aluno aluno) {
        this.id = id;
        this.turma = turma;
        this.aluno = aluno;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    // hashCode e equals
    @Override
    public int hashCode() {
        return Objects.hash(id, turma, aluno);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TurmaAluno that = (TurmaAluno) obj;
        return Objects.equals(id, that.id) &&
               Objects.equals(turma, that.turma) &&
               Objects.equals(aluno, that.aluno);
    }

}
