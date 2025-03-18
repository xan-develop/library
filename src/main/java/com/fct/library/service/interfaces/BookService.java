package com.fct.library.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.fct.library.dto.book.BookCopiesDTO;
import com.fct.library.dto.book.BookDTO;
import com.fct.library.dto.book.CreateBookDTO;
import com.fct.library.dto.book.UpdateBookDTO;
import com.fct.library.model.Book;

public interface BookService {
    List<BookDTO> findAll();
    Optional<BookDTO> findById(Long id);
    Optional<BookCopiesDTO> findCopiesById(Long id);
    List<BookCopiesDTO> findCopies();
    Book save(CreateBookDTO book);
    void deleteById(Long id);
    Optional<BookDTO> update(Long id, UpdateBookDTO updateBookDTO);
}
