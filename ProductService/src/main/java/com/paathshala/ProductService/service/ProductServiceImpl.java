package com.paathshala.ProductService.service;

import com.paathshala.ProductService.entity.Product;
import com.paathshala.ProductService.exception.ProductServiceCustomException;
import com.paathshala.ProductService.model.ProductRequest;
import com.paathshala.ProductService.model.ProductResponse;
import com.paathshala.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product...");
        Product product = Product.builder()
                .productName(productRequest.getName())
                .quantity((productRequest.getQuantity()))
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product saved...");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Getting product for id" + productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given id not found", "PRODUCT_NOT_FOUND"));
        /*ProductResponse productResponse = ProductResponse.builder()
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();*/
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        log.info("Got product for id" + productId);
        return productResponse;
    }

}
