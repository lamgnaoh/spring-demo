package com.lamgnaoh.springdemo.service;

import com.lamgnaoh.springdemo.dao.ProductDAO;
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


}
