package com.fct.library.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fct.library.dto.book.BookCopiesDTO;
import com.fct.library.dto.book.BookDTO;
import com.fct.library.dto.book.CreateBookDTO;
import com.fct.library.model.Author;
import com.fct.library.model.Book;
import com.fct.library.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository , AuthorService authorService) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
            .map(this::convertToDTO)
            .toList();
    }

    public Optional<BookDTO> findById(Long id) {
        return bookRepository.findById(id)
            .map(this::convertToDTO);
    }
    public Optional<BookCopiesDTO> findCopiesById(Long id) {
        return bookRepository.findById(id)
            .map(this::convertToCopiesDTO);
    }
    public List<BookCopiesDTO> findCopies() {
        return bookRepository.findAll().stream()
            .map(this::convertToCopiesDTO)
            .collect(Collectors.toList());
    }
   
    public Book save(CreateBookDTO book) {
        Author author = authorService.findAuthorById(book.getAuthorId())
            .orElseThrow(() -> new IllegalArgumentException("Author not found"));
        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setGenre(book.getGenre());
        newBook.setUniqueCode(book.getUniqueCode());
        newBook.setAuthor(author);
        return bookRepository.save(newBook);
    }
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
    public Book update(Book book) {
        return bookRepository.save(book);
    }

    private BookDTO convertToDTO(Book book) {
        return new BookDTO(
            book.getId(),
            book.getTitle(),
            book.getGenre(),
            book.getUniqueCode(),
            book.getAuthor().getName()  
        );
    }
    private BookCopiesDTO convertToCopiesDTO(Book book) {
        return new BookCopiesDTO(
            book.getId(),
            book.getTitle(),
            book.getGenre(),
            book.getUniqueCode(),
            book.getAuthor().getName(),
            book.getCopies()
        );
    }

}
