package com.dailyon.searchservice.document;

import com.dailyon.searchservice.document.common.BaseEntity;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

  private Long id;
  private String name;
  private String type;
  private String code;
  private String imgUrl;
  private List<String> describeImgUrls;
  private Integer price;
  private String gender;
  private Brand brand;
  private List<Category> categories;
  private List<ProductSizeAndStock> sizeAndStocks;
  private ProductAggregate aggregate;
  private Boolean isDeleted;
}
