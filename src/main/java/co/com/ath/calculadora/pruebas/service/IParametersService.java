package co.com.ath.calculadora.pruebas.service;

import co.com.ath.calculadora.pruebas.dto.ApiResponseDto;
import co.com.ath.calculadora.pruebas.dto.LoginDto;

public interface IParametersService {
    public ApiResponseDto getParameters(String valor,Integer pageNo,Integer pageSize);

}
