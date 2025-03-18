package com.fct.library.dto.loan;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para respuesta de préstamos")
public class LoanResponseDTO {
    
    private Long id;
    private Long userId;
    private String userName;
    private Long bookCopyId;
    private String bookTitle;
    private String uniqueIdentifier;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    
    
    public LoanResponseDTO() {
    }
    
    public LoanResponseDTO(Long id, Long userId, String userName, Long bookCopyId, 
                          String bookTitle, String uniqueIdentifier, LocalDate startDate, 
                          LocalDate dueDate, LocalDate returnDate) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.bookCopyId = bookCopyId;
        this.bookTitle = bookTitle;
        this.uniqueIdentifier = uniqueIdentifier;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }
    
    // Getters y setters
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
    
    public Long getBookCopyId() {
        return bookCopyId;
    }
    
    public void setBookCopyId(Long bookCopyId) {
        this.bookCopyId = bookCopyId;
    }
    
    public String getBookTitle() {
        return bookTitle;
    }
    
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    
    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }
    
    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
}
