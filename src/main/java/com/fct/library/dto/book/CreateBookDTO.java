package com.fct.library.dto.book;

public class CreateBookDTO {
    private String title;
    private Long categoryId;
    private String uniqueCode;
    private Long authorId;
    
    // Constructors
    public CreateBookDTO() {}
    
    public CreateBookDTO(String title, Long categoryId, String uniqueCode, Long authorId) {
        this.title = title;
        this.categoryId = categoryId;
        this.uniqueCode = uniqueCode;
        this.authorId = authorId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
