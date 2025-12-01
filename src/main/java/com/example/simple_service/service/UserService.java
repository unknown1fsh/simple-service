package com.example.simple_service.service;

import com.example.simple_service.entity.User;
import com.example.simple_service.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

//TODO: UserService - Kullanıcı Service Interface
// Bu interface, User entity'si için iş mantığı (business logic) metodlarını tanımlar.
// BaseService<User, Long>'dan extend edilerek temel CRUD metodları otomatik gelir.
// Interface kavramı: Java'da interface'ler sözleşme (contract) tanımlar, implementasyon UserServiceImpl'de yapılır.
// Service katmanının görevleri: İş kurallarını uygular, validasyon yapar, transaction yönetimi sağlar.
// Separation of Concerns (Sorumlulukların Ayrılması): Controller repository'ye doğrudan erişmez, service üzerinden erişir.
// Polymorphism (Çok biçimlilik): BaseService tipinde referans ile UserService kullanılabilir.
// User'a özel metodlar (getUserByEmail, findByNameContainingIgnoreCase) burada tanımlanır.

/**
 * User Service Interface
 * 
 * Bu interface, User entity'si için iş mantığı (business logic) metodlarını tanımlar.
 * Layered Architecture'da Service katmanında yer alır.
 * 
 * BaseService'den extend edilerek:
 * - save(), findAll(), findById(), update(), delete() gibi temel CRUD metodları otomatik sağlanır
 * - User'a özel iş mantığı metodları burada tanımlanır
 * 
 * Service katmanının görevleri:
 * - İş kurallarını (business rules) uygular
 * - Validasyon yapar
 * - Transaction yönetimi sağlar
 * - Controller ve Repository katmanları arasında köprü görevi görür
 * 
 * Kullanım:
 * Controller sınıfları bu interface üzerinden iş mantığına erişir.
 * Repository'ye doğrudan erişim yapılmaz (separation of concerns).
 */
public interface UserService extends BaseService<User, Long> {

    /**
     * Email adresine göre kullanıcıyı bulur
     * 
     * User'a özel bir metod. BaseService'de yoktur.
     * 
     * @param email Aranacak email adresi
     * @return Optional<User> - Kullanıcı bulunursa içinde, bulunamazsa boş
     */
    Optional<User> getUserByEmail(String email);
    
    /**
     * Sayfalama ile tüm kullanıcıları getirir
     * 
     * @param pageable Sayfalama bilgileri (sayfa numarası, sayfa başına kayıt sayısı)
     * @return Page<User> - Sayfalanmış kullanıcı listesi
     */
    Page<User> findAll(Pageable pageable);
    
    /**
     * İsme göre sayfalama ile kullanıcıları getirir
     * 
     * @param name Aranacak isim (kısmi eşleşme, case-insensitive)
     * @param pageable Sayfalama bilgileri
     * @return Page<User> - Sayfalanmış kullanıcı listesi
     */
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Not: BaseService'den gelen metodlar:
    // - User save(User user) - saveUser yerine save kullanılır
    // - List<User> findAll() - getAllUsers yerine findAll kullanılır
    // - User findById(Long id) - getUserById yerine findById kullanılır
    // - User update(Long id, User user) - updateUser yerine update kullanılır
    // - boolean delete(Long id) - deleteUser yerine delete kullanılır
}
