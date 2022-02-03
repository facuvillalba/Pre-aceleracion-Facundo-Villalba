package com.alkemy.disney.disney.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiErrorDTO {

    private HttpStatus status;

    @Enumerated(EnumType.STRING)
    private String errorMessage;

    private List<String> errors;


}
