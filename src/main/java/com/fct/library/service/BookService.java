package com.fct.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fct.library.dto.book.BookCopiesDTO;
import com.fct.library.dto.book.BookDTO;
import com.fct.library.dto.book.CreateBookDTO;
import com.fct.library.dto.book.UpdateBookDTO;
import com.fct.library.model.Author;
import com.fct.library.model.Book;
import com.fct.library.model.Category;
import com.fct.library.repository.BookRepository;
import com.fct.library.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, AuthorService authorService, CategoryRepository categoryRepository) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
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
            .toList();
    }
   
    public Book save(CreateBookDTO book) {
        Author author = authorService.findAuthorById(book.getAuthorId())
            .orElseThrow(() -> new IllegalArgumentException("Author not found"));
            
        Category category = categoryRepository.findById(book.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            
        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setUniqueCode(book.getUniqueCode());
        newBook.setAuthor(author);
        newBook.setCategory(category);
        
        return bookRepository.save(newBook);
    }
    
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
    
    public Optional<BookDTO> update(Long id, UpdateBookDTO updateBookDTO) {
        return bookRepository.findById(id)
            .map(existingBook -> {
                // Solo actualiza título si no es null
                if (updateBookDTO.getTitle() != null) {
                    existingBook.setTitle(updateBookDTO.getTitle());
                }
                
                // Solo actualiza código único si no es null
                if (updateBookDTO.getUniqueCode() != null) {
                    existingBook.setUniqueCode(updateBookDTO.getUniqueCode());
                }
                
                // Solo actualiza autor si no es null
                if (updateBookDTO.getAuthorId() != null) {
                    Author author = authorService.findAuthorById(updateBookDTO.getAuthorId())
                        .orElseThrow(() -> new IllegalArgumentException("Author not found"));
                    existingBook.setAuthor(author);
                }
                
                //  Solo actualiza categoría si no es null
                if (updateBookDTO.getCategoryId() != null) {
                    Category category = categoryRepository.findById(updateBookDTO.getCategoryId())
                        .orElseThrow(() -> new IllegalArgumentException("Category not found"));
                    existingBook.setCategory(category);
                }
                
                Book savedBook = bookRepository.save(existingBook);
                return convertToDTO(savedBook);
            });
    }

    private BookDTO convertToDTO(Book book) {
        return new BookDTO(
            book.getId(),
            book.getTitle(),
            book.getCategory() != null ? book.getCategory().getName() : null,
            book.getUniqueCode(),
            book.getAuthor().getName()  
        );
    }
    private BookCopiesDTO convertToCopiesDTO(Book book) {
        return new BookCopiesDTO(
            book.getId(),
            book.getTitle(),
            book.getCategory() != null ? book.getCategory().getName() : null,
            book.getUniqueCode(),
            book.getAuthor().getName(),
            book.getCopies()
        );
    }
}
