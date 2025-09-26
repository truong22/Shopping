package com.dailycodework.dreamshops.Repository;

import com.dailycodework.dreamshops.entity.Image;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image>findByProductId(Long id);
}
