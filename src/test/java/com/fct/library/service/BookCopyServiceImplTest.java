package com.fct.library.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fct.library.dto.BookCopyDTO;
import com.fct.library.dto.BookCopyResponseDTO;
import com.fct.library.model.Author;
import com.fct.library.model.Book;
import com.fct.library.model.BookCopy;
import com.fct.library.repository.BookCopyRepository;
import com.fct.library.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class BookCopyServiceImplTest {
    
    @Mock
    private BookCopyRepository bookCopyRepository;
    
    @Mock
    private BookRepository bookRepository;
    
    @InjectMocks
    private BookCopyServiceImpl bookCopyService;

    private BookCopy bookCopy1;
    private BookCopy bookCopy2;
    private BookCopy bookCopy3;
    private Book book;
    private List<BookCopy> bookCopies;
    private BookCopyDTO bookCopyDTO;

    @BeforeEach
    void setUp() {
        // Crear un libro para las copias
        book = new Book();
        book.setId(1L);
        book.setTitle("El Quijote");
        book.setUniqueCode("QUIJ-001");
        
        Author author = new Author();
        author.setId(1L);
        author.setName("Miguel de Cervantes");
        book.setAuthor(author);

        // Crear copias de libros para las pruebas
        bookCopy1 = new BookCopy();
        bookCopy1.setId(1L);
        bookCopy1.setUniqueIdentifier("COPY-1234567890");
        bookCopy1.setBook(book);
        bookCopy1.setOnloan(true);
        bookCopy1.setPurchased(true);

        bookCopy2 = new BookCopy();
        bookCopy2.setId(2L);
        bookCopy2.setUniqueIdentifier("COPY-0987654321");
        bookCopy2.setBook(book);
        bookCopy2.setOnloan(false);
        bookCopy2.setPurchased(true);

        bookCopy3 = new BookCopy();
        bookCopy3.setId(3L);
        bookCopy3.setUniqueIdentifier("COPY-1357924680");
        bookCopy3.setBook(book);
        bookCopy3.setOnloan(false);
        bookCopy3.setPurchased(false);

        // Lista de copias
        bookCopies = List.of(bookCopy1, bookCopy2, bookCopy3);
        
        // DTO para pruebas
        bookCopyDTO = new BookCopyDTO();
        bookCopyDTO.setBookId(1L);
        bookCopyDTO.setUniqueIdentifier("COPY-NEW123456");
    }

    @Test
    void testCreateBookCopy() {
        when(bookCopyRepository.save(any(BookCopy.class))).thenReturn(bookCopy1);
    
        BookCopy result = bookCopyService.createBookCopy(bookCopy1);
        
        // Verificar resultados
        assertNotNull(result);
        assertEquals(bookCopy1.getId(), result.getId());
        assertEquals(bookCopy1.getUniqueIdentifier(), result.getUniqueIdentifier());
        verify(bookCopyRepository, times(1)).save(any(BookCopy.class));
    }
    
    
    @Test
    void testGetBookCopyByUniqueIdentifier() {
        when(bookCopyRepository.findByUniqueIdentifier(bookCopy1.getUniqueIdentifier())).thenReturn(bookCopy1);
        BookCopyResponseDTO result = bookCopyService.getBookCopyByUniqueIdentifier(bookCopy1.getUniqueIdentifier());
        
        // Verificar resultados
        assertNotNull(result);
        assertEquals(bookCopy1.getId(), result.getId());
        assertEquals(bookCopy1.getUniqueIdentifier(), result.getUniqueIdentifier());
    }
    
    @Test
    void testGetAllBookCopies() {
        when(bookCopyRepository.findAll()).thenReturn(bookCopies);
        
        List<BookCopyResponseDTO> result = bookCopyService.getAllBookCopies();
        
        // Verificar resultados
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(bookCopy1.getId(), result.get(0).getId());
        assertEquals(bookCopy2.getId(), result.get(1).getId());
        assertEquals(bookCopy3.getId(), result.get(2).getId());
    }
    
    @Test
    void testDeleteBookCopy() {
        when(bookCopyRepository.findById(1L)).thenReturn(Optional.of(bookCopy1));
        doNothing().when(bookCopyRepository).delete(any(BookCopy.class));
        
        // Ejecutar el método bajo prueba
        bookCopyService.deleteBookCopy(1L);
        
        // Verificar que se llamó al método delete del repositorio
        verify(bookCopyRepository, times(1)).delete(any(BookCopy.class));
    }
    

    
    @Test
    void testCreateBookCopyFromDTO() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookCopyRepository.save(any(BookCopy.class))).thenAnswer(invocation -> {
            BookCopy savedCopy = invocation.getArgument(0);
            savedCopy.setId(4L); 
            return savedCopy;
        });
        
        BookCopy result = bookCopyService.createBookCopyFromDTO(bookCopyDTO);
        
        // Verificar resultados
        assertNotNull(result);
        assertEquals(4L, result.getId());
        assertEquals(bookCopyDTO.getUniqueIdentifier(), result.getUniqueIdentifier());
        assertEquals(book, result.getBook());
        assertFalse(result.isOnloan());
        }
        

        
        @Test
        void testUpdateBookCopyFromDTO() {
        when(bookCopyRepository.findById(1L)).thenReturn(Optional.of(bookCopy1));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookCopyRepository.save(any(BookCopy.class))).thenAnswer(invocation -> invocation.getArgument(0));
        

        bookCopyDTO.setUniqueIdentifier("UPDATED-UNIQUE-ID");
        bookCopyDTO.setBookId(1L);
        
        // Ejecutar el método bajo prueba
        BookCopy result = bookCopyService.updateBookCopyFromDTO(1L, bookCopyDTO);
        
        // Verificar resultados
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("UPDATED-UNIQUE-ID", result.getUniqueIdentifier());
        assertEquals(book, result.getBook());
        
        // Verificar que el repositorio guardó la copia actualizada
        verify(bookCopyRepository, times(1)).save(bookCopy1);
        }
        
        @Test
        void testGetAvailableCopies() {
        List<BookCopy> availableCopies = List.of(bookCopy2, bookCopy3);
        when(bookCopyRepository.findAvailableCopies()).thenReturn(availableCopies);
        List<BookCopyResponseDTO> result = bookCopyService.getAvailableCopies();
        
        // Verificar resultados
        assertNotNull(result);
        assertEquals(2, result.size());
        assertFalse(result.get(0).getOnloan());
        assertFalse(result.get(1).getOnloan());
    }
}
