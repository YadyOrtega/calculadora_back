package co.com.ath.calculadora.pruebas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.ath.calculadora.pruebas.dto.ApiResponseDto;
import co.com.ath.calculadora.pruebas.dto.LoginDto;
import co.com.ath.calculadora.pruebas.service.IAuthenticationService;
import co.com.ath.calculadora.pruebas.util.ResponseError;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

	@Autowired
	private IAuthenticationService service;
	
	@PostMapping
	public ResponseEntity<ApiResponseDto> login(@RequestBody LoginDto dto) {
		ApiResponseDto response;
		try {
			response = service.authentication(dto);
		} catch (Exception e) {
		  	log.error("Ha ocurriod un error en la autenticacion del login: {} ", e.getMessage());
		  	response = ResponseError.error("error Login");
		}
		return new ResponseEntity<>(response, response.getStatus());
	}
	
}
