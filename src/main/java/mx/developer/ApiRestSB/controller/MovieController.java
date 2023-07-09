package mx.developer.ApiRestSB.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.developer.ApiRestSB.model.Movie;
import mx.developer.ApiRestSB.repository.MovieRepository;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class MovieController {

	private MovieRepository repo;
	
	public MovieController(MovieRepository repo) {
		this.repo = repo;
	}
	
	//Metodo de prueba h2 - cargar registros iniciales
	@GetMapping("/movies")
	public void createMovie() {
		Movie m1 = new Movie("Avatar",2023,"Adventure");
		Movie m2 = new Movie("Simpson", 2000, "Comedy");
		Movie m3 = new Movie("Im leyend", 2012,"Action");
		
		repo.save(m1);
		repo.save(m2);
		repo.save(m3);
	}
	
	@GetMapping("/Getmovies")
	public List<Movie> getMovies(){
		return repo.findAll();
	}

	@GetMapping("/movies/{id}")
	public ResponseEntity<Movie> getOneMovie(@PathVariable Long id){
		Optional<Movie> opt = repo.findById(id);
		
		if(opt.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(opt.get());
		}
	}
	
	@PostMapping("/movies")
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
		if(movie.getId() != null) {
			return ResponseEntity.badRequest().build();
		}else {
			repo.save(movie);
			return ResponseEntity.ok(movie);
		}
	}
	
	@PutMapping("/movies/{id}")
	public ResponseEntity<Movie> editMovie(@PathVariable Long id, @RequestBody Movie movie){
		Optional<Movie> opt = repo.findById(id);
		
		if(!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}else {
			Movie newMovie = opt.get();
			newMovie.setTitle(movie.getTitle());
			newMovie.setGender(movie.getGender());
			newMovie.setReleaseYear(movie.getReleaseYear());
			repo.save(newMovie);
			return ResponseEntity.ok(newMovie);
		}
	}
	
	@DeleteMapping("/movies/{id}")
	public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
		Optional<Movie> opt = repo.findById(id);
		if(!opt.isPresent()) {
			return ResponseEntity.notFound().build();
		}else {			
			repo.deleteById(id);
			return ResponseEntity.ok("Movie deleted");
		}
	}
	
}
