package com.example.jewellery.common;

public class BadRequestException extends RuntimeException {
	public BadRequestException(String message) {
		super(message);
	}
}

