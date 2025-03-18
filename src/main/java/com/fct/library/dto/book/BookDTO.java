package com.fct.library.dto.book;

public class BookDTO {
    private Long id;
    private String title;
    private String categoryName;
    private String uniqueCode;
    private String authorName;
    
    // Constructors
    public BookDTO() {}
    
    public BookDTO(Long id, String title, String categoryName, String uniqueCode, String authorName) {
        this.id = id;
        this.title = title;
        this.categoryName = categoryName;
        this.uniqueCode = uniqueCode;
        this.authorName = authorName;
    }
    
    // Getters and setters
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
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getUniqueCode() {
        return uniqueCode;
    }
    
    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}