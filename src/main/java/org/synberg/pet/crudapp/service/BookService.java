package org.synberg.pet.crudapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.synberg.pet.crudapp.dto.create.BookCreateDto;
import org.synberg.pet.crudapp.dto.BookDto;
import org.synberg.pet.crudapp.dto.update.BookUpdateDto;
import org.synberg.pet.crudapp.entity.Book;
import org.synberg.pet.crudapp.exception.AlreadyExistsException;
import org.synberg.pet.crudapp.exception.NotFoundException;
import org.synberg.pet.crudapp.repository.BookRepository;

import java.util.List;

/**
 * Сервис для управления книгами в библиотеке.
 * <p>
 * Предоставляет методы для CRUD-операций: создание, получение,
 * обновление и удаление книг. Работает с сущностью {@link Book}
 * и возвращает DTO-модели {@link BookDto}.
 */
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    /**
     * Находит книгу по её идентификатору.
     *
     * @param id идентификатор книги
     * @return DTO книги {@link BookDto}
     * @throws NotFoundException если книга с указанным id не найдена
     */
    public BookDto find(Long id) {
        return bookRepository.findById(id).map(book ->
                new BookDto(book.getId(), book.getTitle(), book.getAuthor())).orElseThrow(() ->
                new NotFoundException("Book not found"));
    }

    /**
     * Получает список всех книг в библиотеке.
     *
     * @return список DTO книг
     */
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(book ->
                new BookDto(book.getId(), book.getTitle(), book.getAuthor())).toList();
    }

    /**
     * Создаёт новую книгу на основе данных из {@link BookCreateDto}.
     *
     * @param bookCreateDto DTO с данными для создания книги
     * @return созданная книга в виде {@link BookDto}
     */
    public BookDto create(BookCreateDto bookCreateDto) {
        if (bookRepository.existsByTitleAndAuthor(bookCreateDto.title(), bookCreateDto.author())) {
            throw new AlreadyExistsException("Book already exists");
        }
        Book book = new Book();
        book.setTitle(bookCreateDto.title());
        book.setAuthor(bookCreateDto.author());
        bookRepository.save(book);
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor());
    }

    /**
     * Обновляет данные существующей книги.
     *
     * @param id            идентификатор книги
     * @param bookUpdateDto DTO с новыми данными книги
     * @return обновлённая книга в виде {@link BookDto}
     * @throws NotFoundException если книга с указанным id не найдена
     */
    public BookDto update(Long id, BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        book.setTitle(bookUpdateDto.title());
        book.setAuthor(bookUpdateDto.author());
        Book updatedBook = bookRepository.save(book);
        return new BookDto(updatedBook.getId(), updatedBook.getTitle(), updatedBook.getAuthor());
    }

    /**
     * Удаляет книгу по её идентификатору.
     *
     * @param id идентификатор книги
     * @throws NotFoundException если книга с указанным id не найдена
     */
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
    }
}
