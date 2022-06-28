package red.biopersona.faceservice.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseFaceQualityDTO implements Serializable {

    /** Variable para serializar la clase. */
    private static final long serialVersionUID = 1L;
    
    private int quality;
    private int sharpness;
    private int backgroundUniformity;
    private int grayScale;
    
    private byte[] FaceTemplate;
    private byte[] FaceToken;
}