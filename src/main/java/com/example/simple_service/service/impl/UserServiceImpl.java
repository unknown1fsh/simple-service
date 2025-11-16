package com.example.simple_service.service.impl;

import com.example.simple_service.entity.User;
import com.example.simple_service.repository.UserRepository;
import com.example.simple_service.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

// Bu sınıf, UserService interface'ini implemente eder ve gerçek iş mantığını içerir.
// @Service anotasyonu sayesinde Spring tarafından otomatik olarak bean olarak tanımlanır.
@Service
public class UserServiceImpl implements UserService {

    // Veritabanı işlemleri için UserRepository kullanılır
    private final UserRepository userRepository;

    // Email validation için regex pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    // Constructor-based dependency injection (tavsiye edilen yöntem)
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Email formatını kontrol eder
     */
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Kullanıcı verilerini validate eder
     */
    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Kullanıcı bilgisi boş olamaz!");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Kullanıcı adı boş olamaz!");
        }
        if (user.getName().length() > 100) {
            throw new IllegalArgumentException("Kullanıcı adı en fazla 100 karakter olabilir!");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email adresi boş olamaz!");
        }
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Geçersiz email formatı: " + user.getEmail());
        }
        if (user.getEmail().length() > 150) {
            throw new IllegalArgumentException("Email adresi en fazla 150 karakter olabilir!");
        }
    }

    // Yeni kullanıcıyı veritabanına kaydeder
    @Override
    public User saveUser(User user) {
        // Validation
        validateUser(user);
        
        // Email duplicate kontrolü
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail().trim());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Bu email adresi zaten kullanılıyor: " + user.getEmail());
        }
        
        // Email'i trim et
        user.setEmail(user.getEmail().trim());
        user.setName(user.getName().trim());
        
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
        if (id == null) {
            throw new IllegalArgumentException("Kullanıcı ID'si boş olamaz!");
        }
        
        User existingUser = getUserById(id); // Önce ID'ye göre kullanıcı alınır
        if (existingUser == null) {
            return null; // Kullanıcı bulunamazsa null döner
        }
        
        // Validation
        validateUser(user);
        
        // Email değişikliği kontrolü - eğer email değiştiyse duplicate kontrolü yap
        String newEmail = user.getEmail().trim();
        if (!newEmail.equals(existingUser.getEmail())) {
            Optional<User> emailUser = userRepository.findByEmail(newEmail);
            if (emailUser.isPresent() && !emailUser.get().getId().equals(id)) {
                throw new IllegalArgumentException("Bu email adresi başka bir kullanıcı tarafından kullanılıyor: " + newEmail);
            }
        }
        
        // Kullanıcı varsa adı ve email bilgisi güncellenir
        existingUser.setName(user.getName().trim());
        existingUser.setEmail(newEmail);
        
        // Güncellenmiş kullanıcı veritabanına tekrar kaydedilir
        return userRepository.save(existingUser);
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
