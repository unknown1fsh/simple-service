package com.example.simple_service.controller;

import com.example.simple_service.entity.User;
import com.example.simple_service.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User View Controller
 * 
 * Bu sınıf, User entity'si için Thymeleaf template'leri ile web sayfalarını yönetir.
 * Layered Architecture'da Controller katmanında yer alır.
 * 
 * REST API (UserController) ile birlikte çalışır:
 * - UserController: JSON/XML formatında REST API sağlar (frontend uygulamaları için)
 * - UserViewController: HTML sayfaları sağlar (server-side rendering için)
 * 
 * Thymeleaf Template Engine:
 * - Server-side rendering yapar
 * - HTML template'leri render eder
 * - Model verilerini template'lere aktarır
 * 
 * @Controller: Bu sınıfın bir Spring MVC controller olduğunu belirtir
 * - @RestController'dan farklı olarak, view (HTML) döndürür
 * - Metodlar String döner (template adı) veya ModelAndView
 * 
 * Endpoint'ler:
 * - GET / - Ana sayfa
 * - GET /users/view - Kullanıcı listesi sayfası
 * - GET /users/form - Kullanıcı form sayfası (yeni/düzenle)
 * - POST /users/save - Yeni kullanıcı kaydetme
 * - POST /users/update/{id} - Kullanıcı güncelleme
 * - POST /users/delete/{id} - Kullanıcı silme
 */
@Controller
public class UserViewController {

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
    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Ana sayfa
     * 
     * HTTP Method: GET
     * Endpoint: /
     * Template: index.html
     * 
     * @param model Thymeleaf template'e veri aktarmak için kullanılır
     * @return Template adı (index.html)
     */
    @GetMapping("/")
    public String home(Model model) {
        // Tüm kullanıcıları getir (toplam sayı için)
        // BaseService'deki findAll() metodu kullanılır
        List<User> allUsers = userService.findAll();
        model.addAttribute("totalUsers", allUsers != null ? allUsers.size() : 0);
        model.addAttribute("pageTitle", "Ana Sayfa");
        return "index"; // src/main/resources/templates/index.html
    }

