package com.fct.library.service.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fct.library.dto.loan.CreateLoanDTO;
import com.fct.library.dto.loan.LoanResponseDTO;
import com.fct.library.dto.loan.UpdateLoanDTO;

public interface LoanService {
    List<LoanResponseDTO> findAll();
    Optional<LoanResponseDTO> findById(Long id);
    List<LoanResponseDTO> findByUserId(Long userId);
    List<LoanResponseDTO> findByBookCopyId(Long bookCopyId);
    List<LoanResponseDTO> findActiveLoans();
    List<LoanResponseDTO> findOverdueLoans();
    List<LoanResponseDTO> findByDateRange(LocalDate startDate, LocalDate endDate);
    List<LoanResponseDTO> findActiveLoansByUserId(Long userId);
    LoanResponseDTO createLoan(CreateLoanDTO loanDTO);
    Optional<LoanResponseDTO> updateLoan(Long id, UpdateLoanDTO loanDTO);
    Optional<LoanResponseDTO> returnBook(Long id, LocalDate returnDate);
    boolean deleteLoan(Long id);
}
