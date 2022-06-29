package red.biopersona.fingerservice.controller.exception.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase para adminsitrar los errores generados
 */
@Data
public class ApiError implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private HttpStatus status;
    private String message;
    private List<String> errors;
    
    /**
     * Constructor con lista de errores
     */
    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = new ArrayList<String>(errors);
    }
    
    /**
     * Constructor con un solo mensaje
     */
    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}

