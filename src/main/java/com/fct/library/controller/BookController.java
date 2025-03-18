package com.fct.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fct.library.dto.book.BookCopiesDTO;
import com.fct.library.dto.book.BookDTO;
import com.fct.library.dto.book.CreateBookDTO;
import com.fct.library.dto.book.UpdateBookDTO;
import com.fct.library.model.Book;
import com.fct.library.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/book")
@Tag(name = "Book", description = "The Book API")
public class BookController {
    private final BookService bookService;
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Todos los libros con sus copias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all copies"),
            @ApiResponse(responseCode = "404", description = "No copies found")
    })
    @GetMapping("/copies")
    public ResponseEntity<List<BookCopiesDTO>> findCopies() {
        return ResponseEntity.ok(bookService.findCopies());
    }

    @Operation(summary = "Buscar libros con sus copias por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found copies by id"),
            @ApiResponse(responseCode = "404", description = "No copies found")
    })
    @GetMapping("/copies/{id}")
    public ResponseEntity<BookCopiesDTO> findCopiesById(@PathVariable Long id) {
        return bookService.findCopiesById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un libro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Book> save(@RequestBody CreateBookDTO book) {
        logger.info("Creating book: {}", book);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.save(book));
    }

    @Operation(summary = "Actualizar un libro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody UpdateBookDTO bookDTO) {
        logger.info("Actualizando libro con id: {}", id);
        
        // Asegurar que el ID en el path y en el DTO coincidan, solo si el DTO tiene ID
        if (bookDTO.getId() != null && !id.equals(bookDTO.getId())) {
            logger.warn("ID en path ({}) no coincide con ID en body ({})", id, bookDTO.getId());
            return ResponseEntity.badRequest().build();
        }
        
        return bookService.update(id, bookDTO)
                .map(updatedBookDTO -> {
                    logger.info("Libro actualizado: {}", updatedBookDTO.getTitle());
                    return ResponseEntity.ok(updatedBookDTO);
                })
                .orElseGet(() -> {
                    logger.warn("Libro con id: {} no encontrado para actualizar", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Eliminar un libro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Libro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        logger.info("Eliminando libro con id: {}", id);
        
        // Verificar si el libro existe antes de intentar eliminarlo
        if (!bookService.findById(id).isPresent()) {
            logger.warn("Libro con id: {} no encontrado para eliminar", id);
            return ResponseEntity.notFound().build();
        }
        
        bookService.deleteById(id);
        logger.info("Libro con id: {} eliminado correctamente", id);
        
        return ResponseEntity.noContent().build();
    }
}
