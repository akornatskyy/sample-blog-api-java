package com.github.blog.api;

import com.github.blog.shared.service.ErrorState;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public final class ErrorHandler {

  /**
   * Handles validation errors and translates to response.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorState handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {

    ErrorState errorState = new ErrorState();
    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
      errorState.addError(fe.getField(), fe.getDefaultMessage());
    }

    return errorState;
  }

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