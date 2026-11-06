package com.product_service.service;

import com.product_service.entity.Brand;
import com.product_service.entity.Image;
import com.product_service.repository.BrandRepository;
import com.product_service.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final BrandRepository brandRepository;
    private final ImageRepository imageRepository;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public S3ServiceImpl(S3Client s3Client,
                         BrandRepository brandRepository,
                         ImageRepository imageRepository) {
        this.s3Client = s3Client;
        this.brandRepository = brandRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public String uploadFile(MultipartFile file, int brandId) throws IOException {

        // Check Brand
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found with id : " + brandId));

        // Generate Unique File Name
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Upload to S3
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(
                putObjectRequest,
                RequestBody.fromBytes(file.getBytes())
        );

        // Generate URL
        String imageUrl = String.format(
                "https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                region,
                fileName
        );

        // Save URL in Database
        Image image = new Image();
        image.setBrand(brand);
        image.setUrl(imageUrl);

        imageRepository.save(image);

        return imageUrl;
    }
}