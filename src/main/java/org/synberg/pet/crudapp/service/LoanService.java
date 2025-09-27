package org.synberg.pet.crudapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.synberg.pet.crudapp.dto.*;
import org.synberg.pet.crudapp.dto.create.LoanCreateDto;
import org.synberg.pet.crudapp.dto.update.LoanUpdateDto;
import org.synberg.pet.crudapp.entity.Book;
import org.synberg.pet.crudapp.entity.Loan;
import org.synberg.pet.crudapp.entity.User;
import org.synberg.pet.crudapp.exception.NotFoundException;
import org.synberg.pet.crudapp.repository.BookRepository;
import org.synberg.pet.crudapp.repository.LoanRepository;
import org.synberg.pet.crudapp.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для управления операциями по выдаче книг (заемами).
 * <p>
 * Обеспечивает создание, обновление, возврат, удаление и получение информации о займах.
 * Работает с сущностями {@link Loan}, {@link User} и {@link Book},
 * возвращая DTO-модели {@link LoanDto}.
 */
@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    /**
     * Находит заем по его идентификатору.
     *
     * @param id идентификатор займа
     * @return заем в виде {@link LoanDto}
     * @throws NotFoundException если заем с указанным id не найден
     */
    public LoanDto find(Long id) {
        return loanRepository.findById(id).map(loan ->
                new LoanDto(
                        loan.getId(),
                        new UserDto(
                                loan.getUser().getId(),
                                loan.getUser().getName(),
                                loan.getUser().getEmail()
                        ),
                        new BookDto(
                                loan.getBook().getId(),
                                loan.getBook().getTitle(),
                                loan.getBook().getAuthor()
                        ),
                        loan.getLoanDate(),
                        loan.getReturnDate())).orElseThrow(() ->
                new NotFoundException("Loan not found"));
    }

    /**
     * Получает список всех займов.
     *
     * @return список DTO займов
     */
    public List<LoanDto> findAll() {
        return loanRepository.findAll().stream().map(loan ->
                new LoanDto(loan.getId(),
                        new UserDto(
                                loan.getUser().getId(),
                                loan.getUser().getName(),
                                loan.getUser().getEmail()
                        ),
                        new BookDto(
                                loan.getBook().getId(),
                                loan.getBook().getTitle(),
                                loan.getBook().getAuthor()
                        ),
                        loan.getLoanDate(), loan.getReturnDate())).toList();
    }

    /**
     * Создаёт новый заем.
     * <p>
     * Проверяет, существует ли пользователь и книга, а также что книга не выдана другому пользователю.
     *
     * @param dto DTO с информацией для создания займа
     * @return созданный заем в виде {@link LoanDto}
     * @throws NotFoundException если пользователь или книга не найдены
     * @throws RuntimeException если книга уже выдана другому пользователю
     */
    public LoanDto create(LoanCreateDto dto) {
        User user = userRepository.findByEmail(dto.userEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Book book = bookRepository.findByTitleAndAuthor(dto.bookTitle(), dto.bookAuthor())
                .orElseThrow(() -> new NotFoundException("Book not found"));

        if (loanRepository.existsByBookAndReturnDateIsNull(book)) {
            throw new RuntimeException("Book is already loaned");
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDateTime.now());

        Loan saved = loanRepository.save(loan);

        return new LoanDto(
                saved.getId(),
                new UserDto(saved.getUser().getId(), saved.getUser().getName(), saved.getUser().getEmail()),
                new BookDto(saved.getBook().getId(), saved.getBook().getTitle(), saved.getBook().getAuthor()),
                saved.getLoanDate(),
                saved.getReturnDate()
        );
    }

    /**
     * Обновляет существующий заем.
     * <p>
     * Можно изменить пользователя, книгу, дату выдачи и дату возврата.
     *
     * @param id идентификатор займа
     * @param loanUpdateDto DTO с новыми данными займа
     * @return обновленный заем в виде {@link LoanDto}
     * @throws NotFoundException если заем, пользователь или книга не найдены
     */
    public LoanDto update(Long id, LoanUpdateDto loanUpdateDto) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Loan not found"));
        if (loanUpdateDto.userId() != null) {
            User user = userRepository.findById(loanUpdateDto.userId())
                    .orElseThrow(() -> new NotFoundException("User not found"));
            loan.setUser(user);
        }
        if (loanUpdateDto.bookId() != null) {
            Book book = bookRepository.findById(loanUpdateDto.bookId())
                    .orElseThrow(() -> new NotFoundException("Book not found"));
            loan.setBook(book);
        }
        if (loanUpdateDto.loanDate() != null) {
            loan.setLoanDate(loanUpdateDto.loanDate());
        }
        if (loanUpdateDto.returnDate() != null) {
            loan.setReturnDate(loanUpdateDto.returnDate());
        }
        Loan savedLoan = loanRepository.save(loan);
        return new LoanDto(
                savedLoan.getId(),
                new UserDto(
                        savedLoan.getUser().getId(),
                        savedLoan.getUser().getName(),
                        savedLoan.getUser().getEmail()
                ),
                new BookDto(
                        savedLoan.getBook().getId(),
                        savedLoan.getBook().getTitle(),
                        savedLoan.getBook().getAuthor()
                ),
                savedLoan.getLoanDate(),
                savedLoan.getReturnDate()
        );
    }

    /**
     * Отмечает заем как возвращенный.
     * <p>
     * Устанавливает текущую дату и время в поле возврата.
     *
     * @param id идентификатор займа
     * @return обновленный заем в виде {@link LoanDto}
     * @throws NotFoundException если заем не найден
     */
    public LoanDto returnLoan(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new NotFoundException("Loan not found"));
        loan.setReturnDate(LocalDateTime.now());
        Loan savedLoan = loanRepository.save(loan);
        return new LoanDto(
                savedLoan.getId(),
                new UserDto(
                        savedLoan.getUser().getId(),
                        savedLoan.getUser().getName(),
                        savedLoan.getUser().getEmail()
                ),
                new BookDto(
                        savedLoan.getBook().getId(),
                        savedLoan.getBook().getTitle(),
                        savedLoan.getBook().getAuthor()
                ),
                savedLoan.getLoanDate(),
                savedLoan.getReturnDate()
        );
    }

    /**
     * Удаляет заем по его идентификатору.
     *
     * @param id идентификатор займа
     * @throws NotFoundException если заем не найден
     */
    public void delete(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new NotFoundException("Loan not found");
        }
        loanRepository.deleteById(id);
    }
}
