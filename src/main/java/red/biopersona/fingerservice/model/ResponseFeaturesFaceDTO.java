package red.biopersona.fingerservice.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ResponseFeaturesFaceDTO implements Serializable {

	/** Variable para serializar la clase. */
    private static final long serialVersionUID = 1L;
    
    private int personsFound;
    private String statusTemplate;
    private int quality;
    private int sharpness;
    private int backgroundUniformity;
    private int grayScale;
    private String message;
    private List<CaracteristicasFacialesDTO> facialFeatures;

}