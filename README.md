# 📚 Java Layered Architecture Eğitim Seti

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.2-brightgreen)
![Java](https://img.shields.io/badge/Java-17-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-green)
![License](https://img.shields.io/badge/License-Education-lightgrey)

**Profesyonel Java Backend Geliştirme Eğitimi**

Modern Spring Boot uygulamaları için tasarlanmış, base ve generic yapı kullanılan layered architecture öğretim şablonu.

[Mimari Yapı](#-layered-architecture-mimari-yapı) • [Base Yapı](#-base-ve-generic-yapı) • [Kurulum](#-kurulum-ve-çalıştırma) • [Öğrenme Hedefleri](#-öğrenme-hedefleri)

</div>

---

## 🎯 Proje Hakkında

Bu proje, **Java Layered Architecture** öğrenmek isteyen geliştiriciler için hazırlanmış profesyonel bir eğitim setidir. 

### ✨ Eğitim Seti Özellikleri

- 🏗️ **Base ve Generic Yapı**: Tüm katmanlarda base sınıflar ve generic yapı kullanımı
- 📚 **Detaylı Yorumlar**: Her sınıf ve metod için eğitim amaçlı açıklamalar
- 🎓 **Best Practices**: Endüstri standartlarına uygun kod yapısı
- 🔄 **DRY Principle**: Kod tekrarını önleyen base yapılar
- 🧩 **Design Patterns**: Template Method, Dependency Injection vb.
- 📖 **Layered Architecture**: Katmanlı mimari yapısı

---

## 🏗️ Layered Architecture - Mimari Yapı

Bu proje, **Layered Architecture (Katmanlı Mimari)** prensibine göre yapılandırılmıştır:

```
┌─────────────────────────────────────────────────────────┐
│                    CONTROLLER LAYER                      │
│  ┌──────────────────┐  ┌──────────────────────────┐   │
│  │ UserController    │  │ UserViewController       │   │
│  │ (REST API)        │  │ (Thymeleaf Views)        │   │
│  └──────────────────┘  └──────────────────────────┘   │
└───────────────────────┬─────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────┐
│                    SERVICE LAYER                         │
│  ┌──────────────────┐  ┌──────────────────────────┐   │
│  │ BaseService       │  │ UserService              │   │
│  │ (Generic)         │  │ (Extends BaseService)    │   │
│  └──────────────────┘  └──────────────────────────┘   │
│  ┌──────────────────┐  ┌──────────────────────────┐   │
│  │ BaseServiceImpl   │  │ UserServiceImpl         │   │
│  │ (Generic)         │  │ (Extends BaseServiceImpl)│   │
│  └──────────────────┘  └──────────────────────────┘   │
└───────────────────────┬─────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────┐
│                   REPOSITORY LAYER                       │
│  ┌──────────────────┐  ┌──────────────────────────┐   │
│  │ BaseRepository   │  │ UserRepository            │   │
│  │ (Generic)        │  │ (Extends BaseRepository)  │   │
│  └──────────────────┘  └──────────────────────────┘   │
└───────────────────────┬─────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────┐
│                     ENTITY LAYER                         │
│  ┌──────────────────┐  ┌──────────────────────────┐   │
│  │ BaseEntity       │  │ User                      │   │
│  │ (Generic)        │  │ (Extends BaseEntity)      │   │
│  └──────────────────┘  └──────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
```

### Katman Açıklamaları

1. **Controller Layer**: HTTP isteklerini karşılar, Service katmanına yönlendirir
2. **Service Layer**: İş mantığını (business logic) içerir, validasyon yapar
3. **Repository Layer**: Veritabanı işlemlerini yönetir
4. **Entity Layer**: Veritabanı tablolarını temsil eder

---

## 🔧 Base ve Generic Yapı

Proje, **base ve generic yapı** kullanarak kod tekrarını önler ve genişletilebilir bir mimari sunar.

### BaseEntity

```java
public abstract class BaseEntity<ID extends Serializable> {
    private ID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // @PrePersist, @PreUpdate metodları
}
```

**Özellikler:**
- Generic ID tipi (Long, String, UUID vb.)
- Otomatik timestamp yönetimi
- Tüm entity'ler için ortak alanlar

**Kullanım:**
```java
@Entity
public class User extends BaseEntity<Long> {
    private String name;
    private String email;
    // id, createdAt, updatedAt otomatik gelir
}
```

### BaseRepository

```java
public interface BaseRepository<T extends BaseEntity<ID>, ID extends Serializable> 
        extends JpaRepository<T, ID> {
    // Temel CRUD metodları otomatik sağlanır
}
```

**Özellikler:**
- Generic entity ve ID tipi
- Spring Data JPA metodları otomatik sağlanır
- Özel query metodları eklenebilir

**Kullanım:**
```java
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```

### BaseService ve BaseServiceImpl

```java
public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {
    T save(T entity);
    List<T> findAll();
    T findById(ID id);
    T update(ID id, T entity);
    boolean delete(ID id);
}

public abstract class BaseServiceImpl<T, ID, R extends BaseRepository<T, ID>> 
        implements BaseService<T, ID> {
    // Generic CRUD implementasyonu
    // Validation hook'ları
}
```

**Özellikler:**
- Generic CRUD işlemleri
- Transaction yönetimi
- Validation hook'ları (override edilebilir)
- Kod tekrarını önler

**Kullanım:**
```java
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> 
        implements UserService {
    // User'a özel metodlar ve validasyonlar
}
```

---

## 📁 Proje Yapısı

```
simple-service/
├── src/
│   ├── main/
│   │   ├── java/com/example/simple_service/
│   │   │   ├── controller/
│   │   │   │   ├── UserController.java          # REST API endpoints
│   │   │   │   └── UserViewController.java      # Thymeleaf view endpoints
│   │   │   ├── entity/
│   │   │   │   ├── base/
│   │   │   │   │   └── BaseEntity.java         # Generic base entity
│   │   │   │   └── User.java                   # User entity (extends BaseEntity)
│   │   │   ├── repository/
│   │   │   │   ├── base/
│   │   │   │   │   └── BaseRepository.java      # Generic base repository
│   │   │   │   └── UserRepository.java         # User repository (extends BaseRepository)
│   │   │   ├── service/
│   │   │   │   ├── base/
│   │   │   │   │   ├── BaseService.java         # Generic base service interface
│   │   │   │   │   └── BaseServiceImpl.java    # Generic base service implementation
│   │   │   │   ├── UserService.java             # User service interface (extends BaseService)
│   │   │   │   └── impl/
│   │   │   │       └── UserServiceImpl.java   # User service implementation (extends BaseServiceImpl)
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java  # Global exception handling
│   │   │   └── SimpleServiceApplication.java   # Main application class
│   │   └── resources/
│   │       ├── templates/                      # Thymeleaf templates
│   │       ├── static/                         # Static files
│   │       └── application.properties          # Configuration
│   └── test/                                   # Test files
├── pom.xml                                     # Maven dependencies
└── README.md                                   # Bu dosya
```

---

## 🎓 Öğrenme Hedefleri

Bu eğitim setini tamamladığınızda şunları öğrenmiş olacaksınız:

### 1. Layered Architecture
- ✅ Katmanlı mimari yapısını anlama
- ✅ Her katmanın sorumluluğunu öğrenme
- ✅ Katmanlar arası iletişimi kavrama

### 2. Base ve Generic Yapı
- ✅ Generic programlama kavramları
- ✅ Base sınıflar oluşturma
- ✅ Kod tekrarını önleme (DRY principle)
- ✅ Genişletilebilir mimari tasarımı

### 3. Spring Boot ve Spring Data JPA
- ✅ Spring Boot otomatik yapılandırması
- ✅ Spring Data JPA repository pattern
- ✅ JPA entity yönetimi
- ✅ Transaction yönetimi

### 4. Design Patterns
- ✅ Template Method Pattern (BaseServiceImpl)
- ✅ Dependency Injection
- ✅ Repository Pattern
- ✅ Service Layer Pattern

### 5. Best Practices
- ✅ Constructor injection
- ✅ Exception handling
- ✅ Validation stratejileri
- ✅ Code organization

---

## 🚀 Kurulum ve Çalıştırma

### 1️⃣ Gereksinimler

- ☕ **Java 17** veya üzeri
- 🗄️ **MySQL 8.0** veya **PostgreSQL 12+** (ikisinden biri yeterli)
- 🔧 **Maven 3.9+**
- 💻 **IDE** (IntelliJ IDEA önerilir)

### 2️⃣ Veritabanını Hazırlayın

#### MySQL için:

```sql
-- MySQL'e root kullanıcısı ile bağlanın
mysql -u root -p

-- Veritabanını oluşturun
CREATE DATABASE simple_service CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Veritabanını kullanın
USE simple_service;

-- Tablo otomatik olarak oluşturulacak (Hibernate DDL Auto)
```

#### PostgreSQL için:

```sql
-- PostgreSQL'e postgres kullanıcısı ile bağlanın
psql -U postgres

-- Veritabanını oluşturun
CREATE DATABASE simple_service_db 
    WITH ENCODING 'UTF8' 
    LC_COLLATE='en_US.utf8' 
    LC_CTYPE='en_US.utf8';

-- Veritabanına bağlanın
\c simple_service_db

-- Tablo otomatik olarak oluşturulacak (Hibernate DDL Auto)
```

### 3️⃣ Spring Profile Yapılandırması

Proje, **Spring Profiles** kullanarak MySQL ve PostgreSQL desteği sunar.

#### Varsayılan Profile (MySQL)

Varsayılan olarak MySQL profile'ı aktif edilmiştir. `application.properties` dosyasında:

```properties
spring.profiles.active=mysql
```

#### PostgreSQL Profile'ını Aktif Etme

PostgreSQL kullanmak için:

**Yöntem 1: application.properties'te değiştir**
```properties
spring.profiles.active=postgresql
```

**Yöntem 2: Komut satırından**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=postgresql
```

**Yöntem 3: IDE'den**
Run Configuration'da VM options:
```
-Dspring.profiles.active=postgresql
```

### 4️⃣ Veritabanı Bağlantı Ayarları

#### MySQL Profile (`application-mysql.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/simple_service
spring.datasource.username=root
spring.datasource.password=12345
```

#### PostgreSQL Profile (`application-postgresql.properties`)

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/simple_service_db
spring.datasource.username=postgres
spring.datasource.password=&rEXMe^%}}x_2Vga
```

> ⚠️ **Güvenlik Notu**: Production ortamında şifreleri environment variable olarak kullanın!

### 5️⃣ Veritabanı Optimizasyonu (Opsiyonel)

Proje kök dizininde bulunan SQL script'lerini çalıştırarak tabloları optimize edebilirsiniz:

**MySQL için:**
```bash
mysql -u root -p12345 simple_service < database-optimization-mysql.sql
```

**PostgreSQL için:**
```bash
psql -U postgres -d simple_service_db -f database-optimization-postgresql.sql
```

Bu script'ler şunları yapar:
- Eksik index'leri ekler (email, created_at, updated_at)
- Kolon tanımlarını optimize eder
- Tablo charset/collation ayarlarını yapar

### 6️⃣ Projeyi Çalıştırın

```bash
# Maven ile bağımlılıkları indirin
mvn clean install

# Uygulamayı çalıştırın
mvn spring-boot:run
```

Alternatif olarak IDE'nizden `SimpleServiceApplication.java` dosyasını çalıştırabilirsiniz.

### 7️⃣ Tarayıcıda Açın

Uygulama başladıktan sonra:

```
http://localhost:8080
```

---

## 🗄️ Veritabanı Yapılandırması Detayları

### Spring Profiles

Proje, **Spring Profiles** kullanarak farklı veritabanları için ayrı yapılandırmalar sunar:

- **application.properties**: Ortak ayarlar ve aktif profile
- **application-mysql.properties**: MySQL özel ayarları
- **application-postgresql.properties**: PostgreSQL özel ayarları

### Connection Pool (HikariCP)

Her iki profile için HikariCP connection pool yapılandırılmıştır:

```properties
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.connection-timeout=30000
```

### JPA/Hibernate Optimizasyonları

- **Format SQL**: SQL sorguları formatlanmış şekilde loglanır
- **SQL Comments**: Hibernate SQL yorumları ekler
- **Batch Processing**: Toplu işlemler için batch size: 20
- **Naming Strategy**: Snake_case kullanımı (app_user, created_at)

### Tablo Optimizasyonları

**app_user** tablosu için optimize edilmiş yapı:

- **Index'ler**:
  - `idx_app_user_email`: Email kolonu için (sorgu performansı)
  - `idx_app_user_created_at`: Created_at kolonu için (tarih bazlı sorgular)
  - `idx_app_user_updated_at`: Updated_at kolonu için (tarih bazlı sorgular)

- **Unique Constraint**:
  - `uk_app_user_email`: Email kolonu için unique constraint

- **Kolon Tanımları**:
  - `id`: BIGINT AUTO_INCREMENT (MySQL) / BIGSERIAL (PostgreSQL)
  - `name`: VARCHAR(100) NOT NULL
  - `email`: VARCHAR(150) NOT NULL
  - `created_at`: DATETIME(6) NOT NULL (MySQL) / TIMESTAMP(6) NOT NULL (PostgreSQL)
  - `updated_at`: DATETIME(6) NOT NULL (MySQL) / TIMESTAMP(6) NOT NULL (PostgreSQL)

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
| **Maven** | 3.9+ | Build tool |

---

## 📖 Kod İnceleme Rehberi

### 1. BaseEntity İnceleme

**Dosya:** `src/main/java/com/example/simple_service/entity/base/BaseEntity.java`

**Öğrenilecekler:**
- Generic sınıf tanımlama
- JPA lifecycle callback'leri (@PrePersist, @PreUpdate)
- @MappedSuperclass anotasyonu
- Timestamp yönetimi

### 2. BaseRepository İnceleme

**Dosya:** `src/main/java/com/example/simple_service/repository/base/BaseRepository.java`

**Öğrenilecekler:**
- Generic interface tanımlama
- Spring Data JPA repository pattern
- @NoRepositoryBean anotasyonu

### 3. BaseService İnceleme

**Dosya:** `src/main/java/com/example/simple_service/service/base/BaseService.java` ve `BaseServiceImpl.java`

**Öğrenilecekler:**
- Generic service interface ve implementation
- Template Method Pattern
- Transaction yönetimi (@Transactional)
- Validation hook'ları

### 4. User Entity İnceleme

**Dosya:** `src/main/java/com/example/simple_service/entity/User.java`

**Öğrenilecekler:**
- BaseEntity'den extend etme
- JPA entity yapılandırması
- Lombok anotasyonları

### 5. UserService İnceleme

**Dosya:** `src/main/java/com/example/simple_service/service/impl/UserServiceImpl.java`

**Öğrenilecekler:**
- BaseServiceImpl'den extend etme
- Override metodları
- Özel validasyon ekleme
- Email format kontrolü

---

## 🧪 Test Etme

```bash
# Maven ile testleri çalıştır
mvn test

# Sadece uygulamayı çalıştır (test olmadan)
mvn spring-boot:run
```

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

---

## 📚 Öğrenme Kaynakları

Bu projede kullanılan teknolojiler hakkında daha fazla bilgi:

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Java Generics Tutorial](https://docs.oracle.com/javase/tutorial/java/generics/)

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

Bu eğitim setini kullandığınız için teşekkürler! 

> **"Öğrenmek bir yolculuktur, kod ise bu yolculuğun haritasıdır."** 🗺️

**Mutlu öğrenmeler!** 🚀✨

---

<div align="center">

**Yapıldı ❤️ ile Spring Boot ve Layered Architecture kullanılarak**

[⬆ Yukarı Çık](#-java-layered-architecture-eğitim-seti)

</div>
