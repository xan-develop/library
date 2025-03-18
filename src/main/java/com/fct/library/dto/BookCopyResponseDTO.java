package com.fct.library.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para respuesta de consultas de copias de libros")
public class BookCopyResponseDTO {
    
    @Schema(description = "ID de la copia del libro", example = "1")
    private Long id;
    
    @Schema(description = "Identificador único de la copia", example = "ISBN-12345-C01")
    private String uniqueIdentifier;
    
    @Schema(description = "Título del libro", example = "El Quijote")
    private String bookTitle;
    
    @Schema(description = "Indica si la copia está prestada", example = "false")
    private Boolean onloan;
    
    @Schema(description = "Indica si la copia ha sido comprada", example = "false")
    private Boolean purchased;
    
    public BookCopyResponseDTO() {}
    
    public BookCopyResponseDTO(Long id, String uniqueIdentifier, String bookTitle, Boolean onloan, Boolean purchased) {
        this.id = id;
        this.uniqueIdentifier = uniqueIdentifier;
        this.bookTitle = bookTitle;
        this.onloan = onloan;
        this.purchased = purchased;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }
    
    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
    
    public String getBookTitle() {
        return bookTitle;
    }
    
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    
    public Boolean getOnloan() {
        return onloan;
    }
    
    public void setOnloan(Boolean onloan) {
        this.onloan = onloan;
    }
    
    public Boolean getPurchased() {
        return purchased;
    }
    
    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }
}
