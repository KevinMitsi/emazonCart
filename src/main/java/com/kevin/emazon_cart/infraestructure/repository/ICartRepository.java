package com.kevin.emazon_cart.infraestructure.repository;

import com.kevin.emazon_cart.infraestructure.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<CartEntity, Long> {
}
