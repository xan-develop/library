package com.fct.library.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fct.library.dto.loan.CreateLoanDTO;
import com.fct.library.dto.loan.LoanResponseDTO;
import com.fct.library.dto.loan.UpdateLoanDTO;
import com.fct.library.model.BookCopy;
import com.fct.library.model.Loan;
import com.fct.library.model.User;
import com.fct.library.repository.BookCopyRepository;
import com.fct.library.repository.LoanRepository;
import com.fct.library.repository.UserRepository;
import com.fct.library.service.interfaces.LoanService;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookCopyRepository bookCopyRepository;

    public LoanServiceImpl(LoanRepository loanRepository,
            UserRepository userRepository,
            BookCopyRepository bookCopyRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    public List<LoanResponseDTO> findAll() {
        return loanRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LoanResponseDTO> findById(Long id) {
        return loanRepository.findById(id)
                .map(this::mapToDTO);
    }

    @Override
    public List<LoanResponseDTO> findByUserId(Long userId) {
        return loanRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanResponseDTO> findByBookCopyId(Long bookCopyId) {
        return loanRepository.findByBookCopyId(bookCopyId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanResponseDTO> findActiveLoans() {
        return loanRepository.findByReturnDateIsNull().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<LoanResponseDTO> findActiveLoansByUserId(Long userId) {
        return loanRepository.findActiveLoansByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<LoanResponseDTO> findOverdueLoans() {
        return loanRepository.findByReturnDateIsNull().stream()
                .filter(loan -> LocalDate.now().isAfter(loan.getDueDate()))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanResponseDTO> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return loanRepository.findByStartDateBetween(startDate, endDate).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LoanResponseDTO createLoan(CreateLoanDTO loanDTO) {
        User user = userRepository.findById(loanDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + loanDTO.getUserId()));

        BookCopy bookCopy = bookCopyRepository.findById(loanDTO.getBookCopyId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Copia de libro no encontrada con ID: " + loanDTO.getBookCopyId()));

        // Verificar si la copia ya está prestada
        if (bookCopy.isOnloan()) {
            throw new IllegalStateException("La copia del libro ya está prestada");
        }

        // Verificar que la fecha de inicio sea anterior a la fecha de vencimiento
        if (loanDTO.getStartDate() != null && loanDTO.getDueDate() != null) {
            if (!loanDTO.getStartDate().isBefore(loanDTO.getDueDate())) {
                throw new EntityNotFoundException("La fecha de inicio debe ser anterior a la fecha de vencimiento");
            }
        }

        // Crear el préstamo
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBookCopy(bookCopy);
        loan.setStartDate(loanDTO.getStartDate());
        loan.setDueDate(loanDTO.getDueDate());

        // Marcar la copia como prestada
        bookCopy.setOnloan(true);
        bookCopyRepository.save(bookCopy);

        Loan savedLoan = loanRepository.save(loan);
        return mapToDTO(savedLoan);
    }

    @Override
    @Transactional
    public Optional<LoanResponseDTO> updateLoan(Long id, UpdateLoanDTO loanDTO) {
        return loanRepository.findById(id)
                .map(loan -> {
                    // Actualizar usuario si es necesario
                    if (loanDTO.getUserId() != null) {
                        User user = userRepository.findById(loanDTO.getUserId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "Usuario no encontrado con ID: " + loanDTO.getUserId()));
                        loan.setUser(user);
                    }

                    // Actualizar copia del libro si es necesario
                    if (loanDTO.getBookCopyId() != null
                            && !loan.getBookCopy().getId().equals(loanDTO.getBookCopyId())) {
                        // Liberar la copia anterior
                        BookCopy oldCopy = loan.getBookCopy();
                        oldCopy.setOnloan(false);
                        bookCopyRepository.save(oldCopy);

                        // Asignar la nueva copia
                        BookCopy newCopy = bookCopyRepository.findById(loanDTO.getBookCopyId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "Copia de libro no encontrada con ID: " + loanDTO.getBookCopyId()));

                        if (newCopy.isOnloan()) {
                            throw new IllegalStateException("La nueva copia del libro ya está prestada");
                        }

                        newCopy.setOnloan(true);
                        bookCopyRepository.save(newCopy);
                        loan.setBookCopy(newCopy);
                    }

                    // Verificar que la fecha de inicio sea anterior a la fecha de vencimiento
                    if (loanDTO.getStartDate() != null && loanDTO.getDueDate() != null) {
                        if (!loanDTO.getStartDate().isBefore(loanDTO.getDueDate())) {
                            throw new IllegalArgumentException(
                                    "La fecha de inicio debe ser anterior a la fecha de vencimiento");
                        }
                    }

                    // Actualizar fechas si es necesario
                    if (loanDTO.getStartDate() != null) {
                        loan.setStartDate(loanDTO.getStartDate());
                    }

                    if (loanDTO.getDueDate() != null) {
                        loan.setDueDate(loanDTO.getDueDate());
                    }

                    // Actualizar fecha de devolución si es necesario
                    if (loanDTO.getReturnDate() != null && (loan.getReturnDate() == null
                            || !loan.getReturnDate().equals(loanDTO.getReturnDate()))) {
                        loan.setReturnDate(loanDTO.getReturnDate());

                        // Si se establece una fecha de devolución, liberar la copia
                        if (loanDTO.getReturnDate() != null) {
                            BookCopy bookCopy = loan.getBookCopy();
                            bookCopy.setOnloan(false);
                            bookCopyRepository.save(bookCopy);
                        }
                    }

                    Loan updatedLoan = loanRepository.save(loan);
                    return mapToDTO(updatedLoan);
                });
    }

    @Override
    @Transactional
    public Optional<LoanResponseDTO> returnBook(Long id, LocalDate returnDate) {
        return loanRepository.findById(id)
                .map(loan -> {
                    // Validar si el préstamo ya fue devuelto
                    if (loan.getReturnDate() != null) {
                        throw new IllegalStateException("Este préstamo ya ha sido devuelto");
                    }

                    // Establecer la fecha de devolución
                    loan.setReturnDate(returnDate);

                    // Liberar la copia del libro
                    BookCopy bookCopy = loan.getBookCopy();
                    if (!bookCopy.isOnloan()) {
                        throw new IllegalStateException("La copia del libro ya está marcada como no prestada");
                    }
                    bookCopy.setOnloan(false);
                    bookCopyRepository.save(bookCopy);

                    // Guardar el préstamo actualizado
                    Loan updatedLoan = loanRepository.save(loan);
                    return mapToDTO(updatedLoan);
                })
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException("Préstamo no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public boolean deleteLoan(Long id) {
        return loanRepository.findById(id)
                .map(loan -> {
                    // Si el préstamo está activo, liberar la copia del libro primero
                    if (loan.getReturnDate() == null) {
                        BookCopy bookCopy = loan.getBookCopy();
                        bookCopy.setOnloan(false);
                        bookCopyRepository.save(bookCopy);
                    }

                    loanRepository.delete(loan);
                    return true;
                })
                .orElse(false);
    }

    // Método auxiliar para mapear Loan a LoanResponseDTO
    private LoanResponseDTO mapToDTO(Loan loan) {
        return new LoanResponseDTO(
                loan.getId(),
                loan.getUser().getId(),
                loan.getUser().getName(),
                loan.getBookCopy().getId(),
                loan.getBookCopy().getBook().getTitle(),
                loan.getBookCopy().getUniqueIdentifier(),
                loan.getStartDate(),
                loan.getDueDate(),
                loan.getReturnDate());
    }
}
