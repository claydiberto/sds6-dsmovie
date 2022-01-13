package br.com.devsuperior.dsmovie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.devsuperior.dsmovie.dto.MovieDTO;
import br.com.devsuperior.dsmovie.dto.ScoreDTO;
import br.com.devsuperior.dsmovie.entities.Movie;
import br.com.devsuperior.dsmovie.entities.Score;
import br.com.devsuperior.dsmovie.entities.User;
import br.com.devsuperior.dsmovie.repositories.MovieRepository;
import br.com.devsuperior.dsmovie.repositories.ScoreRepository;
import br.com.devsuperior.dsmovie.repositories.UserRepository;

@Service
public class ScoreService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ScoreRepository scoreRepository;
	
	@Transactional
	public MovieDTO saveScore(ScoreDTO dto) {
		
		User user = userRepository.findByEmail(dto.getEmail());
		if (user == null) {
			user = new User();
			user.setEmail(dto.getEmail());
			user = userRepository.saveAndFlush(user);
		}
		
		Movie movie = movieRepository.findById(dto.getMovieId()).get();
		
		Score score = new Score();
		score.setMovie(movie);
		score.setUser(user);
		score.setValue(dto.getScore());
		
		score = scoreRepository.saveAndFlush(score);
		
		double sum = 0.0;
		
		for (Score s : movie.getScores()) {
			sum += s.getValue();
		}
		
		int countScores = movie.getScores().size();
		double avg = sum / countScores;
		
		movie.setScore(avg);
		movie.setCount(countScores);
		
		movie = movieRepository.save(movie);
		
		return new MovieDTO(movie);		
	}
	
}
