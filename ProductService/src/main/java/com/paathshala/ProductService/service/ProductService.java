package com.paathshala.ProductService.service;

import com.paathshala.ProductService.model.ProductRequest;
import com.paathshala.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);
    ProductResponse getProductById(long productId);
}


