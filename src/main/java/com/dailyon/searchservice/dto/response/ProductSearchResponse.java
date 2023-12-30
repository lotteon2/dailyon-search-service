package com.dailyon.searchservice.dto.response;

import com.dailyon.searchservice.document.Category;
import com.dailyon.searchservice.document.Product;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSearchResponse {

  private Long id;
  private String name;
  private Integer price;
  private String imgUrl;
  private String code;
  private String brandName;
  private String categoryName;
  private Double reviewAvgRating;
  private Long reviewCount;
  private Object[] searchAfter;

  public static ProductSearchResponse fromDocument(Product product, Object[] searchAfter) {
    ProductSearchResponseBuilder productSearchResponseBuilder = ProductSearchResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .imgUrl(product.getImgUrl())
            .code(product.getCode())
            .brandName(product.getBrand().getName())
            .reviewAvgRating(product.getAggregate().getReviewAvgRating())
            .reviewCount(product.getAggregate().getReviewCount())
            .searchAfter(searchAfter);

    for (Category category : product.getCategories()) {
      if(category.getIsLeaf()) {
        productSearchResponseBuilder.categoryName(category.getName());
      }
    }

    return productSearchResponseBuilder
        .build();
  }
}
