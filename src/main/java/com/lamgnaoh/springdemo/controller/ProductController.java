package com.lamgnaoh.springdemo.controller;

import com.lamgnaoh.springdemo.dto.ProductPageDto;
import com.lamgnaoh.springdemo.dto.ProductRequestDto;
import com.lamgnaoh.springdemo.entity.Product;
import com.lamgnaoh.springdemo.service.ProductService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

  @GetMapping("/{id}")
  public ResponseEntity<?> getProduct(@PathVariable Integer id ){
    try{
      Product product = productService.getById(id);
      return ResponseEntity.ok(product);
    } catch (IndexOutOfBoundsException ex){
      Map<String,String> responseBody = new HashMap<>();
      responseBody.put("message" , "Product with id: " + id  + " not found");
      return new ResponseEntity<>(responseBody , HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/search")
  public ResponseEntity<ProductPageDto> searchProduct(@RequestParam String query,
      @RequestParam(required = false,defaultValue = "30") Integer limit,
      @RequestParam(required = false,defaultValue = "0") Integer skip
      ){
    List<Product> products = productService.getByQuery(query,limit,skip);
    int total = products.size();
    return ResponseEntity.ok(new ProductPageDto(products, total ,skip ,limit));
  }

  @GetMapping("/categories")
  public ResponseEntity<List<String>> getAllProductCategories(){
    List<String> categories = productService.getAllCategories();
    return ResponseEntity.ok(categories);
  }

  @GetMapping("/category/{type}")
  public ResponseEntity<ProductPageDto> getAllByCategoryType(@PathVariable String type,
      @RequestParam(required = false, defaultValue = "30") Integer limit,
      @RequestParam(required = false, defaultValue = "0") Integer skip) {
    List<Product> products = productService.getAllByCategoryType(type,limit,skip);
    int total = products.size();
    return ResponseEntity.ok(new ProductPageDto(products, total ,skip ,limit));
  }

  @PostMapping("/add")
  public ResponseEntity<Product> addProduct(@RequestBody ProductRequestDto productRequestDto){
    Product product = productService.add(productRequestDto);
    return new ResponseEntity<>(product,HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateProduct(@PathVariable Integer id,
      @RequestBody ProductRequestDto productRequestDto) {
    try{
      Product product = productService.update(id,productRequestDto);
      return new ResponseEntity<>(product,HttpStatus.OK);
    } catch(IndexOutOfBoundsException e){
      Map<String,String> responseBody = new HashMap<>();
      responseBody.put("message" , "Product with id: " + id  + " not found");
      return new ResponseEntity<>(responseBody , HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
    try{
      Product product = productService.delete(id);
      return new ResponseEntity<>(product,HttpStatus.OK);
    } catch(IndexOutOfBoundsException e){
      Map<String,String> responseBody = new HashMap<>();
      responseBody.put("message" , "Product with id: " + id  + " not found");
      return new ResponseEntity<>(responseBody , HttpStatus.NOT_FOUND);
    }
  }

}
