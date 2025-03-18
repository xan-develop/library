package com.fct.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.fct.library.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Override
    @NonNull Optional<Author> findById(@NonNull Long id);
    Optional<Author> findByName(String name);

}
