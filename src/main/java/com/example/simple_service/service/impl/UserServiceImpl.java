package com.example.simple_service.service.impl;

import com.example.simple_service.entity.User;
import com.example.simple_service.repository.UserRepository;
import com.example.simple_service.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Bu sınıf, UserService interface'ini implemente eder ve gerçek iş mantığını içerir.
// @Service anotasyonu sayesinde Spring tarafından otomatik olarak bean olarak tanımlanır.
@Service
public class UserServiceImpl implements UserService {

    // Veritabanı işlemleri için UserRepository kullanılır
    private final UserRepository userRepository;

    // Constructor-based dependency injection (tavsiye edilen yöntem)
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Yeni kullanıcıyı veritabanına kaydeder
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Tüm kullanıcıları veritabanından getirir
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ID'ye göre kullanıcıyı getirir; yoksa null döner
    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null); // Eğer kullanıcı bulunamazsa null döner
    }

    // Mevcut bir kullanıcıyı günceller (adı ve email bilgisi güncellenir)
    @Override
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id); // Önce ID'ye göre kullanıcı alınır
        if (existingUser != null) {
            // Kullanıcı varsa adı ve email bilgisi güncellenir
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            // Güncellenmiş kullanıcı veritabanına tekrar kaydedilir
            return userRepository.save(existingUser);
        }
        // Kullanıcı bulunamazsa null döner
        return null;
    }

    // Belirli bir kullanıcıyı siler, başarılıysa true döner
    @Override
    public boolean deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            // Kullanıcı bulunduysa silinir
            userRepository.delete(userOptional.get());
            return true;
        }
        // Kullanıcı bulunamazsa silme işlemi gerçekleşmez
        return false;
    }

    // Email'e göre kullanıcıyı getirir
    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
