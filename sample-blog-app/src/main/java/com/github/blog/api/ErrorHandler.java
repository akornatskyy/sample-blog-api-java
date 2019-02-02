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
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
final class ErrorHandler {

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

  /**
   * Handles resource not found response.
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorState handleNoHandlerFoundException(
      NoHandlerFoundException ex) {

    ErrorState errorState = new ErrorState();
    errorState.addError(
        "Oops! Code 404. Sorry, we can't find that resource. "
        + "Unfortunately the resource you are looking for may have been "
        + "removed, had its name changed, under construction or is temporarily "
        + "unavailable. Try checking the web address for typos, please. "
        + "We apologize for the inconvenience.");
    return errorState;
  }
}