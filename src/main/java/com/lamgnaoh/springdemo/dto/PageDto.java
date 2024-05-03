package com.lamgnaoh.springdemo.dto;

import lombok.Data;

@Data
public class PageDto {
  private Integer total;
  private Integer skip;
  private Integer limit;

  public PageDto() {
  }

  public PageDto(Integer total, Integer skip, Integer limit) {
    this.total = total;
    this.skip = skip;
    this.limit = limit;
  }
}
