package org.synberg.pet.crudapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.synberg.pet.crudapp.dto.create.UserCreateDto;
import org.synberg.pet.crudapp.dto.UserDto;
import org.synberg.pet.crudapp.dto.update.UserUpdateDto;
import org.synberg.pet.crudapp.entity.User;
import org.synberg.pet.crudapp.exception.NotFoundException;
import org.synberg.pet.crudapp.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    /**
     * Находит пользователя по ID.
     *
     * @param id идентификатор пользователя
     * @return {@link UserDto} с данными пользователя
     * @throws NotFoundException если пользователь не найден
     */
    public UserDto find(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return список {@link UserDto}
     */
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getId(), user.getName(), user.getEmail()))
                .toList();
    }

    /**
     * Создаёт нового пользователя на основе переданных данных.
     *
     * @param userCreateDto DTO с данными нового пользователя
     * @return созданный {@link UserDto}
     */
    public UserDto create(UserCreateDto userCreateDto) {
        if (userRepository.existsByEmail(userCreateDto.email())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setName(userCreateDto.name());
        user.setEmail(userCreateDto.email());
        userRepository.save(user);
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    /**
     * Обновляет существующего пользователя по его ID.
     *
     * @param id идентификатор пользователя
     * @param userUpdateDto DTO с новыми данными
     * @return обновлённый {@link UserDto}
     * @throws NotFoundException если пользователь не найден
     */
    public UserDto update(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setName(userUpdateDto.name());
        user.setEmail(userUpdateDto.email());
        User updatedUser = userRepository.save(user);
        return new UserDto(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail());
    }

    /**
     * Удаляет пользователя по ID.
     *
     * @param id идентификатор пользователя
     * @throws NotFoundException если пользователь не найден
     */
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}

