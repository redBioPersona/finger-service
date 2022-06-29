package red.biopersona.fingerservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class RequestValidaFaceDTO  implements Serializable {

    /** Variable para serializar la clase. */
    private static final long serialVersionUID = 1L;

    private String client;
    
    private String segmentation;
    @NotNull
    private MultipartFile file;
     
    

}