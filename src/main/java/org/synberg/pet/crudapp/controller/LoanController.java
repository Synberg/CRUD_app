package org.synberg.pet.crudapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.synberg.pet.crudapp.dto.create.LoanCreateDto;
import org.synberg.pet.crudapp.dto.LoanDto;
import org.synberg.pet.crudapp.dto.update.LoanUpdateDto;
import org.synberg.pet.crudapp.exception.NotFoundException;
import org.synberg.pet.crudapp.service.LoanService;

import java.util.List;

/**
 * REST-контроллер для управления выдачами книг (Loan).
 * <p>
 * Поддерживает операции:
 * <ul>
 *     <li>Создание выдачи книги пользователю</li>
 *     <li>Получение информации о выдаче</li>
 *     <li>Обновление данных о выдаче</li>
 *     <li>Фиксация возврата книги</li>
 *     <li>Удаление выдачи</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/loans")
@Tag(name = "Loans", description = "Операции с одалживаниями")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    /**
     * Получает выдачу по её ID.
     *
     * @param id идентификатор выдачи
     * @return данные о выдаче в виде {@link LoanDto}
     * @throws NotFoundException если одалживание не найдено
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получить одалживание по ID")
    public LoanDto getLoanById(@PathVariable Long id) {
        return loanService.find(id);
    }

    /**
     * Получает список всех выдач.
     *
     * @return список выдач в виде {@link LoanDto}
     */
    @GetMapping
    @Operation(summary = "Получить все сохраненные одалживания")
    public List<LoanDto> getAllLoans() {
        return loanService.findAll();
    }

    /**
     * Создает новую выдачу книги пользователю.
     *
     * @param loanCreateDto данные для создания выдачи
     * @return созданная выдача в виде {@link LoanDto}
     * @throws NotFoundException если пользователь/книга не найдены
     * @throws RuntimeException если книга уже выдана
     */
    @PostMapping
    @Operation(summary = "Создать новое одалживание")
    public ResponseEntity<LoanDto> createLoan(@Valid @RequestBody LoanCreateDto loanCreateDto) {
        LoanDto createdLoan = loanService.create(loanCreateDto);
        return ResponseEntity.ok(createdLoan);
    }

    /**
     * Обновляет существующую выдачу.
     *
     * @param id идентификатор выдачи
     * @param loanUpdateDto новые данные выдачи
     * @return обновленная выдача в виде {@link LoanDto}
     * @throws NotFoundException если одалживание/пользователь/книга не найдены
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновить одалживание по ID")
    public ResponseEntity<LoanDto> updateLoan(@PathVariable Long id, @Valid @RequestBody LoanUpdateDto loanUpdateDto) {
        LoanDto updatedLoan = loanService.update(id, loanUpdateDto);
        return ResponseEntity.ok(updatedLoan);
    }

    /**
     * Фиксирует возврат книги по указанной выдаче.
     * <p>Выставляет дату возврата как текущий момент времени.</p>
     *
     * @param id идентификатор выдачи
     * @return обновленная выдача с датой возврата
     * @throws NotFoundException если одалживание не найдено
     */
    @PatchMapping("/{id}/return")
    @Operation(summary = "Вернуть книгу по ID одалживания")
    public LoanDto returnLoan(@PathVariable Long id) {
        return loanService.returnLoan(id);
    }

    /**
     * Удаляет выдачу по её ID.
     *
     * @param id идентификатор выдачи
     * @throws NotFoundException если одалживание не найдено
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить одалживание по ID")
    public void deleteLoan(@PathVariable Long id) {
        loanService.delete(id);
    }
}
