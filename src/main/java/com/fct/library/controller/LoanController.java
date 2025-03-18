package com.fct.library.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fct.library.dto.loan.CreateLoanDTO;
import com.fct.library.dto.loan.LoanResponseDTO;
import com.fct.library.dto.loan.UpdateLoanDTO;
import com.fct.library.service.interfaces.LoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/loans")
@Tag(name = "Loan", description = "API para la gestión de préstamos de libros")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(summary = "Obtener todos los préstamos", description = "Devuelve una lista de todos los préstamos registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        return ResponseEntity.ok(loanService.findAll());
    }

    @Operation(summary = "Buscar préstamo por ID", description = "Devuelve un préstamo según el ID proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Préstamo encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Préstamo no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> getLoanById(
            @Parameter(description = "ID del préstamo") @PathVariable Long id) {
        return loanService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar préstamos por usuario", description = "Devuelve todos los préstamos asociados a un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class)))
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanResponseDTO>> getLoansByUserId(
            @Parameter(description = "ID del usuario") @PathVariable Long userId) {
        return ResponseEntity.ok(loanService.findByUserId(userId));
    }

    @Operation(summary = "Buscar préstamos por copia de libro", description = "Devuelve todos los préstamos asociados a una copia específica de libro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class)))
    })
    @GetMapping("/bookcopy/{bookCopyId}")
    public ResponseEntity<List<LoanResponseDTO>> getLoansByBookCopyId(
            @Parameter(description = "ID de la copia del libro") @PathVariable Long bookCopyId) {
        return ResponseEntity.ok(loanService.findByBookCopyId(bookCopyId));
    }

    @Operation(summary = "Obtener préstamos activos", description = "Devuelve todos los préstamos que están actualmente en curso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class)))
    })
    @GetMapping("/active")
    public ResponseEntity<List<LoanResponseDTO>> getActiveLoans() {
        return ResponseEntity.ok(loanService.findActiveLoans());
    }

    @Operation(summary = "Obtener préstamos vencidos", description = "Devuelve todos los préstamos que han excedido su fecha de devolución")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class)))
    })
    @GetMapping("/overdue")
    public ResponseEntity<List<LoanResponseDTO>> getOverdueLoans() {
        return ResponseEntity.ok(loanService.findOverdueLoans());
    }

    @Operation(summary = "Buscar préstamos por rango de fechas", description = "Devuelve todos los préstamos realizados entre las fechas especificadas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class)))
    })
    @GetMapping("/daterange")
    public ResponseEntity<List<LoanResponseDTO>> getLoansByDateRange(
            @Parameter(description = "Fecha de inicio (formato ISO)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "Fecha de fin (formato ISO)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(loanService.findByDateRange(startDate, endDate));
    }

    @Operation(summary = "Crear nuevo préstamo", description = "Registra un nuevo préstamo en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Préstamo creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta - Datos inválidos", content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"La fecha de inicio debe ser anterior a la fecha de vencimiento\"}"))),
        @ApiResponse(responseCode = "409", description = "Conflicto - El libro no está disponible", content = @Content)
    })
    @PostMapping
    public ResponseEntity<LoanResponseDTO> createLoan(
            @Parameter(description = "Datos del nuevo préstamo") @RequestBody CreateLoanDTO loanDTO) {
        LoanResponseDTO createdLoan = loanService.createLoan(loanDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
    }

    @Operation(summary = "Actualizar préstamo", description = "Modifica la información de un préstamo existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Préstamo actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Préstamo no encontrado", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflicto durante la actualización", content = @Content),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> updateLoan(
            @Parameter(description = "ID del préstamo a actualizar") @PathVariable Long id, 
            @Parameter(description = "Datos actualizados del préstamo") @RequestBody UpdateLoanDTO loanDTO) {
        return loanService.updateLoan(id, loanDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Registrar devolución de libro", description = "Marca un préstamo como devuelto con la fecha especificada o la fecha actual")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devolución registrada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Préstamo no encontrado", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflicto durante la devolución", content = @Content),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    })
    @PutMapping("/{id}/return")
    public ResponseEntity<LoanResponseDTO> returnBook(
            @Parameter(description = "ID del préstamo") @PathVariable Long id,
            @Parameter(description = "Fecha de devolución (opcional, formato ISO)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {
        LocalDate effectiveReturnDate = returnDate == null ? LocalDate.now() : returnDate;
        return loanService.returnBook(id, effectiveReturnDate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Eliminar préstamo", description = "Elimina un registro de préstamo del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Préstamo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Préstamo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(
            @Parameter(description = "ID del préstamo a eliminar") @PathVariable Long id) {
        boolean deleted = loanService.deleteLoan(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
