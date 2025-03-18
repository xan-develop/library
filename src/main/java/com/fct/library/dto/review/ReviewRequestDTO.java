package com.fct.library.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos para crear o actualizar una reseña")
public class ReviewRequestDTO {
    @NotNull(message = "El ID de usuario no puede ser nulo")
    @Schema(description = "ID del usuario que hace la reseña", example = "1")
    private Long userId;
    
    @NotNull(message = "El ID del libro no puede ser nulo")
    @Schema(description = "ID del libro que se está reseñando", example = "1")
    private Long bookId;
    
    @NotNull(message = "La calificación no puede ser nula")
    @Min(value = 1, message = "La calificación debe ser al menos 1")
    @Max(value = 5, message = "La calificación debe ser como máximo 5")
    @Schema(description = "Calificación del libro (1-5)", example = "4")
    private Integer rating;
    
    @Schema(description = "Comentario sobre el libro", example = "Excelente libro, muy recomendado")
    private String comment;
    
    public ReviewRequestDTO() {
    }
    
    public ReviewRequestDTO(Long userId, Long bookId, Integer rating, String comment) {
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getBookId() {
        return bookId;
    }
    
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
}
