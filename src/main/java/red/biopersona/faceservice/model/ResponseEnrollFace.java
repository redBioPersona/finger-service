package red.biopersona.faceservice.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEnrollFace  implements Serializable {

    /** Variable para serializar la clase. */
    private static final long serialVersionUID = 1L;
    
    private int personsCountFound;
    private String personFound;
    private String statusTemplate;
    private int quality;
    private int sharpness;
    private int backgroundUniformity;
    private int grayScale;
    private String message;
}