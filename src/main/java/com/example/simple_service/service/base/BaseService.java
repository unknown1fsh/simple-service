package com.example.simple_service.service.base;

import com.example.simple_service.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Base Service Interface
 * 
 * Bu interface, tüm service sınıfları için ortak iş mantığı metodlarını tanımlayan generic bir base interface'dir.
 * Layered Architecture'da Service katmanının temel yapı taşıdır.
 * 
 * Service katmanı, Controller ve Repository katmanları arasında köprü görevi görür:
 * - İş kurallarını (business logic) uygular
 * - Validasyon yapar
 * - Transaction yönetimi sağlar
 * - Veri dönüşümleri yapar
 * 
 * Generic yapı sayesinde:
 * - T: Entity tipi (BaseEntity'den extend eden herhangi bir entity)
 * - ID: Entity'nin ID tipi (Long, String, UUID vb.)
 * 
 * Kullanım:
 * Tüm service interface'leri bu interface'den extend edilmelidir:
 * 
 * public interface UserService extends BaseService<User, Long> {
 *     Optional<User> getUserByEmail(String email);
 * }
 */
public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {

    /**
     * Yeni bir entity kaydeder (CREATE işlemi)
     * 
     * @param entity Kaydedilecek entity
     * @return Kaydedilen entity (ID ve timestamp'ler ile birlikte)
     * @throws IllegalArgumentException Validasyon hatalarında fırlatılır
     */
    T save(T entity);

    /**
     * Tüm entity'leri getirir (READ işlemi - listeleme)
     * 
     * @return Tüm entity'lerin listesi
     */
    List<T> findAll();

    /**
     * ID'ye göre entity getirir (READ işlemi - tek kayıt)
     * 
     * @param id Aranacak entity'nin ID'si
     * @return Bulunan entity, bulunamazsa null
     */
    T findById(ID id);

    /**
     * ID'ye göre entity getirir (Optional döner)
     * 
     * @param id Aranacak entity'nin ID'si
     * @return Optional<T> - Entity varsa içinde, yoksa boş
     */
    Optional<T> findByIdOptional(ID id);

    /**
     * Mevcut bir entity'yi günceller (UPDATE işlemi)
     * 
     * @param id Güncellenecek entity'nin ID'si
     * @param entity Güncellenecek yeni veriler
     * @return Güncellenmiş entity, bulunamazsa null
     * @throws IllegalArgumentException Validasyon hatalarında fırlatılır
     */
    T update(ID id, T entity);

    /**
     * Entity'yi siler (DELETE işlemi)
     * 
     * @param id Silinecek entity'nin ID'si
     * @return Silme başarılıysa true, entity bulunamazsa false
     */
    boolean delete(ID id);

    /**
     * Entity'nin var olup olmadığını kontrol eder
     * 
     * @param id Kontrol edilecek entity'nin ID'si
     * @return Entity varsa true, yoksa false
     */
    boolean existsById(ID id);

    /**
     * Toplam entity sayısını döner
     * 
     * @return Toplam kayıt sayısı
     */
    long count();
}

