package co.com.ath.calculadora.pruebas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_usuarios")
public class UserEntity {

	@Id
	private Integer dni;
	@Column(name = "nombre")
	private String name;
	
}
