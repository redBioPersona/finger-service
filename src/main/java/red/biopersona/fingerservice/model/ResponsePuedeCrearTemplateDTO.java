package red.biopersona.fingerservice.model;

import com.neurotec.biometrics.NBiometricStatus;
import com.neurotec.biometrics.NSubject;
import com.neurotec.images.NImage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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