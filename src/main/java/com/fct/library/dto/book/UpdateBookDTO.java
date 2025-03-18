package com.fct.library.dto.book;

public class UpdateBookDTO {
    private Long id;
    private String title;
    private Long categoryId;
    private String uniqueCode;
    private Long authorId;
    
    // Constructors
    public UpdateBookDTO() {}
    
    public UpdateBookDTO(Long id, String title, Long categoryId, String uniqueCode, Long authorId) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.uniqueCode = uniqueCode;
        this.authorId = authorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
