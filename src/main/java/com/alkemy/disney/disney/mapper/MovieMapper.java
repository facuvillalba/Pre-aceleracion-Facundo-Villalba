package com.alkemy.disney.disney.mapper;

import com.alkemy.disney.disney.dto.basic.MovieBasicDTO;
import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.entity.MovieEntity;
import com.alkemy.disney.disney.entity.CharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieMapper {

   @Autowired
   private CharacterMapper characterMapper;

    //Mapper convert from movieDTO to Entity
    public MovieEntity movieDTO2Entity(MovieDTO dto){
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setImage(dto.getImage());
        movieEntity.setTitle(dto.getTitle());
        movieEntity.setCreationDate(dto.getCreationDate());
        movieEntity.setQualification(dto.getQualification());
        movieEntity.setGenreId(dto.getGenreId());

        List<CharacterEntity> personajesEntities = this.characterMapper.characterDTOList2EntityList(dto.getCharacters());
        movieEntity.setCharacters(personajesEntities);

        return movieEntity;
    }

    //Mapper convert from movieEntity to DTO
    public MovieDTO movieEntity2DTO(MovieEntity entity, boolean loadPersonajes){
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(entity.getId());
        movieDTO.setImage(entity.getImage());
        movieDTO.setTitle(entity.getTitle());
        movieDTO.setCreationDate(entity.getCreationDate());
        movieDTO.setQualification(entity.getQualification());
        movieDTO.setGenreId(entity.getGenreId());

        if(loadPersonajes) {
            List<CharacterDTO> characterDTOS = this.characterMapper.characterEntityList2DTOList(entity.getCharacters(), true);
            movieDTO.setCharacters(characterDTOS);
        }
        return movieDTO;
    }

    //Mapper convert from List movieEntity to List DTO
    public List<MovieDTO> movieEntityList2DTOList(List<MovieEntity> entities, boolean loadPersonajes){
        List<MovieDTO> dtos = new ArrayList<>();
        for (MovieEntity entity : entities) {
            dtos.add(this.movieEntity2DTO(entity, loadPersonajes));
        }
        return dtos;
    }

    //Mapper update
    public void movieEntityRefreshValues(MovieEntity entity, MovieDTO movieDTO) {
        entity.setImage(movieDTO.getImage());
        entity.setTitle(movieDTO.getTitle());
        entity.setCreationDate(movieDTO.getCreationDate());
        entity.setQualification(movieDTO.getQualification());
        entity.setGenreId(movieDTO.getGenreId());
        List<CharacterEntity> personajesEntities = this.characterMapper.characterDTOList2EntityList(movieDTO.getCharacters());
        for (CharacterEntity personaje : personajesEntities) {
            entity.getCharacters().add(personaje);
        }
    }

    //Mapper convert List movieEntity to List BasicDTO
    public List<MovieBasicDTO> movieEntityList2BasicDTOList(List<MovieEntity> entities){
        List<MovieBasicDTO> dtos = new ArrayList<>();
        MovieBasicDTO basicDTO;
        for (MovieEntity entity : entities){
            basicDTO = new MovieBasicDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setImage(entity.getImage());
            basicDTO.setTitle(entity.getTitle());
            basicDTO.setCreationDate(entity.getCreationDate());
            dtos.add(basicDTO);
        }
        return dtos;
    }
}
