package red.biopersona.fingerservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	/** Variable objeto respuesta DTO */
	
	/** Variable numero de tarjeta */
	private String msj;

}
