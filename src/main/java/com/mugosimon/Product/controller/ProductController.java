package com.mugosimon.Product.controller;

import com.mugosimon.Product.model.Product;
import com.mugosimon.Product.service.ProductService;
import com.mugosimon.Product.utility.EntityResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/product")
public class ProductController {

    private final String TAG = "ProductController";

    @Autowired
    ProductService productService;

    @PostMapping("/create")
    public EntityResponse createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/fetch")
    public EntityResponse fetchProductById(@RequestParam Long id) {
        return productService.fetchProductById(id);
    }

    @GetMapping("/fetchAll")
    public EntityResponse fetchAllProducts() {
        return productService.fetchAllProducts();
    }

    @PutMapping("/modify")
    public EntityResponse updateProduct(@RequestBody Product updatedProduct) {
        return productService.updateProduct(updatedProduct);
    }

    @DeleteMapping("/delete")
    public EntityResponse deleteProduct(@RequestParam Long id) {
        return productService.deleteProduct(id);
    }

}
