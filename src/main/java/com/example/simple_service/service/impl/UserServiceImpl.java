package com.example.simple_service.service.impl;

import com.example.simple_service.entity.User;
import com.example.simple_service.repository.UserRepository;
import com.example.simple_service.service.UserService;
import com.example.simple_service.service.base.BaseServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * User Service Implementation
 * 
 * Bu sınıf, UserService interface'inin implementasyonudur.
 * Layered Architecture'da Service katmanında yer alır.
 * 
 * BaseServiceImpl'den extend edilerek:
 * - save(), findAll(), findById(), update(), delete() gibi temel CRUD metodları otomatik sağlanır
 * - User'a özel iş mantığı (validation, email kontrolü vb.) burada uygulanır
 * 
 * @Service: Spring tarafından otomatik olarak bean olarak tanımlanır ve dependency injection'a dahil edilir
 * 
 * Design Patterns:
 * - Template Method Pattern: BaseServiceImpl'deki base metodlar, alt sınıflarda override edilebilir
 * - Dependency Injection: Constructor injection ile UserRepository enjekte edilir
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> 
        implements UserService {

    /**
     * Email formatı validasyonu için regex pattern
     * Pattern.compile() ile bir kez derlenir ve tekrar kullanılır (performans)
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    /**
     * Constructor - Dependency Injection
     * 
     * BaseServiceImpl'in constructor'ına repository'yi geçirir.
     * Constructor injection, Spring'de en çok tavsiye edilen dependency injection yöntemidir.
     * 
     * @param userRepository User entity için repository
     */
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
    }

    /**
     * Email formatını kontrol eder
     * 
     * Regex pattern kullanarak email formatının geçerli olup olmadığını kontrol eder.
     * 
     * @param email Kontrol edilecek email adresi
     * @return Email formatı geçerliyse true, değilse false
     */
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Kullanıcı verilerini validate eder
     * 
     * User entity'sinin tüm alanlarını kontrol eder:
     * - Entity null kontrolü
     * - Name boşluk ve uzunluk kontrolü
     * - Email format ve uzunluk kontrolü
     * 
     * @param user Validasyon yapılacak kullanıcı
     * @throws IllegalArgumentException Validasyon hatası durumunda
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

    /**
     * Yeni bir kullanıcıyı kaydeder
     * 
     * BaseServiceImpl'deki save() metodunu override ederek User'a özel validasyon ekler:
     * - Email format kontrolü
     * - Email duplicate kontrolü
     * - String trim işlemleri
     * 
     * @param user Kaydedilecek kullanıcı
     * @return Kaydedilen kullanıcı (ID ve timestamp'ler ile birlikte)
     * @throws IllegalArgumentException Validasyon hatası veya duplicate email durumunda
     */
    @Override
    public User save(User user) {
        // User'a özel validasyon
        validateUser(user);
        
        // Email duplicate kontrolü - aynı email ile kayıt olmamalı
        Optional<User> existingUser = repository.findByEmail(user.getEmail().trim());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Bu email adresi zaten kullanılıyor: " + user.getEmail());
        }
        
        // String alanları trim et (başında/sonunda boşluk varsa temizle)
        user.setEmail(user.getEmail().trim());
        user.setName(user.getName().trim());
        
        // BaseServiceImpl'deki save() metodunu çağır
        return super.save(user);
    }

    /**
     * Mevcut bir kullanıcıyı günceller
     * 
     * BaseServiceImpl'deki update() metodunu override ederek User'a özel validasyon ekler:
     * - Email format kontrolü
     * - Email değişikliği durumunda duplicate kontrolü
     * - String trim işlemleri
     * 
     * @param id Güncellenecek kullanıcının ID'si
     * @param user Güncellenecek yeni veriler
     * @return Güncellenmiş kullanıcı, bulunamazsa null
     * @throws IllegalArgumentException Validasyon hatası veya duplicate email durumunda
     */
    @Override
    public User update(Long id, User user) {
        // BaseServiceImpl'deki validasyon (id ve entity null kontrolü)
        // validateBeforeUpdate() metodu otomatik çağrılır
        
        // Mevcut kullanıcıyı bul
        User existingUser = findById(id);
        if (existingUser == null) {
            return null; // Kullanıcı bulunamadı
        }
        
        // User'a özel validasyon
        validateUser(user);
        
        // Email değişikliği kontrolü - eğer email değiştiyse duplicate kontrolü yap
        String newEmail = user.getEmail().trim();
        if (!newEmail.equals(existingUser.getEmail())) {
            Optional<User> emailUser = repository.findByEmail(newEmail);
            if (emailUser.isPresent() && !emailUser.get().getId().equals(id)) {
                throw new IllegalArgumentException("Bu email adresi başka bir kullanıcı tarafından kullanılıyor: " + newEmail);
            }
        }
        
        // Mevcut kullanıcının alanlarını güncelle
        existingUser.setName(user.getName().trim());
        existingUser.setEmail(newEmail);
        
        // BaseServiceImpl'deki save() metodunu kullanarak güncelle
        return repository.save(existingUser);
    }

    /**
     * Email adresine göre kullanıcıyı bulur
     * 
     * User'a özel bir metod. BaseService'de yoktur.
     * 
     * @param email Aranacak email adresi
     * @return Optional<User> - Kullanıcı bulunursa içinde, bulunamazsa boş
     */
    @Override
    public Optional<User> getUserByEmail(String email) {
        return repository.findByEmail(email);
    }
    
    /**
     * Sayfalama ile tüm kullanıcıları getirir
     * 
     * @param pageable Sayfalama bilgileri (sayfa numarası, sayfa başına kayıt sayısı)
     * @return Page<User> - Sayfalanmış kullanıcı listesi
     */
    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    
    /**
     * İsme göre sayfalama ile kullanıcıları getirir
     * 
     * @param name Aranacak isim (kısmi eşleşme, case-insensitive)
     * @param pageable Sayfalama bilgileri
     * @return Page<User> - Sayfalanmış kullanıcı listesi
     */
    @Override
    public Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }
}
