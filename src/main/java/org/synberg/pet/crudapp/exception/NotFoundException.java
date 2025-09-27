package org.synberg.pet.crudapp.exception;

/**
 * Исключение, выбрасываемое, когда сущность не найдена в базе данных.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
