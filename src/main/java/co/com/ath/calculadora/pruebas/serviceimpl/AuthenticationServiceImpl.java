package co.com.ath.calculadora.pruebas.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import co.com.ath.calculadora.pruebas.dto.ApiResponseDto;
import co.com.ath.calculadora.pruebas.dto.LoginDto;
import co.com.ath.calculadora.pruebas.dto.LoginResponseDto;
import co.com.ath.calculadora.pruebas.entity.UserEntity;
import co.com.ath.calculadora.pruebas.repository.IUserRepository;
import co.com.ath.calculadora.pruebas.service.IAuthenticationService;

/**
 * @author xxxxxxx Clase que realiza la autenticacion
 */

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

	@Autowired
	private IUserRepository repository;

	/**
	 * Metodo que valida la autenticacion
	 * 
	 * @param dto -> objeto con lso datos del usuario, clave y nombre
	 * @return ApiResponseDto
	 */

	@Override
	public ApiResponseDto authentication(LoginDto dto) {
		UserEntity user = repository.findUserByDni(dto.getUser());
		LoginResponseDto response = new LoginResponseDto();
		if (user != null) {
			response.setToken("fdsjkbfkdshbfjdbs");
			return ApiResponseDto.builder().data(response).message("entro").status(HttpStatus.OK).build();
		}
		return ApiResponseDto.builder().data(null).message("no existe el usuario").status(HttpStatus.BAD_REQUEST)
				.build();
	}

}
