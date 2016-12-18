package com.github.blog.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

public final class ApiRequestHandler
    implements RequestHandler<HttpRequest, HttpResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      ApiRequestHandler.class);

  private final ObjectMapper mapper;

  public ApiRequestHandler() {
    mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  @Override
  public HttpResponse handleRequest(HttpRequest request, Context context) {
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("handling request");
    }

    HttpResponse response = new HttpResponse();

    try {
      response.setBody(mapper.writeValueAsString(new HashMap<String, Object>() {
        {
          put("message", "Hello World!");
          put("request", request);
        }
      }));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("finished");
    }

    return response;
  }
}