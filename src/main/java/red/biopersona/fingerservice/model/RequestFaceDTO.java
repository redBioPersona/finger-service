package red.biopersona.fingerservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
public class RequestFaceDTO implements Serializable {

    /** Variable para serializar la clase. */
    private static final long serialVersionUID = 1L;
    
    private String operation;
    
    private boolean getFacialFeatures=false;
    
    private MultipartFile file;

}
