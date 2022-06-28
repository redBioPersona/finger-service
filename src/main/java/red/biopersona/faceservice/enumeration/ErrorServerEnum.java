package red.biopersona.faceservice.enumeration;

/***
 * Enum con los tipos de error del lado de servidor
 * 
 * @author Omar
 *
 */
public enum ErrorServerEnum {
	MSG_ERROR_PATH_INVALIDO("ERROR_PATH_INVALIDO"), MSG_ERROR_EN_CLIENTE("ERROR_EN_CLIENTE"),
	MSG_ERROR_AL_REINICIAR("ERROR_AL_REINICIAR"), MSG_ERROR_EN_SERVIDOR("ERROR_EN_SERVIDOR"),
	MSG_ERROR_DE_CONEXION("ERROR_DE_CONEXION"), MSG_ERROR_NO_DISPONIBLE("ERROR_NO_DISPONIBLE"),
	MSG_ERROR_DE_RESPUESTA("ERROR_DE_RESPUESTA"), MSG_ERROR_SERVICIO_NO_DISPONIBLE("ERROR_SERVICIO_NO_DISPONIBLE");

	// Variable para obtener el mensaje
	private final String msj;

	/**
	 * Constructor del enum
	 * 
	 * @param msj
	 */
	ErrorServerEnum(final String msj) {
		this.msj = msj;
	}
	
	/***
	 * Metodo para obtner el mensaje
	 * 
	 * @return mensaje del error
	 */
	public String getMsj() {
		return msj;
	}
}
