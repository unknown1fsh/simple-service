package com.example.simple_service.entity;

// JPA (Java Persistence API) için gerekli anotasyonlar
import jakarta.persistence.*;

// Lombok kütüphanesinden gelen anotasyonlar: getter, setter, constructor, builder gibi kodları otomatik üretir
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Bu sınıfın bir veritabanı tablosunu temsil ettiğini belirtir
@Entity
// Tablonun adı ve benzersiz (unique) kolon kısıtlaması tanımlanır
@Table(
        name = "app_user", // Veritabanındaki tablo adı
        uniqueConstraints = {@UniqueConstraint(columnNames = "email")} // "email" kolonu benzersiz olmalı (aynı email birden fazla kayıt olamaz
)
// Lombok anotasyonları:
// @Data → getter, setter, toString, equals, hashCode otomatik oluşturur
// @Builder → builder pattern ile nesne oluşturmayı sağlar
// @NoArgsConstructor → parametresiz constructor üretir
// @AllArgsConstructor → tüm alanları içeren constructor üretir
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // Birincil anahtar (primary key) alanı
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Otomatik artan ID kullanılır
    private Long id;

    // Kullanıcının adı. Bu alan boş bırakılamaz (nullable = false) ve en fazla 100 karakter olabilir
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    // Kullanıcının email adresi. Bu alan da zorunlu ve maksimum 150 karakter olabilir
    @Column(name = "email", nullable = false, length = 150)
    private String email;
}
