# 🚀 Simple Service - Kullanıcı Yönetim Sistemi

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.2-brightgreen)
![Java](https://img.shields.io/badge/Java-17-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-green)
![License](https://img.shields.io/badge/License-Free-lightgrey)

**"Basit ama güçlü, sade ama şık!"** ✨

Modern web uygulamaları için tasarlanmış, Spring Boot 3 ve Thymeleaf ile geliştirilmiş kullanıcı yönetim sistemi.

[Özellikler](#-özellikler) • [Kurulum](#-kurulum-ve-çalıştırma) • [Kullanım](#-kullanım) • [API](#-api-endpoints)

</div>

---

## 🎯 Proje Hakkında

Bu proje, **ara sıra girip incelemek** için ideal bir basit servis örneğidir. Hem REST API hem de güzel bir web arayüzü sunar. Thymeleaf template engine ile server-side rendering yapılır, Tailwind CSS ile modern ve responsive bir tasarım kullanılır.

### ✨ Öne Çıkan Özellikler

- 🎨 **Modern UI**: Glassmorphism efektleri, gradient arka planlar, animasyonlar
- 🚀 **Hızlı ve Basit**: Spring Boot 3 ile optimize edilmiş performans
- 💎 **Thymeleaf Entegrasyonu**: Server-side rendering ile hızlı sayfa yükleme
- 🔒 **Güvenli Validasyon**: Email format kontrolü, duplicate kontrolü
- 📊 **Timestamps**: Otomatik oluşturulma ve güncelleme tarihleri
- 🔍 **Gelişmiş Arama**: ID, isim veya email ile arama yapabilme
- 📱 **Responsive Tasarım**: Mobil, tablet ve masaüstü uyumlu
- 🌙 **Dark Mode**: Karanlık mod desteği (localStorage ile saklanır)

---

## 🏗️ Teknoloji Stack'i

| Teknoloji | Versiyon | Amaç |
|-----------|----------|------|
| **Java** | 17 | Programlama dili |
| **Spring Boot** | 3.3.2 | Framework |
| **Spring Data JPA** | - | Veritabanı erişimi |
| **Thymeleaf** | - | Template engine |
| **MySQL** | 8+ | Veritabanı |
| **Lombok** | 1.18.36 | Kod sadeleştirme |
| **Tailwind CSS** | CDN | Stil framework'ü |
| **Maven** | 3.9+ | Build tool |

---

## 📋 Gereksinimler

Projeyi çalıştırmak için aşağıdakilere ihtiyacınız var:

- ☕ **Java 17** veya üzeri
- 🗄️ **MySQL 8.0** veya üzeri
- 🔧 **Maven 3.9+**
- 💻 **IDE** (IntelliJ IDEA, Eclipse, VS Code vb.)

---

## 🚀 Kurulum ve Çalıştırma

### 1️⃣ MySQL Veritabanını Hazırlayın

MySQL'e bağlanın ve veritabanını oluşturun:

```sql
-- MySQL'e root kullanıcısı ile bağlanın
mysql -u root -p

-- Veritabanını oluşturun
CREATE DATABASE simple_service CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Veritabanını kullanın
USE simple_service;

-- Tablo otomatik olarak oluşturulacak (Hibernate DDL Auto)
-- Ancak manuel oluşturmak isterseniz:
CREATE TABLE app_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    created_at DATETIME NULL,
    updated_at DATETIME NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 2️⃣ Veritabanı Bağlantı Ayarları

`src/main/resources/application.properties` dosyasında MySQL bağlantı bilgileri zaten yapılandırılmış:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/simple_service?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=12345
```

> ⚠️ **Güvenlik Notu**: Production ortamında şifreleri environment variable olarak kullanın!

### 3️⃣ Projeyi Çalıştırın

```bash
# Projeyi klonlayın (eğer git repo ise)
git clone <repo-url>
cd simple-service

# Maven ile bağımlılıkları indirin ve projeyi çalıştırın
mvn clean install
mvn spring-boot:run
```

Alternatif olarak IDE'nizden `SimpleServiceApplication.java` dosyasını çalıştırabilirsiniz.

### 4️⃣ Tarayıcıda Açın

Uygulama başladıktan sonra tarayıcınızda şu adrese gidin:

```
http://localhost:8080
```

🎉 **Tebrikler!** Artık kullanıcı yönetim sisteminiz hazır!

---

## 📁 Proje Yapısı

```
simple-service/
├── src/
│   ├── main/
│   │   ├── java/com/example/simple_service/
│   │   │   ├── controller/
│   │   │   │   ├── UserController.java      # REST API endpoints
│   │   │   │   └── UserViewController.java  # Thymeleaf view endpoints
│   │   │   ├── entity/
│   │   │   │   └── User.java                # JPA Entity
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java      # Spring Data JPA Repository
│   │   │   ├── service/
│   │   │   │   ├── UserService.java         # Service interface
│   │   │   │   └── impl/
│   │   │   │       └── UserServiceImpl.java # Service implementation
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java # Exception handling
│   │   │   └── SimpleServiceApplication.java # Main class
│   │   └── resources/
│   │       ├── templates/                   # Thymeleaf templates
│   │       │   ├── layout.html              # Base layout
│   │       │   ├── index.html               # Ana sayfa
│   │       │   └── users/
│   │       │       ├── list.html            # Kullanıcı listesi
│   │       │       └── form.html            # Kullanıcı formu
│   │       ├── static/                      # Static dosyalar
│   │       └── application.properties       # Konfigürasyon
│   └── test/                                # Test dosyaları
├── pom.xml                                  # Maven dependencies
└── README.md                                # Bu dosya
```

---

## 🔌 API Endpoints

### REST API (JSON)

| Metot | Endpoint | Açıklama | Request Body |
|-------|----------|----------|--------------|
| `GET` | `/users` | Tüm kullanıcıları listele | - |
| `GET` | `/users/{id}` | ID'ye göre kullanıcı getir | - |
| `GET` | `/users/email?email={email}` | Email'e göre kullanıcı getir | - |
| `POST` | `/users` | Yeni kullanıcı oluştur | `{"name": "...", "email": "..."}` |
| `PUT` | `/users/{id}` | Kullanıcı güncelle | `{"name": "...", "email": "..."}` |
| `DELETE` | `/users/{id}` | Kullanıcı sil | - |

### Web UI (Thymeleaf)

| Endpoint | Açıklama |
|----------|----------|
| `/` | Ana sayfa |
| `/users/view` | Kullanıcı listesi (arama parametreleri: `?id=`, `?name=`, `?email=`) |
| `/users/form` | Yeni kullanıcı formu |
| `/users/form?id={id}` | Kullanıcı düzenleme formu |

---

## 💻 Kullanım Örnekleri

### cURL ile REST API Kullanımı

```bash
# Yeni kullanıcı oluştur
curl -X POST http://localhost:8080/users \
     -H "Content-Type: application/json" \
     -d '{"name":"Ahmet Yılmaz","email":"ahmet@example.com"}'

# Tüm kullanıcıları listele
curl http://localhost:8080/users

# ID'ye göre kullanıcı getir
curl http://localhost:8080/users/1

# Email'e göre kullanıcı getir
curl "http://localhost:8080/users/email?email=ahmet@example.com"

# Kullanıcı güncelle
curl -X PUT http://localhost:8080/users/1 \
     -H "Content-Type: application/json" \
     -d '{"name":"Ahmet Yılmaz","email":"ahmet.yilmaz@example.com"}'

# Kullanıcı sil
curl -X DELETE http://localhost:8080/users/1
```

### Web Arayüzü Kullanımı

1. **Ana Sayfa**: `http://localhost:8080` - Proje hakkında bilgi ve hızlı erişim
2. **Kullanıcı Listesi**: `http://localhost:8080/users/view` - Tüm kullanıcıları görüntüle, ara, düzenle, sil
3. **Yeni Kullanıcı**: `http://localhost:8080/users/form` - Yeni kullanıcı ekle
4. **Kullanıcı Düzenle**: Listeden "Düzenle" butonuna tıklayın

---

## 🎨 Özellikler Detayı

### ✅ Validasyon Özellikleri

- ✉️ **Email Format Kontrolü**: Regex ile email formatı doğrulanır
- 🔒 **Unique Email**: Aynı email ile birden fazla kullanıcı kaydedilemez
- 📏 **Uzunluk Kontrolü**: İsim max 100, email max 150 karakter
- 🚫 **Boş Alan Kontrolü**: Zorunlu alanlar kontrol edilir

### 🗄️ Veritabanı Özellikleri

- 📅 **Otomatik Timestamps**: `created_at` ve `updated_at` otomatik güncellenir
- 🔑 **Primary Key**: Auto-increment ID
- 🎯 **Unique Constraint**: Email alanı unique
- 📊 **Index**: Email alanı için otomatik index

### 🎭 UI/UX Özellikleri

- 🌈 **Gradient Arka Planlar**: Modern görsel efektler
- 💎 **Glassmorphism**: Cam efekti kartlar
- 🌙 **Dark Mode Toggle**: Karanlık mod desteği (localStorage ile saklanır)
- 📱 **Responsive**: Mobil, tablet, masaüstü uyumlu
- ✨ **Animasyonlar**: Hover efektleri, float animasyonları
- 🔔 **Flash Messages**: Başarı/hata mesajları
- 🎯 **Error Handling**: Browser extension hatalarını otomatik filtreleme

---

## 🐛 Sorun Giderme

### Veritabanı Bağlantı Hatası

```
Error: Access denied for user 'root'@'localhost'
```

**Çözüm**: MySQL şifrenizi kontrol edin. `application.properties` dosyasında doğru şifreyi girin.

### Port Zaten Kullanımda

```
Error: Port 8080 is already in use
```

**Çözüm**: 
- Başka bir port kullanın: `server.port=8081` ekleyin `application.properties`'e
- Veya 8080 portunu kullanan uygulamayı durdurun

### Tablo Bulunamadı

```
Error: Table 'simple_service.app_user' doesn't exist
```

**Çözüm**: 
- `spring.jpa.hibernate.ddl-auto=update` ayarının aktif olduğundan emin olun
- Uygulamayı yeniden başlatın (Hibernate tabloyu otomatik oluşturacaktır)

### Timestamp Kolonları Hatası

Eğer mevcut tabloda veri varsa ve timestamp kolonları eklenemiyorsa:

```sql
USE simple_service;
ALTER TABLE app_user ADD COLUMN created_at DATETIME NULL;
ALTER TABLE app_user ADD COLUMN updated_at DATETIME NULL;
UPDATE app_user SET created_at = NOW(), updated_at = NOW();
```

---

## 🧪 Test Etme

```bash
# Maven ile testleri çalıştır
mvn test

# Sadece uygulamayı çalıştır (test olmadan)
mvn spring-boot:run
```

---

## 📚 Öğrenme Kaynakları

Bu projede kullanılan teknolojiler hakkında daha fazla bilgi:

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Tailwind CSS](https://tailwindcss.com/docs)

---

## 🤝 Katkıda Bulunma

Katkılarınızı bekliyoruz! 

1. 🍴 Projeyi fork edin
2. 🌿 Yeni bir branch oluşturun (`git checkout -b feature/amazing-feature`)
3. 💾 Değişikliklerinizi commit edin (`git commit -m 'Add amazing feature'`)
4. 📤 Branch'inizi push edin (`git push origin feature/amazing-feature`)
5. 🔄 Pull Request oluşturun

---

## 📝 Lisans

Bu proje eğitim ve kişisel kullanım amaçlıdır. İstediğiniz gibi kullanabilirsiniz.

---

## 💬 İletişim

Sorularınız, önerileriniz veya hata bildirimleri için:

- 📧 Issue açabilirsiniz
- 💬 Discussion başlatabilirsiniz
- ⭐ Projeyi beğenmeyi unutmayın!

---

## 🎉 Teşekkürler

Bu projeyi kullandığınız için teşekkürler! 

> **"Kod yazmak bir sanattır, basitlik ise en yüksek formudur."** 🎨

**Mutlu kodlamalar!** 🚀✨

---

<div align="center">

**Yapıldı ❤️ ile Spring Boot ve Thymeleaf kullanılarak**

[⬆ Yukarı Çık](#-simple-service---kullanıcı-yönetim-sistemi)

</div>
