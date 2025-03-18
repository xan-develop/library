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

import com.fct.library.dto.review.ReviewRequestDTO;
import com.fct.library.dto.review.ReviewResponseDTO;
import com.fct.library.service.interfaces.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Review", description = "API de gestión de reseñas de libros")
public class ReviewController {

    private final ReviewService reviewService;
    private final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las reseñas")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviews() {
        logger.info("Obteniendo todas las reseñas");
        return ResponseEntity.ok(reviewService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una reseña por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Long id) {
        logger.info("Obteniendo reseña con ID: {}", id);
        return reviewService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Obtener reseñas por ID de libro")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByBookId(@PathVariable Long bookId) {
        logger.info("Obteniendo reseñas para el libro con ID: {}", bookId);
        List<ReviewResponseDTO> reviews = reviewService.findByBookId(bookId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener reseñas por ID de usuario")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByUserId(@PathVariable Long userId) {
        logger.info("Obteniendo reseñas del usuario con ID: {}", userId);
        List<ReviewResponseDTO> reviews = reviewService.findByUserId(userId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/rating/{rating}")
    @Operation(summary = "Obtener reseñas por calificación")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByRating(@PathVariable Integer rating) {
        logger.info("Obteniendo reseñas con calificación: {}", rating);
        List<ReviewResponseDTO> reviews = reviewService.findByRating(rating);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva reseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Reseña creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o el usuario ya ha reseñado este libro")
    })
    public ResponseEntity<Object> createReview(@Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
        logger.info("Creando nueva reseña para libro ID: {} por usuario ID: {}", 
                reviewRequestDTO.getBookId(), reviewRequestDTO.getUserId());
        
        // Verificar si el usuario ya ha reseñado este libro
        if (reviewService.existsByUserIdAndBookId(reviewRequestDTO.getUserId(), reviewRequestDTO.getBookId())) {
            logger.warn("El usuario ya ha reseñado este libro");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("El usuario ya ha reseñado este libro");
        }
        
        ReviewResponseDTO createdReview = reviewService.create(reviewRequestDTO);
        logger.info("Reseña creada correctamente con ID: {}", createdReview.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una reseña existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long id, 
                                                        @Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
        logger.info("Actualizando reseña con ID: {}", id);
        return reviewService.update(id, reviewRequestDTO)
                .map(updatedReview -> {
                    logger.info("Reseña actualizada correctamente con ID: {}", updatedReview.getId());
                    return ResponseEntity.ok(updatedReview);
                })
                .orElseGet(() -> {
                    logger.warn("No se pudo encontrar la reseña con ID: {} para actualizar", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una reseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reseña eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        logger.info("Eliminando reseña con ID: {}", id);
        if (reviewService.delete(id)) {
            logger.info("Reseña eliminada correctamente con ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("No se pudo encontrar la reseña con ID: {} para eliminar", id);
            return ResponseEntity.notFound().build();
        }
    }
}
