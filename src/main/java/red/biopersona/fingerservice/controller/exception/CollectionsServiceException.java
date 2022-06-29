package red.biopersona.fingerservice.controller.exception;

import red.biopersona.fingerservice.util.ErrorEnum;

public class CollectionsServiceException extends Exception {

	private static final long serialVersionUID = 1303454450535514738L;

	/**
	 * Constructor
	 * @param message Mensaje de error
	 */
	public CollectionsServiceException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param message Mensaje de error
	 * @param causa Causa completa del error
	 */
	public CollectionsServiceException(String message, Throwable causa) {
		super(message, causa);
	}

	/**
	 * Constructor que la interfaz del enumerador de error
	 * @param errorEnum Interfaz de enumerador de error
	 */
	public CollectionsServiceException(ErrorEnum errorEnum) {
		super(errorEnum.getMessage());
	}

	/**
	 * Constructor que la interfaz del enumerador de error
	 * @param errorEnum Interfaz de enumerador de error
	 * @param causa Causa completa del error
	 */
	public CollectionsServiceException(ErrorEnum errorEnum, Throwable causa) {
		super(errorEnum.getMessage(), causa);
	}

}