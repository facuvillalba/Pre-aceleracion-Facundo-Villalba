package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.basic.MovieBasicDTO;
import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.dto.filters.MovieFiltersDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.entity.MovieEntity;
import com.alkemy.disney.disney.exception.ParamNotFound;
import com.alkemy.disney.disney.mapper.MovieMapper;
import com.alkemy.disney.disney.repository.MovieRepository;
import com.alkemy.disney.disney.repository.specifications.MovieSpecification;
import com.alkemy.disney.disney.service.CharacterService;
import com.alkemy.disney.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieSpecification movieSpecification;

    @Autowired
    private CharacterService characterService;

    //Service to save movie in Repository.
    public MovieDTO save(MovieDTO dto) {
        MovieEntity entity = this.movieMapper.movieDTO2Entity(dto);
        MovieEntity entitySaved = this.movieRepository.save(entity);
        MovieDTO result = this.movieMapper.movieEntity2DTO(entitySaved, true);
        return result;
    }

    //Service to search movie by id in Repository.
    public MovieDTO getById(Long id) {
        Optional<MovieEntity> entity = this.movieRepository.findById(id);
        if(!entity.isPresent()){
            throw new ParamNotFound("Id movie not found");
        }
        MovieDTO movieDTO = this.movieMapper.movieEntity2DTO(entity.get(), true);
        return movieDTO;
    }

    //Service to search movie with filters in Repository.
    public List<MovieBasicDTO> getByFilters (String title, Long genreId, String order){
        MovieFiltersDTO filtersDTO = new MovieFiltersDTO(title, genreId, order);
        List<MovieEntity> entities = this.movieRepository.findAll(this.movieSpecification.getByFilters(filtersDTO));
        List<MovieBasicDTO> dtos = this.movieMapper.movieEntityList2BasicDTOList(entities);
       return dtos;
    }

    //Service to softly delete movie in Repository.
    public void delete(Long id){
       Optional<MovieEntity> entity = this.movieRepository.findById(id);
           if (!entity.isPresent()){
               throw new ParamNotFound("Id movie not found");
           }
       this.movieRepository.deleteById(id);
   }

    //Service to add new character to a movie.
   public void addCharacter(Long idMovie, Long idCharacter){
       MovieEntity movieEntity = getEntityById(idMovie);
       if (movieEntity == null){
           throw new ParamNotFound("Id movie not found");
       }
       List<CharacterEntity> entities = movieEntity.getCharacters();
       entities.add(characterService.getEntityById(idCharacter));
       movieEntity.setCharacters(entities);
       movieRepository.save(movieEntity);
   }

    //Service to update movie in Repository.
    public MovieDTO update (Long id, MovieDTO movieDTO) {
        Optional<MovieEntity> entity = this.movieRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("Id movie not found");
        }
        this.movieMapper.movieEntityRefreshValues(entity.get(), movieDTO);
        MovieEntity entitySaved = this.movieRepository.save(entity.get());
        MovieDTO result = this.movieMapper.movieEntity2DTO(entitySaved, true);
        return result;
    }

    public MovieEntity getEntityById(Long id){
       MovieEntity movieEntity = movieRepository.getById(id);
        if (movieEntity == null){
            throw new ParamNotFound("Id movie not found");
        }
       return movieEntity;
    }

    //Service to delete character from movie in Repository.
    public void deletedCharacter(Long idMovie, Long idCharacter) {
        MovieEntity movieEntity = getEntityById(idMovie);
        List<CharacterEntity> entities = movieEntity.getCharacters();
        entities.remove(characterService.getEntityById(idCharacter));
        movieEntity.setCharacters(entities);
        movieRepository.save(movieEntity);
    }

    //Service to add new List characters to a movie.
    public void addCharacterList(Long idMovie, List<Long> charactersId) {
        MovieEntity movieEntity = getEntityById(idMovie);
        List<CharacterEntity> movieCharacters = movieEntity.getCharacters();
        for (Long id : charactersId) {
            movieCharacters.add(characterService.getEntityById(id));
        }
        movieEntity.setCharacters(movieCharacters);
        movieRepository.save(movieEntity);
    }
}
