package red.biopersona.fingerservice.service;

import com.neurotec.biometrics.*;
import com.neurotec.biometrics.client.NBiometricClient;
import com.neurotec.images.NImage;
import com.neurotec.images.NImageFormat;
import com.neurotec.licensing.NLicense;
import com.neurotec.licensing.NLicenseManager;
import com.neurotec.samples.util.LibraryManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import red.biopersona.fingerservice.controller.exception.CollectionsServiceException;
import red.biopersona.fingerservice.model.*;
import red.biopersona.fingerservice.util.ErrorEnum;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


@Slf4j
@Service
public class ClientesService implements IClientesService {

    @Autowired
    IPersistenceService persistence;

    private final String notDetected = "Not detected";
    private final String valueFalse = "false";
    private final String valueTrue = "true";

    boolean licenciasFacialesOK = false;
    final String licenses = "FingerExtractor,FingerClient,FingerMatcher";
    NBiometricClient biometricClient = null;

    public ClientesService(@Value("${finger.trialMode:true}") boolean trialMode,
                           @Value("${finger.serverAddress:/local}") String serverAddress,
                           @Value("${finger.serverPort:5000}") int serverPort,
                           @Value("${finger.routeSqlite}") String routeSqlite) {
        
        try {
            log.info("Trial mode: " + trialMode);
            log.info("Path db Sqlite: " + routeSqlite);
            log.info("License server: "+serverAddress + ":" + serverPort + " " + licenses);
          	LibraryManager.initLibraryPath();  
            NLicenseManager.setTrialMode(trialMode);
            
            if (!NLicense.obtain("/local", 5000, licenses)) {
                log.error("Could not obtain license: %s%n", licenses);
                System.exit(-1);
            } else {
                log.info("Licencias obtenidas");
               biometricClient = new NBiometricClient();

                NBiographicDataSchema NBDS = new NBiographicDataSchema();
                NBiographicDataSchema.ElementCollection NBDSEC = NBDS.getElements();
                NBDSEC.add(new NBiographicDataElement("Client", "Client", NDBType.STRING));
                NBDSEC.add(new NBiographicDataElement("Segmentation", "Segmentation", NDBType.STRING));
                NBDSEC.add(new NBiographicDataElement("IdBiometricPerson", "IdBiometricPerson", NDBType.STRING));
                biometricClient.setBiographicDataSchema(NBDS);
                biometricClient.setDatabaseConnectionToSQLite(routeSqlite);
                licenciasFacialesOK = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unlikely-arg-type")
    public ResponseCaracteristicasDTO getFeatures(NSubject subject, boolean showFeatures) {
        log.info("procesando caracteristicas faciales " + showFeatures);

        List<CaracteristicasFacialesDTO> caracteres = new ArrayList<>();
        ResponseCaracteristicasDTO response = new ResponseCaracteristicasDTO();
        int personasFoto = 0;

        for (NFace nface : subject.getFaces()) {
            for (NLAttributes attributes : nface.getObjects()) {
                personasFoto++;

                if (showFeatures == true) {
                    CaracteristicasFacialesDTO caracter = new CaracteristicasFacialesDTO();
                    LocationDTO location = new LocationDTO();
                    location.setX(attributes.getBoundingRect().getBounds().x);
                    location.setY(attributes.getBoundingRect().getBounds().y);
                    location.setWidth(attributes.getBoundingRect().width);
                    location.setHeight(attributes.getBoundingRect().height);
                    caracter.setFaceLocation(location);

                    if ((attributes.getRightEyeCenter().confidence > 0)
                            || (attributes.getLeftEyeCenter().confidence > 0)) {
                        if (attributes.getRightEyeCenter().confidence > 0) {
                            ConfidenceDTO confidencia = new ConfidenceDTO();
                            confidencia.setX(attributes.getRightEyeCenter().x);
                            confidencia.setY(attributes.getRightEyeCenter().y);
                            confidencia.setConfidence(attributes.getRightEyeCenter().confidence);
                            caracter.setRightEyeLocation(confidencia);
                        }

                        if (attributes.getLeftEyeCenter().confidence > 0) {
                            ConfidenceDTO confidencia = new ConfidenceDTO();
                            confidencia.setX(attributes.getLeftEyeCenter().x);
                            confidencia.setY(attributes.getLeftEyeCenter().y);
                            confidencia.setConfidence(attributes.getLeftEyeCenter().confidence);
                            caracter.setLeftEyeLocation(confidencia);
                        }
                    }

                    if (attributes.getNoseTip().confidence > 0) {
                        ConfidenceDTO confidencia = new ConfidenceDTO();
                        confidencia.setX(attributes.getNoseTip().x);
                        confidencia.setY(attributes.getNoseTip().y);
                        confidencia.setConfidence(attributes.getNoseTip().confidence);
                        caracter.setNoseLocation(confidencia);
                    }

                    if (attributes.getMouthCenter().confidence > 0) {
                        ConfidenceDTO confidencia = new ConfidenceDTO();
                        confidencia.setX(attributes.getMouthCenter().x);
                        confidencia.setY(attributes.getMouthCenter().y);
                        confidencia.setConfidence(attributes.getMouthCenter().confidence);
                        caracter.setMouthLocation(confidencia);
                    }

                    int LookingAway = attributes.getLookingAwayConfidence() & 0xFF;
                    if (attributes.getProperties().contains(NICAOWarning.LOOKING_AWAY)) {
                        if (LookingAway >= 0 && LookingAway <= 100) {
                            caracter.setLookingAway(valueTrue);
                        } else {
                            caracter.setLookingAway(notDetected);
                        }
                    } else {
                        caracter.setLookingAway(valueFalse);
                    }

                    int RedEye = attributes.getRedEyeConfidence() & 0xFF;
                    if (attributes.getProperties().contains(NICAOWarning.RED_EYE)) {
                        if (RedEye >= 0 && RedEye <= 100) {
                            caracter.setRedEye(valueTrue);
                        } else {
                            caracter.setRedEye(notDetected);
                        }
                    } else {
                        caracter.setRedEye(valueFalse);
                    }

                    int FACE_DARKNESS = attributes.getFaceDarknessConfidence() & 0xFF;
                    if (attributes.getProperties().contains(NICAOWarning.FACE_DARKNESS)) {
                        if (FACE_DARKNESS >= 0 && FACE_DARKNESS <= 100) {
                            caracter.setFaceDarkness(valueTrue);
                        } else {
                            caracter.setFaceDarkness(notDetected);
                        }
                    } else {
                        caracter.setFaceDarkness(valueFalse);
                    }

                    int PIXELATION = attributes.getPixelationConfidence() & 0xFF;
                    if (attributes.getProperties().contains(NICAOWarning.PIXELATION)) {
                        if (PIXELATION >= 0 && PIXELATION <= 100) {
                            caracter.setPixelation(valueTrue);
                        } else {
                            caracter.setPixelation(notDetected);
                        }
                    } else {
                        caracter.setPixelation(valueFalse);
                    }

                    if (attributes.getAge() == 254) {
                        caracter.setAge(notDetected);
                    } else {
                        caracter.setAge(String.valueOf(attributes.getAge()));
                    }

                    if (attributes.getGenderConfidence() == 255) {
                        caracter.setGender(notDetected);
                    } else {
                        caracter.setGender(attributes.getGender().toString());
                    }

                    if (attributes.getExpressionConfidence() == 255) {
                        caracter.setExpression(notDetected);
                    } else {
                        caracter.setExpression(attributes.getExpression().toString());
                    }

                    if (attributes.getBlinkConfidence() == 255) {
                        caracter.setBlink(notDetected);
                    } else {
                        caracter.setBlink(String.valueOf(attributes.getProperties().contains(NLProperty.BLINK)));
                    }

                    if (attributes.getMustacheConfidence() == 255) {
                        caracter.setMustache(notDetected);
                    } else {
                        caracter.setMustache(String.valueOf(attributes.getProperties().contains(NLProperty.MUSTACHE)));
                    }

                    if (attributes.getHatConfidence() == 255) {
                        caracter.setHat(notDetected);
                    } else {
                        caracter.setHat(String.valueOf(attributes.getProperties().contains(NLProperty.HAT)));
                    }

                    if (attributes.getMouthOpenConfidence() == 255) {
                        caracter.setMouthOpen(notDetected);
                    } else {
                        caracter.setMouthOpen(
                                String.valueOf(attributes.getProperties().contains(NLProperty.MOUTH_OPEN)));
                    }

                    if (attributes.getGlassesConfidence() == 255) {
                        caracter.setGlassess(notDetected);
                    } else {
                        caracter.setGlassess(String.valueOf(attributes.getProperties().contains(NLProperty.GLASSES)));
                    }

                    if (attributes.getDarkGlassesConfidence() == 255) {
                        caracter.setDarkGlasses(notDetected);
                    } else {
                        caracter.setDarkGlasses(
                                String.valueOf(attributes.getProperties().contains(NLProperty.DARK_GLASSES)));
                    }

                    if (attributes.getEthnicityAsianConfidence() == 255) {
                        caracter.setAsian(notDetected);
                    } else {
                        caracter.setAsian(String.valueOf(attributes.getEthnicityAsianConfidence()));
                    }

                    if (attributes.getEthnicityBlackConfidence() == 255) {
                        caracter.setBlack(notDetected);
                    } else {
                        caracter.setBlack(String.valueOf(attributes.getEthnicityBlackConfidence()));
                    }

                    if (attributes.getEthnicityHispanicConfidence() == 255) {
                        caracter.setHispanic(notDetected);
                    } else {
                        caracter.setHispanic(String.valueOf(attributes.getEthnicityHispanicConfidence()));
                    }

                    if (attributes.getEthnicityIndianConfidence() == 255) {
                        caracter.setIndian(notDetected);
                    } else {
                        caracter.setIndian(String.valueOf(attributes.getEthnicityIndianConfidence()));
                    }

                    if (attributes.getEthnicityWhiteConfidence() == 255) {
                        caracter.setWhite(notDetected);
                    } else {
                        caracter.setWhite(String.valueOf(attributes.getEthnicityWhiteConfidence()));
                    }

                    if (attributes.getEthnicityArabianConfidence() == 255) {
                        caracter.setArabian(notDetected);
                    } else {
                        caracter.setArabian(String.valueOf(attributes.getEthnicityArabianConfidence()));
                    }
                    caracteres.add(caracter);
                }
            }
        }
        response.setFacialFeatures(caracteres);
        response.setPersonsFound(personasFoto);
        return response;

    }

    public ResponseFaceQualityDTO geQuality(NSubject subject, int personsFound, NImage image, boolean getToken,
                                            boolean getTemplate) {
    	log.info("Validando la calidad de la muestra");
        ResponseFaceQualityDTO resp = new ResponseFaceQualityDTO();
        NFinger fingerToken = new NFinger();
        fingerToken.setImage(image);
        log.info("a1");
        NSubject subjecToken = new NSubject();
        subjecToken.setId("OB");
        subjecToken.getFingers().add(fingerToken);
        log.info("a2");
        NBiometricTask taskToken = biometricClient.createTask(
                EnumSet.of(NBiometricOperation.SEGMENT, NBiometricOperation.ASSESS_QUALITY), subjecToken);
        log.info("a3");
        biometricClient.performTask(taskToken);
        log.info("a4");
        String nfiq = ""+ subject.getFingers().get(0).getObjects().get(0).getNFIQQuality();
        int quality = subject.getFingers().get(0).getObjects().get(0).getQuality() & 0xFF;
        log.info("nfiq " + nfiq);
        log.info("quality" +quality);
        
        resp.setQuality(quality);

        log.info("Calidad " + resp.toString());
        if (taskToken.getStatus() == NBiometricStatus.OK) {
            /*byte[] FaceToken = null;
            byte[] FaceTemplate = null;

            if (getToken) {
                FaceToken = subjecToken.getFaces().get(1).getImage(true).save(NImageFormat.getJPEG()).toByteArray();
                resp.setFaceToken(FaceToken);
            }

            if (getTemplate) {
                FaceTemplate = subject.getTemplateBuffer().toByteArray();
                resp.setFaceTemplate(FaceTemplate);
            }*/
        }
        return resp;
    }

    public String evitaDuplicados(NSubject subject, RequestEnrollFaceDTO request) {
        String segmentation = request.getSegmentation();
        String client = request.getClient();
        ResponseValidaFaceDTO reso = FaceValidation(subject, client, segmentation);
        return reso.getPersonMatch();
    }

    public String guardaMuestra(RequestEnrollFaceDTO request, ResponseFaceQualityDTO calidad)
            throws CollectionsServiceException {
        String client = request.getClient();
        String biometricPerson = request.getBiometricPerson();
        String segmentation = request.getSegmentation();
        return persistence.saveTemplate(client, biometricPerson, segmentation, calidad.getFaceTemplate());
    }

    public ResponseValidaFaceDTO FaceValidation(NSubject subject, String Client, String Segmentation) {
        ResponseValidaFaceDTO resp = new ResponseValidaFaceDTO();
        if (Segmentation == null) {
            subject.setQueryString("Client='" + Client + "'");
        } else {
            subject.setQueryString("Client='" + Client + "' AND Segmentation='" + Segmentation + "'");
        }
        try {
            NBiometricTask enrollTaskDE = biometricClient.createTask(EnumSet.of(NBiometricOperation.IDENTIFY), subject);
            biometricClient.performTask(enrollTaskDE);
            log.info("enrollTaskDE " + enrollTaskDE.getStatus().name());
            if (enrollTaskDE.getStatus() == NBiometricStatus.OK) {
                ENCONTRADO:
                for (NMatchingResult result : subject.getMatchingResults()) {
                    String[] pFound = result.getId().split("_");
                    resp.setPersonMatch(pFound[1]);
                    resp.setPersonMatchScore(result.getScore());
                    break ENCONTRADO;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    public ResponseFeaturesFaceDTO getCaracteristicas(RequestFeaturesFaceDTO request) throws CollectionsServiceException {
        ResponseFeaturesFaceDTO respuesta = new ResponseFeaturesFaceDTO();
        ResponsePuedeCrearTemplateDTO estatusTemplate = puedeCrearTemplate(request.getFile());

        ResponseFaceQualityDTO calidad = geQuality(estatusTemplate.getSubject(), 1,
                estatusTemplate.getImage(), false, false);
        respuesta.setMessage(estatusTemplate.getStatus().name());
        respuesta.setStatusTemplate(estatusTemplate.getStatus().name());
        respuesta.setQuality(calidad.getQuality());
        respuesta.setSharpness(calidad.getSharpness());
        respuesta.setBackgroundUniformity(calidad.getBackgroundUniformity());
        respuesta.setGrayScale(calidad.getGrayScale());
        return respuesta;
    }


    public ResponseValidaFaceDTO validaFace(RequestValidaFaceDTO request) throws CollectionsServiceException {
        ResponseValidaFaceDTO respuesta = new ResponseValidaFaceDTO();
        ResponsePuedeCrearTemplateDTO estatusTemplate = puedeCrearTemplate(request.getFile());
        ResponseCaracteristicasDTO caracteristicas = getFeatures(estatusTemplate.getSubject(), false);
        ResponseFaceQualityDTO calidad = geQuality(estatusTemplate.getSubject(), caracteristicas.getPersonsFound(),
                estatusTemplate.getImage(), false, false);
        if (estatusTemplate.getStatus() == NBiometricStatus.OK) {
            if (caracteristicas.getPersonsFound() == 1) {
                respuesta = FaceValidation(estatusTemplate.getSubject(), request.getClient(),
                        request.getSegmentation());
            } else {
                respuesta.setMessage("many_persons_found");
            }
        }
        respuesta.setMessage(estatusTemplate.getStatus().name());
        respuesta.setStatusTemplate(estatusTemplate.getStatus().name());
        respuesta.setPersonsFound(caracteristicas.getPersonsFound());
        respuesta.setQuality(calidad.getQuality());
        respuesta.setSharpness(calidad.getSharpness());
        respuesta.setBackgroundUniformity(calidad.getBackgroundUniformity());
        respuesta.setGrayScale(calidad.getGrayScale());
        return respuesta;
    }

    public boolean deleteSample(String client, String sample) throws CollectionsServiceException {
        persistence.deleteSample(client, sample);
        biometricClient.delete(sample);

        return true;
    }


    public ResponseEnrollFace enrollFace(RequestEnrollFaceDTO request) throws CollectionsServiceException {
        ResponseEnrollFace respuesta = new ResponseEnrollFace();
        if (request.getFile() != null) {
            ResponsePuedeCrearTemplateDTO estatusTemplate = puedeCrearTemplate(request.getFile());
            log.info("estatusTemplate " + estatusTemplate.toString());
            ResponseCaracteristicasDTO caracteristicas = getFeatures(estatusTemplate.getSubject(), false);
            ResponseFaceQualityDTO calidad = geQuality(estatusTemplate.getSubject(), caracteristicas.getPersonsFound(),
                    estatusTemplate.getImage(), false, true);
            respuesta.setMessage(estatusTemplate.getStatus().name());
            if (estatusTemplate.getStatus() == NBiometricStatus.OK) {
                log.info("Ok la extración facial");
                if (caracteristicas.getPersonsFound() == 1) {
                    if (calidad.getQuality() < 80) {
                        respuesta.setMessage("poor_quality");
                    } else {
                        boolean continuarEnrolamiento = true;
                        if (request.isAvoidDuplicates()) {
                            String personMatch = evitaDuplicados(estatusTemplate.getSubject(), request);
                            if (personMatch != null) {
                                continuarEnrolamiento = false;
                                respuesta.setMessage("person_already_enroll");
                                respuesta.setPersonFound(personMatch);
                            }
                        }
                        if (continuarEnrolamiento) {
                            log.info("calling enroll service...");
                            String idTemplate = guardaMuestra(request, calidad);
                            estatusTemplate.getSubject().setProperty("Client", request.getClient());
                            if (request.getSegmentation() != null) {
                                estatusTemplate.getSubject().setProperty("Segmentation", request.getSegmentation());
                            }
                            estatusTemplate.getSubject().setProperty("IdBiometricPerson", request.getBiometricPerson());
                            String id = idTemplate + "_" + request.getBiometricPerson();
                            estatusTemplate.getSubject().setId(id);
                            biometricClient.enroll(estatusTemplate.getSubject());
                            respuesta.setMessage("OK");
                        }
                    }
                } else {
                    respuesta.setMessage("many_persons_found");
                }
            }
            respuesta.setStatusTemplate(estatusTemplate.getStatus().name());
            respuesta.setPersonsCountFound(caracteristicas.getPersonsFound());
            respuesta.setQuality(calidad.getQuality());
            respuesta.setSharpness(calidad.getSharpness());
            respuesta.setBackgroundUniformity(calidad.getBackgroundUniformity());
            respuesta.setGrayScale(calidad.getGrayScale());
        } else {
            respuesta.setMessage("FILE_NOT_FOUND");
        }

        return respuesta;
    }

    @SuppressWarnings({"deprecation", "resource"})
    public ResponsePuedeCrearTemplateDTO puedeCrearTemplate(MultipartFile file) throws CollectionsServiceException {
        NSubject subject = null;
        ResponsePuedeCrearTemplateDTO resp = new ResponsePuedeCrearTemplateDTO();
        try {
            subject = new NSubject();
            byte[] byteArr = file.getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(byteArr);
            NImage img = NImage.fromMemory(buffer);
            NFinger face = new NFinger();
            face.setImage(img);
            subject.getFingers().add(face);
            subject.setMultipleSubjects(true);
            biometricClient.setFingersCalculateNFIQ(true);
			biometricClient.setFingersCalculateNFIQ2(true);
            biometricClient.setFingersReturnBinarizedImage(true);
            biometricClient.setFingersQualityThreshold(Byte.parseByte("40"));
            biometricClient.setFingersCalculateNFIQ(true);
            biometricClient.setUseDeviceManager(true);
            biometricClient.setFingersTemplateSize(NTemplateSize.MEDIUM);
            biometricClient.setFingersFastExtraction(true);
            biometricClient.setFingersMatchingSpeed(NMatchingSpeed.HIGH);

            NBiometricStatus status=NBiometricStatus.BAD_OBJECT;
            try {
            	 status= biometricClient.createTemplate(subject);	
            } catch (Exception e) {}
            log.info("status crateTemplate " + status.name());
            resp.setSubject(subject);
            resp.setImage(img);
            resp.setStatus(status);
        } catch (IOException e) {
            log.error(e.getMessage().toString());
            e.printStackTrace();
            throw new CollectionsServiceException(ErrorEnum.EXC_ERROR_PARAMS);
        } finally {
            // subject.close();
        }
        return resp;

    }

}
