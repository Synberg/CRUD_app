package org.synberg.pet.crudapp.dto.create;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO для передачи данных о создании новой книги.
 *
 * @param title название книги
 * @param author автор книги
 */
public record BookCreateDto(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Author is required")
        String author
) {}

