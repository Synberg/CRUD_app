package org.synberg.pet.crudapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.synberg.pet.crudapp.dto.create.UserCreateDto;
import org.synberg.pet.crudapp.dto.UserDto;
import org.synberg.pet.crudapp.dto.update.UserUpdateDto;
import org.synberg.pet.crudapp.service.UserService;
import org.synberg.pet.crudapp.exception.NotFoundException;

import java.util.List;

/**
 * REST-контроллер для управления пользователями.
 * <p>
 * Предоставляет CRUD-операции для сущности {@code User}:
 * <ul>
 *     <li>Создание нового пользователя</li>
 *     <li>Получение одного или всех пользователей</li>
 *     <li>Обновление данных пользователя</li>
 *     <li>Удаление пользователя</li>
 * </ul>
 */

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Операции с пользователями")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Получает пользователя по его ID.
     *
     * @param id идентификатор пользователя
     * @return данные пользователя в виде {@link UserDto}
     * @throws NotFoundException если пользователь не найден
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.find(id);
    }

    /**
     * Получает список всех пользователей.
     *
     * @return список всех пользователей в виде {@link UserDto}
     */
    @GetMapping
    @Operation(summary = "Получить всех сохраненных пользователей")
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    /**
     * Создает нового пользователя.
     *
     * @param userCreateDto данные для создания пользователя
     * @return созданный пользователь в виде {@link UserDto}
     */
    @PostMapping
    @Operation(summary = "Создать нового пользователя")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        UserDto createdUser = userService.create(userCreateDto);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Обновляет данные существующего пользователя.
     *
     * @param id            идентификатор пользователя
     * @param userUpdateDto новые данные пользователя
     * @return обновленный пользователь в виде {@link UserDto}
     * @throws NotFoundException если пользователь не найден
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя по ID")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        UserDto updatedUser = userService.update(id, userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Удаляет пользователя по его ID.
     *
     * @param id идентификатор пользователя
     * @throws NotFoundException если пользователь не найден
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по ID")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
