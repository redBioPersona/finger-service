package red.biopersona.fingerservice.service;

import red.biopersona.fingerservice.controller.exception.CollectionsServiceException;

public interface IPersistenceService {
	String saveTemplate(String client, String biometricPerson, String segmentation,byte[] file) throws CollectionsServiceException;
	String deleteSample(String client, String sample) throws CollectionsServiceException;
}
