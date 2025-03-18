package com.fct.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para crear o actualizar una copia de libro")
public class BookCopyDTO {
    
    @NotNull(message = "El ID del libro no puede ser nulo")
    private Long bookId;
    
    @NotBlank(message = "El identificador único no puede estar vacío")
    private String uniqueIdentifier;
    
    public BookCopyDTO() {}
    
    public BookCopyDTO(Long bookId, String uniqueIdentifier) {
        this.bookId = bookId;
        this.uniqueIdentifier = uniqueIdentifier;
    }
    
    // Getters y setters
    public Long getBookId() {
        return bookId;
    }
    
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    
    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }
    
    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
}
