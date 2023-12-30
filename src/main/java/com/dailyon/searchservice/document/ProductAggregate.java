package com.dailyon.searchservice.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductAggregate {

  private Integer salesAmount;
  private Integer likeCount;
  private Double reviewAvgRating;
  private Long reviewCount;
}
