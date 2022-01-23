package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.basic.MovieBasicDTO;
import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.dto.filters.MovieFiltersDTO;
import com.alkemy.disney.disney.entity.MovieEntity;
import com.alkemy.disney.disney.mapper.MovieMapper;
import com.alkemy.disney.disney.repository.MovieRepository;
import com.alkemy.disney.disney.repository.specifications.MovieSpecification;
import com.alkemy.disney.disney.service.MovieService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieMapper movieMapper;

    private MovieRepository movieRepository;

    private MovieSpecification movieSpecification;


   @Autowired
    public MovieServiceImpl(
            MovieRepository movieRepository,
            MovieSpecification movieSpecification,
            MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieSpecification = movieSpecification;
        this.movieMapper = movieMapper;
    }

    public MovieDTO save(MovieDTO dto) {
        MovieEntity entity = this.movieMapper.movieDTO2Entity(dto);
        MovieEntity entitySaved = this.movieRepository.save(entity);
        MovieDTO result = this.movieMapper.movieEntity2DTO(entitySaved, true);
        return result;
    }

    public List<MovieBasicDTO> getAllBasicMovies(){
        List<MovieEntity> entities = this.movieRepository.findAll();
        List<MovieBasicDTO> movieBasicDTOS = this.movieMapper.movieEntityList2BasicDTOList(entities);
        return movieBasicDTOS;
    }

    public MovieDTO getById(Long id) {
        MovieEntity entity = this.movieRepository.getById(id);
        MovieDTO movieDTO = this.movieMapper.movieEntity2DTO(entity, true);
        return movieDTO;
    }

    public MovieEntity getEntityById(Long id) {
        MovieEntity entity = this.movieRepository.getById(id);
        return entity;
    }

    public List<MovieDTO> getByFilters (String title, String creationDate, Long genreId, String order){
        MovieFiltersDTO filtersDTO = new MovieFiltersDTO(title, creationDate, genreId, order);
        List<MovieEntity> entities = this.movieRepository.findAll(this.movieSpecification.getByFilters(filtersDTO));
        List<MovieDTO> dtos = this.movieMapper.movieEntityList2DTOList(entities, true);
       return dtos;
    }

    public void delete(Long id){
       this.movieRepository.deleteById(id);
   }

    public MovieDTO update (Long id, MovieDTO movieDTO) throws NotFoundException {
        Optional<MovieEntity> entity = this.movieRepository.findById(id);
        if (!entity.isPresent()) {
            throw new NotFoundException("Id pelicula no valida");
        }
        this.movieMapper.movieEntityRefreshValues(entity.get(), movieDTO);
        MovieEntity entitySaved = this.movieRepository.save(entity.get());
        MovieDTO result = this.movieMapper.movieEntity2DTO(entitySaved, true);
        return result;
    }

}
