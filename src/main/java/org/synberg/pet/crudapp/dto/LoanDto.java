package org.synberg.pet.crudapp.dto;

import java.time.LocalDateTime;

public record LoanDto(
        Long id,
        UserDto user,
        BookDto book,
        LocalDateTime loanDate,
        LocalDateTime returnDate
) {};
