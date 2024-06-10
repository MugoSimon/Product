package com.mugosimon.Product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugosimon.Product.dto.Coupon;
import com.mugosimon.Product.dto.CouponResponse;
import com.mugosimon.Product.model.Product;
import com.mugosimon.Product.repo.ProductRepository;
import com.mugosimon.Product.utility.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    private final String TAG = "ProductService: ";
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${couponService.url}")
    private String couponServiceUrl;

    public EntityResponse<Product> createProduct(Product product) {
        EntityResponse<Product> entityResponse = new EntityResponse<>();
        try {
            log.info("====> create product <====");

            Coupon coupon = null;
            try {
                // Attempt to retrieve the coupon using the coupon code from the product
                ResponseEntity<String> response = restTemplate.getForEntity(couponServiceUrl + product.getCouponCode(), String.class);
                log.info("Raw response: " + response.getBody());

                ObjectMapper mapper = new ObjectMapper();
                CouponResponse couponResponse = mapper.readValue(response.getBody(), CouponResponse.class);

                coupon = couponResponse.getEntity();

                if (coupon != null) {
                    log.info("Coupon retrieved successfully: " + coupon.toString());
                } else {
                    log.warn("Coupon retrieval returned null");
                }
            } catch (RestClientException | IOException e) {
                // Log a warning if the coupon service is unavailable or the coupon is not found
                log.warn("Coupon not found or service unavailable: " + e.getMessage());
            }

            // Check if a product with the same name already exists
            productRepository.findByProductName(product.getProductName()).ifPresent(existingProduct -> {
                log.error("Product already exists: " + product.getProductName());
                throw new IllegalArgumentException("Product already exists");
            });

            // Apply the coupon discount if a valid coupon was retrieved
            if (coupon != null && coupon.getDiscount() != null) {
                product.setProductPrice(product.getProductPrice().subtract(coupon.getDiscount()));
                log.info("Coupon applied: Discount of " + coupon.getDiscount() + " applied to the product price");
            } else {
                log.info("No valid coupon applied");
            }

            // Save the product to the repository
            Product savedProduct = productRepository.save(product);
            entityResponse.setEntity(savedProduct);
            log.info("====> product created: " + savedProduct.getProductName());
            entityResponse.setMessage(HttpStatus.CREATED.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.CREATED.value());
            return entityResponse;

        } catch (IllegalArgumentException e) {
            // Handle case where product already exists
            log.error("Error creating product: " + e.getMessage());
            entityResponse.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        } catch (DataAccessException e) {
            // Handle database-related exceptions
            log.error("Database error while creating product: " + e.getMessage());
            entityResponse.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            log.error("Unexpected error while creating product: " + e.getMessage());
            entityResponse.setMessage(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.EXPECTATION_FAILED.value());
        }

        return entityResponse;
    }

    public EntityResponse fetchProductById(Long id) {
        EntityResponse entityResponse = new EntityResponse<>();

        try {
            log.info(TAG + "fetch Product By Id");
            Optional<Product> productOptional = productRepository.findById(id);

            if (productOptional.isPresent()) {
                log.info("====> product fetched successfully: " + productOptional.get().getProductName());
                entityResponse.setEntity(productOptional.get());
                entityResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
                entityResponse.setStatusCode(HttpStatus.FOUND.value());
            } else {
                entityResponse.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                entityResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            }

            return entityResponse;
        } catch (Exception ex) {
            log.error(">+++++> error while fetching product <++++++<: {}", ex.getLocalizedMessage());
            ex.printStackTrace();

            entityResponse.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return entityResponse;
        }
    }

    public EntityResponse fetchAllProducts() {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info(TAG + "fetch all products");

            List<Product> productList = productRepository.findAll();
            if (productList.isEmpty()) {
                log.info("====> products fetched successfully: " + productList.size());
                entityResponse.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
                entityResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
                return entityResponse;
            }
            entityResponse.setEntity(productList);
            entityResponse.setMessage(HttpStatus.FOUND.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.FOUND.value());
            return entityResponse;
        } catch (Exception ex) {
            log.error(">+++++> error while fetching products <++++++<: {}", ex.getLocalizedMessage());
            ex.printStackTrace();
            entityResponse.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return entityResponse;
        }
    }

    public EntityResponse updateProduct(Product updatedProduct) {
        EntityResponse entityResponse = new EntityResponse<>();

        try {
            log.info(TAG + "update product");
            Optional<Product> productOptional = productRepository.findById(updatedProduct.getId());

            if (productOptional.isPresent()) {
                Product existingProduct = productOptional.get();

                existingProduct.setProductName(updatedProduct.getProductName());
                existingProduct.setProductDescription(updatedProduct.getProductDescription());
                existingProduct.setProductPrice(updatedProduct.getProductPrice());

                Product savedProduct = productRepository.save(existingProduct);
                entityResponse.setEntity(savedProduct);
                log.info("====> product updated successfully: " + savedProduct.getProductName());
                entityResponse.setMessage(HttpStatus.OK.getReasonPhrase());
                entityResponse.setStatusCode(HttpStatus.OK.value());

            } else {
                log.info("====> product failed to updated:");
                entityResponse.setMessage(HttpStatus.NOT_MODIFIED.getReasonPhrase());
                entityResponse.setStatusCode(HttpStatus.NOT_MODIFIED.value());
            }

            return entityResponse;
        } catch (Exception ex) {
            log.error(">+++++> error while updating product <++++++<: {}", ex.getLocalizedMessage());
            ex.printStackTrace();

            entityResponse.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return entityResponse;
        }
    }

    public EntityResponse deleteProduct(Long id) {
        EntityResponse entityResponse = new EntityResponse<>();

        try {
            log.info(TAG + "delete Product");
            Optional<Product> productOptional = productRepository.findById(id);

            if (productOptional.isPresent()) {
                productRepository.delete(productOptional.get());
                log.info("====> product deleted successfully");
                entityResponse.setMessage(HttpStatus.OK.getReasonPhrase());
                entityResponse.setStatusCode(HttpStatus.OK.value());
            } else {
                log.info("====> product failed to delete:");
                entityResponse.setMessage(HttpStatus.NOT_IMPLEMENTED.getReasonPhrase());
                entityResponse.setStatusCode(HttpStatus.NOT_IMPLEMENTED.value());
            }

            return entityResponse;
        } catch (Exception ex) {
            log.error(">+++++> error while deleting product <++++++<: {}", ex.getLocalizedMessage());
            ex.printStackTrace();

            entityResponse.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return entityResponse;
        }
    }
}
