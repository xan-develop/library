package com.fct.library.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "book_copy")
@Schema(description = "Representa una copia de un libro")
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Unique identifier cannot be empty")
    @Column(name = "unique_identifier", nullable = false, length = 20, unique = true)
    private String uniqueIdentifier;

    @Column(name = "onloan")
    private Boolean onloan = false;
    
    @Column(name = "purchased")
    private Boolean purchased = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @JsonBackReference
    private Book book;

    // Getters y Setters
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isOnloan() {
        return onloan;
    }

    public void setOnloan(Boolean onloan) {
        this.onloan = onloan;
    }
    
    public Boolean isPurchased() {
        return purchased;
    }
    
    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }
}