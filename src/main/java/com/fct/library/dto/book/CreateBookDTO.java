package com.fct.library.dto.book;

public class CreateBookDTO {
    private String title;
    private String genre;
    private String uniqueCode;
    private Long authorId;
    
    // Constructors
    public CreateBookDTO() {}
    
    public CreateBookDTO(String title, String genre, String uniqueCode, Long authorId) {
        this.title = title;
        this.genre = genre;
        this.uniqueCode = uniqueCode;
        this.authorId = authorId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public Long getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }


}
