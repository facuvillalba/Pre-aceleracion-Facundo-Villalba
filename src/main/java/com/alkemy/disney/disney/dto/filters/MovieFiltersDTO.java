package com.alkemy.disney.disney.dto.filters;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class MovieFiltersDTO {

    private String title;

    private String creationDate;

    private Long genreId;

    private String order;

    public MovieFiltersDTO(String title, String creationDate, Long genreId, String order){
        this.title = title;
        this.creationDate = creationDate;
        this.genreId = genreId;
        this.order = order;
    }

    public boolean isASC() { return this.order.compareToIgnoreCase("ASC") == 0; }

    public boolean isDESC() { return this.order.compareToIgnoreCase("DESC") == 0; }

}
