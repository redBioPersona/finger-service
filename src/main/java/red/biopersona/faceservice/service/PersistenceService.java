package red.biopersona.faceservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import red.biopersona.faceservice.controller.exception.CollectionsServiceException;
import red.biopersona.faceservice.util.FileNameAwareByteArrayResource;

@Slf4j
@Service
public class PersistenceService implements IPersistenceService {

	@Value("${persistence-service.enroll}")
	String enrollEndPoint;
	
	@Value("${persistence-service.delete}")
	String deleteEndPoint;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public String deleteSample(String client, String sample) throws CollectionsServiceException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		LinkedMultiValueMap<String, Object> parametros = new LinkedMultiValueMap<>();
		parametros.add("client", client);
		parametros.add("sample", sample);

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(deleteEndPoint)
				.queryParam("client", client)
				.queryParam("sample", sample);
		log.info("URLbuilder -> direccion ->" + urlBuilder.build().encode().toString());

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parametros, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(urlBuilder.build().encode().toString(), HttpMethod.DELETE, requestEntity,
					String.class);
			log.info("llamando a persistence_eliminado de la muestra");
		} catch (HttpClientErrorException | HttpServerErrorException ex) { // cuando se recibe un mensje 5xx
			log.error("post:error:" + ex.toString());
			if (HttpStatus.valueOf(ex.getRawStatusCode()).is4xxClientError()) {
				throw new CollectionsServiceException(ex.getMessage());
			} else if (HttpStatus.valueOf(ex.getRawStatusCode()).is5xxServerError()) {
				throw new CollectionsServiceException("Error en el servidor" + ":estatus: " + ex.getRawStatusCode(),
						ex);
			} else {
				throw new CollectionsServiceException("Error genérico", ex);
			}

		} catch (RestClientException e) {
			throw new CollectionsServiceException("Error de conexión" + ":estatus: " + e.getMessage(), e);
		}

		if (response != null && response.getStatusCode().is2xxSuccessful()) {
			log.info("Respuesta del persistence-service exitoso");
			return response.getBody();
		} else {
			throw new CollectionsServiceException("Error en el mensaje de respuesta");
		}

	}

	@Override
	public String saveTemplate(String client, String biometricPerson, String segmentation, byte[] file)
			throws CollectionsServiceException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		LinkedMultiValueMap<String, Object> parametros = new LinkedMultiValueMap<>();
		parametros.add("file", new FileNameAwareByteArrayResource("abc.jpg", file, "des"));

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(enrollEndPoint).queryParam("client", client)
				.queryParam("biometricPerson", biometricPerson).queryParam("segmentation", segmentation);
		log.info("URLbuilder -> direccion ->" + urlBuilder.build().encode().toString());

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parametros, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(urlBuilder.build().encode().toString(), HttpMethod.POST, requestEntity,
					String.class);
			log.info("llamando al persistence");
		} catch (HttpClientErrorException | HttpServerErrorException ex) { // cuando se recibe un mensje 5xx
			log.error("post:error:" + ex.toString());
			if (HttpStatus.valueOf(ex.getRawStatusCode()).is4xxClientError()) {
				throw new CollectionsServiceException(ex.getMessage());
			} else if (HttpStatus.valueOf(ex.getRawStatusCode()).is5xxServerError()) {
				throw new CollectionsServiceException("Error en el servidor" + ":estatus: " + ex.getRawStatusCode(),
						ex);
			} else {
				throw new CollectionsServiceException("Error genérico", ex);
			}

		} catch (RestClientException e) {
			throw new CollectionsServiceException("Error de conexión" + ":estatus: " + e.getMessage(), e);
		}

		if (response != null && response.getStatusCode().is2xxSuccessful()) {
			log.info("Respuesta del persistence-service exitoso");
			return response.getBody();
		} else {
			throw new CollectionsServiceException("Error en el mensaje de respuesta");
		}

	}
}
