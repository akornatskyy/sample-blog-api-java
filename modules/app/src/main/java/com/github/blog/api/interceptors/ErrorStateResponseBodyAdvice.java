package com.github.blog.api.interceptors;

import com.github.blog.shared.service.ErrorState;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

//@ControllerAdvice
public final class ErrorStateResponseBodyAdvice
    extends AbstractMappingJacksonResponseBodyAdvice
    implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext)
      throws BeansException {
    this.applicationContext = applicationContext;
  }

  @Override
  protected void beforeBodyWriteInternal(
      MappingJacksonValue mappingJacksonValue,
      MediaType mediaType,
      MethodParameter methodParameter,
      ServerHttpRequest serverHttpRequest,
      ServerHttpResponse serverHttpResponse) {

    if (mappingJacksonValue.getValue() != null) {
      return;
    }

    serverHttpResponse.setStatusCode(HttpStatus.BAD_REQUEST);
    ErrorState errorState = this.applicationContext.getBean(ErrorState.class);
    if (!errorState.hasErrors()) {
      return;
    }

    mappingJacksonValue.setValue(errorState.getErrors());
  }
}