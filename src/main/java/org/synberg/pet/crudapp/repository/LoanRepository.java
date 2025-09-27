package org.synberg.pet.crudapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.synberg.pet.crudapp.entity.Book;
import org.synberg.pet.crudapp.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByBookAndReturnDateIsNull(Book book);
}
