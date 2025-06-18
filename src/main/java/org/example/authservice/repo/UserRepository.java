package org.example.authservice.repo;

import org.example.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Интерфейс для работы с данными класса User
 * findByEmail ищет email объекта User по строковому значению и обрабатывается Optional в избежании NullPointerException
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}

