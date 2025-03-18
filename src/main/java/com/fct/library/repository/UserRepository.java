package com.fct.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fct.library.dto.user.UserLoansYearDTO;
import com.fct.library.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    boolean existsByEmail(String email);
    
    @Query("SELECT new com.fct.library.dto.user.UserLoansYearDTO(u.id, u.name, COUNT(l)) " +
       "FROM User u JOIN u.loans l " +
       "WHERE EXTRACT(YEAR FROM l.startDate) = EXTRACT(YEAR FROM CURRENT_DATE) " +
       "GROUP BY u.id, u.name " +
       "ORDER BY COUNT(l) DESC")
List<UserLoansYearDTO> findUserWithMoreLoansThisYear();

}
