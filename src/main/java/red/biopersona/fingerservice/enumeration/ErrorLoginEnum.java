package red.biopersona.fingerservice.enumeration;

/***
 * Enum con los tipos de error en el formulario de login
 * 
 * @author Omar
 *
 */
public enum ErrorLoginEnum {
	LOGIN_OK("LOGIN_OK", "/imgs/cartera.svg", "OK", "CONTINUAR"),
	LOGIN_CLEAN("Tu tarjeta no tiene adeudo vencido", "/imgs/card_ok.svg",
			"Tu tarjeta se encuentra al corriente, para pagarla puedes usar cualquiera de los medios habituales",
			GenericDataEnum.GENERIC_MSJ_SALIR.getTitulo()),
	LOGIN_BAD_CREDENTIALS("Verifica tu información", GenericDataEnum.GENERIC_IMG_ERROR.getTitulo(),
			"El código que ingresaste no corresponde a los datos de tu tarjeta, por favor validalo y vuelve a ingresar",
			"Reintentar"),
	LOGIN_EXPIRADO("Código vencido", GenericDataEnum.GENERIC_IMG_ERROR.getTitulo(), "El código que ingresaste ya expiró, te invitamos a buscar otro canal para realizar tu pago",
			GenericDataEnum.GENERIC_MSJ_SALIR.getTitulo()),
	LOGIN_SERVER_ERROR("Error interno", GenericDataEnum.GENERIC_IMG_ERROR.getTitulo(), "Ups, hubo un error interno, intente más tarde",
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
	 * 
	 * @param titulo del modal
	 * @param img    del modal
	 * @param msg    cuerpo del modal
	 * @param btn    mensaje en el btn del modal
	 */
	ErrorLoginEnum(final String titulo, final String img, final String msg, final String btn) {
		this.titulo = titulo;
		this.img = img;
		this.msg = msg;
		this.btn = btn;
	}
	
	/**
	 * Metodo que obtiene el titulo
	 * 
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * Metodo que obtiene la imagen del error
	 * 
	 * @return ruta de la imagen
	 */
	public String getImg() {
		return img;
	}

	/***
	 * Metodo que obtiene el mensaje
	 * 
	 * @return mensaje
	 */
	public String getMsg() {
		return msg;
	}

	/***
	 * Metodo que obtiene el texto del btn
	 * 
	 * @return texto del boton
	 */
	public String getBtn() {
		return btn;
	}

	
}
