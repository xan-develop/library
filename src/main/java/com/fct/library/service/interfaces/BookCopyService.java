package com.fct.library.service.interfaces;

import java.util.List;

import com.fct.library.dto.BookCopyDTO;
import com.fct.library.dto.BookCopyResponseDTO;
import com.fct.library.model.BookCopy;

public interface BookCopyService {
    BookCopy createBookCopy(BookCopy bookCopy);
    BookCopy createBookCopyFromDTO(BookCopyDTO bookCopyDTO);
    BookCopyResponseDTO getBookCopyById(Long id);
    BookCopyResponseDTO getBookCopyByUniqueIdentifier(String uniqueIdentifier);
    List<BookCopyResponseDTO> getAllBookCopies();
    List<BookCopyResponseDTO> getAvailableCopies();
    BookCopy updateBookCopy(Long id, BookCopy bookCopyDetails);
    BookCopy updateBookCopyFromDTO(Long id, BookCopyDTO bookCopyDTO);
    void deleteBookCopy(Long id);
}
