package com.fct.library.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fct.library.dto.UserDTO;
import com.fct.library.dto.loan.LoanResponseDTO;
import com.fct.library.dto.user.UserLoansYearDTO;
import com.fct.library.model.User;
import com.fct.library.repository.UserRepository;
import com.fct.library.service.interfaces.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public Optional<UserDTO> findUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (userDetails.getName() != null) {
                        existingUser.setName(userDetails.getName());
                    }
                    if (userDetails.getEmail() != null) {
                        if (!existingUser.getEmail().equals(userDetails.getEmail()) &&
                            userRepository.existsByEmail(userDetails.getEmail())) {
                            throw new IllegalArgumentException("El email ya está registrado");
                        }
                        existingUser.setEmail(userDetails.getEmail());
                    }
                    if (userDetails.getPhone() != null) {
                        existingUser.setPhone(userDetails.getPhone());
                    }
                    return userRepository.save(existingUser);
                });
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    @Override
    public List<UserLoansYearDTO> findUserWithMoreLoansThisYear() {
        return userRepository.findUserWithMoreLoansThisYear().stream()
                .toList();
    }

    // Método auxiliar para convertir User a UserDTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getLoans().stream()
                    .map(loan -> new LoanResponseDTO(
                            loan.getId(),
                            loan.getUser().getId(),
                            loan.getUser().getName(),
                            loan.getBookCopy().getId(),
                            loan.getBookCopy().getBook().getTitle(),
                            loan.getBookCopy().getUniqueIdentifier(),
                            loan.getStartDate(),
                            loan.getDueDate(),
                            loan.getReturnDate()
                    ))
                    .collect(Collectors.toList())
        );
    }

    
}
