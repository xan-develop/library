package com.fct.library.dto.book;

import java.util.List;

import com.fct.library.model.BookCopy;

public class BookCopiesDTO {
    private Long id;
    private String title;
    private String genre;
    private String uniqueCode;
    private String authorName;
    private List<BookCopy> copies;
    private Integer copiesCount;
    
    // Constructors
    public BookCopiesDTO() {}
    
    public BookCopiesDTO(Long id, String title, String genre, String uniqueCode, String authorName, List<BookCopy> copies) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.uniqueCode = uniqueCode;
        this.authorName = authorName;
        this.copies = copies;
        this.copiesCount = countCopies(copies);
    }

    private Integer countCopies(List<BookCopy> copies) {
        return copies.size();
    }

    // Getters and setters

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getCopiesCount() {
        return this.copiesCount;
    }
    public void setCopiesCount(Integer copiesCount) {
        this.copiesCount = copiesCount;
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

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<BookCopy> getCopies() {
        return this.copies;
    }

    public void setCopies(List<BookCopy> copies) {
        this.copies = copies;
    }
    

    
    
}
