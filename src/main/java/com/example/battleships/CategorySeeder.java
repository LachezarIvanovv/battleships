package com.example.battleships;

import com.example.battleships.models.Category;
import com.example.battleships.models.ShipType;
import com.example.battleships.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CategorySeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategorySeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(this.categoryRepository.count() == 0){
            List<Category> categories = Arrays.stream(ShipType.values())
                    .map(Category::new)
                    .toList();
            this.categoryRepository.saveAll(categories);
        }
    }
}
