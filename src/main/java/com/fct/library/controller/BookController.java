package com.fct.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fct.library.dto.book.BookCopiesDTO;
import com.fct.library.dto.book.BookDTO;
import com.fct.library.dto.book.CreateBookDTO;
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
}
