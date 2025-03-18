package com.fct.library.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con datos de una reseña")
public class ReviewResponseDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String bookTitle;
    private Integer rating;
    private String comment;
    
    public ReviewResponseDTO() {
    }
    
    public ReviewResponseDTO(Long id, Long userId, String userName, String bookTitle, Integer rating, String comment) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.bookTitle = bookTitle;
        this.rating = rating;
        this.comment = comment;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getBookTitle() {
        return bookTitle;
    }
    
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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
