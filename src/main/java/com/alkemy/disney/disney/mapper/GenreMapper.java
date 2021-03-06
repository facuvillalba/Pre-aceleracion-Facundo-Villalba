package com.alkemy.disney.disney.mapper;

import com.alkemy.disney.disney.dto.GenreDTO;
import com.alkemy.disney.disney.entity.GenreEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenreMapper {

    //Mapper convert from genreDTO to Entity
    public GenreEntity genreDTO2Entity(GenreDTO dto){
        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setName(dto.getName());
        genreEntity.setImage(dto.getImage());
        return genreEntity;
    }

    //Mapper convert from genreEntity to DTO
    public GenreDTO genreEntity2DTO(GenreEntity entity){
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(entity.getId());
        genreDTO.setName(entity.getName());
        genreDTO.setImage(entity.getImage());
        return genreDTO;
    }

    //Mapper convert from List genreEntity to List DTO
    public List<GenreDTO> genreEntityList2DTOList(List<GenreEntity> entities) {
        List<GenreDTO> dtos = new ArrayList<>();
        for (GenreEntity entity: entities) {
            dtos.add(this.genreEntity2DTO(entity));
        }
        return dtos;
    }
}

