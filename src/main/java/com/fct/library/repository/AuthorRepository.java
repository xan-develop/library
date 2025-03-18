package com.fct.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fct.library.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findById(Long id);
    Optional<Author> findByName(String name);

}
