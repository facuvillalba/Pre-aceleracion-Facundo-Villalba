package com.alkemy.disney.disney.mapper;

import com.alkemy.disney.disney.dto.MovieDTO;
import com.alkemy.disney.disney.dto.basic.CharacterBasicDTO;
import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CharacterMapper {

    @Autowired
    public MovieMapper movieMapper;

    //Mapper convert from characterDTO to Entity
    public CharacterEntity characterDTO2Entity(CharacterDTO dto){
        CharacterEntity characterEntity = new CharacterEntity();
        characterEntity.setImage(dto.getImage());
        characterEntity.setName(dto.getName());
        characterEntity.setAge(dto.getAge());
        characterEntity.setWeight(dto.getWeight());
        characterEntity.setStory(dto.getStory());

        return characterEntity;
    }

    //Mapper convert from characterEntity to DTO
    public CharacterDTO characterEntity2DTO(CharacterEntity entity, boolean loadMovies){
        CharacterDTO characterDTO = new CharacterDTO();
        characterDTO.setId(entity.getId());
        characterDTO.setImage(entity.getImage());
        characterDTO.setName(entity.getName());
        characterDTO.setAge(entity.getAge());
        characterDTO.setWeight(entity.getWeight());
        characterDTO.setStory(entity.getStory());

        if(loadMovies) {
            List<MovieDTO> movieDTOS = this.movieMapper.movieEntityList2DTOList(entity.getMovies(), false);
            characterDTO.setMovies(movieDTOS);
        }
        return characterDTO;
    }

    //Mapper convert from List characterDTO to List Entity
    public List<CharacterEntity> characterDTOList2EntityList(List<CharacterDTO> dtos){
        List<CharacterEntity> entities = new ArrayList<>();
        for (CharacterDTO dto : dtos) {
            entities.add(this.characterDTO2Entity(dto));
        }
        return entities;
    }

    //Mapper convert from Set characterDTO to List Entity
    public Set<CharacterEntity> characterDTOList2EntitySet(List<CharacterDTO> dtos){
        Set<CharacterEntity> entities = new HashSet<>();
        for (CharacterDTO dto : dtos){
            entities.add(this.characterDTO2Entity(dto));
        }
        return entities;
    }

    //Mapper convert from Lis characterEntity to List DTO
    public List<CharacterDTO> characterEntitySet2DTOList(Collection<CharacterEntity> entities, boolean loadMovies){
        List<CharacterDTO> dtos = new ArrayList<>();
        for (CharacterEntity entity : entities){
            dtos.add(characterEntity2DTO(entity, loadMovies));
            }
        return dtos;
        }

    //Mapper update
    public void characterEntityRefreshValues(CharacterEntity entity, CharacterDTO characterDTO) {
        entity.setImage(characterDTO.getImage());
        entity.setName(characterDTO.getName());
        entity.setAge(characterDTO.getAge());
        entity.setWeight(characterDTO.getWeight());
        entity.setStory(characterDTO.getStory());
    }

    //Mapper from List characterEntity to List BasicDTO
    public List<CharacterBasicDTO> characterEntityList2BasicDTOList(List<CharacterEntity> entities){
        List<CharacterBasicDTO> dtos = new ArrayList<>();
        CharacterBasicDTO basicDTO;
        for (CharacterEntity entity : entities) {
            basicDTO = new CharacterBasicDTO();
            basicDTO.setId(entity.getId());
            basicDTO.setImage(entity.getImage());
            basicDTO.setName(entity.getName());
            dtos.add(basicDTO);
        }
        return dtos;
    }
}
