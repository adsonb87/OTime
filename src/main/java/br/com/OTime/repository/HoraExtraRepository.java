package br.com.OTime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.OTime.model.HoraExtra;

@Repository
public interface HoraExtraRepository extends JpaRepository<HoraExtra, Long> {

}
