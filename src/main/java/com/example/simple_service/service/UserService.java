package com.example.simple_service.service;

import com.example.simple_service.entity.User;

import java.util.List;
import java.util.Optional;

// Bu arayüz (interface), User nesnesiyle ilgili iş mantığını tanımlar.
// Controller ile Repository arasındaki katmandır. Amaç: veritabanı işlemleriyle iş kurallarını birbirinden ayırmaktır.
public interface UserService {

    // Yeni bir kullanıcıyı kaydeder (CREATE işlemi)
    User saveUser(User user);

    // Tüm kullanıcıları getirir (READ işlemi - listeleme)
    List<User> getAllUsers();

    // Belirli bir kullanıcıyı ID'sine göre getirir (READ işlemi - tek kayıt)
    User getUserById(Long id);

    // Belirli bir kullanıcıyı günceller (UPDATE işlemi)
    User updateUser(Long id, User user);

    // Belirli bir kullanıcıyı siler (DELETE işlemi)
    boolean deleteUser(Long id);

    // E-posta adresine göre kullanıcıyı bulur (READ işlemi)
    Optional<User> getUserByEmail(String email);
}
