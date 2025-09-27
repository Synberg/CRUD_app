package org.synberg.pet.crudapp.dto.create;

import jakarta.validation.constraints.*;

/**
 * DTO для передачи данных о создании нового одалживания.
 *
 * @param userName имя пользователя
 * @param userEmail email пользователя
 * @param bookTitle название книги
 * @param bookAuthor имя автора
 */
public record LoanCreateDto(
        @NotBlank(message = "Name is required")
        String userName,

        @Email(message = "Invalid email")
        @NotBlank(message = "Email is required")
        String userEmail,

        @NotBlank(message = "Title is required")
        String bookTitle,

        @NotBlank(message = "Author is required")
        String bookAuthor
) {};
