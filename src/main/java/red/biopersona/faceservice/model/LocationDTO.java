package red.biopersona.faceservice.model;

import java.io.Serializable;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDTO implements Serializable {

    /** Variable para serializar la clase. */
    private static final long serialVersionUID = 1L;
    
    private int x;
    private int y;
    private int width;
    private int height;
}
