package br.com.OTime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.OTime.model.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long>{

}
