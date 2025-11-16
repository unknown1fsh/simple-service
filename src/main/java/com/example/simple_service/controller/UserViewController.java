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
 * View Controller - Thymeleaf template'leri için endpoint'ler
 * REST API (UserController) ile birlikte çalışır
 */
@Controller
public class UserViewController {

    private final UserService userService;

    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Ana sayfa
     */
    @GetMapping("/")
    public String home(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("totalUsers", allUsers != null ? allUsers.size() : 0);
        model.addAttribute("pageTitle", "Ana Sayfa");
        return "index";
    }

    /**
     * Kullanıcı listesi sayfası
     * Arama parametreleri ile filtreleme yapılabilir
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
            User user = userService.getUserById(id);
            users = user != null ? List.of(user) : List.of();
        } else if (name != null && !name.trim().isEmpty()) {
            // İsme göre arama - tüm kullanıcıları al ve filtrele
            users = userService.getAllUsers().stream()
                    .filter(u -> u.getName() != null && u.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        } else if (email != null && !email.trim().isEmpty()) {
            // Email'e göre arama
            Optional<User> userOpt = userService.getUserByEmail(email);
            users = userOpt.map(List::of).orElse(List.of());
        } else {
            // Tüm kullanıcıları getir
            users = userService.getAllUsers();
        }
        
        model.addAttribute("users", users);
        model.addAttribute("pageTitle", "Kullanıcı Listesi");
        return "users/list";
    }

    /**
     * Yeni kullanıcı form sayfası
     */
    @GetMapping("/users/form")
    public String showUserForm(@RequestParam(required = false) Long id, Model model) {
        User user;
        if (id != null) {
            user = userService.getUserById(id);
            if (user == null) {
                return "redirect:/users/view?error=Kullanıcı bulunamadı";
            }
        } else {
            user = new User();
        }
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", id != null ? "Kullanıcı Düzenle" : "Yeni Kullanıcı");
        return "users/form";
    }

    /**
     * Kullanıcı kaydetme (yeni kullanıcı)
     */
    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            // Debug: Gelen veriyi logla
            System.out.println("Received user: " + user);
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            
            // Validation: Boş alan kontrolü
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
            
            // Email kontrolü
            Optional<User> existingUser = userService.getUserByEmail(user.getEmail().trim());
            if (existingUser.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "Bu email adresi zaten kullanılıyor: " + user.getEmail());
                return "redirect:/users/form";
            }
            
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Kullanıcı başarıyla kaydedildi!");
            return "redirect:/users/view";
        } catch (IllegalArgumentException e) {
            // Validation hataları
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users/form";
        } catch (Exception e) {
            // Diğer hatalar
            e.printStackTrace(); // Console'a detaylı hata yazdır
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Kullanıcı kaydedilirken hata oluştu: " + e.getMessage());
            return "redirect:/users/form";
        }
    }

    /**
     * Kullanıcı güncelleme
     */
    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, 
                            RedirectAttributes redirectAttributes) {
        try {
            User existingUser = userService.getUserById(id);
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

            userService.updateUser(id, user);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Kullanıcı başarıyla güncellendi!");
            return "redirect:/users/view";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Kullanıcı güncellenirken hata oluştu: " + e.getMessage());
            return "redirect:/users/form?id=" + id;
        }
    }

    /**
     * Kullanıcı silme
     */
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = userService.deleteUser(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Kullanıcı başarıyla silindi!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "Kullanıcı bulunamadı veya silinemedi!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Kullanıcı silinirken hata oluştu: " + e.getMessage());
        }
        return "redirect:/users/view";
    }
}

