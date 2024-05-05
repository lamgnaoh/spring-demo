package com.lamgnaoh.springdemo.service;

import com.lamgnaoh.springdemo.dao.ProductDAO;
import com.lamgnaoh.springdemo.dto.ProductRequestDto;
import com.lamgnaoh.springdemo.entity.Product;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductDAO productDAO;

  public int countAll(){
    return productDAO.getAll().size();
  }

  public List<Product> getAll(Integer limit, Integer skip, List<String> fields) {
    List<Product> products = productDAO.getAll();
    Stream<Product> productStream = products.stream();
    if (limit == 0) {
      productStream = productStream.skip(skip);
    } else {
      productStream = productStream.skip(skip).limit(limit);
    }
    if (fields == null || fields.isEmpty()) {
      return productStream.toList();
    }
    return productStream.map(product -> filterFields(product, fields)).toList();
  }

  public Product filterFields(Product product, List<String> fields){
      Product filteredProduct = Product.builder().build();
      filteredProduct.setId(product.getId());
      for (String field : fields){
        try {
          Field declaredField = product.getClass().getDeclaredField(field);
          declaredField.setAccessible(true);
          Object value = declaredField.get(product);

          Field filteredField = filteredProduct.getClass().getDeclaredField(field);
          filteredField.setAccessible(true);
          filteredField.set(filteredProduct, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
      }
      return filteredProduct;
  }


  public Product getById(Integer id) throws IndexOutOfBoundsException {
    List<Product> products = productDAO.getAll();
    return products.get(id - 1);
  }

  public List<Product> getByQuery(String query, Integer limit, Integer skip) {
    List<Product> productByQuerys = productDAO.getAll().stream()
        .filter(product -> product.getTitle().contains(query)).toList();
    Stream<Product> productStream = productByQuerys.stream();
    if (limit == 0) {
      productStream = productStream.skip(skip);
    } else {
      productStream = productStream.skip(skip).limit(limit);
    }
    return productStream.toList();
  }

  public List<String> getAllCategories() {
    List<Product> products = productDAO.getAll();
    return products.stream().map(Product::getCategory).toList();
  }

  public List<Product> getAllByCategoryType(String type, Integer limit, Integer skip) {
    List<Product> products = productDAO.getAll();
    List<Product> productsByCategory = products.stream()
        .filter(product -> product.getCategory().equals(type)).toList();
    Stream<Product> productStream = productsByCategory.stream();
    if (limit == 0) {
      productStream = productStream.skip(skip);
    } else {
      productStream = productStream.skip(skip).limit(limit);
    }
    return productStream.toList();
  }

  public Product add(ProductRequestDto productRequestDto) {
    Product product = Product.builder().title(productRequestDto.getTitle())
        .description(productRequestDto.getDescription()).price(productRequestDto.getPrice())
        .discountPercentage(productRequestDto.getDiscountPercentage())
        .rating(productRequestDto.getRating()).stock(productRequestDto.getStock())
        .brand(productRequestDto.getBrand()).category(productRequestDto.getCategory())
        .thumbnail(productRequestDto.getThumbnail()).images(productRequestDto.getImages()).build();
    List<Product> products = productDAO.getAll();
    product.setId(!products.isEmpty() ? (products.get(products.size() - 1).getId() + 1) : 1);
//    Save to DB
    productDAO.add(product);
    return product;
  }

  public Product update(Integer id,ProductRequestDto productRequestDto) throws IndexOutOfBoundsException {
    List<Product> products = productDAO.getAll();
    Product existedProduct = products.get(id - 1);
    if (productRequestDto.getTitle() != null){
      existedProduct.setTitle(productRequestDto.getTitle());
    }
    if (productRequestDto.getDescription() != null){
      existedProduct.setDescription(productRequestDto.getDescription());
    }
    if (productRequestDto.getPrice() != null){
      existedProduct.setPrice(productRequestDto.getPrice());
    }
    if (productRequestDto.getDiscountPercentage() != null){
      existedProduct.setDiscountPercentage(productRequestDto.getDiscountPercentage());
    }
    if (productRequestDto.getRating() != null){
      existedProduct.setRating(productRequestDto.getRating());
    }
    if (productRequestDto.getStock() != null){
      existedProduct.setStock(productRequestDto.getStock());
    }
    if (productRequestDto.getBrand() != null){
      existedProduct.setBrand(productRequestDto.getBrand());
    }
    if (productRequestDto.getCategory() != null){
      existedProduct.setCategory(productRequestDto.getCategory());
    }
    if (productRequestDto.getThumbnail() != null){
      existedProduct.setThumbnail(productRequestDto.getThumbnail());
    }
    if (productRequestDto.getImages()!= null &&!productRequestDto.getImages().isEmpty()){
      existedProduct.setImages(productRequestDto.getImages());
    }
//    save to db
    productDAO.update(existedProduct);
    return existedProduct;
  }

  public Product delete(Integer id) throws IndexOutOfBoundsException {
    List<Product> products = productDAO.getAll();
    Product deletedProduct = products.get(id - 1);
    productDAO.delete(deletedProduct);
    return deletedProduct;
  }
}
