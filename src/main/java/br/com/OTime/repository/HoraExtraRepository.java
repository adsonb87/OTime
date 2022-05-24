package br.com.OTime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.OTime.model.HoraExtra;

@Repository
public interface HoraExtraRepository extends JpaRepository<HoraExtra, Long> {
	
	@Query("select he from HoraExtra he join he.usuario u where u.chapa = :chapa")
	List<HoraExtra> findAllByUsuario(@Param("chapa") String chapa);
}
