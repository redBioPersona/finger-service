package red.biopersona.faceservice.model;

import java.io.Serializable;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaracteristicasFacialesDTO implements Serializable {

    /** Variable para serializar la clase. */
    private static final long serialVersionUID = 1L;
    
    private LocationDTO faceLocation;
    private ConfidenceDTO rightEyeLocation;
    private ConfidenceDTO leftEyeLocation;
    private ConfidenceDTO noseLocation;
    private ConfidenceDTO mouthLocation;
    private String lookingAway;
    private String redEye;
    private String faceDarkness;
    private String Pixelation;
    private String Age;
    private String Gender;
    private String Expression;
    private String Blink;
    private String Mustache;
    private String Hat;
    private String Glassess;
    private String mouthOpen;
    private String darkGlasses;
    
    private String asian;
    private String black;
    private String hispanic;
    private String indian;
    private String white;
    private String arabian;
    
}
