package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.basic.MovieBasicDTO;
import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;

import java.util.List;
import java.util.Set;

public interface MovieService {

    MovieDTO save(MovieDTO dto);

    MovieDTO getById(Long id);

    //void addCharacterList(Long idMovie, Set<Long> charactersId);

    void addCharacterList(Long idMovie, List<Long> charactersId);

    void addCharacter(Long idMovie, CharacterEntity character);

    List<MovieBasicDTO> getByFilters(String title, Long genreId, String order);

    MovieDTO update(Long id, MovieDTO pelicula);

    void delete(Long id);



}
