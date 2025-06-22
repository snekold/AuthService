package org.example.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Аннотацией SpringBootApplication помечается основной класс, который производит сборку и запуск нашего проекта
 * Доп. Содержит еще 3 аннотации:
 *
 * Configuration - используется в классах, помеченных данной аннотацией и которые используют аннотацию @Bean
 * EnableAutoConfiguration - включает механизм автоматической настройки Spring Boot на основе зависимостей
 * ComponentScan - Указывает искать компоненты (@Component, @Service, @Repository, @Controller) в текущем пакете и под-пакетах
 */
@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
