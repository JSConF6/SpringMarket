package com.spring.market.service;

import com.spring.market.domain.file.FileMapper;
import com.spring.market.domain.file.dto.SaveFileDto;
import com.spring.market.domain.product.Product;
import com.spring.market.domain.product.ProductMapper;
import com.spring.market.domain.product.dto.*;
import com.spring.market.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductMapper productMapper;
    private final FileMapper fileMapper;

    public List<Product> getProductList() {
        return productMapper.findAll();
    }

    public Product getProductDetail(int productId) {
        return productMapper.findById(productId);
    }

    @Transactional
    public void productAdd(ProductAddDto productAddDto) {
        try {
            productMapper.productAdd(productAddDto);
            System.out.println(productAddDto.getId());
            System.out.println(productAddDto.getImageFiles().size());

            ProductImageDto productImageDto = new ProductImageDto();
            productImageDto.setProductId(productAddDto.getId());
            for (int i = 0; i < productAddDto.getImageFiles().size(); i++) {
                MultipartFile newFile = productAddDto.getImageFiles().get(i);
                if (!newFile.isEmpty()) {
                    UUID uuid = UUID.randomUUID();

                    String originalFilename = newFile.getOriginalFilename();
                    System.out.println(originalFilename);
                    String fileName = uuid + "_" + originalFilename;

                    String userHomeDir = System.getProperty("user.home");

                    String folderPath = userHomeDir + java.io.File.separator + "upload" + java.io.File.separator;

                    productImageDto.setThumbnail(folderPath);

                    java.io.File f = new java.io.File(folderPath);

                    if (!f.exists()) {
                        if (!f.mkdir())
                            throw new CustomApiException("폴더 생성중 오류가 발생 했습니다.");
                    }

                    Path filePath = Paths.get(userHomeDir, "upload", fileName);

                    try {
                        Files.write(filePath, productAddDto.getImageFiles().get(i).getBytes());
                    } catch (Exception e) {
                        throw new CustomApiException("이미지 업로드 중 오류가 발생했습니다.");
                    }

                    SaveFileDto saveFileDto = new SaveFileDto();
                    saveFileDto.setUserId(productAddDto.getUserId());
                    saveFileDto.setOriginalName(originalFilename);
                    saveFileDto.setName(fileName);
                    saveFileDto.setPath(folderPath);
                    saveFileDto.setType("P");
                    fileMapper.save(saveFileDto);
                    productImageDto.setProductImageId(saveFileDto.getId());
                    if(i == 0){
                        productImageDto.setThumbnail("Y");
                    }else {
                        productImageDto.setThumbnail("N");
                    }

                    productMapper.productImageAdd(productImageDto);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public void productDelete(int id) {
        productMapper.productDelete(id);
    }

    @Transactional
    public void productUpdate(ProductUpdateDto productUpdateDto) {
        productMapper.productUpdate(productUpdateDto);
    }

    public List<ProductListDto> getProductList(int id) {
        return productMapper.getProductList(id);
    }

    public List<CategoryDto> getCategory() {
        List<CategoryDto> categoryDtos = productMapper.getCategory();
        return categoryDtos;
    }
}
