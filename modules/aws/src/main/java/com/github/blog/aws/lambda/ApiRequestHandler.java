package com.github.blog.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.blog.shared.service.ErrorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class ApiRequestHandler
    implements RequestHandler<HttpRequest, HttpResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      ApiRequestHandler.class);

  private static final int SC_BAD_REQUEST = 400;

  private final ObjectMapper mapper;
  private final FactoryProvider factoryProvider;

  public ApiRequestHandler() {
    this(new FactoryProvider());
  }

  ApiRequestHandler(FactoryProvider factoryProvider) {
    mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    this.factoryProvider = factoryProvider;
  }

  @Override
  public HttpResponse handleRequest(HttpRequest request, Context context) {
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("handling request");
    }

    String routeName = Router.match(request);
    if (routeName == null) {
      return HttpResponse.NOT_FOUND;
    }

    request.setRouteName(routeName);

    HttpResponse response;
    try {
      response = route(request);
    } catch (Exception ex) {
      LOGGER.error("unhandled error", ex);
      return HttpResponse.INTERNAL_SERVER_ERROR;
    }

    return response;
  }

  private HttpResponse route(HttpRequest request) throws IOException {
    ErrorState errorState = new ErrorState();
    Factory factory = factoryProvider.from(request.getStageVariables());
    Object result;
    switch (request.getRouteName()) {
      case RouteNames.WELCOME:
        result = factory.createWelcomeFacade()
            .process(request);
        break;
      default:
        throw new IllegalStateException(
            "Unknown route name: " + request.getRouteName());
    }

    HttpResponse response = new HttpResponse();
    if (result == null) {
      response.setStatusCode(SC_BAD_REQUEST);
      result = errorState.getErrors();
    }

    response.setBody(render(result));
    return response;
  }

  private String render(Object value) {
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("rendering response");
    }

    String content;
    try {
      content = mapper.writeValueAsString(value);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info(String.format("finished %.3f KB", content.length() / 1024F));
    }

    return content;
  }
}