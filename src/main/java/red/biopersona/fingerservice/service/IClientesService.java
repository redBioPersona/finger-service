package red.biopersona.fingerservice.service;

import com.neurotec.biometrics.NSubject;
import com.neurotec.images.NImage;
import org.springframework.web.multipart.MultipartFile;
import red.biopersona.fingerservice.controller.exception.CollectionsServiceException;
import red.biopersona.fingerservice.model.*;

public interface IClientesService {
	
	ResponseCaracteristicasDTO getFeatures(NSubject subject, boolean showFeatures);
	
	ResponseFaceQualityDTO geQuality(NSubject subject, int personsFound, NImage face, boolean getToken, boolean getTemplate);
	
	public ResponsePuedeCrearTemplateDTO puedeCrearTemplate(MultipartFile file) throws CollectionsServiceException;
	
	ResponseEnrollFace enrollFace(RequestEnrollFaceDTO request) throws CollectionsServiceException;
	
	ResponseFeaturesFaceDTO getCaracteristicas(RequestFeaturesFaceDTO request) throws CollectionsServiceException;
	
	ResponseValidaFaceDTO validaFace(RequestValidaFaceDTO request) throws CollectionsServiceException;
	boolean deleteSample(String client,String sample) throws CollectionsServiceException;
}
