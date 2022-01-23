package com.alkemy.disney.disney.service.impl;

import com.alkemy.disney.disney.dto.basic.CharacterBasicDTO;
import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.dto.filters.CharacterFiltersDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import com.alkemy.disney.disney.mapper.CharacterMapper;
import com.alkemy.disney.disney.repository.CharacterRepository;
import com.alkemy.disney.disney.repository.specifications.CharacterSpecification;
import com.alkemy.disney.disney.service.MovieService;
import com.alkemy.disney.disney.service.CharacterService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {

    private CharacterSpecification characterSpecification;

    private MovieService movieService;

    private CharacterMapper characterMapper;

    private CharacterRepository characterRepository;

    @Autowired
    public CharacterServiceImpl(
            CharacterRepository characterRepository,
            CharacterSpecification characterSpecification,
            MovieService movieService,
            CharacterMapper characterMapper) {
        this.characterRepository = characterRepository;
        this.characterSpecification = characterSpecification;
        this.movieService = movieService;
        this.characterMapper = characterMapper;
    }

    public CharacterDTO save(CharacterDTO dto) {
        CharacterEntity entity = this.characterMapper.characterDTO2Entity(dto);
        CharacterEntity entitySaved = this.characterRepository.save(entity);
        CharacterDTO result = this.characterMapper.characterEntity2DTO(entitySaved, true);
        return result;
    }

    public List<CharacterBasicDTO> getAllBasicCharacters(){
        List<CharacterEntity> entities = this.characterRepository.findAll();
        List<CharacterBasicDTO> characterBasicDTOS = this.characterMapper.characterEntityList2BasicDTOList(entities);
        return characterBasicDTOS;
    }

    public CharacterDTO getById(Long id) {
        CharacterEntity entity = this.characterRepository.getById(id);
        CharacterDTO characterDTO = this.characterMapper.characterEntity2DTO(entity, true);
        return characterDTO;
    }

    public CharacterEntity getEntityById(Long id) {
        CharacterEntity entity = this.characterRepository.getById(id);
        return entity;
    }

    public List<CharacterDTO> getByFilters(String name, Integer age, Long weight, List<Long> movies) {
        CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name, age, weight, movies);
        List<CharacterEntity> entities = this.characterRepository.findAll(this.characterSpecification.getByFilters(filtersDTO));
        List<CharacterDTO> dtos = this.characterMapper.characterEntityList2DTOList(entities, true);
        return dtos;
    }

    public void delete (Long id){
        this.characterRepository.deleteById(id);
    }

    public CharacterDTO update (Long id, CharacterDTO character) throws NotFoundException {
        Optional<CharacterEntity> entity = this.characterRepository.findById(id);
        if (!entity.isPresent()){
            throw new NotFoundException( "Id personaje no valido");
        }
        this.characterMapper.characterEntityRefreshValues(entity.get(), character);
        CharacterEntity entitySaved = this.characterRepository.save(entity.get());
        CharacterDTO result = this.characterMapper.characterEntity2DTO(entitySaved, false);
        return result;
    }

}
