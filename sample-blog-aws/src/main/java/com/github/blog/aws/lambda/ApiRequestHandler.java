package com.github.blog.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.blog.membership.Factory;
import com.github.blog.membership.core.SignInFacade;
import com.github.blog.membership.core.SignInRequest;
import com.github.blog.membership.core.SignInResponse;
import com.github.blog.membership.core.SignUpFacade;
import com.github.blog.membership.core.SignUpRequest;
import com.github.blog.membership.core.SignUpResponse;
import com.github.blog.shared.service.ErrorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class ApiRequestHandler
    implements RequestHandler<HttpRequest, HttpResponse> {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      ApiRequestHandler.class);

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
      .setSerializationInclusion(JsonInclude.Include.NON_NULL);

  private static final int SC_BAD_REQUEST = 400;

  private final FactoryProvider factoryProvider;

  public ApiRequestHandler() {
    this(new FactoryProvider());
  }

  @SuppressWarnings("PMD.CommentDefaultAccessModifier")
  ApiRequestHandler(FactoryProvider factoryProvider) {
    this.factoryProvider = factoryProvider;
  }

  @Override
  public HttpResponse handleRequest(HttpRequest request, Context context) {
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("handling request");
    }

    try {
      return route(request);
    } catch (
        @SuppressWarnings("PMD.AvoidCatchingGenericException") Exception ex) {
      LOGGER.error("unhandled error", ex);
      return HttpResponse.INTERNAL_SERVER_ERROR;
    }
  }

  private HttpResponse route(HttpRequest request) throws IOException {
    String routeName = Router.match(request);
    if (routeName == null) {
      return HttpResponse.NOT_FOUND;
    }

    switch (routeName) {
      case RouteNames.WELCOME:
        return httpOk(new WelcomeFacade().process(request));
      case RouteNames.SIGNIN:
        return signin(request);
      case RouteNames.SIGNUP:
        return signup(request);
      default:
        return HttpResponse.NOT_FOUND;
    }
  }

  private HttpResponse signin(HttpRequest request) throws IOException {
    String body = request.getBody();
    if (body == null) {
      return httpNoBody();
    }

    Factory factory = factoryProvider.from(request.getStageVariables());
    ErrorState errorState = new ErrorState();
    SignInFacade facade = factory.createSignInFacade(errorState);

    SignInResponse response = facade.authenticate(
        OBJECT_MAPPER.readValue(body, SignInRequest.class));

    if (response == SignInResponse.ERROR) {
      return httpBadRequest(errorState);
    }

    return httpOk(response);
  }

  private HttpResponse signup(HttpRequest request) throws IOException {
    String body = request.getBody();
    if (body == null) {
      return httpNoBody();
    }

    Factory factory = factoryProvider.from(request.getStageVariables());
    ErrorState errorState = new ErrorState();
    SignUpFacade facade = factory.createSignUpFacade(errorState);

    SignUpResponse response = facade.createAccount(
        OBJECT_MAPPER.readValue(request.getBody(), SignUpRequest.class));

    if (response == SignUpResponse.ERROR) {
      return httpBadRequest(errorState);
    }

    return httpOk(response);
  }

  private static HttpResponse httpOk(Object result) throws IOException {
    HttpResponse response = new HttpResponse();
    response.setBody(render(result));
    return response;
  }

  private static HttpResponse httpNoBody() throws IOException {
    return httpBadRequest("The HTTP message is not readable.");
  }

  private static HttpResponse httpBadRequest(String message)
      throws IOException {
    ErrorState errorState = new ErrorState();
    errorState.addError(message);
    return httpBadRequest(errorState);
  }

  private static HttpResponse httpBadRequest(ErrorState errorState)
      throws IOException {
    HttpResponse response = new HttpResponse();
    response.setStatusCode(SC_BAD_REQUEST);
    response.setBody(render(errorState));
    return response;
  }

  private static String render(Object value) throws IOException {
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("rendering response");
    }

    String content = OBJECT_MAPPER.writeValueAsString(value);

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info(String.format("finished %.3f KB", content.length() / 1024F));
    }

    return content;
  }
}