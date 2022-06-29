package red.biopersona.fingerservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class RequestEnrollFaceDTO implements Serializable {

	/** Variable para serializar la clase. */
	private static final long serialVersionUID = 1L;

	private String client;

	private String segmentation;

	@NotNull(message = "biometricPerson may not be null")
	@NotEmpty(message = "biometricPerson not empty")
    @Size(min = 6, max = 36)
	private String biometricPerson;

	@NotNull(message = "avoidDuplicates may not be null")
	private boolean avoidDuplicates;

	@NotNull(message = "File may not be null")
	private MultipartFile file;

	@Override
	public String toString() {
		return "client(" + this.client + ", biometricPerson" + this.biometricPerson + ", segmentation" + segmentation
				+ ")";
	}

}