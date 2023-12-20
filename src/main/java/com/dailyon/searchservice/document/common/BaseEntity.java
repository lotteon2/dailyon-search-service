package com.dailyon.searchservice.document.common;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
