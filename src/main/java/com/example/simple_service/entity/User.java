package com.example.simple_service.entity;

import com.example.simple_service.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * User Entity Sınıfı
 * 
 * Bu sınıf, kullanıcı bilgilerini temsil eden JPA entity'sidir.
 * Layered Architecture'da Entity katmanında yer alır.
 * 
 * BaseEntity'den extend edilerek:
 * - id (Long tipinde)
 * - createdAt (oluşturulma tarihi)
 * - updatedAt (güncellenme tarihi)
 * alanları otomatik olarak miras alınır.
 * 
 * @Entity: Bu sınıfın bir JPA entity'si olduğunu belirtir
 * @Table: Veritabanı tablo yapılandırması
 * - name: Tablo adı (app_user)
 * - uniqueConstraints: Email kolonu için unique constraint
 * 
 * Lombok Anotasyonları:
 * - @Data: getter, setter, toString, equals, hashCode otomatik oluşturur
 * - @Builder: Builder pattern ile nesne oluşturmayı sağlar
 * - @NoArgsConstructor: Parametresiz constructor
 * - @AllArgsConstructor: Tüm alanları içeren constructor
 * - @EqualsAndHashCode(callSuper = true): BaseEntity'den gelen alanları da equals/hashCode'a dahil eder
 */
@Entity
@Table(
        name = "app_user", // Veritabanındaki tablo adı
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_app_user_email", columnNames = "email") // Email kolonu benzersiz olmalı
        },
        indexes = {
            @Index(name = "idx_app_user_email", columnList = "email"), // Email için index (sorgu performansı)
            @Index(name = "idx_app_user_created_at", columnList = "created_at"), // Created_at için index (tarih bazlı sorgular)
            @Index(name = "idx_app_user_updated_at", columnList = "updated_at") // Updated_at için index (tarih bazlı sorgular)
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) // BaseEntity'den gelen alanları da equals/hashCode'a dahil et
public class User extends BaseEntity<Long> {

    /**
     * Kullanıcının adı
     * 
     * @Column anotasyonu ile:
     * - name: Veritabanı kolon adı (name - snake_case)
     * - nullable = false: Boş olamaz (zorunlu alan)
     * - length = 100: Maksimum 100 karakter
     * - columnDefinition: MySQL ve PostgreSQL uyumlu VARCHAR tanımı
     */
    @Column(name = "name", nullable = false, length = 100, columnDefinition = "VARCHAR(100) NOT NULL")
    private String name;

    /**
     * Kullanıcının email adresi
     * 
     * @Column anotasyonu ile:
     * - name: Veritabanı kolon adı (email - snake_case)
     * - nullable = false: Boş olamaz (zorunlu alan)
     * - length = 150: Maksimum 150 karakter
     * - columnDefinition: MySQL ve PostgreSQL uyumlu VARCHAR tanımı
     * 
     * Not: 
     * - Unique constraint @Table seviyesinde tanımlıdır (daha güvenli ve performanslı)
     * - Index @Table seviyesinde tanımlıdır (sorgu performansı için)
     * - @Column unique=true kaldırıldı (tekrar önlendi, @Table uniqueConstraint yeterli)
     */
    @Column(name = "email", nullable = false, length = 150, columnDefinition = "VARCHAR(150) NOT NULL")
    private String email;

    // Not: id, createdAt, updatedAt alanları BaseEntity<Long> sınıfından miras alınır
    // Bu alanlar otomatik olarak mevcuttur ve BaseEntity'deki @PrePersist ve @PreUpdate
    // metodları tarafından otomatik yönetilir.
}
