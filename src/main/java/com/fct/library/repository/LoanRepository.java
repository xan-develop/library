package com.fct.library.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fct.library.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId);

    List<Loan> findByBookCopyId(Long bookCopyId);

    List<Loan> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Loan> findByDueDateBefore(LocalDate date);

    List<Loan> findByReturnDateIsNull();

    @Query("SELECT l FROM Loan l WHERE l.user.id = :userId AND l.returnDate IS NULL")
    List<Loan> findActiveLoansByUserId(Long userId);
}
