package co.com.ath.calculadora.pruebas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.ath.calculadora.pruebas.entity.UserEntity;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

	@Query(value = "select u from UserEntity u where u.name = :name")
	public UserEntity findUserByDni(@Param("name") String name);
	
}
