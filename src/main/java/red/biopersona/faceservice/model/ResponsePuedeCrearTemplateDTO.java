package red.biopersona.faceservice.model;

import java.io.Serializable;

import com.neurotec.biometrics.NBiometricStatus;
import com.neurotec.biometrics.NSubject;
import com.neurotec.images.NImage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePuedeCrearTemplateDTO implements Serializable {

    /** Variable para serializar la clase. */
    private static final long serialVersionUID = 1L;
    
    private NBiometricStatus status;
    private NSubject subject;
    private NImage image;
    
	@Override
	public String toString() {
		return "status(" + this.status.name()+")";
	}
}