package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.Exception.ResourcetNotFoundException;
import com.dailycodework.dreamshops.entity.Image;
import com.dailycodework.dreamshops.entity.Product;
import com.dailycodework.dreamshops.reponse.ApiReponse;
import com.dailycodework.dreamshops.service.Image.IimageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images/")
public class ImageController {
    private  final IimageService imageService;

    @DeleteMapping("delete/{id}/image")
    public ResponseEntity<ApiReponse> deleteImage(@PathVariable Long id){
        try{
            imageService.deleteImage(id);
            return ResponseEntity.ok(new ApiReponse("delete ok",null));
        }catch (ResourcetNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiReponse("not id found",null));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiReponse("delete failed",e.getMessage()));
        }
    }
    @PutMapping("update/{id}/image")
    public ResponseEntity<ApiReponse> updateImage(@PathVariable Long id, @RequestBody MultipartFile file){
        try{
            imageService.updateImage(file,id);
            return ResponseEntity.ok(new ApiReponse("update ok",null));
        }catch (ResourcetNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiReponse("not id found",null));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiReponse("update failed",e.getMessage()));
        }
    }
    @PostMapping("upload/{id}")
    public ResponseEntity<ApiReponse> uploadImage(@PathVariable Long id, @RequestParam List<MultipartFile>files){
        try{
            List<ImageDto> imageDtos=imageService.saveImage(files,id);

            return ResponseEntity.ok(new ApiReponse("delete ok",imageDtos));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiReponse("delete failed",e.getMessage()));
        }
    }
    @GetMapping("image/download/{id}")
    public ResponseEntity<Resource>downloadImage(@PathVariable Long id) throws SQLException {
        Image image=imageService.getImageById(id);
        // Lấy dữ liệu nhị phân từ DB
        ByteArrayResource resource = new ByteArrayResource(
                image.getImage().getBytes(1, (int) image.getImage().length())
        );

        // Trả file về client
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }


}
