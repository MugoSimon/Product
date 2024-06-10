package com.mugosimon.Product.service;

import com.mugosimon.Product.model.Product;
import com.mugosimon.Product.repo.ProductRepository;
import com.mugosimon.Product.utility.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    private final String TAG = "ProductService: ";
    @Autowired
    private ProductRepository productRepository;

    public EntityResponse createProduct(Product product) {
        EntityResponse entityResponse = new EntityResponse<>();
        try {
            log.info("====> create product <====");
            Optional<Product> existingProduct = productRepository.findByProductName(product.getProductName());

            if (existingProduct.isPresent()) {
                log.error(TAG + "Product already exists");
                entityResponse.setMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
                entityResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
                return entityResponse;
            }

            Product savedProduct = productRepository.save(product);
            entityResponse.setEntity(savedProduct);
            log.info("====> product created " + savedProduct.getProductName());
            entityResponse.setMessage(HttpStatus.CREATED.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.CREATED.value());
            return entityResponse;

        } catch (Exception exception) {
            exception.printStackTrace();
            log.error(">+++++> error while creating product <++++++<: " + exception.getLocalizedMessage());
            entityResponse.setMessage(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
            entityResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return entityResponse;
        }
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
