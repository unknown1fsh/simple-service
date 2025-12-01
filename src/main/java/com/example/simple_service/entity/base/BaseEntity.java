package com.example.simple_service.entity.base;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

//TODO: BaseEntity - Generic Base Entity Sınıfı
// Bu sınıf, tüm entity sınıflarının ortak özelliklerini içeren generic bir base sınıftır.
// Generic programlama (Generics) kullanılarak farklı ID tipleri (Long, String, UUID vb.) desteklenir.
// @MappedSuperclass anotasyonu ile bu sınıfın kendisi bir entity olmadığı, sadece diğer entity'ler için base olduğu belirtilir.
// JPA Lifecycle Callback'leri (@PrePersist, @PreUpdate) ile otomatik timestamp yönetimi yapılır.
// Inheritance (Kalıtım) kavramı: Tüm entity'ler bu sınıftan extend ederek ortak özellikleri miras alır.
// Bu yaklaşım DRY (Don't Repeat Yourself) prensibine uygundur.

/**
 * Base Entity Sınıfı
 * 
 * Bu sınıf, tüm entity sınıflarının ortak özelliklerini içeren generic bir base sınıftır.
 * Layered Architecture'da Entity katmanının temel yapı taşıdır.
 * 
 * Generic yapı sayesinde farklı ID tipleri (Long, String, UUID vb.) kullanılabilir.
 * 
 * Özellikler:
 * - Otomatik ID yönetimi
 * - Oluşturulma ve güncellenme tarihleri
 * - JPA lifecycle callback metodları (@PrePersist, @PreUpdate)
 * 
 * Kullanım:
 * Tüm entity sınıfları bu sınıftan extend edilmelidir:
 * 
 * @Entity
 * public class User extends BaseEntity<Long> {
 *     // User'a özel alanlar
 * }
 */
@Data
@MappedSuperclass // Bu sınıfın kendisi bir entity değil, diğer entity'ler için base sınıf olduğunu belirtir
public abstract class BaseEntity<ID extends Serializable> {

    /**
     * Birincil anahtar (Primary Key) alanı
     * Her entity'nin benzersiz kimliğini tutar.
     * Generic ID tipi sayesinde Long, String, UUID gibi farklı tipler kullanılabilir.
     * 
     * @Column anotasyonu ile:
     * - name: Veritabanı kolon adı (id)
     * - nullable = false: Boş olamaz (primary key olduğu için)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Veritabanı tarafında otomatik artan ID
    @Column(name = "id", nullable = false)
    private ID id;

    /**
     * Kayıt oluşturulma tarihi
     * 
     * @Column anotasyonu ile veritabanı kolonu yapılandırılır:
     * - name: Veritabanı kolon adı (created_at - snake_case)
     * - nullable = false: Boş olamaz
     * - updatable = false: Güncelleme işlemlerinde değiştirilemez (sadece oluşturulurken set edilir)
     * - columnDefinition: MySQL ve PostgreSQL uyumlu timestamp tanımı (microsecond precision)
     * 
     * Not: Index tanımları alt sınıflarda (User entity gibi) @Table indexes ile yapılmalıdır.
     * BaseEntity @MappedSuperclass olduğu için @Table kullanamaz.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Son güncelleme tarihi
     * Her güncelleme işleminde otomatik olarak güncellenir.
     * 
     * @Column anotasyonu ile:
     * - name: Veritabanı kolon adı (updated_at - snake_case)
     * - nullable = false: Boş olamaz
     * - columnDefinition: MySQL ve PostgreSQL uyumlu timestamp tanımı (microsecond precision)
     * 
     * Not: Index tanımları alt sınıflarda (User entity gibi) @Table indexes ile yapılmalıdır.
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * JPA Lifecycle Callback - PrePersist
     * Entity veritabanına kaydedilmeden ÖNCE çalışır.
     * 
     * Bu metod, yeni bir kayıt oluşturulurken:
     * - createdAt ve updatedAt alanlarını otomatik olarak şu anki zamana set eder
     * - Manuel olarak tarih atanmamışsa otomatik atama yapar
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = now;
        }
    }

    /**
     * JPA Lifecycle Callback - PreUpdate
     * Entity güncellenmeden ÖNCE çalışır.
     * 
     * Bu metod, mevcut bir kayıt güncellenirken:
     * - updatedAt alanını otomatik olarak şu anki zamana günceller
     * - createdAt null ise (eski kayıtlar için) şu anki zamanı atar
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        // Eski kayıtlar için createdAt null olabilir, bu durumda şu anki zamanı atar
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}

