package co.com.ath.calculadora.pruebas.controller;

import co.com.ath.calculadora.pruebas.dto.ApiResponseDto;
import co.com.ath.calculadora.pruebas.service.IParametersService;
import co.com.ath.calculadora.pruebas.util.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parameters")
@Slf4j
public class ParametersController {
    Logger logger = LoggerFactory.getLogger(ParametersController.class);

    @Autowired
    private IParametersService parametersService;


    @GetMapping("/searchValue")
    public ResponseEntity<ApiResponseDto> searchValue(@RequestParam String value,
                                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        ApiResponseDto response = null;
        try {
            log.info("Servicio controller Init. <{}>",value);
            response = parametersService.getParameters(value, pageNo, pageSize);
        } catch (Exception e) {
            log.error("Ha ocurriod un error : {} ", e.getMessage());
            response = ResponseError.error("error ");
        }
        return new ResponseEntity<>(response, response.getStatus());
    }
}
