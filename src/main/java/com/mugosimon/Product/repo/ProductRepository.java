package com.mugosimon.Product.repo;

import com.mugosimon.Product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends
        JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.productName = :productName")
    Optional<Product> findByProductName(@Param("productName") String productName);

    @Query("SELECT p FROM Product p WHERE p.productPrice = :productPrice")
    Optional<Product> findByProductPrice(@Param("productPrice") double productPrice);
}
