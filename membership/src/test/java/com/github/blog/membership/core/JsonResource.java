package com.github.blog.membership.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;

public final class JsonResource {
  private static final ObjectMapper OBJECT_MAPPER =
      new ObjectMapper()
          .setSerializationInclusion(JsonInclude.Include.NON_NULL)
          .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
          .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
          .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
          .enable(SerializationFeature.INDENT_OUTPUT);

  private JsonResource() {
  }

  public static <T> T getResourceAsValue(Class<T> cls, String name) {
    try (InputStream stream = JsonResource.class.getResourceAsStream(name)) {
      return OBJECT_MAPPER.readValue(stream, cls);
    } catch (IOException ex) {
      throw new IllegalArgumentException(name, ex);
    }
  }

  static String getValueAsString(Object value) {
    try {
      return OBJECT_MAPPER.writeValueAsString(value);
    } catch (JsonProcessingException ex) {
      throw new IllegalStateException(ex);
    }
  }

  static String pretty(String jsonString) {
    try {
      return OBJECT_MAPPER.writeValueAsString(
          OBJECT_MAPPER.readValue(jsonString.getBytes(), Object.class));
    } catch (IOException ex) {
      throw new IllegalArgumentException(ex);
    }
  }
}