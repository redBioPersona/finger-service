/**
 * Copyright 2020.
 * PAY SANTANDER.
 * Elaborado por INDRA SISTEMAS MEXICO S.A. de C.V.
 * Se prohíbe la reproducción total y/o parcial.
 * Nombre de Aplicacion: 
 * Nombre de archivo: ErrorEnum.java
 * Fecha de creacion : Noviembre, 2020
 * @author : uaquino
 * @version 1.1
 *
 * Bitácora de modificaciones:
 * CR/Defecto 		Fecha 			Autor 			Descripción del cambio
 * ----------------------------------------------------------------------------
**/
package red.biopersona.faceservice.util;

/**
 * @author Z283689 Errores genericos
 */
public enum ErrorEnum {
	
	/** Variable error generico */
	EXC_GENERICO("EXC.000", "Error generico", "Error generico de servidor", CollectionsConstantes.ERROR, ""),

	/** Variable parametros invalidos */
	EXC_ERROR_PARAMS("EXC.001", "Parametros invalidos", "Parametros invalidos de consumo", CollectionsConstantes.WARNING, ""),
	
	/** Variable premier customer duplicado */
	EXC_DUPLICADO("EXC.100", "Premiercustomer duplicado", "Premiercustomer ya existe, no puede ser sobrescrita", CollectionsConstantes.WARNING, ""),
	
	/** Variable premier customer inexistente */
	EXC_INEXISTENTE("EXC.101", "Premiercustomer inexistente", "Premiercustomer no existe, intente con otro valor", CollectionsConstantes.WARNING, ""),
	
	/** Variable operacion no exitosa */
	EXC_OPER_NO_EXITOSA("EXC.102", "Operacion no exitosa", "Operacion no exitosa", CollectionsConstantes.ERROR, ""),

	/** Variable operacion con errores */
	EXC_OPER_CON_ERRORES("EXC.103", "Operacion con errores", "Operacion con errores", CollectionsConstantes.ERROR, ""),
	
	/** Variable operacion monto invalido */
	EXC_OPER_MONTO_INVALIDO("EXC.104", "Monto Invalido", "Monto Invalido", CollectionsConstantes.ERROR, ""),
	
	EXC_ERROR_DE_CONEXION("EXC.122", "Error de conexión", "Error", "Erro de conexión", "");


	/** Variable code */
	private final String code;

	/** Variable message */
	private final String message;

	/** Variable description */
	private final String description;

	/** Variable level */
	private final String level;

	/** Variable more info */
	private final String moreInfo;

	/**
	 * Constructor de la clase
	 * @param code
	 * @param message
	 * @param description
	 * @param level
	 * @param moreInfo
	 */
	ErrorEnum(final String code, final String message, final String description, final String level, final String moreInfo) {
		this.code = code;
		this.message = message;
		this.description = description;
		this.level = level;
		this.moreInfo = moreInfo;
	}

	/**
	 * Metodo getCode
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Metodo getMessage
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Metodo getDescription
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Metodo getLevel
	 * @return
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Metodo getMoreInfo
	 * @return
	 */
	public String getMoreInfo() {
		return moreInfo;
	}

}
