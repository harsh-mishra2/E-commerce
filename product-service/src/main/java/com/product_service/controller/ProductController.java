package com.product_service.controller;

import com.product_service.dto.ApiResponse;
import com.product_service.dto.CategoryDto;
import com.product_service.dto.ProductDto;
import com.product_service.service.CategoryService;
import com.product_service.service.ProductService;
import com.product_service.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private CategoryService categoryService;
    private ProductService productService;
    private S3Service s3Service;

    public ProductController(CategoryService categoryService, ProductService productService, S3Client s3Client, S3Service s3Service) {
        this.categoryService = categoryService;
        this.productService = productService;

        this.s3Service = s3Service;
    }


    @GetMapping("/list/categories")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getCategories() {

        List<CategoryDto> categoriesDto = categoryService.findAll();
        ApiResponse<List<CategoryDto>> response = new ApiResponse<>();
        if(categoriesDto!=null){
            response.setMessage("All categories data fetched");
            response.setStatus(200);
            response.setData(categoriesDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.setMessage("No categories data found");
        response.setStatus(500);
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/list/search")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getCategories(@RequestParam String keyword) {

        List<ProductDto> productDtos= productService.searchProducts(keyword);
        ApiResponse<List<ProductDto>> response = new ApiResponse<>();
        if(productDtos!=null){


            response.setMessage("All  data fetched");
            response.setStatus(200);
            response.setData(productDtos);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.setMessage("No categories data found");
        response.setStatus(500);
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }



    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile[] files,
            @RequestParam("brandId") int brandId) {

        List<String> imageUrls = new ArrayList<>();

        try {

            for (MultipartFile file : files) {

                String url = s3Service.uploadFile(file, brandId);

                imageUrls.add(url);
            }

            return ResponseEntity.ok(imageUrls.toString());

        } catch (Exception e) {

            return ResponseEntity.internalServerError()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addProduct(){

        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage("Product added");
        response.setStatus(201);
        response.setData("Product added successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }



    }



