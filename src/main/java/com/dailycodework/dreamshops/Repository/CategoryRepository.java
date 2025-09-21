package com.dailycodework.dreamshops.Repository;

import com.dailycodework.dreamshops.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryByName(String name);
    boolean existsByName(String name);
}
