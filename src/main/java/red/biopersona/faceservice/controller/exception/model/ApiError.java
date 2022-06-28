package red.biopersona.faceservice.controller.exception.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;
import org.springframework.http.HttpStatus;

import lombok.Data;

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

