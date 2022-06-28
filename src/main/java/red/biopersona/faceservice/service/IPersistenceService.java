package red.biopersona.faceservice.service;

import red.biopersona.faceservice.controller.exception.CollectionsServiceException;

public interface IPersistenceService {
	String saveTemplate(String client, String biometricPerson, String segmentation,byte[] file) throws CollectionsServiceException;
	String deleteSample(String client, String sample) throws CollectionsServiceException;
}
