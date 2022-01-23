package com.alkemy.disney.disney.service;

import com.alkemy.disney.disney.dto.basic.CharacterBasicDTO;
import com.alkemy.disney.disney.dto.CharacterDTO;
import com.alkemy.disney.disney.entity.CharacterEntity;
import javassist.NotFoundException;

import java.util.List;

public interface CharacterService {

    CharacterDTO save(CharacterDTO dto);

    List<CharacterBasicDTO> getAllBasicCharacters();

    CharacterDTO getById(Long id);

    List<CharacterDTO> getByFilters(String name, Integer age, Long weight, List<Long> movies);

    CharacterEntity getEntityById(Long id);

    CharacterDTO update(Long id, CharacterDTO character) throws NotFoundException;

    void delete(Long id);

}
