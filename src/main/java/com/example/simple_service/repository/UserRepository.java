package com.example.simple_service.repository;

import com.example.simple_service.entity.User;
import com.example.simple_service.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository Interface
 * 
 * Bu interface, User entity'si için veritabanı işlemlerini tanımlar.
 * Layered Architecture'da Repository katmanında yer alır.
 * 
 * BaseRepository'den extend edilerek:
 * - save(), findById(), findAll(), delete() gibi temel CRUD metodları otomatik sağlanır
 * - Spring Data JPA'nın tüm özellikleri kullanılabilir
 * 
 * Spring Data JPA Naming Convention:
 * - Metod isimlerine göre otomatik SQL sorguları oluşturulur
 * - Örnek: findByEmail(String email) → SELECT * FROM app_user WHERE email = ?
 * 
 * @Repository: Bu interface'in bir Spring repository bean'i olduğunu belirtir
 * (Spring Data JPA tarafından otomatik implement edilir)
 */
@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    /**
     * Email adresine göre kullanıcıyı bulur
     * 
     * Spring Data JPA, metod ismine göre otomatik olarak şu SQL sorgusunu oluşturur:
     * SELECT * FROM app_user WHERE email = ? LIMIT 1
     * 
     * Naming Convention:
     * - findBy: Arama işlemi
     * - Email: email kolonuna göre (case-insensitive)
     * 
     * @param email Aranacak email adresi
     * @return Optional<User> - Kullanıcı bulunursa içinde, bulunamazsa boş
     */
    Optional<User> findByEmail(String email);
}
