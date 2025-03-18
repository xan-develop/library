package com.fct.library.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fct.library.dto.review.ReviewRequestDTO;
import com.fct.library.dto.review.ReviewResponseDTO;
import com.fct.library.model.Book;
import com.fct.library.model.Review;
import com.fct.library.model.User;
import com.fct.library.repository.BookRepository;
import com.fct.library.repository.ReviewRepository;
import com.fct.library.repository.UserRepository;
import com.fct.library.service.interfaces.ReviewService;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    
    public ReviewServiceImpl(ReviewRepository reviewRepository, BookRepository bookRepository, 
                           UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ReviewResponseDTO> findAll() {
        return reviewRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReviewResponseDTO> findById(Long id) {
        return reviewRepository.findById(id)
                .map(this::mapToResponseDTO);
    }

    @Override
    public List<ReviewResponseDTO> findByBookId(Long bookId) {
        return reviewRepository.findByBookId(bookId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> findByUserId(Long userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> findByRating(Integer rating) {
        return reviewRepository.findByRating(rating).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewResponseDTO create(ReviewRequestDTO reviewRequestDTO) {
        User user = userRepository.findById(reviewRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + reviewRequestDTO.getUserId()));
        
        Book book = bookRepository.findById(reviewRequestDTO.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + reviewRequestDTO.getBookId()));
        
        Review review = new Review();
        review.setUser(user);
        review.setBook(book);
        review.setRating(reviewRequestDTO.getRating());
        review.setComment(reviewRequestDTO.getComment());
        
        Review savedReview = reviewRepository.save(review);
        return mapToResponseDTO(savedReview);
    }

    @Override
    @Transactional
    public Optional<ReviewResponseDTO> update(Long id, ReviewRequestDTO reviewRequestDTO) {
        return reviewRepository.findById(id)
                .map(review -> {
                    // Solo actualiza el usuario si se proporciona un ID diferente
                    if (reviewRequestDTO.getUserId() != null && !review.getUser().getId().equals(reviewRequestDTO.getUserId())) {
                        User user = userRepository.findById(reviewRequestDTO.getUserId())
                                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + reviewRequestDTO.getUserId()));
                        review.setUser(user);
                    }
                    
                    // Solo actualiza el libro si se proporciona un ID diferente
                    if (reviewRequestDTO.getBookId() != null && !review.getBook().getId().equals(reviewRequestDTO.getBookId())) {
                        Book book = bookRepository.findById(reviewRequestDTO.getBookId())
                                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + reviewRequestDTO.getBookId()));
                        review.setBook(book);
                    }
                    
                    // Actualiza rating y comentario si no son nulos
                    if (reviewRequestDTO.getRating() != null) {
                        review.setRating(reviewRequestDTO.getRating());
                    }
                    
                    if (reviewRequestDTO.getComment() != null) {
                        review.setComment(reviewRequestDTO.getComment());
                    }
                    
                    Review updatedReview = reviewRepository.save(review);
                    return mapToResponseDTO(updatedReview);
                });
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByUserIdAndBookId(Long userId, Long bookId) {
        return reviewRepository.existsByUserIdAndBookId(userId, bookId);
    }
    
    // Método auxiliar para mapear Review a ReviewResponseDTO
    private ReviewResponseDTO mapToResponseDTO(Review review) {
        return new ReviewResponseDTO(
                review.getId(),
                review.getUser().getId(),
                review.getUser().getName(),
                review.getBook().getTitle(),
                review.getRating(),
                review.getComment()
        );
    }
}
