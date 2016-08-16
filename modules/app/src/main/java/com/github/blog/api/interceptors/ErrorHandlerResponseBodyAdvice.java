package com.github.blog.api.interceptors;

import com.github.blog.shared.service.ErrorState;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;
import java.util.Map;

@ControllerAdvice
public final class ErrorHandlerResponseBodyAdvice {

  /**
   * Handles validation errors and translates to response.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public Map<String, Collection<String>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {

    ErrorState errorState = new ErrorState();
    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
      errorState.addError(fe.getField(), fe.getDefaultMessage());
    }

    return errorState.getErrors();
  }
}