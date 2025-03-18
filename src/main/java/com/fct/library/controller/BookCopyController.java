package com.fct.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fct.library.dto.BookCopyDTO;
import com.fct.library.dto.BookCopyResponseDTO;
import com.fct.library.model.BookCopy;
import com.fct.library.service.interfaces.BookCopyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookcopies")
@Tag(name = "BookCopy", description = "API para la gestión de copias de libros")
public class BookCopyController {

    private final BookCopyService bookCopyService;

    
    public BookCopyController(BookCopyService bookCopyService) {
        this.bookCopyService = bookCopyService;
    }

    @PostMapping
    @Operation(summary = "Crear una nueva copia de libro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Copia de libro creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados")
    })
    public ResponseEntity<BookCopy> createBookCopy(@Valid @RequestBody BookCopyDTO bookCopyDTO) {
        BookCopy createdBookCopy = bookCopyService.createBookCopyFromDTO(bookCopyDTO);
        return new ResponseEntity<>(createdBookCopy, HttpStatus.CREATED);
    }

    @GetMapping("/available")
    @Operation(summary = "Obtener copias de libros disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Copias disponibles encontradas"),
        @ApiResponse(responseCode = "404", description = "No se encontraron copias disponibles")
    })
    public ResponseEntity<List<BookCopyResponseDTO>> getAvailableCopies() {
        List<BookCopyResponseDTO> availableCopies = bookCopyService.getAvailableCopies();
        if (availableCopies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(availableCopies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener copia de libro por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Copia de libro encontrada"),
        @ApiResponse(responseCode = "404", description = "Copia de libro no encontrada")
    })
    public ResponseEntity<BookCopyResponseDTO> getBookCopyById(@PathVariable Long id) {
        BookCopyResponseDTO bookCopy = bookCopyService.getBookCopyById(id);
        return ResponseEntity.ok(bookCopy);
    }

    @GetMapping("/identifier/{uniqueIdentifier}")
    @Operation(summary = "Obtener copia de libro por identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Copia de libro encontrada"),
        @ApiResponse(responseCode = "404", description = "Copia de libro no encontrada")
    })
    public ResponseEntity<BookCopyResponseDTO> getBookCopyByUniqueIdentifier(@PathVariable String uniqueIdentifier) {
        BookCopyResponseDTO bookCopy = bookCopyService.getBookCopyByUniqueIdentifier(uniqueIdentifier);
        return ResponseEntity.ok(bookCopy);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las copias de libros")
    public ResponseEntity<List<BookCopyResponseDTO>> getAllBookCopies() {
        List<BookCopyResponseDTO> bookCopies = bookCopyService.getAllBookCopies();
        return ResponseEntity.ok(bookCopies);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar copia de libro por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Copia de libro actualizada"),
        @ApiResponse(responseCode = "404", description = "Copia de libro no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos proporcionados")
    })
    public ResponseEntity<BookCopy> updateBookCopy(@PathVariable Long id, @Valid @RequestBody BookCopyDTO bookCopyDTO) {
        BookCopy updatedBookCopy = bookCopyService.updateBookCopyFromDTO(id, bookCopyDTO);
        return ResponseEntity.ok(updatedBookCopy);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar copia de libro por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Copia de libro eliminada"),
        @ApiResponse(responseCode = "404", description = "Copia de libro no encontrada")
    })
    public ResponseEntity<Void> deleteBookCopy(@PathVariable Long id) {
        bookCopyService.deleteBookCopy(id);
        return ResponseEntity.noContent().build();
    }
}
