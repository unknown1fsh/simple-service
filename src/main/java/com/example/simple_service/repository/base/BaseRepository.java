package com.example.simple_service.repository.base;

import com.example.simple_service.entity.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

//TODO: BaseRepository - Generic Base Repository Interface
// Bu interface, tüm repository sınıfları için ortak CRUD işlemlerini tanımlayan generic bir base interface'dir.
// Generic programlama (Generics) ile farklı entity ve ID tipleri desteklenir.
// Spring Data JPA'nın JpaRepository'sinden extend edilerek temel CRUD metodları otomatik sağlanır.
// @NoRepositoryBean anotasyonu ile bu interface'in kendisi bir repository bean'i olmadığı belirtilir.
// Interface kavramı: Java'da interface'ler sözleşme (contract) tanımlar, implementasyon alt sınıflarda yapılır.
// Spring Data JPA, metod isimlerine göre otomatik SQL sorguları oluşturur (Query Method Naming Convention).

/**
 * Base Repository Interface
 * 
 * Bu interface, tüm repository sınıfları için ortak CRUD işlemlerini tanımlayan generic bir base interface'dir.
 * Layered Architecture'da Repository katmanının temel yapı taşıdır.
 * 
 * Generic yapı sayesinde:
 * - T: Entity tipi (BaseEntity'den extend eden herhangi bir entity)
 * - ID: Entity'nin ID tipi (Long, String, UUID vb.)
 * 
 * Spring Data JPA'nın JpaRepository'sinden extend edilerek:
 * - save(), findById(), findAll(), delete() gibi temel CRUD metodları otomatik sağlanır
 * - Query metodları için Spring Data JPA'nın naming convention'ı kullanılabilir
 * 
 * @NoRepositoryBean: Bu interface'in kendisi bir repository bean'i değildir,
 * sadece diğer repository'ler için base interface görevi görür.
 * 
 * Kullanım:
 * Tüm repository interface'leri bu interface'den extend edilmelidir:
 * 
 * public interface UserRepository extends BaseRepository<User, Long> {
 *     Optional<User> findByEmail(String email);
 * }
 */
@NoRepositoryBean // Spring'e bu interface'in bean olarak oluşturulmaması gerektiğini söyler
public interface BaseRepository<T extends BaseEntity<ID>, ID extends Serializable> 
        extends JpaRepository<T, ID> {
    
    /**
     * Bu interface, JpaRepository'den gelen tüm metodları içerir:
     * 
     * Temel CRUD İşlemleri:
     * - <S extends T> S save(S entity) - Kaydetme/güncelleme
     * - Optional<T> findById(ID id) - ID'ye göre bulma
     * - List<T> findAll() - Tümünü listeleme
     * - void delete(T entity) - Silme
     * - void deleteById(ID id) - ID'ye göre silme
     * - boolean existsById(ID id) - Varlık kontrolü
     * - long count() - Toplam kayıt sayısı
     * 
     * Alt sınıflar bu metodları kullanabilir veya kendi özel metodlarını ekleyebilir.
     * Spring Data JPA, metod isimlerine göre otomatik query oluşturur.
     * 
     * Örnek:
     * Optional<User> findByEmail(String email);
     * Spring otomatik olarak şu SQL'i oluşturur:
     * SELECT * FROM app_user WHERE email = ? LIMIT 1
     */
}

