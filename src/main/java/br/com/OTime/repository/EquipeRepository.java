package br.com.OTime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.OTime.model.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long>{

	@Query("select e from Equipe e join e.usuarios u where e.id = :id")
	List<Equipe> findAllByEquipe(@Param("id") Long id);
	
	
	
}
