package com.example.simple_service.service.base;

import com.example.simple_service.entity.base.BaseEntity;
import com.example.simple_service.repository.base.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Base Service Implementation
 * 
 * Bu sınıf, BaseService interface'inin generic implementasyonudur.
 * Tüm service implementation sınıfları için ortak CRUD işlemlerini sağlar.
 * 
 * Layered Architecture'da Service katmanının temel implementasyonudur.
 * 
 * Generic yapı:
 * - T: Entity tipi
 * - ID: Entity'nin ID tipi
 * - R: Repository tipi (BaseRepository'den extend eden repository)
 * 
 * Bu sınıf abstract olarak tanımlanmıştır çünkü:
 * - Doğrudan kullanılmaz, sadece alt sınıflar için base görevi görür
 * - Alt sınıflar kendi özel iş mantıklarını ekleyebilir
 * 
 * @Transactional: Tüm metodlar transaction içinde çalışır.
 * Hata durumunda rollback yapılır (veri tutarlılığı için).
 * 
 * Kullanım:
 * Tüm service implementation sınıfları bu sınıftan extend edilmelidir:
 * 
 * @Service
 * public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> 
 *         implements UserService {
 *     public UserServiceImpl(UserRepository repository) {
 *         super(repository);
 *     }
 * }
 */
public abstract class BaseServiceImpl<T extends BaseEntity<ID>, 
                                      ID extends Serializable, 
                                      R extends BaseRepository<T, ID>> 
        implements BaseService<T, ID> {

    /**
     * Repository dependency'si
     * Constructor injection ile enjekte edilir (best practice)
     */
    protected final R repository;

    /**
     * Constructor - Repository'yi enjekte eder
     * 
     * @param repository Entity için repository
     */
    protected BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    /**
     * Yeni bir entity kaydeder
     * 
     * @param entity Kaydedilecek entity
     * @return Kaydedilen entity
     * @throws IllegalArgumentException Validasyon hatalarında
     */
    @Override
    @Transactional // Transaction içinde çalışır, hata durumunda rollback
    public T save(T entity) {
        // Alt sınıflar validate metodunu override ederek özel validasyon ekleyebilir
        validateBeforeSave(entity);
        return repository.save(entity);
    }

    /**
     * Tüm entity'leri getirir
     * 
     * @return Tüm entity'lerin listesi
     */
    @Override
    @Transactional(readOnly = true) // Sadece okuma işlemi, performans için readOnly
    public List<T> findAll() {
        return repository.findAll();
    }

    /**
     * ID'ye göre entity getirir
     * 
     * @param id Aranacak entity'nin ID'si
     * @return Bulunan entity, bulunamazsa null
     */
    @Override
    @Transactional(readOnly = true)
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * ID'ye göre entity getirir (Optional döner)
     * 
     * @param id Aranacak entity'nin ID'si
     * @return Optional<T>
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<T> findByIdOptional(ID id) {
        return repository.findById(id);
    }

    /**
     * Mevcut bir entity'yi günceller
     * 
     * @param id Güncellenecek entity'nin ID'si
     * @param entity Güncellenecek yeni veriler
     * @return Güncellenmiş entity, bulunamazsa null
     */
    @Override
    @Transactional
    public T update(ID id, T entity) {
        // Önce mevcut entity'yi bul
        T existingEntity = findById(id);
        if (existingEntity == null) {
            return null; // Entity bulunamadı
        }
        
        // Alt sınıflar validate metodunu override ederek özel validasyon ekleyebilir
        validateBeforeUpdate(id, entity);
        
        // ID'yi set et (güvenlik için)
        entity.setId(id);
        
        // Güncelle
        return repository.save(entity);
    }

    /**
     * Entity'yi siler
     * 
     * @param id Silinecek entity'nin ID'si
     * @return Silme başarılıysa true, entity bulunamazsa false
     */
    @Override
    @Transactional
    public boolean delete(ID id) {
        if (existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Entity'nin var olup olmadığını kontrol eder
     * 
     * @param id Kontrol edilecek entity'nin ID'si
     * @return Entity varsa true, yoksa false
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    /**
     * Toplam entity sayısını döner
     * 
     * @return Toplam kayıt sayısı
     */
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    /**
     * Kaydetme öncesi validasyon metodu
     * Alt sınıflar bu metodu override ederek özel validasyon ekleyebilir
     * 
     * @param entity Validasyon yapılacak entity
     * @throws IllegalArgumentException Validasyon hatası durumunda
     */
    protected void validateBeforeSave(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity boş olamaz!");
        }
    }

    /**
     * Güncelleme öncesi validasyon metodu
     * Alt sınıflar bu metodu override ederek özel validasyon ekleyebilir
     * 
     * @param id Güncellenecek entity'nin ID'si
     * @param entity Validasyon yapılacak entity
     * @throws IllegalArgumentException Validasyon hatası durumunda
     */
    protected void validateBeforeUpdate(ID id, T entity) {
        if (id == null) {
            throw new IllegalArgumentException("ID boş olamaz!");
        }
        if (entity == null) {
            throw new IllegalArgumentException("Entity boş olamaz!");
        }
    }
}

