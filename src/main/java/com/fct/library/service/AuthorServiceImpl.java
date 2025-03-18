package com.fct.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fct.library.model.Author;
import com.fct.library.repository.AuthorRepository;
import com.fct.library.service.interfaces.AuthorService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository autorRepository;
    
    public AuthorServiceImpl(AuthorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }
    
    @Override
    public List<Author> findAllAuthors() {
        return autorRepository.findAll();
    }
    
    @Override
    public Optional<Author> findAuthorById(Long id) {
        return autorRepository.findById(id);
    }
    
    @Override
    public Author saveAuthor(Author author) {
        return autorRepository.save(author);
    }
    
    @Override
    public void deleteAuthor(Long id) {
        autorRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return autorRepository.existsById(id);
    }
    
    @Override
    public long countAuthors() {
        return autorRepository.count();
    }
    
    @Override
    public List<Author> findAuthorByName(String name) {
        return autorRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Override
    public Author updateAuthor(Long id, Author authorDetails) {
        return autorRepository.findById(id)
            .map(existingAuthor -> {
                existingAuthor.setName(authorDetails.getName());
                return autorRepository.save(existingAuthor);
            })
            .orElse(null);
    }
}
