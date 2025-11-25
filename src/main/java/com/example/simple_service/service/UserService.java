package com.example.simple_service.service;

import com.example.simple_service.entity.User;
import com.example.simple_service.service.base.BaseService;

import java.util.Optional;

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
    
    // Not: BaseService'den gelen metodlar:
    // - User save(User user) - saveUser yerine save kullanılır
    // - List<User> findAll() - getAllUsers yerine findAll kullanılır
    // - User findById(Long id) - getUserById yerine findById kullanılır
    // - User update(Long id, User user) - updateUser yerine update kullanılır
    // - boolean delete(Long id) - deleteUser yerine delete kullanılır
}
