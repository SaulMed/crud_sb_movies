package mx.developer.ApiRestSB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.developer.ApiRestSB.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

}
