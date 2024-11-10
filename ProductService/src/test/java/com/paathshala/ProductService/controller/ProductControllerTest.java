package com.paathshala.ProductService.controller;

import com.paathshala.ProductService.model.ProductRequest;
import com.paathshala.ProductService.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(ProductController.class)  // Automatically loads the controller and related beans
public class ProductControllerTest {

    /*@Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;*/
    @Autowired
    private MockMvc mockMvc;  // Automatically injects MockMvc for testing the controller

    @MockBean
    private ProductService productService;  // Mock the ProductService and inject it into the controller


    /*public ProductControllerTest() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }*/

    @Test
    void testAddProduct() throws Exception {
        // Prepare test data
        ProductRequest productRequest = new ProductRequest("Test Product", 100, 50);
        long expectedProductId = 1L;

        // Mock the service method
        Mockito.when(productService.addProduct(Mockito.any(ProductRequest.class))).thenReturn(expectedProductId);

        // Perform the POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                .contentType("application/json")
                .content("{\"productName\":\"Test Product\", \"price\":100, \"quantity\":50}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(expectedProductId)));


        // Verify that the service method was called once
        Mockito.verify(productService, Mockito.times(1))
                .addProduct(Mockito.any(ProductRequest.class));
    }

}
