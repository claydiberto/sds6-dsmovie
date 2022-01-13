package br.com.devsuperior.dsmovie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.devsuperior.dsmovie.entities.Score;
import br.com.devsuperior.dsmovie.entities.ScorePK;

public interface ScoreRepository extends JpaRepository<Score, ScorePK> {

}
