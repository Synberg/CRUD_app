package org.synberg.pet.crudapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.synberg.pet.crudapp.dto.create.BookCreateDto;
import org.synberg.pet.crudapp.dto.BookDto;
import org.synberg.pet.crudapp.dto.update.BookUpdateDto;
import org.synberg.pet.crudapp.exception.NotFoundException;
import org.synberg.pet.crudapp.service.BookService;

import java.util.List;

/**
 * REST-контроллер для управления книгами.
 * <p>
 * Предоставляет CRUD-операции для сущности {@code Book}:
 * <ul>
 *     <li>Создание новой книги</li>
 *     <li>Получение одной или всех книг</li>
 *     <li>Обновление данных книги</li>
 *     <li>Удаление книги</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Операции с книгами")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    /**
     * Получает книгу по ее ID.
     *
     * @param id идентификатор книги
     * @return данные книги в виде {@link BookDto}
     * @throws NotFoundException если книга не найдена
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получить книгу по ID")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.find(id);
    }

    /**
     * Получает список всех книг.
     *
     * @return список книг в виде {@link BookDto}
     */
    @GetMapping
    @Operation(summary = "Получить все сохраненные книги")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    /**
     * Создает новую книгу.
     *
     * @param bookCreateDto данные для создания книги
     * @return созданная книга в виде {@link BookDto}
     */
    @PostMapping
    @Operation(summary = "Создать новую книгу")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        BookDto createdBook = bookService.create(bookCreateDto);
        return ResponseEntity.ok(createdBook);
    }

    /**
     * Обновляет данные существующей книги.
     *
     * @param id            идентификатор книги
     * @param bookUpdateDto новые данные книги
     * @return обновленная книга в виде {@link BookDto}
     * @throws NotFoundException если книга не найдена
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновить книгу по ID")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateDto bookUpdateDto) {
        BookDto updatedBook = bookService.update(id, bookUpdateDto);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Удаляет книгу по ее ID.
     *
     * @param id идентификатор книги
     * @throws NotFoundException если книга не найдена
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить книгу по ID")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }
}
