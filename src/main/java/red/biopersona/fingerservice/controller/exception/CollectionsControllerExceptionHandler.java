package red.biopersona.fingerservice.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import red.biopersona.fingerservice.controller.exception.model.ApiError;
import red.biopersona.fingerservice.controller.exception.model.DefaultError;
import red.biopersona.fingerservice.controller.exception.model.DefaultErrorList;
import red.biopersona.fingerservice.util.ErrorEnum;

@Slf4j
@ControllerAdvice
public class CollectionsControllerExceptionHandler {

	/**
	 * Manejo de excepciones de validacion de argumentos de entrada
	 * 
	 * @param pe Excepcion de tipo MethodArgumentNotValidException
	 * @return La entidad de respuesta que maneja el error como objeto
	 */
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<DefaultErrorList> handleValidationExceptionA(MethodArgumentNotValidException pe) {
		log.warn("Argmentos invalidos", pe.getMessage());
		FieldError err = pe.getBindingResult().getFieldError();
		if (err == null) {
			err = new FieldError("todo", "esta", "mal");
		}
		return new ResponseEntity<DefaultErrorList>(
				new DefaultErrorList(
						new DefaultError(ErrorEnum.EXC_ERROR_PARAMS, err.toString())),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * Manejo de excepciones de validacion de formatos de numeros de entrada
	 * 
	 * @param pe Excepcion de tipo NumberFormatException
	 * @return La entidad de respuesta que maneja el error como objeto
	 */
	@ExceptionHandler(value = { NumberFormatException.class })
	public ResponseEntity<DefaultErrorList> handleValidationExceptionB(NumberFormatException pe) {
		log.warn("Excepcion de formatos de numeros de entrada", pe.getMessage());
		return new ResponseEntity<DefaultErrorList>(
				new DefaultErrorList(new DefaultError(ErrorEnum.EXC_ERROR_PARAMS, pe.getLocalizedMessage())),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * Manejo de excepciones de validacion de tipo de datos de entrada
	 * 
	 * @param pe Excepcion de tipo MethodArgumentTypeMismatchException
	 * @return La entidad de respuesta que maneja el error como objeto
	 */
	@ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
	public ResponseEntity<DefaultErrorList> handleValidationExceptionC(MethodArgumentTypeMismatchException pe) {
		log.warn("Excepcion de tipo de datos de entrada", pe.getMessage());
		return new ResponseEntity<DefaultErrorList>(
				new DefaultErrorList(new DefaultError(ErrorEnum.EXC_ERROR_PARAMS, pe.getLocalizedMessage())),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * Manejo de excepciones de validacion de cantidad de parametros de entrada
	 * enviados
	 * 
	 * @param pe Excepcion de tipo MissingServletRequestParameterException
	 * @return La entidad de respuesta que maneja el error como objeto
	 */
	@ExceptionHandler(value = { MissingServletRequestParameterException.class })
	public ResponseEntity<DefaultErrorList> handleValidationExceptionD(MissingServletRequestParameterException pe) {
		log.warn("Excepcion de cantidad de parametros de entrada enviados", pe.getMessage());
		return new ResponseEntity<DefaultErrorList>(
				new DefaultErrorList(new DefaultError(ErrorEnum.EXC_ERROR_PARAMS, pe.getLocalizedMessage())),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		//LOGGER.error("Error en la ejecucion", ex);
		ApiError apiError = new ApiError(
		HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "error occurred");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}