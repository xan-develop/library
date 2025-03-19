package com.fct.library.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import com.fct.library.model.Author;
import com.fct.library.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {
    @Mock
    private AuthorRepository authorRepository;
    
    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        Author author1 = new Author("John Doe");
        author1.setId(1L);
        Author author2 = new Author("Jane Doe");
        author2.setId(2L);
        Author author3 = new Author("Mark Twain");
        author3.setId(3L);

        author = author1;
    }

    @Test
    void testCreateAuthor() {
        when(authorRepository.save(author)).thenReturn(author);
        Author savedauthor = authorService.saveAuthor(author);
        Assertions.assertEquals("John Doe", savedauthor.getName());
        Assertions.assertNotNull(authorService.findAuthorById(author.getId()));
    }
    @Test
    void testFindAuthorById() {
        when(authorRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(author));
        Optional<Author> foundAuthor = authorService.findAuthorById(1L);
        Assertions.assertEquals("John Doe", foundAuthor.get().getName());
    }

}