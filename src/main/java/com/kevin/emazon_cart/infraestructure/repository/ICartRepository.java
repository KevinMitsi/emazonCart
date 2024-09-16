package com.kevin.emazon_cart.infraestructure.repository;

import com.kevin.emazon_cart.infraestructure.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ICartRepository extends JpaRepository<CartEntity, Long> {
    @Query("SELECT c.itemId FROM CartEntity c WHERE  c.userId = :userId")
    List<Long> itemsInUserCart(Long userId);

    Optional<CartEntity> findByItemId(Long itemId);

    @Query("SELECT c.quantity FROM  CartEntity c WHERE c.itemId = :itemId")
    Long findItemQuantityByItemId(Long itemId);

    @Query("SELECT c.creationDate FROM CartEntity c WHERE  c.itemId = :itemId")
    Optional<Date> findDateByItemId(Long itemId);

    void deleteByItemIdAndUserId(Long itemId, Long userId);

    List<CartEntity>findAllByUserId(Long userId);
}
