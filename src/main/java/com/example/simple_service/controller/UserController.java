package com.example.simple_service.controller;

// Gerekli sınıflar ve anotasyonlar import ediliyor.
import com.example.simple_service.entity.User;
import com.example.simple_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Bu sınıf bir REST controller'dır. HTTP isteklerini karşılamak için kullanılır.
@RestController
// Tüm metotlar "/users" ile başlayan endpoint’leri temsil eder.
@RequestMapping("/users")
// Bu controller’a başka bir origin'den (örn: frontend) erişim izni verilir. CORS problemi yaşanmaması için kullanılır.
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    // UserService sınıfı üzerinden iş mantığı çağrılır.
    private final UserService userService;

    // Constructor injection: UserService, bu controller'a dışarıdan verilir.
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Yeni bir kullanıcı oluşturmak için POST isteği yapılır.
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Kullanıcı verisi kaydedilir.
        User savedUser = userService.saveUser(user);
        // 201 CREATED durum kodu ile birlikte kaydedilen kullanıcı döndürülür.
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // Tüm kullanıcıları listelemek için GET isteği yapılır.
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        // Veritabanındaki tüm kullanıcılar alınır.
        List<User> users = userService.getAllUsers();
        // 200 OK durumu ile kullanıcı listesi döndürülür.
        return ResponseEntity.ok(users);
    }

    // Belirli bir kullanıcıyı ID'sine göre getirmek için GET isteği yapılır.
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // ID'ye göre kullanıcı aranır.
        Optional<User> userOptional = Optional.ofNullable(userService.getUserById(id));
        // Kullanıcı bulunduysa 200 OK ile döndürülür, yoksa 404 NOT FOUND döner.
        return userOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Belirli bir kullanıcıyı güncellemek için PUT isteği yapılır.
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        // ID’ye göre kullanıcı güncellenir.
        Optional<User> updatedUser = Optional.ofNullable(userService.updateUser(id, user));
        // Güncellenen kullanıcı döndürülür ya da kullanıcı bulunamazsa 404 NOT FOUND döner.
        return updatedUser
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Belirli bir kullanıcıyı silmek için DELETE isteği yapılır.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // Kullanıcı silinir.
        boolean deleted = userService.deleteUser(id);
        // Silme başarılıysa 204 NO CONTENT, değilse 404 NOT FOUND döner.
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Email’e göre kullanıcıyı bulmak için GET isteği yapılır.
    @GetMapping("/email")
    public ResponseEntity<Optional<User>> getUserByEmail(@RequestParam String email) {
        // Email'e göre kullanıcı aranır.
        Optional<User> user = userService.getUserByEmail(email);
        // Kullanıcı varsa 200 OK ile döndürülür (null da olabilir).
        return ResponseEntity.ok(user);
    }
}
