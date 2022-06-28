package red.biopersona.faceservice.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author MINSAIT
 * La clase DefaultErrorResponseBean, 
 * usada para enviar la respuesta en caso de error durante la ejecucion del servicio.
 */
public class DefaultErrorListDTO implements Serializable {

	/**
	 * Variable para serializar la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * La variable errors.
	 */
	private List<DefaultErrorDTO> errors;
	
	/** 
	 * Constructor de la clase. 
	 * ErrorEnum es la clase con la serie de codigos de errores (Puede utilizarse esa misma clase generica, o una implementacion propia)
	 * @param errorBean Bean de errores
	 */
	public DefaultErrorListDTO(final DefaultErrorDTO errorBean){
		this.add(errorBean);
	}

	/**
	 * Obtiene el valor de la variable errors.
	 * @return el errors
	 */
	public List<DefaultErrorDTO> getErrors() {
		return new ArrayList<>(errors);
	}

	/**
	 * Coloca el valor de errors.
	 * @param errors es el nuevo valor de errors
	 */
	public void setErrors(List<DefaultErrorDTO> errors) {
		this.errors = new ArrayList<>(errors);
	}

	/**
	 * Adds the BEAN
	 * @param errorBean el parametro error bean
	 */
	public void add(final DefaultErrorDTO errorBean) {
		if (this.errors == null || this.errors.isEmpty()) {
			this.errors = new ArrayList<>();
		}
		this.errors.add(errorBean);
	}
	
	/**
	 * Metodo que retorna una cadena del mapper
	 * @return El json en string del objeto completo
	 * @throws JsonProcessingException Cuando existe error en el parseo
	 */
	public String toJsonString() throws JsonProcessingException{
		ObjectMapper objectMapper= new ObjectMapper();
		return objectMapper.writeValueAsString(this);
	}
}
