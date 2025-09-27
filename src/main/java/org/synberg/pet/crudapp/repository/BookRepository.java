package org.synberg.pet.crudapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.synberg.pet.crudapp.entity.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleAndAuthor(String title, String author);
    boolean existsByTitleAndAuthor(String title, String author);
}
