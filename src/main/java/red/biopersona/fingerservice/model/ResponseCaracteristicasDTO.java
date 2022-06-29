package red.biopersona.fingerservice.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ResponseCaracteristicasDTO  implements Serializable {

    /** Variable para serializar la clase. */
    private static final long serialVersionUID = 1L;
    
    private int personsFound;
    private List<CaracteristicasFacialesDTO> facialFeatures;
    private String quality;
    private String sharpness;
    private String backgroundUniformity;
    private String grayScale;
}