package com.patient_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex,
                                                      HttpServletRequest request) {
    return build(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                        HttpServletRequest request) {
    Map<String, String> details = new LinkedHashMap<>();
    ex.getBindingResult().getFieldErrors()
            .forEach(error ->
                    details.putIfAbsent(error.getField(), error.getDefaultMessage()));
    return build(HttpStatus.BAD_REQUEST, "Données invalides", request, details);
  }
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleUnreadable(HttpMessageNotReadableException ex,
                                                        HttpServletRequest request) {
    return build(HttpStatus.BAD_REQUEST,
            "Corps de requête illisible ou valeur d'énumération invalide", request, null);
  }
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleTypeMismatch(
          MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
    return build(HttpStatus.BAD_REQUEST,
            "Paramètre '%s' invalide".formatted(ex.getName()), request, null);
  }
  private ResponseEntity<ErrorResponse> build(HttpStatus status, String message,
                                              HttpServletRequest request,
                                              Map<String, String> details) {
    ErrorResponse body = new ErrorResponse(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            message,
            request.getRequestURI(),
            details);
    return ResponseEntity.status(status).body(body);
  }
}