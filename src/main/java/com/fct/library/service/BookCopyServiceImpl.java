package com.fct.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fct.library.dto.BookCopyDTO;
import com.fct.library.dto.BookCopyResponseDTO;
import com.fct.library.model.Book;
import com.fct.library.model.BookCopy;
import com.fct.library.repository.BookCopyRepository;
import com.fct.library.repository.BookRepository;
import com.fct.library.service.interfaces.BookCopyService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookCopyServiceImpl implements BookCopyService {

    private final BookCopyRepository bookCopyRepository;
    private final BookRepository bookRepository;
    
    
    public BookCopyServiceImpl(BookCopyRepository bookCopyRepository, BookRepository bookRepository) {
        this.bookCopyRepository = bookCopyRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookCopy createBookCopy(BookCopy bookCopy) {
        return bookCopyRepository.save(bookCopy);
    }

    @Override
    public BookCopyResponseDTO getBookCopyById(Long id) {
        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BookCopy not found with id: " + id));
        return mapToResponseDTO(bookCopy);
    }

    @Override
    public BookCopyResponseDTO getBookCopyByUniqueIdentifier(String uniqueIdentifier) {
        BookCopy bookCopy = bookCopyRepository.findByUniqueIdentifier(uniqueIdentifier);
        if (bookCopy == null) {
            throw new EntityNotFoundException("BookCopy not found with unique identifier: " + uniqueIdentifier);
        }
        return mapToResponseDTO(bookCopy);
    }

    @Override
    public List<BookCopyResponseDTO> getAllBookCopies() {
        List<BookCopy> bookCopies = bookCopyRepository.findAll();
        return bookCopies.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public BookCopy updateBookCopy(Long id, BookCopy bookCopyDetails) {
        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BookCopy not found with id: " + id));
        
        bookCopy.setUniqueIdentifier(bookCopyDetails.getUniqueIdentifier());
        bookCopy.setOnloan(bookCopyDetails.isOnloan());
        bookCopy.setPurchased(bookCopyDetails.isPurchased());
        bookCopy.setBook(bookCopyDetails.getBook());
        
        return bookCopyRepository.save(bookCopy);
    }

    @Override
    public void deleteBookCopy(Long id) {
        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BookCopy not found with id: " + id));
        bookCopyRepository.delete(bookCopy);
    }

    @Override
    public BookCopy createBookCopyFromDTO(BookCopyDTO bookCopyDTO) {
        Book book = bookRepository.findById(bookCopyDTO.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookCopyDTO.getBookId()));
        
        BookCopy bookCopy = new BookCopy();
        bookCopy.setUniqueIdentifier(bookCopyDTO.getUniqueIdentifier());
        bookCopy.setBook(book);
        bookCopy.setOnloan(false);
        bookCopy.setPurchased(false);
        
        return bookCopyRepository.save(bookCopy);
    }

    @Override
    public BookCopy updateBookCopyFromDTO(Long id, BookCopyDTO bookCopyDTO) {
        BookCopy bookCopy = bookCopyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BookCopy not found with id: " + id));
        Book book = bookRepository.findById(bookCopyDTO.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookCopyDTO.getBookId()));
        
        bookCopy.setUniqueIdentifier(bookCopyDTO.getUniqueIdentifier());
        bookCopy.setBook(book);
        
        return bookCopyRepository.save(bookCopy);
    }
    
    @Override
    public List<BookCopyResponseDTO> getAvailableCopies() {
        List<BookCopy> availableCopies = bookCopyRepository.findAvailableCopies();
        return availableCopies.stream()
            .map(this::mapToResponseDTO)
            .toList();
    }

    private BookCopyResponseDTO mapToResponseDTO(BookCopy bookCopy) {
        return new BookCopyResponseDTO(
            bookCopy.getId(),
            bookCopy.getUniqueIdentifier(),
            bookCopy.getBook().getTitle(),
            bookCopy.isOnloan(),
            bookCopy.isPurchased()
        );
    }
}
