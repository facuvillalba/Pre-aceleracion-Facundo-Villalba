package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.basic.CharacterBasicDTO;
import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.dto.filters.CharacterFiltersDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.exception.ParamNotFound;
import com.alkemy.disney.disney.mapper.CharacterMapper;
import com.alkemy.disney.disney.repository.CharacterRepository;
import com.alkemy.disney.disney.repository.specifications.CharacterSpecification;
import com.alkemy.disney.disney.service.MovieService;
import com.alkemy.disney.disney.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterSpecification characterSpecification;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CharacterMapper characterMapper;

    @Autowired
    private CharacterRepository characterRepository;

    //Service to save character in Repository.
    public CharacterDTO save(CharacterDTO dto, Long idMovie) {
        CharacterEntity entity = this.characterMapper.characterDTO2Entity(dto);
        CharacterEntity entitySaved = this.characterRepository.save(entity);
        movieService.addCharacter(idMovie, entitySaved);
        CharacterDTO result = this.characterMapper.characterEntity2DTO(entitySaved, true);
        return result;
    }

    //Service to search character by id in Repository.
    public CharacterDTO getById(Long id) {
        Optional<CharacterEntity> entity = this.characterRepository.findById(id);
        if(!entity.isPresent()){
           throw new ParamNotFound("Id character not found");
        }
        CharacterDTO characterDTO = this.characterMapper.characterEntity2DTO(entity.get(), true);
        return characterDTO;
    }

    //Service to search character with filters in Repository.
    public List<CharacterBasicDTO> getByFilters(String name, Integer age, Long weight, List<Long> movies) {
        CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name, age, weight, movies);
        List<CharacterEntity> entities = this.characterRepository.findAll(this.characterSpecification.getByFilters(filtersDTO));
        List<CharacterBasicDTO> dtos = this.characterMapper.characterEntityList2BasicDTOList(entities);
        return dtos;
    }

    //Service to softly delete character in Repository.
    public void delete (Long id){
        Optional<CharacterEntity> entity = this.characterRepository.findById(id);
        if (!entity.isPresent()){
            throw new ParamNotFound("Id character not found");
        }
        this.characterRepository.deleteById(id);
    }

    //Service to update character in Repository.
    public CharacterDTO update (Long id, CharacterDTO character) {
        Optional<CharacterEntity> entity = this.characterRepository.findById(id);
        if (!entity.isPresent()){
            throw new ParamNotFound( "Id character not found");
        }
        this.characterMapper.characterEntityRefreshValues(entity.get(), character);
        CharacterEntity entitySaved = this.characterRepository.save(entity.get());
        CharacterDTO result = this.characterMapper.characterEntity2DTO(entitySaved, true);
        return result;
    }

    //Service to search character by id in repository.
    public CharacterEntity getEntityById(Long id) {
        return characterRepository.getById(id);
    }
}
