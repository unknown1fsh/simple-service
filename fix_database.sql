-- Simple Service Veritabanı Düzeltme Scripti
-- Bu script'i MySQL'de çalıştırarak tabloyu düzeltebilirsiniz

USE simple_service;

-- Mevcut tablo yapısını kontrol edin
DESCRIBE app_user;

-- created_at kolonunu ekle (eğer yoksa)
-- Not: Eğer kolon zaten varsa hata verecektir, o zaman bu satırı atlayın
ALTER TABLE app_user 
ADD COLUMN created_at DATETIME NULL;

-- updated_at kolonunu ekle (eğer yoksa)
-- Not: Eğer kolon zaten varsa hata verecektir, o zaman bu satırı atlayın
ALTER TABLE app_user 
ADD COLUMN updated_at DATETIME NULL;

-- Mevcut kayıtlar için tarihleri güncelle
UPDATE app_user 
SET created_at = NOW(), updated_at = NOW() 
WHERE created_at IS NULL OR updated_at IS NULL;

-- Tablo yapısını tekrar kontrol edin
DESCRIBE app_user;

