package co.com.ath.calculadora.pruebas.util;

import org.springframework.http.HttpStatus;

import co.com.ath.calculadora.pruebas.dto.ApiResponseDto;

public class ResponseError {
	
	private ResponseError() {}

	public static ApiResponseDto error (String message) {
		return ApiResponseDto.builder()
				.data(null)
				.message(message)
				.status(HttpStatus.UNAUTHORIZED).build();
	}
	
}
