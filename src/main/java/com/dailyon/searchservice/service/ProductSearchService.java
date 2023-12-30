package com.dailyon.searchservice.service;

import com.dailyon.searchservice.document.Product;
import com.dailyon.searchservice.dto.response.ProductSearchPageResponse;
import com.dailyon.searchservice.dto.response.ProductSearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSearchService {

  private static final String PRODUCT_INDEX = "product";
  private final RestHighLevelClient client;
  private final ObjectMapper objectMapper;

  public ProductSearchPageResponse getProductBySearch(
      String query, Pageable pageable, Object[] searchAfter) {

    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

    MultiMatchQueryBuilder multiMatchQuery =
        QueryBuilders.multiMatchQuery(query, "name", "categories.name", "brand.name", "gender")
            .field("name", 2);

    boolQuery.must(multiMatchQuery);

    boolQuery.filter(new TermQueryBuilder("type", "NORMAL"));
    boolQuery.filter(new TermQueryBuilder("isDeleted", false));

    sourceBuilder.query(boolQuery);

    sourceBuilder.from(pageable.getPageNumber() * pageable.getPageSize());
    sourceBuilder.size(pageable.getPageSize());
    pageable.getSort().stream()
        .forEach(
            order ->
                sourceBuilder
                    .sort(
                        SortBuilders.fieldSort(order.getProperty())
                            .order(SortOrder.fromString(order.getDirection().name())))
                    .sort(SortBuilders.fieldSort("_score").order(SortOrder.fromString("desc"))));

    if (searchAfter != null) {
      AtomicReference<String> sortProperty = new AtomicReference<>();
      pageable.getSort().forEach(order -> sortProperty.set(order.getProperty()));

      if(sortProperty.get().equals("createdAt") || sortProperty.get().equals("updatedAt")) {
        Long timestamp = Long.parseLong(searchAfter[0].toString());

        LocalDateTime dateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.UTC).toLocalDateTime();

        String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss"));

        searchAfter = new Object[]{formattedDateTime, searchAfter[1]};
      }

      sourceBuilder.searchAfter(searchAfter);
    }

    SearchResponse searchResponse = search(sourceBuilder);

    List<ProductSearchResponse> productSearchResponses =
        Arrays.stream(searchResponse.getHits().getHits())
            .map(
                hit -> {
                  Product product = objectMapper.convertValue(hit.getSourceAsMap(), Product.class);

                  Object[] sortValues = hit.getSortValues();
                  return ProductSearchResponse.fromDocument(product, sortValues);
                })
            .collect(Collectors.toList());

    return ProductSearchPageResponse.fromDocument(productSearchResponses, pageable);
  }

  /** document search */
  public SearchResponse search(SearchSourceBuilder searchSourceBuilder) {

    try {
      SearchRequest searchRequest = new SearchRequest(PRODUCT_INDEX);
      searchRequest.source(searchSourceBuilder);

      return client.search(searchRequest, RequestOptions.DEFAULT);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
