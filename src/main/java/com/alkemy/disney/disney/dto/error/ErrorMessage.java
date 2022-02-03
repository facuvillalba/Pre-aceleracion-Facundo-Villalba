package com.alkemy.disney.disney.dto.error;

public enum ErrorMessage {
    CHARACTER_NOT_FOUND("Id character not found"),
    CHARACTER_NOT_UPDATED("Character not updated, id not found or request error"),
    CHARACTER_NOT_DELETED("Character not deleted, id not found."),
    MOVIE_NOT_FOUND("Id movie not found"),
    MOVIE_NOT_UPDATED("Movie not updated, id not found or request error"),
    MOVIE_NOT_DELETED("Movie not deleted, id not found."),
    MOVIE_TITLE_EXISTS("Existing movie title"),
    VALIDATION_ERROR("There are errors in your request");

    private String key;

    ErrorMessage(String key){
        this.key = key;

    }

    public String key(){
        return key;
    }
}


