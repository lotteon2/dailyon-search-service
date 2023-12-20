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
public class Category extends BaseEntity {

  private Long id;
  private String name;
  private Boolean isDeleted;
}
