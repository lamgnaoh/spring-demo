package com.lamgnaoh.springdemo.controller;

import com.lamgnaoh.springdemo.dto.ProductPageDto;
import com.lamgnaoh.springdemo.entity.Product;
import com.lamgnaoh.springdemo.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<ProductPageDto> getAllProducts(
      @RequestParam(required = false,defaultValue = "30") Integer limit,
      @RequestParam(required = false,defaultValue = "0") Integer skip,
      @RequestParam(required = false,name = "select") List<String> fields
  ){
    List<Product> products = productService.getAll(limit,skip, fields);
    int total = productService.countAll();
    return ResponseEntity.ok(new ProductPageDto(products, total ,skip ,limit));
  }

}
