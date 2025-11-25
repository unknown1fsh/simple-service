package com.example.simple_service.controller;

import com.example.simple_service.entity.User;
import com.example.simple_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * User REST Controller
 * 
 * Bu sınıf, User entity'si için REST API endpoint'lerini tanımlar.
 * Layered Architecture'da Controller katmanında yer alır.
 * 
 * REST API Standartları:
 * - POST /users - Yeni kullanıcı oluşturma (CREATE)
 * - GET /users - Tüm kullanıcıları listeleme (READ - list)
 * - GET /users/{id} - ID'ye göre kullanıcı getirme (READ - single)
 * - PUT /users/{id} - Kullanıcı güncelleme (UPDATE)
 * - DELETE /users/{id} - Kullanıcı silme (DELETE)
 * - GET /users/email?email=... - Email'e göre kullanıcı bulma (READ - query)
 * 
 * @RestController: Bu sınıfın bir REST controller olduğunu belirtir
 * - @Controller + @ResponseBody kombinasyonudur
 * - Tüm metodlar otomatik olarak JSON/XML formatında response döner
 * 
 * @RequestMapping: Base URL path tanımlar
 * - Tüm endpoint'ler "/users" ile başlar
 * 
 * @CrossOrigin: CORS (Cross-Origin Resource Sharing) ayarları
 * - Frontend uygulamalarının (örn: React, Angular) bu API'ye erişmesine izin verir
 * - Production'da daha spesifik origin'ler belirtilmelidir
 * 
 * Dependency Injection:
 * - Constructor injection ile UserService enjekte edilir (best practice)
 * - Service katmanı üzerinden iş mantığına erişilir (Repository'ye doğrudan erişilmez)
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    /**
     * UserService dependency'si
     * Service katmanı üzerinden iş mantığına erişilir
     */
    private final UserService userService;

    /**
     * Constructor - Dependency Injection
     * 
     * @param userService User iş mantığı için service
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Yeni bir kullanıcı oluşturur (CREATE)
     * 
     * HTTP Method: POST
     * Endpoint: /users
     * Request Body: User JSON objesi
     * 
     * @param user Request body'den gelen kullanıcı bilgileri
     * @return ResponseEntity<User> - 201 CREATED durum kodu ile kaydedilen kullanıcı
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Service katmanı üzerinden kullanıcıyı kaydet
        // BaseService'deki save() metodu kullanılır
        User savedUser = userService.save(user);
        // 201 CREATED: Yeni kaynak başarıyla oluşturuldu
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * Tüm kullanıcıları listeler (READ - list)
     * 
     * HTTP Method: GET
     * Endpoint: /users
     * 
     * @return ResponseEntity<List<User>> - 200 OK durum kodu ile kullanıcı listesi
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        // Service katmanı üzerinden tüm kullanıcıları getir
        // BaseService'deki findAll() metodu kullanılır
        List<User> users = userService.findAll();
        // 200 OK: İstek başarılı
        return ResponseEntity.ok(users);
    }

    /**
     * ID'ye göre kullanıcı getirir (READ - single)
     * 
     * HTTP Method: GET
     * Endpoint: /users/{id}
     * 
     * @param id Path variable - Aranacak kullanıcının ID'si
     * @return ResponseEntity<User> - 200 OK (bulundu) veya 404 NOT FOUND (bulunamadı)
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // Service katmanı üzerinden ID'ye göre kullanıcıyı getir
        // BaseService'deki findById() metodu kullanılır
        User user = userService.findById(id);
        
        // Optional pattern kullanarak null kontrolü yap
        Optional<User> userOptional = Optional.ofNullable(user);
        return userOptional
                .map(ResponseEntity::ok) // Kullanıcı varsa 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Yoksa 404 NOT FOUND
    }

    /**
     * Kullanıcıyı günceller (UPDATE)
     * 
     * HTTP Method: PUT
     * Endpoint: /users/{id}
     * Request Body: User JSON objesi (güncellenecek veriler)
     * 
     * @param id Path variable - Güncellenecek kullanıcının ID'si
     * @param user Request body'den gelen güncellenecek kullanıcı bilgileri
     * @return ResponseEntity<User> - 200 OK (güncellendi) veya 404 NOT FOUND (bulunamadı)
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        // Service katmanı üzerinden kullanıcıyı güncelle
        // BaseService'deki update() metodu kullanılır
        User updatedUser = userService.update(id, user);
        
        // Optional pattern kullanarak null kontrolü yap
        Optional<User> userOptional = Optional.ofNullable(updatedUser);
        return userOptional
                .map(ResponseEntity::ok) // Güncellendi ise 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Bulunamadı ise 404 NOT FOUND
    }

    /**
     * Kullanıcıyı siler (DELETE)
     * 
     * HTTP Method: DELETE
     * Endpoint: /users/{id}
     * 
     * @param id Path variable - Silinecek kullanıcının ID'si
     * @return ResponseEntity<Void> - 204 NO CONTENT (silindi) veya 404 NOT FOUND (bulunamadı)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // Service katmanı üzerinden kullanıcıyı sil
        // BaseService'deki delete() metodu kullanılır
        boolean deleted = userService.delete(id);
        
        if (deleted) {
            // 204 NO CONTENT: İşlem başarılı ama response body yok
            return ResponseEntity.noContent().build();
        }
        // 404 NOT FOUND: Kullanıcı bulunamadı
        return ResponseEntity.notFound().build();
    }

    /**
     * Email adresine göre kullanıcı bulur (READ - query)
     * 
     * HTTP Method: GET
     * Endpoint: /users/email?email=...
     * 
     * @param email Request parameter - Aranacak email adresi
     * @return ResponseEntity<Optional<User>> - 200 OK ile kullanıcı (varsa) veya boş Optional
     */
    @GetMapping("/email")
    public ResponseEntity<Optional<User>> getUserByEmail(@RequestParam String email) {
        // Service katmanı üzerinden email'e göre kullanıcıyı getir
        // UserService'e özel getUserByEmail() metodu kullanılır
        Optional<User> user = userService.getUserByEmail(email);
        // 200 OK: İstek başarılı (kullanıcı bulunmuş olabilir veya bulunmamış olabilir)
        return ResponseEntity.ok(user);
    }
}
