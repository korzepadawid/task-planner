package io.github.korzepadawid.springtaskplanning.controller;

import io.github.korzepadawid.springtaskplanning.dto.ErrorResponse;
import io.github.korzepadawid.springtaskplanning.exception.BusinessLogicException;
import io.github.korzepadawid.springtaskplanning.exception.ResourceNotFoundException;
import io.github.korzepadawid.springtaskplanning.exception.StorageException;
import io.github.korzepadawid.springtaskplanning.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  @ExceptionHandler(ResourceNotFoundException.class)
  public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException exception) {
    return new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND.value());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler({
    BusinessLogicException.class,
    StorageException.class,
    UserAlreadyExistsException.class
  })
  public ErrorResponse handleRuntimeExceptions(Exception exception) {
    return new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    ErrorResponse errorResponse =
        new ErrorResponse("Validation error.", HttpStatus.BAD_REQUEST.value());
    exception
        .getBindingResult()
        .getAllErrors()
        .forEach(
            objectError -> {
              String field = ((FieldError) objectError).getField();
              errorResponse.addValidationError(field, objectError.getDefaultMessage());
            });
    return errorResponse;
  }
}
