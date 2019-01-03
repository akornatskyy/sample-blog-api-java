package com.github.blog.api.interceptors;

import com.github.blog.shared.service.ErrorState;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public final class HttpMessageNotReadableResponseBodyAdvice {

  /**
   * Handles message not readable response.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorState handleMethodArgumentNotValidException(
      HttpMessageNotReadableException ex) {
    ErrorState errorState = new ErrorState();
    errorState.addError("The HTTP message is not readable.");
    return errorState;
  }
}