package com.product_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Service {

    public String uploadFile(MultipartFile file,int brandId) throws IOException;
}
