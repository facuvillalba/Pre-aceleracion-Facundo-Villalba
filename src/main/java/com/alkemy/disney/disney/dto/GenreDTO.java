package com.alkemy.disney.disney.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class GenreDTO {

    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private String image;
}
