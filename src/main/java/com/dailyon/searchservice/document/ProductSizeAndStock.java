package com.dailyon.searchservice.document;

import com.dailyon.searchservice.document.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeAndStock extends BaseEntity {

  private Long id;
  private String name;
  private Long quantity;
  private Boolean isDeleted;
}
