package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.basic.MovieBasicDTO;
import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.entity.MovieEntity;
import javassist.NotFoundException;

import java.util.List;

public interface MovieService {

    MovieDTO save(MovieDTO dto);

    List<MovieBasicDTO> getAllBasicMovies();

    MovieDTO getById(Long id);

    List<MovieDTO> getByFilters(String title, String creationDate, Long genreId, String order);

    MovieEntity getEntityById(Long id);

    MovieDTO update(Long id, MovieDTO pelicula) throws NotFoundException;

    void delete(Long id);



}
