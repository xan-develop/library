package com.fct.library.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.fct.library.dto.review.ReviewRequestDTO;
import com.fct.library.dto.review.ReviewResponseDTO;

public interface ReviewService {
    List<ReviewResponseDTO> findAll();
    Optional<ReviewResponseDTO> findById(Long id);
    List<ReviewResponseDTO> findByBookId(Long bookId);
    List<ReviewResponseDTO> findByUserId(Long userId);
    List<ReviewResponseDTO> findByRating(Integer rating);
    ReviewResponseDTO create(ReviewRequestDTO reviewRequestDTO);
    Optional<ReviewResponseDTO> update(Long id, ReviewRequestDTO reviewRequestDTO);
    boolean delete(Long id);
    boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
