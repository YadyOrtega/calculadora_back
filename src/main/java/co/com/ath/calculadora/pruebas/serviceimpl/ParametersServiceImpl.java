package co.com.ath.calculadora.pruebas.serviceimpl;

import co.com.ath.calculadora.pruebas.controller.ParametersController;
import co.com.ath.calculadora.pruebas.dto.ApiResponseDto;
import co.com.ath.calculadora.pruebas.dto.LoginDto;
import co.com.ath.calculadora.pruebas.dto.ParametersDto;
import co.com.ath.calculadora.pruebas.entity.ParametersEntity;
import co.com.ath.calculadora.pruebas.repository.ParametersRepository;
import co.com.ath.calculadora.pruebas.service.IParametersService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ParametersServiceImpl implements IParametersService {


    @Autowired
    private ParametersRepository parametersRepository;

    /**
     * Metodo para obtener la consulta de la tabla parametros con paginacion
     *
     * @param valor
     * @param pageNo
     * @param pageSize
     * @return ApiResponseDto
     */
    @Override
    public ApiResponseDto getParameters(String valor, Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        log.info("buscando en Base datos valor <{}>",valor);
        Page<ParametersEntity> parametersEntity = parametersRepository.findByValor(valor,paging);
        log.info("<<<<<Response de Base datos>>>>> <{}>",parametersEntity.getContent());


        ParametersDto parametersDto = new ParametersDto();
        if (parametersEntity != null) {
            //mapeo de la entidad a dto
            for(ParametersEntity parameters:parametersEntity.getContent()){
                parametersDto.setDni(parameters.getDni());
                parametersDto.setCapa(parameters.getCapa());
                parametersDto.setEstado(parameters.getEstado());
                parametersDto.setValor(parameters.getValor());
            }
            return ApiResponseDto.builder().data(parametersDto).message("successful").status(HttpStatus.OK).build();
        }
        return ApiResponseDto.builder().data(null).message("no existe data").status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
