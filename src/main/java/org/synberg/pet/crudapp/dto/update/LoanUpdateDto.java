package org.synberg.pet.crudapp.dto.update;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

/**
 * DTO для передачи данных об обновлении существующего одалживания.
 *
 * @param userId ID пользователя
 * @param bookId ID книги
 * @param loanDate дата одалживания
 * @param returnDate дата возврата одалживания
 */
public record LoanUpdateDto(
        @NotNull(message = "User ID is required")
        Long userId,

        @NotNull(message = "Book ID is required")
        Long bookId,

        @NotNull(message = "Loan date is required")
        LocalDateTime loanDate,

        LocalDateTime returnDate
) {}

