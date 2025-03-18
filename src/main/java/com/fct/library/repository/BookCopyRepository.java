package com.fct.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fct.library.model.BookCopy;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    BookCopy findByUniqueIdentifier(String uniqueIdentifier);
    @Query("SELECT bc FROM BookCopy bc WHERE bc.onloan = false AND bc.purchased = false")
    List<BookCopy> findAvailableCopies();

}
