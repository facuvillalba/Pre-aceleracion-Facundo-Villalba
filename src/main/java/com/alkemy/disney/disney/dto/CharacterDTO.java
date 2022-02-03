package com.alkemy.disney.disney.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterDTO {

    private Long id;
    private String image;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @Min(value = 1, message = "Age min 1")
    private Integer age;
    @NotNull(message = "Weight cannot be null")
    private Long weight;
    @Size(min=10, max = 200, message = "Story text min 10, max 200 characters")
    private String story;

    private List<MovieDTO> movies;

}
