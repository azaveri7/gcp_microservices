package com.paathshala.ProductService.controller;

import com.paathshala.ProductService.model.ProductRequest;
import com.paathshala.ProductService.model.ProductResponse;
import com.paathshala.ProductService.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(expectedProductId)));


        // Verify that the service method was called once
        Mockito.verify(productService, Mockito.times(1))
                .addProduct(Mockito.any(ProductRequest.class));
    }

    @Test
    void testGetProductById() throws Exception {
        // Prepare test data
        long productId = 1L;
        ProductResponse productResponse = new ProductResponse(
                "Test Product", productId, 100, 50);

        // Mock the service method
        Mockito.when(productService.getProductById(productId)).thenReturn(productResponse);

        // Perform GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("Test Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(100));


        // Verify the service method was called once
        Mockito.verify(productService, Mockito.times(1)).getProductById(productId);
    }

}
