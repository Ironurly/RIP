package main.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Базовый сервис, который перенаправляет вызовы часто используемых методов
 * к соответствующим методам репозитория.
 * @param <T> тип сущности, которая хранится в репозитории
 */
public abstract class BaseService<T> {
    public abstract JpaRepository<T, Long> getRepository();

    public T save(T entity) {
        return getRepository().save(entity);
    }

    public Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }

    public void deleteById(Long id) {
        getRepository().deleteById(id);
    }

    public List<T> findAll() {
        return getRepository().findAll();
    }
}
