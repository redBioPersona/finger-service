package red.biopersona.fingerservice.enumeration;

/***
 * Enum con los tipos de error
 * 
 * @author Omar
 *
 */
public enum ErrorMitResponseEnum {
	MITRESPONSE_APROBADO("LOGIN_OK", "/imgs/cartera.svg", "OK", "CONTINUAR"),
	MITRESPONSE_RECHAZADO("No pudimos procesar tu pago", "/imgs/card_dennied.svg", "¿Deseas pagar con otra tarjeta?",
			"Usar otra tarjeta"),
	MITRESPONSE_ERROR("No pudimos procesar tu pago", "/imgs/generic_error.svg", "¿Deseas pagar con otra tarjeta?",
			"Usar otra tarjeta");

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
	ErrorMitResponseEnum(final String titulo, final String img, final String msg, final String btn) {
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