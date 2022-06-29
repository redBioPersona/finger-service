package red.biopersona.fingerservice.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import red.biopersona.fingerservice.controller.exception.CollectionsServiceException;
import red.biopersona.fingerservice.model.*;
import red.biopersona.fingerservice.service.IClientesService;

import javax.validation.Valid;

@RestController
@RequestMapping("/finger")
@Slf4j
public class FingerController {
    @Autowired
    IClientesService clientesService;

    @ApiOperation(value = "Carga de archivo", notes = "En el header Location devuelve el recurso que fue registrado", response= ResponseEntity.class, httpMethod="POST")
    @PostMapping(value = "/enroll", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> enroll(@RequestHeader("client") String client, @Valid @ModelAttribute RequestEnrollFaceDTO request) throws CollectionsServiceException {
        log.info("Face enrolling..");
        request.setClient(client);
        HttpStatus code = HttpStatus.BAD_REQUEST;
        ResponseEnrollFace resul=clientesService.enrollFace(request);
        if(resul.getMessage().equals("OK")) {
            code=HttpStatus.OK;
        }
        return new ResponseEntity<>(resul, code);
    }

    @ApiOperation(value = "Carga de archivo", notes = "Elimina la muestra", response=ResponseEntity.class, httpMethod="DELETE")
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> enroll(
            @RequestHeader("client") String client,
            @RequestParam String sample) throws CollectionsServiceException {
        HttpStatus code = HttpStatus.BAD_REQUEST;
        boolean resul=clientesService.deleteSample(client, sample);
        if(resul) {
            code=HttpStatus.OK;
        }
        return new ResponseEntity<>(resul, code);
    }


    @ApiOperation(value = "Calidad de la muestra", notes = "Obtiene las caracteristicas faciales de la muestra", response=ResponseEntity.class, httpMethod="POST")
    @PostMapping(value = "/getFeatures", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> identify(@RequestHeader("client") String client,@Valid @ModelAttribute RequestFeaturesFaceDTO request) throws CollectionsServiceException {
        log.info("Face enrolling..");
        request.setClient(client);
        HttpStatus code = HttpStatus.BAD_REQUEST;
        ResponseFeaturesFaceDTO resp=clientesService.getCaracteristicas(request);
        if(resp.getMessage().equals("OK")) {
            code=HttpStatus.OK;
        }
        return new ResponseEntity<>(resp, code);
    }

    @ApiOperation(value = "Carga de archivo", notes = "Valida la muestra facial comparandola 1-n, 1-m", response=ResponseEntity.class, httpMethod="POST")
    @PostMapping(value = "/valida", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> valida(@RequestHeader("client") String client,@Valid @ModelAttribute RequestValidaFaceDTO request) throws CollectionsServiceException {
        request.setClient(client);
        HttpStatus code = HttpStatus.BAD_REQUEST;
        ResponseValidaFaceDTO resul=clientesService.validaFace(request);
        if(resul.getMessage().equals("OK")) {
            code=HttpStatus.OK;
        }
        return new ResponseEntity<>(resul, code);
    }
}
