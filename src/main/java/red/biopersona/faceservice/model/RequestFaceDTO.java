package red.biopersona.faceservice.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestFaceDTO implements Serializable {

    /** Variable para serializar la clase. */
    private static final long serialVersionUID = 1L;
    
    private String operation;
    
    private boolean getFacialFeatures=false;
    
    private MultipartFile file;

}
