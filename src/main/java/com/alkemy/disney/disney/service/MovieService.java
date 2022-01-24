package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.basic.MovieBasicDTO;
import com.alkemy.disney.disney.dto.MovieDTO;

import java.util.List;

public interface MovieService {

    MovieDTO save(MovieDTO dto);

    MovieDTO getById(Long id);

    List<MovieBasicDTO> getByFilters(String title, String creationDate, Long genreId, String order);

    MovieDTO update(Long id, MovieDTO pelicula);

    void delete(Long id);



}