    /**
     * Kullanıcı listesi sayfası
     * 
     * Arama parametreleri ile filtreleme yapılabilir:
     * - id: ID'ye göre arama
     * - name: İsme göre arama (contains - içerir)
     * - email: Email'e göre arama (exact match)
     * 
     * HTTP Method: GET
     * Endpoint: /users/view
     * Template: users/list.html
     * 
     * @param id Arama parametresi - Kullanıcı ID'si
     * @param name Arama parametresi - Kullanıcı adı (kısmi eşleşme)
     * @param email Arama parametresi - Email adresi (tam eşleşme)
     * @param model Thymeleaf template'e veri aktarmak için kullanılır
     * @return Template adı (users/list.html)
     */
    @GetMapping("/users/view")
    public String viewUsers(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            Model model) {
        
        List<User> users;
        
        // Arama parametrelerine göre filtreleme
        if (id != null) {
            // ID'ye göre arama - BaseService'deki findById() metodu
            User user = userService.findById(id);
            users = user != null ? List.of(user) : List.of();
        } else if (name != null && !name.trim().isEmpty()) {
            // İsme göre arama - tüm kullanıcıları al ve filtrele (case-insensitive)
            // BaseService'deki findAll() metodu
            users = userService.findAll().stream()
                    .filter(u -> u.getName() != null && u.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        } else if (email != null && !email.trim().isEmpty()) {
            // Email'e göre arama - UserService'e özel getUserByEmail() metodu
            Optional<User> userOpt = userService.getUserByEmail(email);
            users = userOpt.map(List::of).orElse(List.of());
        } else {
            // Arama parametresi yoksa tüm kullanıcıları getir
            // BaseService'deki findAll() metodu
            users = userService.findAll();
        }
        
        // Model'e verileri ekle (Thymeleaf template'inde kullanılacak)
        model.addAttribute("users", users);
        model.addAttribute("pageTitle", "Kullanıcı Listesi");
        return "users/list"; // src/main/resources/templates/users/list.html
    }

    /**
     * Kullanıcı form sayfası (yeni kullanıcı veya düzenleme)
     * 
     * HTTP Method: GET
     * Endpoint: /users/form
     * Template: users/form.html
     * 
     * @param id Request parameter - Düzenlenecek kullanıcının ID'si (opsiyonel)
     * @param model Thymeleaf template'e veri aktarmak için kullanılır
     * @return Template adı (users/form.html) veya redirect
     */
    @GetMapping("/users/form")
    public String showUserForm(@RequestParam(required = false) Long id, Model model) {
        User user;
        if (id != null) {
            // Düzenleme modu - mevcut kullanıcıyı getir
            // BaseService'deki findById() metodu
            user = userService.findById(id);
            if (user == null) {
                // Kullanıcı bulunamadı - listeye yönlendir
                return "redirect:/users/view?error=Kullanıcı bulunamadı";
            }
        } else {
            // Yeni kullanıcı modu - boş kullanıcı oluştur
            user = new User();
        }
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", id != null ? "Kullanıcı Düzenle" : "Yeni Kullanıcı");
        return "users/form"; // src/main/resources/templates/users/form.html
    }

    /**
     * Yeni kullanıcı kaydetme
     * 
     * HTTP Method: POST
     * Endpoint: /users/save
     * 
     * @param user Form'dan gelen kullanıcı bilgileri (@ModelAttribute ile otomatik bind edilir)
     * @param redirectAttributes Redirect sonrası flash message'lar için
     * @return Redirect URL (users/view)
     */
    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            // Validation: Boş alan kontrolü (basit validasyon)
            // Detaylı validasyon Service katmanında yapılır
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "Kullanıcı adı boş olamaz!");
                return "redirect:/users/form";
            }
            
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "Email adresi boş olamaz!");
                return "redirect:/users/form";
            }
            
            // Email duplicate kontrolü (basit kontrol)
            // Detaylı kontrol Service katmanında yapılır
            Optional<User> existingUser = userService.getUserByEmail(user.getEmail().trim());
            if (existingUser.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "Bu email adresi zaten kullanılıyor: " + user.getEmail());
                return "redirect:/users/form";
            }
            
            // Service katmanı üzerinden kullanıcıyı kaydet
            // BaseService'deki save() metodu kullanılır (validation dahil)
            userService.save(user);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Kullanıcı başarıyla kaydedildi!");
            return "redirect:/users/view";
        } catch (IllegalArgumentException e) {
            // Service katmanından gelen validation hataları
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users/form";
        } catch (Exception e) {
            // Beklenmeyen hatalar
            e.printStackTrace(); // Log için
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Kullanıcı kaydedilirken hata oluştu: " + e.getMessage());
            return "redirect:/users/form";
        }
    }

    /**
     * Kullanıcı güncelleme
     * 
     * HTTP Method: POST
     * Endpoint: /users/update/{id}
     * 
     * @param id Path variable - Güncellenecek kullanıcının ID'si
     * @param user Form'dan gelen güncellenecek kullanıcı bilgileri
     * @param redirectAttributes Redirect sonrası flash message'lar için
     * @return Redirect URL (users/view veya users/form)
     */
    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, 
                            RedirectAttributes redirectAttributes) {
        try {
            // Mevcut kullanıcıyı kontrol et
            // BaseService'deki findById() metodu
            User existingUser = userService.findById(id);
            if (existingUser == null) {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "Güncellenecek kullanıcı bulunamadı!");
                return "redirect:/users/view";
            }

            // Email değişikliği kontrolü
            if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
                Optional<User> emailUser = userService.getUserByEmail(user.getEmail());
                if (emailUser.isPresent() && !emailUser.get().getId().equals(id)) {
                    redirectAttributes.addFlashAttribute("errorMessage", 
                        "Bu email adresi başka bir kullanıcı tarafından kullanılıyor!");
                    return "redirect:/users/form?id=" + id;
                }
            }

            // Service katmanı üzerinden kullanıcıyı güncelle
            // BaseService'deki update() metodu kullanılır (validation dahil)
            userService.update(id, user);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Kullanıcı başarıyla güncellendi!");
            return "redirect:/users/view";
        } catch (Exception e) {
            // Hata durumunda
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Kullanıcı güncellenirken hata oluştu: " + e.getMessage());
            return "redirect:/users/form?id=" + id;
        }
    }

    /**
     * Kullanıcı silme
     * 
     * HTTP Method: POST
     * Endpoint: /users/delete/{id}
     * 
     * @param id Path variable - Silinecek kullanıcının ID'si
     * @param redirectAttributes Redirect sonrası flash message'lar için
     * @return Redirect URL (users/view)
     */
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // Service katmanı üzerinden kullanıcıyı sil
            // BaseService'deki delete() metodu kullanılır
            boolean deleted = userService.delete(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Kullanıcı başarıyla silindi!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "Kullanıcı bulunamadı veya silinemedi!");
            }
        } catch (Exception e) {
            // Hata durumunda
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Kullanıcı silinirken hata oluştu: " + e.getMessage());
        }
        return "redirect:/users/view";
    }
}

