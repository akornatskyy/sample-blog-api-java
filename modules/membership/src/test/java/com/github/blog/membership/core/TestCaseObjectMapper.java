package com.github.blog.membership.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

final class TestCaseObjectMapper {
  public static final ObjectMapper MAPPER;

  static {
    MAPPER = new ObjectMapper();
    MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    MAPPER.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
    MAPPER.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
    MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public static String getValueAsString(Object value) {
    try {
      return MAPPER.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(e);
    }
  }
}