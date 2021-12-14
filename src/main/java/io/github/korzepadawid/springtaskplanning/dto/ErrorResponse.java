package io.github.korzepadawid.springtaskplanning.dto;

import java.util.HashMap;

public class ErrorResponse {

  private String message;
  private int code;
  private final HashMap<String, String> fields = new HashMap<>();

  public ErrorResponse(String message, int code) {
    this.message = message;
    this.code = code;
  }

  public ErrorResponse() {}

  public void addValidationError(String field, String message) {
    fields.put(field, message);
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public HashMap<String, String> getFields() {
    return fields;
  }
}
