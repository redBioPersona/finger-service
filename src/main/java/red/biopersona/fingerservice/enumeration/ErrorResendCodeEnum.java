package red.biopersona.fingerservice.enumeration;

/***
 * Enum con los tipos de error en el reeenvio de codigo
 * 
 * @author Omar
 *
 */
public enum ErrorResendCodeEnum {
	RESENDCODE_OK("Código enviado", "/imgs/codigoExito.svg", "Hemos reenviado el código con éxito", GenericDataEnum.GENERIC_MSJ_SALIR.getTitulo()),
	RESENDCODE_CLEAN("Tu tarjeta no tiene adeudo vencido", "/imgs/cartera.svg",
			"Tu tarjeta se encuentra al corriente, para pagarla puedes usar cualquiera de los medios habituales",
			GenericDataEnum.GENERIC_MSJ_SALIR.getTitulo()),
	RESENDCODE_BAD_CREDENTIALS("Verifica la información", "/imgs/targetaMal.svg",
			"Los datos que ingresaste no corresponden a los datos de tu tarjeta, por favor validalo y vuelve a ingresar",
			"Reintentar"),
	RESENDCODE_SERVER_ERROR("Error interno", "/imgs/generic_error.svg", "Ups, hubo un error interno, intente más tarde",
	GenericDataEnum.GENERIC_MSJ_SALIR.getTitulo());

	// Variable para obtener el titulo
	private final String titulo;

	// Variable para obtener el img
	private final String img;

	// Variable para obtener el msg
	private final String msg;

	// Variable para obtener el btn
	private final String btn;
	
	/**
	 * Constructor del enul
	 * @param titulo del modal
	 * @param img del modal
	 * @param msg cuerpo del modal
	 * @param btn mensaje en el btn del modal
	 */
	ErrorResendCodeEnum(final String titulo, final String img, final String msg, final String btn) {
		this.titulo = titulo;
		this.img = img;
		this.msg = msg;
		this.btn = btn;
	}
	

	/**
	 * Metodo que obtiene el titulo
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Metodo que obtiene la imagen del error
	 * @return ruta de la imagen
	 */
	public String getImg() {
		return img;
	}

	/***
	 * Metodo que obtiene el mensaje
	 * @return mensaje
	 */
	public String getMsg() {
		return msg;
	}

	/***
	 * Metodo que obtiene el texto del btn
	 * @return texto del boton
	 */
	public String getBtn() {
		return btn;
	}

	
}