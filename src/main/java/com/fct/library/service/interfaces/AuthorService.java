package com.fct.library.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.fct.library.model.Author;

public interface AuthorService {
    List<Author> findAllAuthors();
    Optional<Author> findAuthorById(Long id);
    Author saveAuthor(Author author);
    void deleteAuthor(Long id);
    boolean existsById(Long id);
    long countAuthors();
    List<Author> findAuthorByName(String name);
    Author updateAuthor(Long id, Author authorDetails);
}
