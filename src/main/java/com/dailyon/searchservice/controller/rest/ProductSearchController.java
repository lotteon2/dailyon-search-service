package com.dailyon.searchservice.controller.rest;

import com.dailyon.searchservice.dto.response.ProductSearchPageResponse;
import com.dailyon.searchservice.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
public class ProductSearchController {

  private final ProductSearchService productSearchService;

  @GetMapping("/search")
  public ResponseEntity<ProductSearchPageResponse> getProductBySearch(
      @RequestParam(name = "query") String query,
      @RequestParam(name = "searchAfter", required = false) Object[] searchAfter,
      @PageableDefault(
              page = 0,
              size = 10,
              sort = {"updatedAt"},
              direction = Sort.Direction.DESC)
          Pageable pageable) {
    ProductSearchPageResponse productSearchPageResponse =
        productSearchService.getProductBySearch(query, pageable, searchAfter);
    return ResponseEntity.ok(productSearchPageResponse);
  }
}
