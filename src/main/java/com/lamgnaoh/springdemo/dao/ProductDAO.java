package com.lamgnaoh.springdemo.dao;

import com.github.javafaker.Faker;
import com.lamgnaoh.springdemo.entity.Product;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDAO {
  private List<Product> products;
  private final Faker faker;

  @PostConstruct
  public void init(){
    products = new ArrayList<>();
    for (int i = 0 ; i < 50 ; i ++){
      Integer id = i + 1;
      String title = faker.commerce().productName();
      String description = faker.lorem().paragraph();
      Double price = Double.valueOf(faker.commerce().price(0, 1000));
      Double discountPercentage = Double.valueOf(faker.commerce().price(0, 100));
      Double rating = Double.valueOf(faker.commerce().price(0, 5));
      Integer stock = faker.number().numberBetween(1, 100);
      String brand = faker.company().name();
      String category = faker.commerce().department();
      String thumbnail = faker.avatar().image();
      List<String> images = Arrays.asList(faker.avatar().image(),faker.avatar().image(),faker.avatar().image());
      Product product = Product.builder()
                      .id(id)
                      .title(title)
                      .description(description)
                      .price(price)
                      .discountPercentage(discountPercentage)
                      .rating(rating)
                      .stock(stock)
                      .brand(brand)
                      .category(category)
                      .thumbnail(thumbnail)
                      .images(images)
                      .build();
      products.add(product);
    }
    log.info("Product mock successful !!!");
  }

  public List<Product> getAll(){
    return products;
  }


}
