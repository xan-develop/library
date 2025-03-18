package com.fct.library.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.fct.library.dto.UserDTO;
import com.fct.library.dto.user.UserLoansYearDTO;
import com.fct.library.model.User;

public interface UserService {
    List<UserDTO> findAllUsers();
    Optional<UserDTO> findUserById(Long id);
    User saveUser(User user);
    Optional<User> updateUser(Long id, User userDetails);
    void deleteUser(Long id);
    boolean existsByEmail(String email);
    List<UserLoansYearDTO> findUserWithMoreLoansThisYear();
}
