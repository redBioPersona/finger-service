package red.biopersona.faceservice.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestFeaturesFaceDTO  implements Serializable {

    /** Variable para serializar la clase. */
    private static final long serialVersionUID = 1L;

    private String client;
    
    @NotNull
    private MultipartFile file; 

}