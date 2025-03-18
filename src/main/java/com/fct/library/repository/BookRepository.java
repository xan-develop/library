package com.fct.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.fct.library.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @NonNull
    @Override
    Optional<Book> findById(@NonNull Long id);
}
