# 🚀 Simple Service - Java Layered Architecture Eğitim Projesi

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.2-brightgreen?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue?style=for-the-badge)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-green?style=for-the-badge)
![License](https://img.shields.io/badge/License-Education-lightgrey?style=for-the-badge)

**Modern Java Backend Geliştirme Eğitimi için Profesyonel Şablon**

[English](#-simple-service---java-layered-architecture-education-project) • [Türkçe](#-simple-service---java-layered-architecture-eğitim-projesi)

[Özellikler](#-özellikler) • [Hızlı Başlangıç](#-hızlı-başlangıç) • [Mimari](#-mimari-yapı) • [API Dokümantasyonu](#-api-dokümantasyonu)

</div>

---

## 📋 İçindekiler

- [Özellikler](#-özellikler)
- [Hızlı Başlangıç](#-hızlı-başlangıç)
- [Mimari Yapı](#-mimari-yapı)
- [Kurulum](#-kurulum)
- [Kullanım](#-kullanım)
- [API Dokümantasyonu](#-api-dokümantasyonu)
- [Teknoloji Stack'i](#-teknoloji-stacki)
- [Öğrenme Rehberi](#-öğrenme-rehberi)

---

## ✨ Özellikler

### 🏗️ Mimari Özellikler
- ✅ **Layered Architecture** - Katmanlı mimari yapısı
- ✅ **Base & Generic Yapı** - Kod tekrarını önleyen generic base sınıflar
- ✅ **DRY Principle** - Don't Repeat Yourself prensibi
- ✅ **Design Patterns** - Template Method, Dependency Injection, Repository Pattern

### 🎓 Eğitim Özellikleri
- ✅ **Detaylı TODO Açıklamaları** - Her class için eğitim amaçlı açıklamalar
- ✅ **Best Practices** - Endüstri standartlarına uygun kod yapısı
- ✅ **Clean Code** - Okunabilir ve bakımı kolay kod
- ✅ **Comprehensive Comments** - Kapsamlı yorumlar ve dokümantasyon

### 💾 Veritabanı Desteği
- ✅ **MySQL 8.0+** - Tam destek
- ✅ **PostgreSQL 12+** - Tam destek
- ✅ **Spring Profiles** - Kolay veritabanı değiştirme
- ✅ **Auto Schema Generation** - Otomatik tablo oluşturma

### 🌐 Web Özellikleri
- ✅ **REST API** - JSON formatında API endpoints
- ✅ **Thymeleaf UI** - Modern ve responsive web arayüzü
- ✅ **Pagination** - Sayfalama desteği
- ✅ **Search & Filter** - Arama ve filtreleme

---

## 🚀 Hızlı Başlangıç

### Gereksinimler

- ☕ **Java 17** veya üzeri
- 🗄️ **MySQL 8.0+** veya **PostgreSQL 12+**
- 🔧 **Maven 3.9+**
- 💻 **IDE** (IntelliJ IDEA, Eclipse, VS Code)

### 1️⃣ Projeyi Klonlayın

```bash
git clone <repository-url>
cd simple-service
```

### 2️⃣ Veritabanını Hazırlayın

#### MySQL

```bash
mysql -u root -p
```

```sql
CREATE DATABASE simple_service CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

#### PostgreSQL

```bash
psql -U postgres
```

```sql
CREATE DATABASE simple_service_db WITH ENCODING 'UTF8' TEMPLATE template0;
\q
```

### 3️⃣ Uygulamayı Çalıştırın

```bash
# Bağımlılıkları indir
mvn clean install

# Uygulamayı başlat
mvn spring-boot:run
```

### 4️⃣ Tarayıcıda Açın

```
http://localhost:8080
```

🎉 **Tebrikler!** Uygulama çalışıyor!

---

## 🏗️ Mimari Yapı

Bu proje **Layered Architecture (Katmanlı Mimari)** prensibine göre yapılandırılmıştır:

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

### Katman Sorumlulukları

1. **Controller Layer** - HTTP isteklerini karşılar, Service katmanına yönlendirir
2. **Service Layer** - İş mantığını (business logic) içerir, validasyon yapar
3. **Repository Layer** - Veritabanı işlemlerini yönetir
4. **Entity Layer** - Veritabanı tablolarını temsil eder

---

## 📦 Kurulum

### Detaylı Kurulum Adımları

#### 1. Java Kurulumu

Java 17 veya üzeri versiyonunun kurulu olduğundan emin olun:

```bash
java -version
```

#### 2. Maven Kurulumu

Maven 3.9+ kurulu olmalı:

```bash
mvn -version
```

#### 3. Veritabanı Yapılandırması

**MySQL için** (`application-mysql.properties`):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/simple_service
spring.datasource.username=root
spring.datasource.password=12345
```

**PostgreSQL için** (`application-postgresql.properties`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/simple_service_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

#### 4. Spring Profile Seçimi

Varsayılan olarak MySQL aktif. PostgreSQL kullanmak için:

**Yöntem 1:** `application.properties` dosyasında:
```properties
spring.profiles.active=postgresql
```

**Yöntem 2:** Komut satırından:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=postgresql
```

**Yöntem 3:** IDE'den VM options:
```
-Dspring.profiles.active=postgresql
```

---

## 💻 Kullanım

### Web Arayüzü

1. **Ana Sayfa**: `http://localhost:8080`
2. **Kullanıcı Listesi**: `http://localhost:8080/users/view`
3. **Yeni Kullanıcı**: `http://localhost:8080/users/form`
4. **Kullanıcı Düzenle**: `http://localhost:8080/users/form?id={id}`

### REST API Kullanımı

#### Yeni Kullanıcı Oluştur

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Ahmet Yılmaz","email":"ahmet@example.com"}'
```

#### Tüm Kullanıcıları Listele

```bash
curl http://localhost:8080/users
```

#### ID'ye Göre Kullanıcı Getir

```bash
curl http://localhost:8080/users/1
```

#### Email'e Göre Kullanıcı Getir

```bash
curl "http://localhost:8080/users/email?email=ahmet@example.com"
```

#### Kullanıcı Güncelle

```bash
curl -X PUT http://localhost:8080/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Ahmet Yılmaz","email":"ahmet.yilmaz@example.com"}'
```

#### Kullanıcı Sil

```bash
curl -X DELETE http://localhost:8080/users/1
```

---

## 📚 API Dokümantasyonu

### REST API Endpoints

| HTTP Metodu | Endpoint | Açıklama | Request Body |
|-------------|----------|----------|--------------|
| `GET` | `/users` | Tüm kullanıcıları listele | - |
| `GET` | `/users/{id}` | ID'ye göre kullanıcı getir | - |
| `GET` | `/users/email?email={email}` | Email'e göre kullanıcı getir | - |
| `POST` | `/users` | Yeni kullanıcı oluştur | `{"name": "...", "email": "..."}` |
| `PUT` | `/users/{id}` | Kullanıcı güncelle | `{"name": "...", "email": "..."}` |
| `DELETE` | `/users/{id}` | Kullanıcı sil | - |

### Web UI Endpoints

| Endpoint | Açıklama | Parametreler |
|----------|----------|--------------|
| `/` | Ana sayfa | - |
| `/users/view` | Kullanıcı listesi | `?id=`, `?name=`, `?email=`, `?page=`, `?size=` |
| `/users/form` | Yeni kullanıcı formu | - |
| `/users/form?id={id}` | Kullanıcı düzenleme formu | `id` |

### HTTP Status Codes

- `200 OK` - İstek başarılı
- `201 CREATED` - Yeni kaynak oluşturuldu
- `204 NO CONTENT` - İşlem başarılı (response body yok)
- `404 NOT FOUND` - Kaynak bulunamadı

---

## 🛠️ Teknoloji Stack'i

| Teknoloji | Versiyon | Amaç |
|-----------|----------|------|
| **Java** | 17 | Programlama dili |
| **Spring Boot** | 3.3.2 | Framework |
| **Spring Data JPA** | - | Veritabanı erişimi |
| **Thymeleaf** | - | Template engine |
| **MySQL** | 8.0+ | Veritabanı |
| **PostgreSQL** | 12+ | Veritabanı |
| **Lombok** | 1.18.36 | Kod sadeleştirme |
| **Maven** | 3.9+ | Build tool |

---

## 📖 Öğrenme Rehberi

### Temel Kavramlar

#### 1. Generic Programlama

```java
// BaseEntity generic ID tipi ile
public abstract class BaseEntity<ID extends Serializable> {
    private ID id;
    // ...
}
```

#### 2. Inheritance (Kalıtım)

```java
// User, BaseEntity'den extend eder
@Entity
public class User extends BaseEntity<Long> {
    // id, createdAt, updatedAt otomatik gelir
}
```

#### 3. Dependency Injection

```java
// Constructor injection (best practice)
public UserController(UserService userService) {
    this.userService = userService;
}
```

#### 4. Transaction Management

```java
@Transactional
public T save(T entity) {
    // Transaction içinde çalışır
}
```

### Kod İnceleme Sırası

1. **BaseEntity** - Generic base entity yapısı
2. **BaseRepository** - Generic repository pattern
3. **BaseService** - Generic service pattern
4. **User Entity** - Concrete entity örneği
5. **UserService** - Business logic implementasyonu
6. **UserController** - REST API endpoints

### Öğrenme Hedefleri

- ✅ Layered Architecture kavramı
- ✅ Generic programlama
- ✅ Design Patterns (Template Method, DI, Repository)
- ✅ Spring Boot otomatik yapılandırma
- ✅ JPA/Hibernate kullanımı
- ✅ REST API geliştirme
- ✅ Thymeleaf template engine

---

## 🐛 Sorun Giderme

### Veritabanı Bağlantı Hatası

**Hata:** `Access denied for user 'root'@'localhost'`

**Çözüm:** 
- Veritabanı şifresini kontrol edin
- `application-mysql.properties` veya `application-postgresql.properties` dosyasında doğru bilgileri girin

### Port Zaten Kullanımda

**Hata:** `Port 8080 is already in use`

**Çözüm:**
- `application.properties` dosyasına `server.port=8081` ekleyin
- Veya 8080 portunu kullanan uygulamayı durdurun

### Tablo Bulunamadı

**Hata:** `Table 'simple_service.app_user' doesn't exist`

**Çözüm:**
- `spring.jpa.hibernate.ddl-auto=update` ayarının aktif olduğundan emin olun
- Uygulamayı yeniden başlatın (Hibernate tabloyu otomatik oluşturacaktır)

---

## 📚 Ek Kaynaklar

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
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

<div align="center">

**Made with ❤️ using Spring Boot and Layered Architecture**

[⬆ Back to Top](#-simple-service---java-layered-architecture-eğitim-projesi)

</div>

---

# 🚀 Simple Service - Java Layered Architecture Education Project

<div align="center">

**Professional Template for Modern Java Backend Development Education**

[Features](#-features) • [Quick Start](#-quick-start) • [Architecture](#-architecture) • [API Documentation](#-api-documentation)

</div>

---

## 📋 Table of Contents

- [Features](#-features)
- [Quick Start](#-quick-start)
- [Architecture](#-architecture)
- [Installation](#-installation)
- [Usage](#-usage)
- [API Documentation](#-api-documentation)
- [Technology Stack](#-technology-stack)
- [Learning Guide](#-learning-guide)

---

## ✨ Features

### 🏗️ Architectural Features
- ✅ **Layered Architecture** - Layered architecture structure
- ✅ **Base & Generic Structure** - Generic base classes preventing code duplication
- ✅ **DRY Principle** - Don't Repeat Yourself principle
- ✅ **Design Patterns** - Template Method, Dependency Injection, Repository Pattern

### 🎓 Educational Features
- ✅ **Detailed TODO Comments** - Educational explanations for each class
- ✅ **Best Practices** - Industry-standard code structure
- ✅ **Clean Code** - Readable and maintainable code
- ✅ **Comprehensive Comments** - Extensive comments and documentation

### 💾 Database Support
- ✅ **MySQL 8.0+** - Full support
- ✅ **PostgreSQL 12+** - Full support
- ✅ **Spring Profiles** - Easy database switching
- ✅ **Auto Schema Generation** - Automatic table creation

### 🌐 Web Features
- ✅ **REST API** - JSON format API endpoints
- ✅ **Thymeleaf UI** - Modern and responsive web interface
- ✅ **Pagination** - Pagination support
- ✅ **Search & Filter** - Search and filtering

---

## 🚀 Quick Start

### Requirements

- ☕ **Java 17** or higher
- 🗄️ **MySQL 8.0+** or **PostgreSQL 12+**
- 🔧 **Maven 3.9+**
- 💻 **IDE** (IntelliJ IDEA, Eclipse, VS Code)

### 1️⃣ Clone the Project

```bash
git clone <repository-url>
cd simple-service
```

### 2️⃣ Prepare the Database

#### MySQL

```bash
mysql -u root -p
```

```sql
CREATE DATABASE simple_service CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

#### PostgreSQL

```bash
psql -U postgres
```

```sql
CREATE DATABASE simple_service_db WITH ENCODING 'UTF8' TEMPLATE template0;
\q
```

### 3️⃣ Run the Application

```bash
# Download dependencies
mvn clean install

# Start the application
mvn spring-boot:run
```

### 4️⃣ Open in Browser

```
http://localhost:8080
```

🎉 **Congratulations!** The application is running!

---

## 🏗️ Architecture

This project is structured according to the **Layered Architecture** principle:

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

### Layer Responsibilities

1. **Controller Layer** - Handles HTTP requests, routes to Service layer
2. **Service Layer** - Contains business logic, performs validation
3. **Repository Layer** - Manages database operations
4. **Entity Layer** - Represents database tables

---

## 📦 Installation

### Detailed Installation Steps

#### 1. Java Installation

Ensure Java 17 or higher is installed:

```bash
java -version
```

#### 2. Maven Installation

Maven 3.9+ must be installed:

```bash
mvn -version
```

#### 3. Database Configuration

**For MySQL** (`application-mysql.properties`):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/simple_service
spring.datasource.username=root
spring.datasource.password=12345
```

**For PostgreSQL** (`application-postgresql.properties`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/simple_service_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

#### 4. Spring Profile Selection

MySQL is active by default. To use PostgreSQL:

**Method 1:** In `application.properties` file:
```properties
spring.profiles.active=postgresql
```

**Method 2:** From command line:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=postgresql
```

**Method 3:** From IDE VM options:
```
-Dspring.profiles.active=postgresql
```

---

## 💻 Usage

### Web Interface

1. **Home Page**: `http://localhost:8080`
2. **User List**: `http://localhost:8080/users/view`
3. **New User**: `http://localhost:8080/users/form`
4. **Edit User**: `http://localhost:8080/users/form?id={id}`

### REST API Usage

#### Create New User

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com"}'
```

#### List All Users

```bash
curl http://localhost:8080/users
```

#### Get User by ID

```bash
curl http://localhost:8080/users/1
```

#### Get User by Email

```bash
curl "http://localhost:8080/users/email?email=john@example.com"
```

#### Update User

```bash
curl -X PUT http://localhost:8080/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john.doe@example.com"}'
```

#### Delete User

```bash
curl -X DELETE http://localhost:8080/users/1
```

---

## 📚 API Documentation

### REST API Endpoints

| HTTP Method | Endpoint | Description | Request Body |
|-------------|----------|-------------|--------------|
| `GET` | `/users` | List all users | - |
| `GET` | `/users/{id}` | Get user by ID | - |
| `GET` | `/users/email?email={email}` | Get user by email | - |
| `POST` | `/users` | Create new user | `{"name": "...", "email": "..."}` |
| `PUT` | `/users/{id}` | Update user | `{"name": "...", "email": "..."}` |
| `DELETE` | `/users/{id}` | Delete user | - |

### Web UI Endpoints

| Endpoint | Description | Parameters |
|----------|-------------|------------|
| `/` | Home page | - |
| `/users/view` | User list | `?id=`, `?name=`, `?email=`, `?page=`, `?size=` |
| `/users/form` | New user form | - |
| `/users/form?id={id}` | Edit user form | `id` |

### HTTP Status Codes

- `200 OK` - Request successful
- `201 CREATED` - New resource created
- `204 NO CONTENT` - Operation successful (no response body)
- `404 NOT FOUND` - Resource not found

---

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 17 | Programming language |
| **Spring Boot** | 3.3.2 | Framework |
| **Spring Data JPA** | - | Database access |
| **Thymeleaf** | - | Template engine |
| **MySQL** | 8.0+ | Database |
| **PostgreSQL** | 12+ | Database |
| **Lombok** | 1.18.36 | Code simplification |
| **Maven** | 3.9+ | Build tool |

---

## 📖 Learning Guide

### Core Concepts

#### 1. Generic Programming

```java
// BaseEntity with generic ID type
public abstract class BaseEntity<ID extends Serializable> {
    private ID id;
    // ...
}
```

#### 2. Inheritance

```java
// User extends BaseEntity
@Entity
public class User extends BaseEntity<Long> {
    // id, createdAt, updatedAt automatically inherited
}
```

#### 3. Dependency Injection

```java
// Constructor injection (best practice)
public UserController(UserService userService) {
    this.userService = userService;
}
```

#### 4. Transaction Management

```java
@Transactional
public T save(T entity) {
    // Runs within transaction
}
```

### Code Review Order

1. **BaseEntity** - Generic base entity structure
2. **BaseRepository** - Generic repository pattern
3. **BaseService** - Generic service pattern
4. **User Entity** - Concrete entity example
5. **UserService** - Business logic implementation
6. **UserController** - REST API endpoints

### Learning Objectives

- ✅ Layered Architecture concept
- ✅ Generic programming
- ✅ Design Patterns (Template Method, DI, Repository)
- ✅ Spring Boot auto-configuration
- ✅ JPA/Hibernate usage
- ✅ REST API development
- ✅ Thymeleaf template engine

---

## 🐛 Troubleshooting

### Database Connection Error

**Error:** `Access denied for user 'root'@'localhost'`

**Solution:**
- Check database password
- Enter correct credentials in `application-mysql.properties` or `application-postgresql.properties` file

### Port Already in Use

**Error:** `Port 8080 is already in use`

**Solution:**
- Add `server.port=8081` to `application.properties` file
- Or stop the application using port 8080

### Table Not Found

**Error:** `Table 'simple_service.app_user' doesn't exist`

**Solution:**
- Ensure `spring.jpa.hibernate.ddl-auto=update` is active
- Restart the application (Hibernate will automatically create the table)

---

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Java Generics Tutorial](https://docs.oracle.com/javase/tutorial/java/generics/)

---

## 🤝 Contributing

We welcome your contributions!

1. 🍴 Fork the project
2. 🌿 Create a new branch (`git checkout -b feature/amazing-feature`)
3. 💾 Commit your changes (`git commit -m 'Add amazing feature'`)
4. 📤 Push to the branch (`git push origin feature/amazing-feature`)
5. 🔄 Open a Pull Request

---

## 📝 License

This project is for educational and personal use. You can use it as you wish.

---

## 💬 Contact

For questions, suggestions, or bug reports:
- 📧 Open an issue
- 💬 Start a discussion
- ⭐ Don't forget to star the project!

---

<div align="center">

**Made with ❤️ using Spring Boot and Layered Architecture**

[⬆ Back to Top](#-simple-service---java-layered-architecture-education-project)

</div>
