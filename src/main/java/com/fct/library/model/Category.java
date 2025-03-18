package com.fct.library.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "category")
@Schema(description = "Representa una categoría de libros")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la categoría", example = "1")
    private Long id;

    @Column(nullable = false, length = 255)
    @Schema(description = "Nombre de la categoría", example = "Ficción")
    private String name;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
