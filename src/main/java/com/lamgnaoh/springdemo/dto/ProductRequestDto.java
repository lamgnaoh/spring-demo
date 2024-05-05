package com.lamgnaoh.springdemo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ProductRequestDto {

  private String title;
  private String description;
  private Double price;
  private Double discountPercentage;
  private Double rating;
  private Integer stock;
  private String brand;
  private String category;
  private String thumbnail;
  private List<String> images;

}
