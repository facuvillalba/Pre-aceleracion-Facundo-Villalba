package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.basic.MovieBasicDTO;
import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.dto.error.ErrorMessage;
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
import java.util.Set;

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
        MovieEntity entityTitle = movieRepository.findByTitle(entity.getTitle());
        if(entityTitle!=null){
            throw new ParamNotFound(ErrorMessage.MOVIE_TITLE_EXISTS.key());
        }
        MovieEntity entitySaved = this.movieRepository.save(entity);
        MovieDTO result = this.movieMapper.movieEntity2DTO(entitySaved, true);
        return result;
    }
    //Service to search movie by id in Repository.
    public MovieDTO getById(Long id) {
        Optional<MovieEntity> entity = this.movieRepository.findById(id);
        if(!entity.isPresent()){
            throw new ParamNotFound(ErrorMessage.MOVIE_NOT_FOUND.key());
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
               throw new ParamNotFound(ErrorMessage.MOVIE_NOT_DELETED.key());
           }
       this.movieRepository.deleteById(id);
   }
    //Service to add new character to a movie.
   public void addCharacter(Long idMovie, Long idCharacter){
       MovieEntity movieEntity = getEntityById(idMovie);
       Set<CharacterEntity> entities = movieEntity.getCharacters();
       entities.add(characterService.getEntityById(idCharacter));
       movieEntity.setCharacters(entities);
       movieRepository.save(movieEntity);
   }

    //Service to update movie in Repository.
    public MovieDTO update (Long id, MovieDTO movieDTO) {
        Optional<MovieEntity> entity = this.movieRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound(ErrorMessage.CHARACTER_NOT_UPDATED.key());
        }
        this.movieMapper.movieEntityRefreshValues(entity.get(), movieDTO);
        MovieEntity entitySaved = this.movieRepository.save(entity.get());
        MovieDTO result = this.movieMapper.movieEntity2DTO(entitySaved, true);
        return result;
    }

    //Service to search movie by id in repository.
    public MovieEntity getEntityById(Long id){
       Optional<MovieEntity> movieEntity = movieRepository.findById(id);
        if (!movieEntity.isPresent()){
            throw new ParamNotFound(ErrorMessage.MOVIE_NOT_FOUND.key());
        }
       return movieEntity.get();
    }

    //Service to remove character from movie in Repository.
    public void removeCharacter(Long idMovie, Long idCharacter) {
        MovieEntity movieEntity = getEntityById(idMovie);
        Set<CharacterEntity> entities = movieEntity.getCharacters();
        entities.remove(characterService.getEntityById(idCharacter));
        movieEntity.setCharacters(entities);
        movieRepository.save(movieEntity);
    }
}
