-- ============================================
-- PostgreSQL Veritabanı Optimizasyon Script'i
-- ============================================
-- Bu script, simple_service_db veritabanındaki app_user tablosunu optimize eder.
-- Çalıştırmadan önce: \c simple_service_db;

-- ============================================
-- 1. Mevcut Tablo Yapısını Kontrol Et
-- ============================================
-- \d app_user
-- \d+ app_user

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
-- 3. Kolon Tanımlarını Optimize Et
-- ============================================

-- Name kolonu optimizasyonu
ALTER TABLE app_user 
ALTER COLUMN name TYPE VARCHAR(100);

-- Email kolonu optimizasyonu
ALTER TABLE app_user 
ALTER COLUMN email TYPE VARCHAR(150);

-- Created_at kolonu optimizasyonu (microsecond precision)
ALTER TABLE app_user 
ALTER COLUMN created_at TYPE TIMESTAMP(6) NOT NULL;

-- Updated_at kolonu optimizasyonu (microsecond precision)
ALTER TABLE app_user 
ALTER COLUMN updated_at TYPE TIMESTAMP(6) NOT NULL;

-- ============================================
-- 4. Constraint'leri Kontrol Et
-- ============================================

-- Email unique constraint kontrolü (zaten varsa hata vermez)
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint 
        WHERE conname = 'uk_app_user_email'
    ) THEN
        ALTER TABLE app_user 
        ADD CONSTRAINT uk_app_user_email UNIQUE (email);
    END IF;
END $$;

-- ============================================
-- 5. Index'leri Kontrol Et
-- ============================================
-- \d+ app_user

-- ============================================
-- 6. Tablo İstatistiklerini Güncelle
-- ============================================
ANALYZE app_user;

-- ============================================
-- Script Tamamlandı
-- ============================================
-- Tüm optimizasyonlar uygulandı.
-- Tablo yapısını kontrol etmek için: \d app_user
-- Index'leri kontrol etmek için: \d+ app_user

