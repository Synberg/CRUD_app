package org.synberg.pet.crudapp.dto.create;

import jakarta.validation.constraints.*;

/**
 * DTO для передачи данных о создании нового одалживания.
 *
 * @param userEmail email пользователя
 * @param bookTitle название книги
 * @param bookAuthor имя автора
 */
public record LoanCreateDto(
        @Email(message = "Invalid email")
        @NotBlank(message = "Email is required")
        String userEmail,

        @NotBlank(message = "Title is required")
        String bookTitle,

        @NotBlank(message = "Author is required")
        String bookAuthor
) {};
