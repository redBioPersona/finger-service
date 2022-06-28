package red.biopersona.faceservice.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseFaceDTO  implements Serializable {

    /** Variable para serializar la clase. */
    private static final long serialVersionUID = 1L;
    
    private int personsFound;
    private String statusTemplate;
    private List<CaracteristicasFacialesDTO> facialFeatures;
    private String quality;
    private String sharpness;
    private String backgroundUniformity;
    private String grayScale;
}