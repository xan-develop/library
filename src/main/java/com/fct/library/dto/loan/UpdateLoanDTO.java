package com.fct.library.dto.loan;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;

@Schema(description = "DTO para actualizar un préstamo existente")
public class UpdateLoanDTO {
    
    @Schema(description = "ID del usuario que solicita el préstamo", example = "1")
    private Long userId;
    
    @Schema(description = "ID de la copia del libro a prestar", example = "1")
    private Long bookCopyId;
    
    @Schema(description = "Fecha de inicio del préstamo", example = "2025-04-01")
    private LocalDate startDate;
    
    @Future(message = "La fecha de devolución debe ser en el futuro")
    @Schema(description = "Fecha prevista de devolución", example = "2025-04-15")
    private LocalDate dueDate;
    
    @Schema(description = "Fecha real de devolución", example = "2025-04-10")
    private LocalDate returnDate;
    
    // Constructores
    public UpdateLoanDTO() {
    }
    
    public UpdateLoanDTO(Long userId, Long bookCopyId, LocalDate startDate, 
                      LocalDate dueDate, LocalDate returnDate) {
        this.userId = userId;
        this.bookCopyId = bookCopyId;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
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
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
