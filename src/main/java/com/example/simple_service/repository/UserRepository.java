package com.example.simple_service.repository;

import com.example.simple_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Bu arayüz (interface), User entity'si için veritabanı işlemlerini otomatik olarak sağlar.
// JpaRepository, Spring Data JPA tarafından sağlanır ve temel CRUD işlemlerini (create, read, update, delete) otomatik olarak içerir.
public interface UserRepository extends JpaRepository<User, Long> {

    // Email'e göre kullanıcıyı bulmak için özel bir sorgu metodu.
    // Spring, metodun ismine göre SQL sorgusunu otomatik olarak oluşturur.
    // Örn: SELECT * FROM app_user WHERE email = ? LIMIT 1
    Optional<User> findByEmail(String email);
}
