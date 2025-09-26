package com.dailycodework.dreamshops.service.Image;

import com.dailycodework.dreamshops.Exception.ResourcetNotFoundException;
import com.dailycodework.dreamshops.Repository.ImageRepository;
import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.entity.Image;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.service.Product.IproductService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service@RequiredArgsConstructor
public class ImageService implements IimageService{
    private final ImageRepository imageRepository;
    private final IproductService productService;
    @Override
    public void updateImage(MultipartFile file, Long id) {
        Image image=getImageById(id);
        try{
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }catch (IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->{
            throw new ResourcetNotFoundException("not id found");
        });


    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product=productService.getByProductById(productId);
        List<ImageDto> saveImageDto=new ArrayList<>();
        for(MultipartFile file:files){
            try{
                Image images = new Image();
                images.setFileName(file.getOriginalFilename());
                images.setFileType(file.getContentType());
                images.setImage(new SerialBlob(file.getBytes()));
                images.setProduct(product);
                Image savedImage = imageRepository.save(images);

                // Tạo downloadUrl bằng id đã sinh
                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + savedImage.getId();

                savedImage.setDowmloadUrl(downloadUrl);
                imageRepository.save(savedImage);

                ImageDto imageDto =new ImageDto();
                imageDto.setFileName(file.getOriginalFilename());
                imageDto.setId(savedImage.getId());
                imageDto.setDownloadUrl(savedImage.getDowmloadUrl());
                saveImageDto.add(imageDto);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
    }return saveImageDto;
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()->new ResourcetNotFoundException("not id found"));
    }
}
