package com.alkemy.disney.disney.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDTO {

    private Long id;
    @NotBlank
    private String image;
    @NotBlank(message = "Title cannot be null ")
    private String title;
    @NotNull(message = "Date cannot be null ")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    @Min(1)
    @Max(5)
    @NotNull(message = "Qualification value between 1 and 5")
    private Integer qualification;
    @NotNull(message = "Id gender cannot be null")
    private Long genreId;

    private List<CharacterDTO> characters;

}
