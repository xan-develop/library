package com.fct.library.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fct.library.dto.review.ReviewRequestDTO;
import com.fct.library.dto.review.ReviewResponseDTO;
import com.fct.library.model.Book;
import com.fct.library.model.Review;
import com.fct.library.model.User;
import com.fct.library.repository.BookRepository;
import com.fct.library.repository.ReviewRepository;
import com.fct.library.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private User user1;
    private User user2;
    private Book book1;
    private Book book2;
    private Review review1;
    private Review review2;
    private Review review3;
    private List<Review> bookReviews;
    private ReviewRequestDTO createreview;

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

        // Configurar libros
        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("El Quijote");

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Cien años de soledad");

        // Configurar reviews
        review1 = new Review();
        review1.setId(1L);
        review1.setUser(user1);
        review1.setBook(book1);
        review1.setRating(5);
        review1.setComment("Excelente libro clásico");

        review2 = new Review();
        review2.setId(2L);
        review2.setUser(user2);
        review2.setBook(book1);
        review2.setRating(4);
        review2.setComment("Muy buen libro, aunque largo");

        review3 = new Review();
        review3.setId(3L);
        review3.setUser(user1);
        review3.setBook(book2);
        review3.setRating(5);
        review3.setComment("Una obra maestra");

        // Configurar DTO de review 
        createreview = new ReviewRequestDTO();
        createreview.setBookId(4L);
        createreview.setComment("Maravilloso");
        createreview.setRating(2);
        createreview.setUserId(2L);

        // Lista de reviews para el libro 1
        bookReviews = List.of(review1, review2 ,review3);
    }

    @Test
    void testCreateReview() {
        // Configurar mocks
        when(userRepository.findById(createreview.getUserId())).thenReturn(java.util.Optional.of(user2));
        when(bookRepository.findById(createreview.getBookId())).thenReturn(java.util.Optional.of(book2));
        
        // Configurar mock para guardar
        Review savedReview = new Review();
        savedReview.setId(4L);
        savedReview.setUser(user2);
        savedReview.setBook(book2);
        savedReview.setRating(createreview.getRating());
        savedReview.setComment(createreview.getComment());
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // Ejecutar prueba
        ReviewResponseDTO newReview = reviewService.create(createreview);

        // Verificaciones
        assertNotNull(newReview);
        assertEquals(user2.getId(), newReview.getUserId());
        assertEquals(createreview.getComment(), newReview.getComment());
        assertEquals(createreview.getRating(), newReview.getRating());
        
        // Verificar que se han llamado 
        verify(userRepository, times(1)).findById(createreview.getUserId());
        verify(bookRepository, times(1)).findById(createreview.getBookId());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void testFindByBookId() {
        // Configurar comportamiento del mock
        when(reviewRepository.findByBookId(1L)).thenReturn(bookReviews);

        // Ejecutar el método a probar
        List<ReviewResponseDTO> result = reviewService.findByBookId(1L);

        // Verificar resultados
        assertNotNull(result);
        assertEquals(3, result.size());
        
        // Verificar el primer review
        assertEquals(1L, result.get(0).getId());
        assertEquals(user1.getId(), result.get(0).getUserId());
        assertEquals(user1.getName(), result.get(0).getUserName());
        assertEquals(book1.getTitle(), result.get(0).getBookTitle());
        assertEquals(5, result.get(0).getRating());
        assertEquals("Excelente libro clásico", result.get(0).getComment());
        
        // Verificar el segundo review
        assertEquals(2L, result.get(1).getId());
        assertEquals(user2.getId(), result.get(1).getUserId());
        assertEquals(user2.getName(), result.get(1).getUserName());
        assertEquals(book1.getTitle(), result.get(1).getBookTitle());
        assertEquals(4, result.get(1).getRating());
        assertEquals("Muy buen libro, aunque largo", result.get(1).getComment());

        // Verificar que se llamó al método del repositorio
        verify(reviewRepository, times(1)).findByBookId(1L);
    }
}
