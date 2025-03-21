package com.fct.library.service;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fct.library.dto.loan.LoanResponseDTO;
import com.fct.library.model.Book;
import com.fct.library.model.BookCopy;
import com.fct.library.model.Loan;
import com.fct.library.model.User;
import com.fct.library.repository.BookCopyRepository;
import com.fct.library.repository.LoanRepository;
import com.fct.library.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookCopyRepository bookCopyRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    private User user1;
    private User user2;
    private Book book;
    private BookCopy bookCopy1;
    private BookCopy bookCopy2;
    private Loan activeLoan1;
    private Loan activeLoan2;
    private Loan returnedLoan;
    private List<Loan> activeLoans;

    @BeforeEach
    void setUp() {
        // Configurar usuarios
        user1 = new User();
        user1.setId(1L);
        user1.setName("Juan Pérez");
        user1.setEmail("juan@example.com");

        user2 = new User();
        user2.setId(2L);
        user2.setName("María López");
        user2.setEmail("maria@example.com");

        // Configurar libro y copias
        book = new Book();
        book.setId(1L);
        book.setTitle("El Quijote");

        bookCopy1 = new BookCopy();
        bookCopy1.setId(1L);
        bookCopy1.setUniqueIdentifier("COPY-123");
        bookCopy1.setBook(book);
        bookCopy1.setOnloan(true);

        bookCopy2 = new BookCopy();
        bookCopy2.setId(2L);
        bookCopy2.setUniqueIdentifier("COPY-456");
        bookCopy2.setBook(book);
        bookCopy2.setOnloan(true);

        // Configurar préstamos
        activeLoan1 = new Loan();
        activeLoan1.setId(1L);
        activeLoan1.setUser(user1);
        activeLoan1.setBookCopy(bookCopy1);
        activeLoan1.setStartDate(LocalDate.now().minusDays(10));
        activeLoan1.setDueDate(LocalDate.now().plusDays(5));
        activeLoan1.setReturnDate(null); // Préstamo activo

        activeLoan2 = new Loan();
        activeLoan2.setId(2L);
        activeLoan2.setUser(user1);
        activeLoan2.setBookCopy(bookCopy2);
        activeLoan2.setStartDate(LocalDate.now().minusDays(5));
        activeLoan2.setDueDate(LocalDate.now().plusDays(10));
        activeLoan2.setReturnDate(null); // Préstamo activo

        returnedLoan = new Loan();
        returnedLoan.setId(3L);
        returnedLoan.setUser(user1);
        returnedLoan.setBookCopy(bookCopy1);
        returnedLoan.setStartDate(LocalDate.now().minusDays(30));
        returnedLoan.setDueDate(LocalDate.now().minusDays(15));
        returnedLoan.setReturnDate(LocalDate.now().minusDays(16)); // Préstamo devuelto

        // Lista de préstamos activos
        activeLoans = List.of(activeLoan1, activeLoan2);
    }

    @Test
    void testFindActiveLoansByUserId() {
        when(loanRepository.findActiveLoansByUserId(1L)).thenReturn(activeLoans);

        // Ejecutar el método a probar
        List<LoanResponseDTO> result = loanService.findActiveLoansByUserId(1L);

        // Verificar resultados
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Verificar los prestamos devueltos 
        assertEquals(1L, result.get(0).getId());
        assertEquals(user1.getId(), result.get(0).getUserId());
        assertEquals(user1.getName(), result.get(0).getUserName());
        assertEquals(bookCopy1.getId(), result.get(0).getBookCopyId());
        assertEquals(book.getTitle(), result.get(0).getBookTitle());
        assertEquals(bookCopy1.getUniqueIdentifier(), result.get(0).getUniqueIdentifier());
        assertNull(result.get(0).getReturnDate());
        
        assertEquals(2L, result.get(1).getId());
        assertEquals(user1.getId(), result.get(1).getUserId());
        assertNull(result.get(1).getReturnDate());

        // Verificar que se llamo al método 
        verify(loanRepository, times(1)).findActiveLoansByUserId(1L);
    }
}
