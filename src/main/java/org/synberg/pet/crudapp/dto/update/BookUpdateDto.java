package org.synberg.pet.crudapp.dto.update;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO для передачи данных об обновлении существующей книги.
 *
 * @param title название книги
 * @param author автор книги
 */
public record BookUpdateDto (
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Author is required")
        String author
) {}
