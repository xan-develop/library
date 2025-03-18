package com.fct.library.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.fct.library.model.Author;
import com.fct.library.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/author")
@Tag(name = "Author", description = "API para gestionar autores")
public class AuthorController {
    
    private final AuthorService authorService;
    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    
    @Operation(summary = "Obtener todos los autores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de autores obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        logger.info("Obteniendo todos los autores");
        return ResponseEntity.ok(authorService.findAllAuthors());
    }
    
    @Operation(summary = "Obtener un autor por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        logger.info("Buscando autor con ID: {}", id);
        return authorService.findAuthorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Autor con ID: {} no encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }
    
    @Operation(summary = "Crear un nuevo autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de autor inválidos")
    })
    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        logger.info("Creando nuevo autor: {}", author.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.saveAuthor(author));
    }
    
    @Operation(summary = "Actualizar un autor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de autor inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author author) {
        logger.info("Actualizando autor con ID: {}", id);
        
        if (!authorService.existsById(id)) {
            logger.warn("Autor con ID: {} no encontrado para actualización", id);
            return ResponseEntity.notFound().build();
        }
        
        author.setId(id); // Asegurar que el ID sea el correcto
        Author updatedAuthor = authorService.updateAuthor(id, author);
        logger.info("Autor con ID: {} actualizado correctamente", id);
        return ResponseEntity.ok(updatedAuthor);
    }
    
    @Operation(summary = "Eliminar un autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        logger.info("Eliminando autor con ID: {}", id);
        
        if (!authorService.existsById(id)) {
            logger.warn("Autor con ID: {} no encontrado para eliminación", id);
            return ResponseEntity.notFound().build();
        }
        
        authorService.deleteAuthor(id);
        logger.info("Autor con ID: {} eliminado correctamente", id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Buscar autor por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<Author> getAuthorByName(@PathVariable String name) {
        logger.info("Buscando autor con nombre: {}", name);
        return authorService.findAuthorByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Autor con nombre: {} no encontrado", name);
                    return ResponseEntity.notFound().build();
                });
    }
}
