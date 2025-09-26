package com.dailycodework.dreamshops.service.Image;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.entity.Image;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IimageService {
    void updateImage(MultipartFile file,Long id);
    void deleteImage(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files,Long productId);
    Image getImageById(Long id);
}
