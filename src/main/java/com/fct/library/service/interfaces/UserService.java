package com.fct.library.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.fct.library.dto.UserDTO;
import com.fct.library.dto.user.UserLoansYearDTO;
import com.fct.library.model.User;
import com.fct.library.dto.user.CreateUserDTO;
import com.fct.library.dto.user.UpdateUserDTO;

public interface UserService {
    List<UserDTO> findAllUsers();
    Optional<UserDTO> findUserById(Long id);
    User saveUser(CreateUserDTO createUserDTO);
    Optional<User> updateUser(Long id, UpdateUserDTO updateUserDTO);
    void deleteUser(Long id);
    boolean existsByEmail(String email);
    List<UserLoansYearDTO> findUserWithMoreLoansThisYear();
}
