package com.fct.library.dto.loan;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para crear un nuevo préstamo")
public class CreateLoanDTO {
    
    @NotNull(message = "El ID del usuario no puede ser nulo")
    @Schema(description = "ID del usuario que solicita el préstamo", example = "1")
    private Long userId;
    
    @NotNull(message = "El ID de la copia del libro no puede ser nulo")
    @Schema(description = "ID de la copia del libro a prestar", example = "1")
    private Long bookCopyId;
    
    @NotNull(message = "La fecha de inicio no puede ser nula")
    @Schema(description = "Fecha de inicio del préstamo", example = "2025-04-01")
    private LocalDate startDate;
    
    @NotNull(message = "La fecha de devolución prevista no puede ser nula")
    @Future(message = "La fecha de devolución debe ser en el futuro")
    @Schema(description = "Fecha prevista de devolución", example = "2025-04-15")
    private LocalDate dueDate;
    
    // Constructores
    public CreateLoanDTO() {
    }
    
    public CreateLoanDTO(Long userId, Long bookCopyId, LocalDate startDate, LocalDate dueDate) {
        this.userId = userId;
        this.bookCopyId = bookCopyId;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
    
    // Getters y setters
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getBookCopyId() {
        return bookCopyId;
    }
    
    public void setBookCopyId(Long bookCopyId) {
        this.bookCopyId = bookCopyId;
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
}
