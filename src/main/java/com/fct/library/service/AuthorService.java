package com.fct.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fct.library.model.Author;
import com.fct.library.repository.AuthorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthorService {
    private final AuthorRepository autorRepository;
    
    public AuthorService(AuthorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }
    
    public List<Author> findAllAuthors() {
        return autorRepository.findAll();
    }
    
    public Optional<Author> findAuthorById(Long id) {
        return autorRepository.findById(id);
    }
    
    public Author saveAuthor(Author author) {
        return autorRepository.save(author);
    }
    
    public void deleteAuthor(Long id) {
        autorRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return autorRepository.existsById(id);
    }
    
    public long countAuthors() {
        return autorRepository.count();
    }
    
    public Optional<Author> findAuthorByName(String name) {
        return autorRepository.findByName(name);
    }
    
    public Author updateAuthor(Long id, Author authorDetails) {
        return autorRepository.findById(id)
            .map(existingAuthor -> {
                existingAuthor.setName(authorDetails.getName());
                return autorRepository.save(existingAuthor);
            })
            .orElse(null);
    }
}
