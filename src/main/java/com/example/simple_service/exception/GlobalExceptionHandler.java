package com.example.simple_service.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Global Exception Handler
 * Tüm controller'larda oluşan exception'ları yakalar ve kullanıcıya gösterir
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * IllegalArgumentException için handler
     * Genellikle validation hatalarında kullanılır
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, 
                                                  RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/users/view";
    }

    /**
     * Genel Exception handler
     * Beklenmeyen hatalar için
     */
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, 
                                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", 
            "Bir hata oluştu: " + ex.getMessage());
        return "redirect:/users/view";
    }
}

