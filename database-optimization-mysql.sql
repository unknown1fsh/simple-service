-- ============================================
-- MySQL Veritabanı Optimizasyon Script'i
-- ============================================
-- Bu script, simple_service veritabanındaki app_user tablosunu optimize eder.
-- Çalıştırmadan önce: USE simple_service;

USE simple_service;

-- ============================================
-- 1. Mevcut Tablo Yapısını Kontrol Et
-- ============================================
-- DESCRIBE app_user;
-- SHOW INDEXES FROM app_user;
-- SHOW CREATE TABLE app_user;

-- ============================================
-- 2. Eksik Index'leri Ekle
-- ============================================

-- Email kolonu için index (sorgu performansı için)
-- Not: Unique constraint zaten index oluşturur, ama explicit index daha iyi kontrol sağlar
CREATE INDEX IF NOT EXISTS idx_app_user_email ON app_user(email);

-- Created_at kolonu için index (tarih bazlı sorgular için)
CREATE INDEX IF NOT EXISTS idx_created_at ON app_user(created_at);

-- Updated_at kolonu için index (tarih bazlı sorgular için)
CREATE INDEX IF NOT EXISTS idx_updated_at ON app_user(updated_at);

-- ============================================
-- 3. Tablo Yapısını Optimize Et
-- ============================================

-- Tablo engine'ini InnoDB yap (transaction desteği için)
ALTER TABLE app_user ENGINE = InnoDB;

-- Charset ve Collation ayarları (UTF-8 desteği için)
ALTER TABLE app_user CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ============================================
-- 4. Kolon Tanımlarını Optimize Et
-- ============================================

-- Name kolonu optimizasyonu
ALTER TABLE app_user 
MODIFY COLUMN name VARCHAR(100) NOT NULL 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Email kolonu optimizasyonu
ALTER TABLE app_user 
MODIFY COLUMN email VARCHAR(150) NOT NULL 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Created_at kolonu optimizasyonu (microsecond precision)
ALTER TABLE app_user 
MODIFY COLUMN created_at DATETIME(6) NOT NULL;

-- Updated_at kolonu optimizasyonu (microsecond precision)
ALTER TABLE app_user 
MODIFY COLUMN updated_at DATETIME(6) NOT NULL;

-- ============================================
-- 5. Index'leri Kontrol Et
-- ============================================
SHOW INDEXES FROM app_user;

-- ============================================
-- 6. Tablo İstatistiklerini Güncelle
-- ============================================
ANALYZE TABLE app_user;

-- ============================================
-- Script Tamamlandı
-- ============================================
-- Tüm optimizasyonlar uygulandı.
-- Tablo yapısını kontrol etmek için: DESCRIBE app_user;
-- Index'leri kontrol etmek için: SHOW INDEXES FROM app_user;

