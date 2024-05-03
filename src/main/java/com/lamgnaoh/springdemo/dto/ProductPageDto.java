package com.lamgnaoh.springdemo.dto;

import com.lamgnaoh.springdemo.entity.Product;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductPageDto extends PageDto {
  private List<Product> products;

  public ProductPageDto() {
    super();
  }
  public ProductPageDto(List<Product> products,Integer total, Integer skip, Integer limit) {
    super(total,skip,limit);
    this.products = products;
  }
}
