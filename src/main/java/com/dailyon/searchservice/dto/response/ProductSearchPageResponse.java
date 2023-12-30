package com.dailyon.searchservice.dto.response;

import java.util.List;
import lombok.*;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductSearchPageResponse {

  private List<ProductSearchResponse> products;
  private Boolean hasNext;

  public static ProductSearchPageResponse fromDocument(
      List<ProductSearchResponse> products, Pageable pageable) {

    ProductSearchPageResponseBuilder productSearchPageResponseBuilder =
        ProductSearchPageResponse.builder().products(products);
    if (products.size() < pageable.getPageSize()) {
      productSearchPageResponseBuilder.hasNext(false);
    } else {
      productSearchPageResponseBuilder.hasNext(true);
    }

    return productSearchPageResponseBuilder.build();
  }
}
