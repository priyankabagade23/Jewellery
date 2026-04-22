package com.example.jewellery.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ApiError handleNotFound(NotFoundException ex, HttpServletRequest req) {
		return base(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI(), List.of());
	}

	@ExceptionHandler(BadRequestException.class)
	public ApiError handleBadRequest(BadRequestException ex, HttpServletRequest req) {
		return base(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI(), List.of());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiError handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
		List<ApiError.FieldViolation> fieldViolations = ex.getBindingResult().getFieldErrors().stream()
				.map(this::toFieldViolation)
				.toList();
		return base(HttpStatus.BAD_REQUEST, "Validation failed", req.getRequestURI(), fieldViolations);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ApiError handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
		List<ApiError.FieldViolation> violations = ex.getConstraintViolations().stream()
				.map(v -> new ApiError.FieldViolation(v.getPropertyPath().toString(), v.getMessage()))
				.toList();
		return base(HttpStatus.BAD_REQUEST, "Validation failed", req.getRequestURI(), violations);
	}

	@ExceptionHandler(ErrorResponseException.class)
	public ProblemDetail handleErrorResponseException(ErrorResponseException ex) {
		return ex.getBody();
	}

	@ExceptionHandler(Exception.class)
	public ApiError handleGeneric(Exception ex, HttpServletRequest req) {
		return base(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), req.getRequestURI(), List.of());
	}

	private ApiError base(HttpStatus status, String message, String path, List<ApiError.FieldViolation> fieldViolations) {
		return new ApiError(
				Instant.now(),
				status.value(),
				status.getReasonPhrase(),
				message,
				path,
				fieldViolations
		);
	}

	private ApiError.FieldViolation toFieldViolation(FieldError fe) {
		String msg = fe.getDefaultMessage() == null ? "Invalid value" : fe.getDefaultMessage();
		return new ApiError.FieldViolation(fe.getField(), msg);
	}
}

