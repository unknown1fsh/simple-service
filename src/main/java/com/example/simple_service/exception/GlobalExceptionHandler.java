package com.example.simple_service.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//TODO: GlobalExceptionHandler - Global Exception Handler Sınıfı
// Bu sınıf, tüm controller'larda oluşan exception'ları yakalar ve merkezi bir şekilde yönetir.
// @ControllerAdvice anotasyonu ile bu sınıfın tüm controller'lar için global exception handler olduğu belirtilir.
// Exception Handling (Hata Yönetimi): Merkezi exception yönetimi sağlar, kod tekrarını önler (DRY principle).
// @ExceptionHandler anotasyonu ile spesifik exception tipleri için handler metodları tanımlanır.
// Try-Catch-Finally: Exception handling Java'da try-catch blokları ile yapılır, burada merkezi yönetim sağlanır.
// Error Handling Stratejisi: Spesifik exception'lar için özel handler'lar, genel exception'lar için fallback handler.
// RedirectAttributes: Flash attribute'lar ile kullanıcıya hata mesajları gösterilir.
// Best Practice: Production'da loglama ve hata tracking sistemi entegrasyonu yapılmalıdır.

/**
 * Global Exception Handler
 * 
 * Bu sınıf, tüm controller'larda oluşan exception'ları yakalar ve merkezi bir şekilde yönetir.
 * Layered Architecture'da Exception Handling katmanında yer alır.
 * 
 * @ControllerAdvice: Bu sınıfın tüm controller'lar için global exception handler olduğunu belirtir
 * - Tüm @Controller ve @RestController sınıflarındaki exception'ları yakalar
 * - Merkezi exception yönetimi sağlar (DRY principle)
 * - Kod tekrarını önler
 * 
 * Exception Handling Stratejisi:
 * - Spesifik exception'lar için özel handler'lar tanımlanır
 * - Genel exception'lar için fallback handler tanımlanır
 * - Kullanıcıya anlaşılır hata mesajları gösterilir
 * - Loglama yapılabilir (production'da önerilir)
 * 
 * RedirectAttributes:
 * - Flash attribute'lar kullanılır (bir request sonrası kaybolur)
 * - Kullanıcıya hata mesajları gösterilir
 * - Thymeleaf template'lerinde kullanılabilir
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * IllegalArgumentException için exception handler
     * 
     * Bu exception genellikle şu durumlarda fırlatılır:
     * - Validation hataları (Service katmanında)
     * - Geçersiz parametreler
     * - İş kuralı ihlalleri
     * 
     * @param ex Yakalanan IllegalArgumentException
     * @param redirectAttributes Redirect sonrası flash message'lar için
     * @return Redirect URL (kullanıcı listesi sayfasına yönlendir)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, 
                                                  RedirectAttributes redirectAttributes) {
        // Hata mesajını flash attribute olarak ekle
        // Thymeleaf template'inde ${errorMessage} ile erişilebilir
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        // Kullanıcı listesi sayfasına yönlendir
        return "redirect:/users/view";
    }

    /**
     * Genel Exception handler (fallback)
     * 
     * Beklenmeyen veya handle edilmemiş exception'lar için kullanılır.
     * Production'da bu handler'a loglama eklenmelidir.
     * 
     * @param ex Yakalanan Exception
     * @param redirectAttributes Redirect sonrası flash message'lar için
     * @return Redirect URL (kullanıcı listesi sayfasına yönlendir)
     */
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, 
                                        RedirectAttributes redirectAttributes) {
        // Genel hata mesajı (kullanıcıya detaylı teknik bilgi gösterilmez - güvenlik)
        redirectAttributes.addFlashAttribute("errorMessage", 
            "Bir hata oluştu: " + ex.getMessage());
        // Kullanıcı listesi sayfasına yönlendir
        return "redirect:/users/view";
        
        // Not: Production'da şu eklemeler yapılmalıdır:
        // - Logger ile hata loglama
        // - Hata detaylarını kullanıcıya göstermeme (güvenlik)
        // - Hata tracking sistemi entegrasyonu (örn: Sentry)
    }
}

