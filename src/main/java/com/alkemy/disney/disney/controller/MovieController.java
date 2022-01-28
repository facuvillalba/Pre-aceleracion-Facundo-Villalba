package com.alkemy.disney.disney.controller;

import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.dto.basic.MovieBasicDTO;
import com.alkemy.disney.disney.service.MovieService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    //Controller to create movie.
    @PostMapping
    public ResponseEntity<MovieDTO> save(@RequestBody MovieDTO movie) {
        MovieDTO movieSalved = this.movieService.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieSalved);
    }

    //Controller to search movie by id (BasicDTO).
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getById(@PathVariable Long id) {
        MovieDTO movie = this.movieService.getById(id);
        return ResponseEntity.ok(movie);
    }

    //Controller to search movie by filters (List BasicDTO).
    @GetMapping
    public ResponseEntity<List<MovieBasicDTO>> getDetailsByFilters(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ){
    List<MovieBasicDTO> movies = this.movieService.getByFilters(title, genreId, order);
       return  ResponseEntity.ok(movies);
    }

    //Controller to movie update.
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @RequestBody MovieDTO movie) throws NotFoundException {
        MovieDTO result = this.movieService.update(id, movie);
        return ResponseEntity.ok().body(result);
    }

    //Controller to movie delete.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.movieService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //Controller to add to an existing movie and an existing character.
    @PostMapping("{idMovie}/character/{idCharacter}")
    public ResponseEntity<MovieDTO> addCharacterToMovie(@PathVariable Long idMovie, @PathVariable Long idCharacter){
        movieService.addCharacter(idMovie, idCharacter );
        return ResponseEntity.ok().body(movieService.getById(idMovie));
    }

    //Controller to delete character from movie.
    @DeleteMapping("{idMovie}/character/{idCharacter}")
    public ResponseEntity<MovieDTO> deleteCharacterFromMovie(@PathVariable Long idMovie, @PathVariable Long idCharacter){
        movieService.deletedCharacter(idMovie, idCharacter);
        return ResponseEntity.ok().body(movieService.getById(idMovie));
    }
}
